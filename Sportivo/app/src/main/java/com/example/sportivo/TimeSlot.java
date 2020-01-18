package com.example.sportivo;

import java.util.Date;

public class TimeSlot {

    //public Date starts;
    //public Date ends;

    private int startsHour, endsHour, startsMinutes, endsMinutes, courtId, price;

    public TimeSlot(int start,  int startMin, int end, int endMin, int courtId, int price){
        startsHour = start;
        endsHour = end;
        startsMinutes = startMin;
        endsMinutes = endMin;
        this.courtId = courtId;
        this.price = price;
    }

    public int getCourtId() {
        return courtId;
    }

    public int getStartsHour() {
        return startsHour;
    }

    public int getEndsHour() {
        return endsHour;
    }

    public int getStartsMinutes() {
        return startsMinutes;
    }

    public int getEndsMinutes() {
        return endsMinutes;
    }

    public String getTimeOfDay(){

        return String.valueOf(startsHour) + ":" + String.valueOf(startsMinutes);
    }

    public String getStartTimeOfDayWithMinutes(){

        return String.format("%02d", startsHour) + ":" + String.format("%02d", startsMinutes) + ":00";
    }

    public String getEndTimeOfDayWithMinutes(){

        return String.format("%02d", endsHour) + ":" + String.format("%02d", endsMinutes) + ":00";
    }

    public String getPrice(){
        return this.price + "kn";
    }

    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + startsMinutes;
        result = prime * result + startsHour;
        result = prime * result + endsMinutes;
        result = prime * result + endsHour;
        result = prime * result + courtId;
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof TimeSlot)) {
            return false;
        }
        TimeSlot other = (TimeSlot) obj;
        if (startsHour != other.startsHour) {
            return false;
        }
        if (startsMinutes != other.startsMinutes) {
            return false;
        }
        if (courtId != other.courtId) {
            return false;
        }
        return true;
    }
}