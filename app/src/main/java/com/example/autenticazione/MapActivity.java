package com.example.autenticazione;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";
    private MapView mapView;
    GoogleMap gmap;


    LocationManager locationManager;
    LocationListener locationListener;


    LatLng posizioneUtente;



    private ImageButton ibBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        initUI();

        createMapView(savedInstanceState);

        getLocation();

        verifyPermissions();


    }

    public void initUI(){
        ibBack=findViewById(R.id.ibBack);
    }

    public void goBackToMainActivity (View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    private void createMapView(Bundle savedInstanceState){
        Bundle mapViewBundle = null;
        if (savedInstanceState != null)
            //se savedInstanceState è diverso da null si valorizza mapViewBundle
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);

        //stiamo accedendo alla mapView che abbiamo nel nostro layout
        mapView = findViewById(R.id.mapView);
        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);

    }


    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();

    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    //la nostra applicazione resta in attesa finchè non arriva una mappa
    public void onMapReady(GoogleMap googleMap) {
        gmap = googleMap;

        //per visualizzare il traffico nella mappa
        gmap.setTrafficEnabled(true);

        //per modificare il tipo di mappa visibile
        gmap.setMapType(GoogleMap.MAP_TYPE_NORMAL);


        //imosta mappe indoor
        gmap.setIndoorEnabled(true);

        //per modificare i setting della mappa
        UiSettings uiSettings = gmap.getUiSettings();
        //su uiSettings si impostano alcuni metodi che cambiano la nostra mappa
        //ad esempio per cambiare i livelli di zoom
        uiSettings.setZoomControlsEnabled(true);
        //si può impostare la bussola
        uiSettings.setCompassEnabled(true);
        //permette di visualizzare diversi livelli indoor
        uiSettings.setIndoorLevelPickerEnabled(true);

        //mostra la maptoolbar che permette di navigare verso una determinata applicazione
        uiSettings.setMyLocationButtonEnabled(true);
        uiSettings.setMapToolbarEnabled(true);


        //si può impostare il livello di zoom con Preference
        //gmap.setMinZoomPreference(12);

        // 45 4 7 sono grosso modo le coordinate di Torino
        LatLng city = new LatLng(45.071305, 7.685112);

        //si aggiunge un marker in versione semplificata
        gmap.addMarker(new MarkerOptions().position(city)
        .title("sei qui")
        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));



        // si muove la camera su il punto LatLng definito
        //senza zoom
     //   gmap.moveCamera(CameraUpdateFactory.newLatLng(city));

        //con zoom
        //si imposta il livello di zoom manualmente
        gmap.moveCamera(CameraUpdateFactory.newLatLngZoom(city, 15));

    }

 /*
    LOCATION
     */

    public void getLocation(){
        // creo un nuovo Location Manager
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        // creo un nuovo Location Listener
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                // la posizione dell'utente è cambiata
                // Log.i("mylog", location.toString());

                // in alternativa, posso fare il log così
                Log.i("mylog", location.getLatitude() + ", " + location.getLongitude());



                posizioneUtente = new LatLng(location.getLatitude(), location.getLongitude());
                displayUserPosition(posizioneUtente);

                // vecchio, utilizzato solo per costruire la soluzione

                //LatLng destinazione = new LatLng(45.061892, 7.661223);
                //calcolaPercorso(destinazione);
/*
                if (polylinePoints != null && polylinePoints.size() >0){
                    polylinePoints.remove(0);
                    polylinePoints.add(0, posizioneUtente);
                    drawPolylines();
                }

                reverseGeocoding(posizioneUtente);
                */


            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };


    }

    //metodo che si occupa di gestire i permessi
    public void verifyPermissions() {
        // controllo se i permessi del Manifest sono di tipo ACCESS_FINE_LOCATION
        // e se non ce li fornisce

        if (Build.VERSION.SDK_INT >= 23){
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // se l'utente non ha fornito i permessi, glieli chiedo
                //il requestcode comunica quale richiesta è stata effettuata
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }else
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 10, locationListener);
        }else
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 10, locationListener);

    }

    //per capire cosa fare quando l'utente richiede i permessi


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // effettua operazioni quando l'utente fornisce i permessi

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // l'utente ha concesso i permessi


            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 10, locationListener);
                //qui viene richiesta la posizione dell'utente solo nel momento in cui concede i permessi
            }
        }
    }

    public void displayUserPosition(LatLng posizioneUtente){
        gmap.clear();
        gmap.addMarker(new MarkerOptions().position(posizioneUtente));
        gmap.moveCamera(CameraUpdateFactory.newLatLng(posizioneUtente));
    }



}
