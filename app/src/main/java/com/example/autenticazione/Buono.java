package com.example.autenticazione;

import android.os.Parcel;
import android.os.Parcelable;

public class Buono {

    public int id_buono;
    public int id_partner;
    public String discount;
    public String code;
    public String expiringDate;

    public Buono () {
    }

    public Buono(String code, String discount, String expiringDate) {
        this.code = code;
        this.discount = discount;
        this.expiringDate = expiringDate;
    }

    public int getId_buono() {
        return id_buono;
    }

    public int getId_partner() {
        return id_partner;
    }

    public String getDiscount() {
        return discount;
    }

    public String getCode() {
        return code;
    }

    public String getExpiringDate() {
        return expiringDate;
    }
}
