package com.example.autenticazione;

import java.sql.Time;
import java.util.Date;

public class Reservation {

    private String id_transaction;
    private String longi;
    private String lat;
    private String username_entrant;
    private String username_incumbent;
    private Time endTime;
    private Time startTime;
    private Date date;
    private String note_incumbent;
    private String note_entrant;
    private boolean closingType;
    private int feedback_incumbent;
    private int feedback_entrant;
    private String licensePlate_entrant;
    private String model_entrant;
    private String color_entrant;
    private float rating_entrant;
    private float rating_incumbent;
    private String timeMeeting;
    //manca l'immagine utente entrant
    public Reservation(){}
    public Reservation(String timeMeeting, String username_entrant, String licensePlate_entrant, float rating_entrant, String model_entrant, String color_entrant) {
        this.timeMeeting = timeMeeting;
        this.username_entrant = username_entrant;
        this.rating_entrant = rating_entrant;
        this.model_entrant = model_entrant;
        this.color_entrant = color_entrant;
        this.licensePlate_entrant = licensePlate_entrant;
    }

    public String getId_transaction() {
        return id_transaction;
    }

    public String getLongi() {
        return longi;
    }

    public String getLat() {
        return lat;
    }

    public String getUsername_entrant() {
        return username_entrant;
    }

    public String getUsername_incumbent() {
        return username_incumbent;
    }

    public Time getEndTime() {
        return endTime;
    }

    public Time getStartTime() {
        return startTime;
    }

    public Date getDate() {
        return date;
    }

    public String getNote_incumbent() {
        return note_incumbent;
    }

    public String getNote_entrant() {
        return note_entrant;
    }

    public boolean isClosingType() {
        return closingType;
    }

    public int getFeedback_incumbent() {
        return feedback_incumbent;
    }

    public int getFeedback_entrant() {
        return feedback_entrant;
    }

    public String getLicensePlate_entrant() {
        return licensePlate_entrant;
    }

    public String getModel_entrant() {
        return model_entrant;
    }

    public String getColor_entrant() {
        return color_entrant;
    }

    public float getRating_entrant() {
        return rating_entrant;
    }

    public float getRating_incumbent() {
        return rating_incumbent;
    }

    public String getTimeMeeting() {
        return timeMeeting;
    }

    public void setId_transaction(String id_transaction) {
        this.id_transaction = id_transaction;
    }

    public void setLongi(String longi) {
        this.longi = longi;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public void setUsername_entrant(String username_entrant) {
        this.username_entrant = username_entrant;
    }

    public void setUsername_incumbent(String username_incumbent) {
        this.username_incumbent = username_incumbent;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setNote_incumbent(String note_incumbent) {
        this.note_incumbent = note_incumbent;
    }

    public void setNote_entrant(String note_entrant) {
        this.note_entrant = note_entrant;
    }

    public void setClosingType(boolean closingType) {
        this.closingType = closingType;
    }

    public void setFeedback_incumbent(int feedback_incumbent) {
        this.feedback_incumbent = feedback_incumbent;
    }

    public void setFeedback_entrant(int feedback_entrant) {
        this.feedback_entrant = feedback_entrant;
    }

    public void setLicensePlate_entrant(String licensePlate_entrant) {
        this.licensePlate_entrant = licensePlate_entrant;
    }

    public void setModel_entrant(String model_entrant) {
        this.model_entrant = model_entrant;
    }

    public void setColor_entrant(String color_entrant) {
        this.color_entrant = color_entrant;
    }

    public void setRating_entrant(float rating_entrant) {
        this.rating_entrant = rating_entrant;
    }

    public void setRating_incumbent(float rating_incumbent) {
        this.rating_incumbent = rating_incumbent;
    }

    public void setTimeMeeting(String timeMeeting) {
        this.timeMeeting = timeMeeting;
    }
}
