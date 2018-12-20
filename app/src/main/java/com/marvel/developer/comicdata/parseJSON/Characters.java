

package com.marvel.developer.comicdata.parseJSON;

import java.util.ArrayList;
import java.util.List;

public class Characters {

    private Integer available;
    private String collectionURI;
    private List<ItemA> items = new ArrayList<ItemA>();
    private Integer returned;

    /**
     * No args constructor for use in serialization
     *
     */
    public Characters() {
    }

    /**
     *
     * @param items
     * @param collectionURI
     * @param available
     * @param returned
     */
    public Characters(Integer available, String collectionURI, List<ItemA> items, Integer returned) {
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

    public List<ItemA> getItems() {
        return items;
    }

    public void setItems(List<ItemA> items) {
        this.items = items;
    }

    public Integer getReturned() {
        return returned;
    }

    public void setReturned(Integer returned) {
        this.returned = returned;
    }

}