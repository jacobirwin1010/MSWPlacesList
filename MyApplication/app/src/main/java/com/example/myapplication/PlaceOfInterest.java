package com.example.myapplication;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

public class PlaceOfInterest {
    String placeID;
    LatLng position;
    double rating;
    String name;
    String openingHours = "No opening hours provided";

    PlaceOfInterest(JSONObject inputObject) throws JSONException{
        JSONObject locationObject = inputObject.getJSONObject("geometry").getJSONObject("location");

        this.placeID=  inputObject.getString("place_id");
        this.position = new LatLng(Double.parseDouble(locationObject.getString("lat")), Double.parseDouble(locationObject.getString("lng")));
        this.rating = inputObject.getDouble("rating");
        this.name = inputObject.getString("name");
        try {
            JSONObject openingHoursObject = inputObject.getJSONObject("opening_hours");
            if(openingHoursObject.getBoolean("open_now")){
                this.openingHours = "Open now";
            }
            this.openingHours = openingHoursObject.getString("periods");
            Log.e("OPENING HOURS FOUND", this.openingHours);
            //this.openingHours = inputObject.getString("opening_hours");
        } catch(Exception e){
            //suppresses errors caused by opening hours not being supplied
        }
    }

    PlaceOfInterest(){
        this.position = new LatLng(50.4155, 5.0737);
        this.rating = 0;
        this.name = new String() ;
    }

    PlaceOfInterest(LatLng pos, int rat, String nam){
        this.position = pos;
        this.rating = rat;
        this.name = nam;
    }


}
