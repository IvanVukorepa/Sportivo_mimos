package com.example.sportivo;

import com.example.sportivo.reservation_screen.Item;

import java.util.ArrayList;
import java.util.List;

public class ReservationDataStorage {

    public static int sportId;
    public static int companyId;
    public static int year, month, day;
    public static String date;

    public static ArrayList<Reservation> reservations = new ArrayList<Reservation>();
    public static ArrayList<Court> courts = new ArrayList<Court>();
    public static List<List<TimeSlot>> availableReservations = new ArrayList<List<TimeSlot>>();
    //public static ArrayList<Reservation> availableSlots = new ArrayList<Reservation>();
    public static String url="";


    public static String getDate(){
        return ReservationDataStorage.year + "-" + ReservationDataStorage.month + "-" + ReservationDataStorage.day;
    }
}
