package com.example.autenticazione;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivityPartner extends AppCompatActivity {

    private ConstraintLayout mainLayoutPartner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_partner);

        //settaggio sfondo

        mainLayoutPartner = (ConstraintLayout) this.findViewById(R.id.mainLayoutPartner);
        mainLayoutPartner.setBackgroundColor(getResources().getColor(R.color.colorBackgroundPartner));
    }

    //funzioni per raggiungere le funzionalità disponibili: aggiungere un coupon e gestire i coupon

    public void addNewCoupon(View v) {
        Intent intent = new Intent (this, AddCouponActivity.class);
        startActivity(intent);
    }

    public void manageCoupons(View v) {
        Intent intent = new Intent (this, ManageCouponActivity.class);
        startActivity(intent);
    }
}
