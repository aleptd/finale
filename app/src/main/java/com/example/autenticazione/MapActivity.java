package com.example.autenticazione;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";
    private MapView mapView;
    GoogleMap gmap;


    LocationManager locationManager;
    LocationListener locationListener;

    EditText etIndirizzo;



    LatLng posizioneUtente;
    DownloadTask task;

    PolylineOptions plo;

    List<LatLng> polylinePoints = new LinkedList<LatLng>();

    Geocoder geocoder;




    private ImageButton ibBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        geocoder = new Geocoder(MapActivity.this, Locale.getDefault()); //per inizializzare geocoder

        initUI();

        createMapView(savedInstanceState);

        getLocation();

        verifyPermissions();

        viewLocationFromAddressInEditText();


    }

    public void initUI() {
        ibBack = findViewById(R.id.ibBack);
    }

    public void goBackToMainActivity(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    private void createMapView(Bundle savedInstanceState) {
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

    Marker[] markers = new Marker[1000];

    @Override
    //la nostra applicazione resta in attesa finchè non arriva una mappa
    public void onMapReady(GoogleMap googleMap) {

        /*

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference myRef = database.getReference().child("markerList");
        myRef.addValueEventListener(
                new ValueEventListener() {




                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Get map of users in datasnapshot

                        int i = 0;
                        //iterate through each user, ignoring their UID
                        for (Map.Entry<String, Object> entry :   ((Map<String, Object>) dataSnapshot.getValue()).entrySet()){
                            //if(type == "Utenti"){
                            //Get user map
                            //Get phone field and append to list
                            if(i!=0){
                                Map keyValueFinalObj = (Map) entry.getValue();

                                String category = (String)keyValueFinalObj.get("category");
                                String description = (String)keyValueFinalObj.get("description");
                                String id_marker = (String)keyValueFinalObj.get("id_marker");
                                long lat = (long) keyValueFinalObj.get("lat");
                                long longi = (long)keyValueFinalObj.get("longi");
                                String name = (String)keyValueFinalObj.get("name");

                                markers[i-1]= new Marker(id_marker,lat,longi,true,description,name);


                            }
                            i++;

                         }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
                    }
                });

        */

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
/*
        for(int k= 0; k< markers.length; k++){
            //si aggiunge un marker in versione semplificata
            gmap.addMarker(new MarkerOptions().position(new LatLng(markers[k].getLongi(), markers[k].getLat()))
                    .title("sei qui")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));



        }
*/

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

    public void getLocation() {
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
                /*LatLng destinazione = new LatLng(45.061892, 7.661223);
                calcolaPercorso(destinazione);*/

                if (polylinePoints != null && polylinePoints.size() >0){ //quindi abbiamo almeno un punto
                    polylinePoints.remove(0);
                    polylinePoints.add(0, posizioneUtente);
                    drawPolylines();
                }

                reverseGeocoding(posizioneUtente);


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

        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // se l'utente non ha fornito i permessi, glieli chiedo
                //il requestcode comunica quale richiesta è stata effettuata
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            } else
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 10, locationListener);
        } else
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

    public void displayUserPosition(LatLng posizioneUtente) {
        //gmap.clear();
        //da qui si possono specificare tutta una serie di info sul marker dell'utente aggiungendo un punto dopo .position
        gmap.addMarker(new MarkerOptions().position(posizioneUtente)
                .title("posto"));
        gmap.moveCamera(CameraUpdateFactory.newLatLng(posizioneUtente));

    }

    /*
    Calcolo percorso
     */

    public void calcolaPercorso(LatLng destinazione) {  //calcola il percorso dalla posizione dell'utente dove si trova in questo istante alla destinazione
        //per ripulire la lista di polylinePoints
        polylinePoints.clear();
        //stiamo aggiungendo la posizione dell'utente attuale--> il punto di partenza di tutto il percorso
         polylinePoints.add(posizioneUtente);  //è una lista

        task = new DownloadTask();
        //per eseguire il task
        //si passa l'url per il calcolo del percorso
        task.execute("https://maps.googleapis.com/maps/api/directions/json?origin=" +
                posizioneUtente.latitude + "," + posizioneUtente.longitude +
                "&destination=" +
                destinazione.latitude + "," + destinazione.longitude +
                "&key=AIzaSyD9xPAfQ78vrBHzNgBby4slgrdLTjh1k4M");

    }

    public void drawPolylines(){
        plo = new PolylineOptions();

        for(LatLng latLng : polylinePoints){
            plo.add(latLng);
            plo.color(Color.RED);
            plo.width(10); //per ingrandire lo spessore della riga
        }

        gmap.addPolyline(plo); // si aggiunge la polyline alla mappa
    }

  /*
    Reverse Geocoding
     */

    private void reverseGeocoding(LatLng coordinate){

        try{

            List<Address> addresses = geocoder.getFromLocation(coordinate.latitude, coordinate.longitude, 1);
            Log.i("indirizzo", addresses.toString());

            //bisogna estrarre un indirizzo specifico in formato stringa da questa lista di indirizzi

            String indirizzo = addresses.get(0).getAddressLine(0);
            Toast.makeText(this, "Ecco l'indirizzo in cui ti trovi "+indirizzo, Toast.LENGTH_SHORT).show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    //metodo che ci permette di leggere l'etichetta modificata

    private void viewLocationFromAddressInEditText(){
        etIndirizzo = findViewById(R.id.indirizzo);


        //stiamo aggiungendo un listener che verifica quando è modificata l'editText
        etIndirizzo.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER) || actionId == EditorInfo.IME_ACTION_DONE){
                    Log.i("mylog", "Enter premuto");

                    LatLng destinazione = geocoding(etIndirizzo.getText().toString());
                    Log.i("mylog", destinazione.toString());

                    gmap.clear();
                    gmap.addMarker(new MarkerOptions().position(destinazione).title("destinazione"));
                    gmap.moveCamera(CameraUpdateFactory.newLatLng(destinazione)); //per muovere la camera

                    calcolaPercorso(destinazione); //prende la posizione dell'utente e calcola il percorso fino a destinazione
                }

                return false;
            }
        });
    }
