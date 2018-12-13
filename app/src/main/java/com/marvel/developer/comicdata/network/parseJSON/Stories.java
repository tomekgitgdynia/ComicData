package com.marvel.developer.comicdata.network.parseJSON;

import java.util.ArrayList;
import java.util.List;

public class Stories {

    private Integer available;
    private String collectionURI;
    private List<ItemB> items = new ArrayList<ItemB>();
    private Integer returned;

    /**
     * No args constructor for use in serialization
     *
     */
    public Stories() {
    }

    /**
     *
     * @param items
     * @param collectionURI
     * @param available
     * @param returned
     */
    public Stories(Integer available, String collectionURI, List<ItemB> items, Integer returned) {
        super();
        this.available = available;
        this.collectionURI = collectionURI;
        this.items = items;
        this.returned = returned;
    }

    public Integer getAvailable() {
        return available;
    }

    public void setAvailable(Integer available) {
        this.available = available;
    }

    public String getCollectionURI() {
        return collectionURI;
    }

    public void setCollectionURI(String collectionURI) {
        this.collectionURI = collectionURI;
    }

    public List<ItemB> getItems() {
        return items;
    }

    public void setItems(List<ItemB> items) {
        this.items = items;
    }

    public Integer getReturned() {
        return returned;
    }

    public void setReturned(Integer returned) {
        this.returned = returned;
    }

}