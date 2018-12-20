package com.marvel.developer.comicdata.parseJSON;


public class JsonBody {

    private Integer code;
    private String status;
    private String copyright;
    private String attributionText;
    private String attributionHTML;
    private String etag;
    private Data data;

    /**
     * No args constructor for use in serialization
     *
     */
    public JsonBody() {
    }

    /**
     *
     * @param attributionText
     * @param etag
     * @param status
     * @param data
     * @param copyright
     * @param code
     * @param attributionHTML
     */
    public JsonBody(Integer code, String status, String copyright, String attributionText, String attributionHTML, String etag, Data data) {
        super();
        this.code = code;
        this.status = status;
        this.copyright = copyright;
        this.attributionText = attributionText;
        this.attributionHTML = attributionHTML;
        this.etag = etag;
        this.data = data;
    }

    public String getCopyright() {
        return copyright;
    }

    public Data getData() {
        return data;
    }

}