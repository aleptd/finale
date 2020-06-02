package com.example.autenticazione;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentMyRequests extends Fragment {

    public FragmentMyRequests() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_my_requests2, container, false);
        ListView listMyRequests;
        listMyRequests= (ListView)rootView.findViewById(R.id.listMyRequests);
        Request[] requests = new Request[2];
        //riempire requests con tutte le prenotazioni dove user=entrante
        //

        Request request = new Request ("13:10","Sara",
                "CA999PE", "Panda", "Verde",
                2, true);
        Request request1 = new Request("23:15", "Marco", "PI210ER",
                "Seat Ibiza", "Blu", 1, false);
        requests[0]= request;
        requests[1]= request1;
        CustomAdapterRequest customAdapter = new CustomAdapterRequest(getActivity(), R.layout.row_layout_request, requests);
        listMyRequests.setAdapter(customAdapter);
        return rootView;
    }
}
