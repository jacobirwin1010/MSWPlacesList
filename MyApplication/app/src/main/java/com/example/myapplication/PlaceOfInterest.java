package com.example.myapplication;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;
import android.util.Log;
import android.view.View;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

public class PlaceOfInterest {
    String placeID;
    LatLng position;
    double rating = 0;
    String name;
    String openingHours = "No opening hours provided";

    PlaceOfInterest(JSONObject inputObject) throws JSONException{
        JSONObject locationObject = inputObject.getJSONObject("geometry").getJSONObject("location");

        this.placeID=  inputObject.getString("place_id");
        this.position = new LatLng(Double.parseDouble(locationObject.getString("lat")), Double.parseDouble(locationObject.getString("lng")));
        this.name = inputObject.getString("name");

        //CODE TO RETURN ALL OF THE PLACES OPENING TIMES, TAKES TOO LONG TO GET SO IT HAS BEEN OMITTED
////String openingHoursLookupUrl = "https://maps.googleapis.com/maps/api/place/details/json?place_id="+this.placeID+"&fields=opening_hours&key="+ apiKey;
//
//String openingHoursResult;
//try {
//    openingHoursResult = new PlaceFinder().execute(openingHoursLookupUrl).get();
//}
//catch(Exception e)
//{
//    Log.e("openingHoursLookup", e.getMessage());
//}

        try {
            this.rating = inputObject.getDouble("rating");

            JSONObject openingHoursObject = inputObject.getJSONObject("opening_hours");
            if(openingHoursObject.getBoolean("open_now")){
                this.openingHours = "Open now";
            }
            this.openingHours = openingHoursObject.getString("periods");
            Log.e("OPENING HOURS FOUND", this.openingHours);
            //this.openingHours = inputObject.getString("opening_hours");
        } catch(Exception e){
            //suppresses errors caused by opening hours not being supplied
            Log.d("PlaceOfIInterest","error");
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
