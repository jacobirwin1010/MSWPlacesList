package com.example.myapplication;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.VisibleRegion;
import com.google.gson.JsonObject;
import com.google.maps.android.SphericalUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private static final int PERMISSION_REQUEST_CODE = 1;
    private GoogleMap mMap;
    private String searchTerm = "surf"; //initialised to surf, can be changed
    private String searchType = "lodging";

    private void requestPermission(){
        ActivityCompat.requestPermissions(MapsActivity.this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                PERMISSION_REQUEST_CODE);

    }

    public void refreshMap(View view){
        LatLng mapCenter = mMap.getCameraPosition().target;
        LatLngBounds mapBounds = mMap.getProjection().getVisibleRegion().latLngBounds;
        LatLng northeast = mapBounds.northeast;
        LatLng southwest = mapBounds.southwest;

        //the radius is equal to half the map from corner to corner
        int radius = (int) Math.round(SphericalUtil.computeDistanceBetween(northeast, southwest)/2);

        ArrayList<PlaceOfInterest> returnedMapPoints = getLocalPlaces(mapCenter, radius, searchTerm);
        writeMarkersToMap(returnedMapPoints);



    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                   mMap.setMyLocationEnabled(true);
                } else {
                    mMap.setMyLocationEnabled(false);
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        requestPermission();
     //   mMap.setMyLocationEnabled(true);

        // Add a marker in Sydney and move the camera
        LatLng userLocation = new LatLng(50.41563, -5.07521);
        mMap.addMarker(new MarkerOptions().position(userLocation).title("Marker in user location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(userLocation));

        ArrayList<PlaceOfInterest> returnedMapPoints = getLocalPlaces(userLocation, 1500, "surf");
        writeMarkersToMap(returnedMapPoints);

    }
    //consumes an arraylist of placesOfInterest and writes them to the map
        public void writeMarkersToMap(ArrayList<PlaceOfInterest> placesOfInterest){
            if (placesOfInterest.size() > 0) {
                Iterator<PlaceOfInterest> iterator = placesOfInterest.iterator();

                while (iterator.hasNext()) {
                    PlaceOfInterest current = iterator.next();
                    MarkerOptions markerToWrite = new MarkerOptions()
                            .position(current.position)
                            .title(current.name)
                            .snippet("Rating: "+current.rating+"\n"+current.openingHours);


                    mMap.addMarker(markerToWrite);

                }
            }
        }

    //takes all of the search criteria and returns an arrayList of PlaceOfInterest
        public ArrayList<PlaceOfInterest> getLocalPlaces(LatLng userPosition, int radiusMeters, String nameSearchString){ //radius should be 1000 as a default
            String placesSearchStr = "https://maps.googleapis.com/maps/api/place/nearbysearch/" +
                    "json"+
                    "?location="+userPosition.latitude+","+userPosition.longitude+
                    "&radius="+radiusMeters+
                    "&type=lodging"+
                    "&keyword="+nameSearchString+
                    "&key=AIzaSyANmCydjCgDcZWuddfBMQ7qkkIApK1j_pk"; //CHANGE TO ACCEPT API KEY IN SEPERATE FILE

            JSONObject jsonResult = new JSONObject();
            String stringResult = null;
            ArrayList<PlaceOfInterest> listResults = new ArrayList<PlaceOfInterest>();

            try {
                stringResult = new PlaceFinder().execute(placesSearchStr).get();
                if (stringResult!=null){
                    jsonResult = new JSONObject(stringResult);

                }
            } catch(Exception e){
                Log.e("MapsActivity",e.getMessage());
            }

            if(jsonResult.length()>0){
                try {
                    JSONArray results = jsonResult.getJSONArray("results");

                    for (int i =0; i<results.length();i++){
                        listResults.add(new PlaceOfInterest(results.getJSONObject(i)));

                    }

                } catch (Exception e){
                    Log.e("MapsActivity",e.getMessage());
                }
            }




    return listResults;

}
}


