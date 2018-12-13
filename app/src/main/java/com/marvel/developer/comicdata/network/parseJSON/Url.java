package com.marvel.developer.comicdata.network.parseJSON;


public class Url {

    private String type;
    private String url;

    /**
     * No args constructor for use in serialization
     *
     */
    public Url() {
    }

    /**
     *
     * @param type
     * @param url
     */
    public Url(String type, String url) {
        super();
        this.type = type;
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
