package com.example.sportivo;

import android.content.Context;
import android.util.Log;

import com.example.sportivo.objects_screen.Company;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ReservationDataStorage {

    public static int sportId;
    public static int companyId;
    private static int year, month, day;
    private static int hour, minute, second;
    private static int length = 1;
    public static String date;

    public static ArrayList<Reservation> reservations = new ArrayList<Reservation>();
    public static ArrayList<Court> courts = new ArrayList<Court>();
    public static List<List<TimeSlot>> availableReservations = new ArrayList<List<TimeSlot>>();
    public static String url="";
    public static String urlResByHour="";

    public static ArrayList<Company> companies = new ArrayList<Company>();

    public static int getYear(){ return ReservationDataStorage.year; }
    public static int getMonth() { return ReservationDataStorage.month;}
    public static int getDay() { return ReservationDataStorage.day; }
    public static int getHour() { return ReservationDataStorage.hour; }
    public static int getMinute() { return ReservationDataStorage.minute; }
    public static int getSecond() { return ReservationDataStorage.second; }
    public static int getLength() { return ReservationDataStorage.length; }

    public static String getDate(){
        return ReservationDataStorage.year + "-" + ReservationDataStorage.month + "-" + ReservationDataStorage.day;
    }

    public static void setDate(Context context){
        Calendar cal = Calendar.getInstance();

        cal.setTime(new Date());

        ReservationDataStorage.year = cal.get(Calendar.YEAR);
        ReservationDataStorage.month = cal.get(Calendar.MONTH) + 1;
        ReservationDataStorage.day = cal.get(Calendar.DAY_OF_MONTH);

        ReservationDataStorage.url = context.getString(R.string.baseURL) + context.getString(R.string.reservationsURL) + "get?companyId=" +
                ReservationDataStorage.companyId + "&date=" + ReservationDataStorage.year + "-" + ReservationDataStorage.month + "-" + ReservationDataStorage.day;
    }

    public static void setDate(Context context, int year, int month, int day){

        ReservationDataStorage.year = year;
        ReservationDataStorage.month = month + 1;
        ReservationDataStorage.day = day;

        Log.i("blabla", String.valueOf(ReservationDataStorage.month));

        ReservationDataStorage.url = context.getString(R.string.baseURL) + context.getString(R.string.reservationsURL) + "get?companyId=" +
                ReservationDataStorage.companyId + "&date=" + ReservationDataStorage.year + "-" + ReservationDataStorage.month + "-" + ReservationDataStorage.day;
    }

    public static void setTime(Context context, int hour, int minute, int second){
        ReservationDataStorage.hour = hour;
        ReservationDataStorage.minute = minute;
        ReservationDataStorage.second = second;

        ReservationDataStorage.urlResByHour = context.getString(R.string.baseURL) + context.getString(R.string.reservationsURL) + "getOnDateTime?date="
                + ReservationDataStorage.year + "-" + ReservationDataStorage.month + "-" + ReservationDataStorage.day + "T"
                + String.format("%02d", ReservationDataStorage.hour) + ":" + String.format("%02d", ReservationDataStorage.minute) + ":" + String.format("%02d", ReservationDataStorage.second);
    }
}
