package com.example.autenticazione;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class AddCouponActivity extends AppCompatActivity {

    private Button bConfirm;
    private Button bCodeGenerator;
    private TextView tvRandomCode;
    private ConstraintLayout addCouponLayout;
    private ListView couponList;
    private EditText etDiscount;
    private EditText etExpiringDate;
    private ArrayList<Buono> buonoArrayList;
    private CustomAdapterCouponPartner customAdapterCouponPartner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_coupon);

        //riferimenti agli elementi grafici

        bConfirm = (Button) findViewById(R.id.bConfirm);
        bCodeGenerator = (Button) findViewById(R.id.bCodeGenerator);
        tvRandomCode = (TextView) findViewById(R.id.tvRandomCode);
        etDiscount = findViewById(R.id.etDiscount);
        etExpiringDate = findViewById(R.id.etExpiringDate);
        couponList = findViewById(R.id.couponList);

        //settaggio colore di sfondo

        addCouponLayout = (ConstraintLayout) this.findViewById(R.id.addCouponLayout);
        addCouponLayout.setBackgroundColor(getResources().getColor(R.color.colorBackgroundPartner));

        //settaggio bottone che crea codice

        bCodeGenerator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvRandomCode.setText(getRandomString(10));
            }
        });

        buonoArrayList = new ArrayList<Buono>();
        customAdapterCouponPartner = new CustomAdapterCouponPartner(this, R.layout.row_layout_coupon_partner, buonoArrayList);
        couponList.setAdapter(customAdapterCouponPartner);
    }

    //funzione per creare nuovo elemento ed inserirlo

    public void addNewCoupon(View v) {
        String code = tvRandomCode.getText().toString();
        String expiringDate = etExpiringDate.getText().toString();
        String discount = etDiscount.getText().toString();
        Buono tmp = new Buono(code, discount, expiringDate);
        buonoArrayList.add(tmp);
        customAdapterCouponPartner.notifyDataSetChanged();
    }

    //funzione che crea random string di 10 caratteri

    public String getRandomString(int i) {
        final String characters = "abcdefghilmnopqrstuvwxyzjk0123456789ABCDEFGHILMNOPQRSTUVWXYZJK";
        StringBuilder result = new StringBuilder();
        while (i>0) {
            Random rand = new Random();
            result.append(characters.charAt(rand.nextInt(characters.length())));
            i--;
        }
        return result.toString();
    }

    //funzione per tornare al menu principale

    public void getBack(View v) {
        Intent intent = new Intent(this, MainActivityPartner.class);
        startActivity(intent);
    }
}
