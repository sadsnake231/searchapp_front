package com.example.abc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

import java.util.ArrayList;

//активити с картой для создания области поиска
public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener {
    GoogleMap mMap;
    public static String searchName;
    public static int searchID;
    ImageButton buttonSet, buttonDelete;
    static ArrayList<LatLng> pointsForPolygon = new ArrayList<>(); //лист точек для создания области
    int counter = -1;
    Marker lastMarker = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        buttonSet = (ImageButton) findViewById(R.id.setSearchButton);
        buttonDelete = (ImageButton) findViewById(R.id.deleteButton);
        buttonSet.setOnClickListener(this);
        buttonDelete.setOnClickListener(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * <p>
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        setTitle("Создание");
        //длинные нажатия - создаем маркеры, короткое - соединяем их в многоугольник
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng point) {
                lastMarker = mMap.addMarker(new MarkerOptions()
                        .position(point));
                        counter += 1;
                        pointsForPolygon.add(point);
            }
        });
    }
    @Override
    public void onClick(View view){
        switch (view.getId()) {
            case R.id.deleteButton:
                if(!(lastMarker == null)) {
                    pointsForPolygon.remove(counter);
                    counter -= 1;
                    lastMarker.remove();
                    lastMarker = null;
                }
                else{
                    Toast nothing = Toast.makeText(getApplicationContext(),
                            "Nothing to delete!"    , Toast.LENGTH_SHORT);
                    nothing.show();
                }
                break;
            case R.id.setSearchButton:
                if (pointsForPolygon.size() > 2) {
                    LatLng[] currentPolygon = new LatLng[pointsForPolygon.size()];
                    currentPolygon = pointsForPolygon.toArray(currentPolygon);
                    PolygonOptions polygonOptions = new PolygonOptions()
                            .add(currentPolygon);
                    Polygon polygon = mMap.addPolygon(polygonOptions);
                    searchActivity.searchName = searchName;
                    Intent goToSearch = new Intent(this, searchActivity.class);
                    startActivity(goToSearch);
                    finish();
                }
                else {
                Toast fewpoints = Toast.makeText(getApplicationContext(),
                        "Too few points! 3 points are needed to draw a search area", Toast.LENGTH_LONG
                );
                fewpoints.show();
                }
                break;
        }
    }
}

