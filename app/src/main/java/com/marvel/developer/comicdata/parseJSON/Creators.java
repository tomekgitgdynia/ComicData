package com.marvel.developer.comicdata.parseJSON;

import java.util.ArrayList;
import java.util.List;

public class Creators {

    private Integer available;
    private String collectionURI;
    private List<Item> items = new ArrayList<Item>();
    private Integer returned;

    /**
     * No args constructor for use in serialization
     *
     */
    public Creators() {
    }

    /**
     *
     * @param items
     * @param collectionURI
     * @param available
     * @param returned
     */
    public Creators(Integer available, String collectionURI, List<Item> items, Integer returned) {
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

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Integer getReturned() {
        return returned;
    }

    public void setReturned(Integer returned) {
        this.returned = returned;
    }

}