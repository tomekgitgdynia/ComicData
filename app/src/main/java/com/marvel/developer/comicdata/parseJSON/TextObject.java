package com.marvel.developer.comicdata.parseJSON;


public class TextObject {

    private String type;
    private String language;
    private String text;

    /**
     * No args constructor for use in serialization
     *
     */
    public TextObject() {
    }

    /**
     *
     * @param text
     * @param language
     * @param type
     */
    public TextObject(String type, String language, String text) {
        super();
        this.type = type;
        this.language = language;
        this.text = text;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}