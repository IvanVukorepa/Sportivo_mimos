package com.example.sportivo.admin_screen;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.sportivo.R;
import com.example.sportivo.Singleton;
import com.example.sportivo.start_screen.Frag1_Sports;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AdminAddCourt extends AppCompatActivity {
    private ArrayList<Frag1_Sports> sports;
    Spinner sportsSpinner;
    Button create;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_addcourt);

        create = findViewById(R.id.admin_add_court_confirm_btn);
        sportsSpinner = findViewById(R.id.admin_add_court_sports_spinner);
        fillSpinner(getApplicationContext());

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = sportsSpinner.getSelectedItemPosition();
                
            }
        });
    }

    public void fillSpinner(final Context context){
        String url = context.getString(R.string.baseURL) + context.getString(R.string.sportsURL) + "getAll";

        JsonArrayRequest getSports = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Gson gson = new Gson();
                Type type = new TypeToken<List<Frag1_Sports>>(){}.getType();
                sports = gson.fromJson(response.toString(), type);
                ArrayList<String>sportsNames= new ArrayList<String>();
                for(Frag1_Sports sport : sports){
                    sportsNames.add(sport.getName());
                }
                sportsSpinner.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.admin_add_court_spinner_item, sportsNames));

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Error retrieving data from server", Toast.LENGTH_SHORT).show();
            }
        });

        Singleton.getInstance(context).addToRequestQueue(getSports);
    }
}
