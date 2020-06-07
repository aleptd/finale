package com.example.autenticazione;

public class Marker {

    private String id_marker;

    private long longi;
    private long lat;
    private boolean category;
    private String name;
    private String description;

    public Marker(String id_marker, long longi, long lat, boolean category, String name, String description) {
        this.id_marker = id_marker;
        this.longi = longi;
        this.lat = lat;
        this.category = category;
        this.name = name;
        this.description = description;
    }

    public Marker(String id_marker) {
        this.id_marker = id_marker;
    }
    public Marker( ) {
     }

    public void setId_marker(String id_marker) {
        this.id_marker = id_marker;
    }

    public void setLongi(long longi) {
        this.longi = longi;
    }

    public void setLat(long lat) {
        this.lat = lat;
    }

    public void setCategory(boolean category) {
        this.category = category;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId_marker() {
        return id_marker;
    }

    public long getLongi() {
        return longi;
    }

    public long getLat() {
        return lat;
    }

    public boolean isCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
