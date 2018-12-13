package com.marvel.developer.comicdata.network.parseJSON;

// Items for class Characters
public class ItemA {

    private String resourceURI;
    private String name;

    /**
     * No args constructor for use in serialization
     *
     */
    public ItemA() {
    }

    /**
     *
     * @param resourceURI
     * @param name
     */
    public ItemA(String resourceURI, String name) {
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