package com.example.autenticazione;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;

public class RegisterActivityPartner extends AppCompatActivity {

    private ConstraintLayout registerPartnerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_partner);

        //Settaggio colore sfondo
        registerPartnerLayout = (ConstraintLayout) this.findViewById(R.id.registerPartnerLayout);
        registerPartnerLayout.setBackgroundColor(getResources().getColor(R.color.colorBackgroundPartner));


    }


}
