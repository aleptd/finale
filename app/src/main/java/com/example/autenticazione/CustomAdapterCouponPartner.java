package com.example.autenticazione;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.autenticazione.Buono;

import java.util.ArrayList;

public class CustomAdapterCouponPartner extends ArrayAdapter {

    Activity activity;
    int layout;
    ArrayList<Buono> buonoArrayList;

    public CustomAdapterCouponPartner(@NonNull Activity activity, int layout, @NonNull ArrayList<Buono> buonoArrayList) {
        super(activity, layout, buonoArrayList);

        this.activity=activity;
        this.layout=layout;
        this.buonoArrayList=buonoArrayList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        convertView = layoutInflater.inflate(layout, null);
        TextView tvCode = (TextView) convertView.findViewById(R.id.tvCode);
        TextView tvExpiringDate = (TextView) convertView.findViewById(R.id.tvExpiringDate);
        TextView tvDiscount = (TextView) convertView.findViewById(R.id.tvDiscount);

        tvCode.setText(buonoArrayList.get(position).getCode());
        tvDiscount.setText(buonoArrayList.get(position).getDiscount());
        tvExpiringDate.setText(buonoArrayList.get(position).getExpiringDate());
        return convertView;
    }
}

