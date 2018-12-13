package com.marvel.developer.comicdata.network.parseJSON;


public class Date {

    private String type;
    private String date;

    /**
     * No args constructor for use in serialization
     *
     */
    public Date() {
    }

    /**
     *
     * @param date
     * @param type
     */
    public Date(String type, String date) {
        super();
        this.type = type;
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}