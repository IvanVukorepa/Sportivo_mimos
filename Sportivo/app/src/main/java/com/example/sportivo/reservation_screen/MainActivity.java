package com.example.sportivo.reservation_screen;



import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.sportivo.Court;
import com.example.sportivo.R;
import com.example.sportivo.Reservation;
import com.example.sportivo.ReservationDataStorage;
import com.example.sportivo.Singleton;
import com.example.sportivo.TimeSlot;
import com.example.sportivo.TokenManager;
import com.example.sportivo.time_screen.Time;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    TextView reservationDate;
    ExpandableListView expandableListView;
    MyExpanableListViewAdapter expandableListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rs_activity_main);

        expandableListView=findViewById(R.id.expandableListView);

        reservationDate = (TextView) findViewById(R.id.reservationDate);
        setDate();

        reservationDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Time.class);
                startActivity(intent);
            }
        });

        expandableListAdapter = new MyExpanableListViewAdapter(getApplicationContext());

        expandableListView.setAdapter(expandableListAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.i("blabla", "token: " + TokenManager.getToken());

        Calendar cal = Calendar.getInstance();
        cal.set(ReservationDataStorage.year, ReservationDataStorage.month, ReservationDataStorage.day);
        
        reservationDate.setText(SimpleDateFormat.getDateInstance().format(cal.getTime()));

        JsonArrayRequest getReservations = new JsonArrayRequest(Request.Method.GET, ReservationDataStorage.url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.i("blabla", "response: " + response.toString());

                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
                Type type = new TypeToken<List<Reservation>>(){}.getType();
                ReservationDataStorage.reservations = gson.fromJson(response.toString(), type);

                updateSlots();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        Singleton.getInstance(getApplicationContext()).addToRequestQueue(getReservations);
    }

    private void setDate(){
        Calendar cal = Calendar.getInstance();

        cal.setTime(new Date());

        ReservationDataStorage.year = cal.get(Calendar.YEAR);
        //Months are counted form 0
        ReservationDataStorage.month = cal.get(Calendar.MONTH) + 1;
        ReservationDataStorage.day = cal.get(Calendar.DAY_OF_MONTH);

        ReservationDataStorage.url = getApplicationContext().getString(R.string.baseURL) + getApplicationContext().getString(R.string.reservationsURL) + "get?companyId=" +
                ReservationDataStorage.companyId + "&date=" + ReservationDataStorage.year + "-" + ReservationDataStorage.month + "-" + ReservationDataStorage.day;
    }

    public static void setDate(Context context, int year, int month, int day){

        ReservationDataStorage.year = year;
        ReservationDataStorage.month = month;
        ReservationDataStorage.day = day;

        ReservationDataStorage.url = context.getString(R.string.baseURL) + context.getString(R.string.reservationsURL) + "get?companyId=" +
                ReservationDataStorage.companyId + "&date=" + ReservationDataStorage.year + "-" + ReservationDataStorage.month + "-" + ReservationDataStorage.day;
    }

    private Map<String, ArrayList<TimeSlot>> getAvailableSlots(){

        ArrayList<TimeSlot> availableSlots = getAllSlots();
        ArrayList<TimeSlot> reservedSlots = getAllReserved();

        for ( TimeSlot slot : reservedSlots) {
            availableSlots.remove(new TimeSlot(slot.getStartsHour(), slot.getStartsMinutes(), slot.getEndsHour(), slot.getEndsMinutes(), slot.getCourtId(), 30));
        }

        Map<String, ArrayList<TimeSlot>> availableSlotsMap = new HashMap<>();

        for( TimeSlot ts : availableSlots){
            int courtId = ts.getCourtId();
            if (availableSlotsMap.get(String.valueOf(courtId)) == null) {
                availableSlotsMap.put(String.valueOf(courtId), new ArrayList<TimeSlot>());
            }
            availableSlotsMap.get(String.valueOf(courtId)).add(ts);
        }

        return availableSlotsMap;
    }

    private ArrayList<TimeSlot> getAllSlots(){
        ArrayList<TimeSlot> slots = new ArrayList<>();

        for (Court court : ReservationDataStorage.courts){
            for(int i=8; i<=23; i++){
                slots.add(new TimeSlot(i, 0, i+1, 0, court.getCourtId(), 30));
            }
        }

        return slots;
    }

    private ArrayList<TimeSlot> getAllReserved(){
        final ArrayList<TimeSlot> reserved = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();

        for (Reservation reservation : ReservationDataStorage.reservations){
            calendar.setTime(reservation.getStartTime());
            reserved.add(new TimeSlot(calendar.get(Calendar.HOUR_OF_DAY), 0, calendar.get(Calendar.HOUR_OF_DAY)+1, 0, reservation.getCourtId(), 30));
        }

        return reserved;
    }

    private void updateSlots(){

        String url = getApplicationContext().getString(R.string.baseURL) + getApplicationContext().getString(R.string.courtsURL) + "getCourts?companyId=" +
                ReservationDataStorage.companyId + "&sportId=" + ReservationDataStorage.sportId;

        final JsonArrayRequest getCourts = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                //Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
                Gson gson = new Gson();
                Type type = new TypeToken<List<Court>>() {}.getType();
                ReservationDataStorage.courts = gson.fromJson(response.toString(), type);
                Map<String, ArrayList<TimeSlot>> availableSlots = getAvailableSlots();

                if(!availableSlots.isEmpty()){
                    expandableListAdapter.notifyDataSetInvalidated();
                    ReservationDataStorage.availableReservations.clear();
                    for (Map.Entry<String, ArrayList<TimeSlot>> entry : availableSlots.entrySet()){
                        ReservationDataStorage.availableReservations.add(entry.getValue());
                    }
                    expandableListAdapter.notifyDataSetChanged();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        Singleton.getInstance(getApplicationContext()).addToRequestQueue(getCourts);
    }

    private String getCourtName(int courtId){
        for (Court court : ReservationDataStorage.courts){
            if(court.getCourtId() == courtId)
                return court.getCourtName();
        }

        return "Name";
    }

    public static void createReservation(final Context context, final int groupPosition, final int childPosition){

        String url = context.getString(R.string.baseURL) + context.getString(R.string.reservationsURL) + "create?start="
                + ReservationDataStorage.getDate() + "T" + ReservationDataStorage.availableReservations.get(groupPosition).get(childPosition).getStartTimeOfDayWithMinutes()
                + "&end=" + ReservationDataStorage.getDate() + "T" + ReservationDataStorage.availableReservations.get(groupPosition).get(childPosition).getEndTimeOfDayWithMinutes();

        StringRequest createReservation = new StringRequest(StringRequest.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.equals("false")){
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(context, "Reservation successfully created", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(context, com.example.sportivo.start_screen.MainActivity.class);
                    context.startActivity(intent);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("blabla", error.toString());
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  authParams = new HashMap<>();

                authParams.put("Authorization", "Bearer " + TokenManager.getToken());
                return authParams;
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                JSONObject reservation = new JSONObject();
                try{
                    reservation.put("StartTime", ReservationDataStorage.getDate() + "T" + ReservationDataStorage.availableReservations.get(groupPosition).get(childPosition).getStartTimeOfDayWithMinutes());
                    reservation.put("EndTime", ReservationDataStorage.getDate() + "T" + ReservationDataStorage.availableReservations.get(groupPosition).get(childPosition).getEndTimeOfDayWithMinutes());
                    reservation.put("CourtId", ReservationDataStorage.availableReservations.get(groupPosition).get(childPosition).getCourtId());
                } catch(JSONException e){ e.printStackTrace(); }

                return reservation.toString().getBytes();
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };

        Singleton.getInstance(context).addToRequestQueue(createReservation);
    }

}