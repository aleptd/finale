package com.example.autenticazione;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivityPartner extends AppCompatActivity {

    private ConstraintLayout mainLayoutPartner;
    private FirebaseAuth mAuth;
    private ImageView ivLogoutPartner;
    private ImageView ivAddCoupon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_partner);
        ivLogoutPartner=(ImageView)findViewById(R.id.ivLogoutPartner);
        ivAddCoupon=(ImageView)findViewById(R.id.ivAddCoupon);
        mAuth = FirebaseAuth.getInstance();

        //settaggio sfondo

        mainLayoutPartner = (ConstraintLayout) this.findViewById(R.id.mainLayoutPartner);
        mainLayoutPartner.setBackgroundColor(getResources().getColor(R.color.colorBackgroundPartner));
    }

    //funzioni per raggiungere le funzionalità disponibili: aggiungere un coupon e gestire i coupon

    public void addNewCoupon(View v) {
        Intent intent = new Intent (this, AddCouponActivity.class);
        startActivity(intent);
    }

    public void logOut(View v) {
        mAuth.signOut();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
