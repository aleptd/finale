package com.example.autenticazione;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;

import static com.example.autenticazione.NotificationChannels2.CHANNEL_2_ID;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentMyAvailabilities extends Fragment {

    int idNotifica=0;



    public FragmentMyAvailabilities() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_my_availabilities, container, false);
        ListView listMyAvailabilites;
        listMyAvailabilites= (ListView)rootView.findViewById(R.id.listMyAvailabilities);
        Availability[] availabilities = new Availability[2];
        Availability availability = new Availability ("13:10","ES453TO", "Giulietta", "Metallizzato");
        Availability availability1 = new Availability("21:30", "Sa204ET", "Giulietta", "Metallizzato");
        availabilities[0]= availability;
        availabilities[1]= availability1;
        CustomAdapterAvailability customAdapter = new CustomAdapterAvailability(getActivity(), R.layout.row_layout_availabilities, availabilities);
        listMyAvailabilites.setAdapter(customAdapter);
        return rootView;



    }
/*
    public void inviaNotifica(String testo){

        //si fà un controllo sulla versione di andorid
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            //creo notifica con i channel
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_2_ID)
                    .setSmallIcon(R.drawable.ic_forum_black_24dp)
                    .setContentTitle("PMSC Firebase Database")
                    .setContentText("Aggiunti gli studenti" +testo)
                    .setStyle(new NotificationCompat.BigTextStyle()
                            .bigText(testo))

                    .setAutoCancel(true)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            notificationManager.notify(idNotifica,builder.build());
            idNotifica++;


        } else {
            //creo la notifica senza i channel


            //si può specificare che ad esempio se si clicca su una notifica venga aperta l'applicazione
            // -->cliccando sulla notifica essa ci rimanda sull'applicazione
            Intent intent = new Intent(this, Reservation.class);
            //impostiamo dei flag nell'intent per dire come vogliamo gestire il passaggio all'intent
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0 );

            // in questo modo si crea la notifica, si crea un oggetto con le info relative alla notifica:
            // icona,titolo, testo e priorità della notifica
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.ic_forum_black_24dp)
                    .setContentTitle("PMSC Firebase Database")
                    .setContentText("Aggiunti gli studenti" +testo)

                    //si modifica il builder per specificare che si raggiunga questo comportamento specifico
                    .setContentIntent(pendingIntent)

                    //si può far sì che la notifica venga cancellata quando l'utente clicca sulla notifica
                    .setAutoCancel(true)

                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

            // per far sì che la notifica venga generata ci si appoggia ad un NotificationManager
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            notificationManager.notify(idNotifica,builder.build());
            //si incrementa il valore dopo aver generato la notifica
            idNotifica++;


        }



}

 */
}