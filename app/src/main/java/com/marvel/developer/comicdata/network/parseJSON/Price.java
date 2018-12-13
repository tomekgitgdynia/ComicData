package com.marvel.developer.comicdata.network.parseJSON;


public class Price {

    private String type;
    private Float price;

    /**
     * No args constructor for use in serialization
     *
     */
    public Price() {
    }

    /**
     *
     * @param price
     * @param type
     */
    public Price(String type, Float price) {
        super();
        this.type = type;
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

}