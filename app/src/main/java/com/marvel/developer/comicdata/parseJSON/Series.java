package com.marvel.developer.comicdata.parseJSON;


public class Series {

    private String resourceURI;
    private String name;

    /**
     * No args constructor for use in serialization
     *
     */
    public Series() {
    }

    /**
     *
     * @param resourceURI
     * @param name
     */
    public Series(String resourceURI, String name) {
        super();
        this.resourceURI = resourceURI;
        this.name = name;
    }

    public String getResourceURI() {
        return resourceURI;
    }

    public void setResourceURI(String resourceURI) {
        this.resourceURI = resourceURI;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}