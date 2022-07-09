package com.example.abc;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.Looper;
import android.os.Messenger;
import android.widget.TextView;
import android.os.Build;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;

public class searchActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    static ArrayList<LatLng> pointsForPolygon = new ArrayList<>(MapsActivity.pointsForPolygon); //лист точек для создания области
    public static String searchName;
    public static int searchID = 111111;
    boolean isStopped = false;

    FusedLocationProviderClient fusedLocationClient;
    LocationRequest locationRequest;
    LocationCallback locationCallback;

    @SuppressLint("MissingPermission")
    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationClient.requestLocationUpdates(locationRequest,
                locationCallback,
                Looper.getMainLooper());
    }


    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.searchMap);
        mapFragment.getMapAsync(this);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);


        locationRequest = LocationRequest.create();
        locationRequest.setInterval(3000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setMaxWaitTime(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                Location location =  locationResult.getLastLocation();
                Toast.makeText(getApplicationContext(),
                        "Lat: "+Double.toString(location.getLatitude()) + '\n' +
                                "Long: " + Double.toString(location.getLongitude()), Toast.LENGTH_LONG).show();
                double lat = location.getLatitude();
                double lon = location.getLongitude();
                LatLng currentloc = new LatLng(lat, lon);
                CircleOptions circleOptions = new CircleOptions()
                        .center(currentloc)
                        .radius(10)
                        .fillColor(Color.RED);
                Circle circle = mMap.addCircle(circleOptions);
                //locationArrayList.add(new LatLng(location.getLatitude(), location.getLongitude()));

               /* for (Location location : locationResult.getLocations()) {
                  location
                }*/
            }
        };
        startLocationUpdates();

    }


    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        setTitle("Поиск");
        TextView idAndName = findViewById(R.id.idAndNameText);
        idAndName.setText(String.format("ID: %d", searchID));
        LatLng[] currentPolygon = new LatLng[pointsForPolygon.size()];
        currentPolygon = pointsForPolygon.toArray(currentPolygon);
        PolygonOptions polygonOptions = new PolygonOptions()
                .add(currentPolygon);
        Polygon polygon = mMap.addPolygon(polygonOptions);
        Toast youjoined = Toast.makeText(getApplicationContext(),
                "you joined!", Toast.LENGTH_LONG);
        youjoined.show();
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng point) {
                if(!isStopped){
                    isStopped = true;
                    //TODO здесь должны посылать запрос, что мы нашли человека

                    Toast found = Toast.makeText(getApplicationContext(),
                            "Sharing your coordinates with others...", Toast.LENGTH_SHORT);
                    found.show();
                }
                else {
                    Toast end = Toast.makeText(getApplicationContext(),
                            "Ending search...", Toast.LENGTH_SHORT);
                    end.show();
                    //TODO здесь должны завершать поиск
                }
            }
        });
    }

}