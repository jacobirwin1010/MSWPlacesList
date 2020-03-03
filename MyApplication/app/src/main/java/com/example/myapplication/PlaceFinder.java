package com.example.myapplication;

import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class PlaceFinder extends AsyncTask<String, Void, String>{

//    public void getLocalPlaces(LatLng userPosition, int radiusMeters){ //radius should be 1000 as a default
//        String placesSearchStr = "https://maps.googleapis.com/maps/api/place/nearbysearch/" +
//                "json?location="+50.4155+","+5.0737+
//                "&radius="+1000+"&sensor=true" +
//                "&types=hospital|health"+
//                "&key=AIzaSyANmCydjCgDcZWuddfBMQ7qkkIApK1j_pk"; //CHANGE TO ACCEPT API KEY IN SEPERATE FILE
////
////
//    }




    @Override
    protected String doInBackground(String... params) {
        String stringURL = params[0];
        String result="";
        String inputLine;

        try {
            //Create a URL object holding our url
            URL myUrl = new URL(stringURL);

            //Create a connection
            HttpURLConnection connection =(HttpURLConnection)
            myUrl.openConnection();

            connection.setRequestMethod("GET");
            connection.setReadTimeout(15000);
            connection.setConnectTimeout(15000);

            connection.connect();

            //Create a new InputStreamReader
            InputStreamReader streamReader = new InputStreamReader(connection.getInputStream());
            BufferedReader reader = new BufferedReader(streamReader);
            StringBuilder stringBuilder = new StringBuilder();

            while((inputLine = reader.readLine()) != null){
                stringBuilder.append(inputLine);
            }

            reader.close();
            streamReader.close();

            result = stringBuilder.toString();

        } catch(Exception e){
            Log.e("PlaceFinder",e.getMessage());
        }


        return result;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}
