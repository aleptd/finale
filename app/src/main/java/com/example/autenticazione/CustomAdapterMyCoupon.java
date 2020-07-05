package com.example.autenticazione;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CustomAdapterMyCoupon extends ArrayAdapter {

    Activity activity;
    int layout;
    ArrayList<Buono> couponArrayList;


    public CustomAdapterMyCoupon(@NonNull Activity activity, int layout, @NonNull ArrayList<Buono> couponArrayList) {
        super(activity, layout, couponArrayList);

        this.activity=activity;
        this.layout=layout;
        this.couponArrayList=couponArrayList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        convertView = layoutInflater.inflate(layout, null);
        TextView tvCode = (TextView) convertView.findViewById(R.id.tvCodeMyCoupon);
        TextView tvExpiringDate = (TextView) convertView.findViewById(R.id.tvExpiringDateMyCoupon);
        TextView tvDiscount = (TextView) convertView.findViewById(R.id.tvDiscountMyCoupon);

        tvCode.setText(couponArrayList.get(position).getCode());
        tvDiscount.setText(couponArrayList.get(position).getDiscount());
        tvExpiringDate.setText(couponArrayList.get(position).getExpiringDate());
        return convertView;
    }
}
