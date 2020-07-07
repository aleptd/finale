package com.example.autenticazione;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class MyCouponActivity extends AppCompatActivity {

    private static final int DELETE_MENU_OPTION = 1;
    private ConstraintLayout myCouponLayout;
    private ArrayList<Buono> couponArrayList;
    private CustomAdapterMyCoupon customAdapterMyCoupon;
    private ListView listMyCoupon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_coupon);

        //Settaggio sfondo

        myCouponLayout = (ConstraintLayout) this.findViewById(R.id.myCouponLayout);
        myCouponLayout.setBackgroundColor(getResources().getColor(R.color.colorBackground));

        //settaggio della listview di coupon scaricati dall'utente

        couponArrayList = new ArrayList<Buono>();
        listMyCoupon = (ListView) findViewById(R.id.listMyCoupon);
        customAdapterMyCoupon = new CustomAdapterMyCoupon(this, R.layout.row_layout_my_coupom, couponArrayList);
        listMyCoupon.setAdapter(customAdapterMyCoupon);
        Buono tmp= new Buono("5AAAAB", "20", "03/01/2020");
        couponArrayList.add(tmp);
        registerForContextMenu(listMyCoupon);
    }

    //menu di contesto che permette di modificare o eliminare elementi della lista

    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(contextMenu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_my_coupon, contextMenu);
        contextMenu.setHeaderTitle("Seleziona azione:");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item){
        if(item.getItemId() == R.id.deleteAction){
            return true;
        }
        else{
            return false;
        }
    }



    //Funzione per tornare alla home

    public void goBack(View v){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