// Geocoding

    private LatLng geocoding(String indirizzo){

        List<Address> addresses ;
        LatLng destinazione; //ossia il nostro indirizzo

        try{
            addresses = geocoder.getFromLocationName(indirizzo, 5); //stiamo speicificando 5 risultati possibili

            if (addresses == null)  //se è null usciamo dal metodo --> non vi è nessuna coppia di LatLng
                return null;

            //se non è null ci facciamo restituire la prima posizione della prima lista di indirizzi (location)

            Address location = addresses.get(0);

            destinazione = new LatLng(location.getLatitude(), location.getLongitude());
            return destinazione;


        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }

 /*
    DownloadTask
     */

    private class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String result = "";
            URL url;
            HttpsURLConnection urlConnection;

            //quando creo il task gli passo un URL da contattare, al quale si vuole inviare la richiesta

            try {
                url = new URL(strings[0]); //questo metodo può generare una ricezione
                urlConnection = (HttpsURLConnection) url.openConnection(); //da qui si apre la connessione verso l'URL

                //per ricevere i dati dall'urlConnection
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);

                int data = reader.read();

                while (data != -1) { //ossia affinchè si hanno dei dati da leggere
                    char cur = (char) data; //si possono leggere i dati carattere per carattere
                    result += cur;
                    data = reader.read();
                }

                Log.i("mylog", result);
                return result; //se si è riusciti a leggere tutti i dati si ritorna result

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(String stringa) {
            super.onPostExecute(stringa);

            try {
                //si trasforma l'oggetto in stringa
                JSONObject jsonObject = new JSONObject(stringa);

                String routes = jsonObject.getString("routes");

                JSONArray arrayRoutes = new JSONArray(routes);
                //prendiamo la prima routes, immaginando sia proprio quella che ci interessi
                JSONObject primaRoute = arrayRoutes.getJSONObject(0);

                String legs = primaRoute.getString("legs");
                JSONArray arrayLegs = new JSONArray(legs);
                JSONObject primaLeg = arrayLegs.getJSONObject(0);

                String steps = primaLeg.getString("steps");
                JSONArray arraySteps = new JSONArray(steps);

                //all'interno del ciclo for ci prendiamo i jsonobject relativi alla posizione i-esima dell'arraysteps

                for (int i = 0; i<arraySteps.length(); i++){
                    JSONObject step = arraySteps.getJSONObject(i); //stiamo leggendo uno per uno i singoli elementi
                    String lat = step.getJSONObject("end_location").getString("lat");
                    String lon = step.getJSONObject("end_location").getString("lng");

                    Log.i("mylog", lat+" "+lon);
                   polylinePoints.add(new LatLng(Double.parseDouble(lat), Double.parseDouble(lon)));

                }


                drawPolylines();


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }



}

