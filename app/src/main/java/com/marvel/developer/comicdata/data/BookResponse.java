package com.marvel.developer.comicdata.data;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;


public class BookResponse implements Parcelable
{

    private String id;
    private String title;
    private String price;
    private String description;
    private String imageUrl;
    private ArrayList<AttributeListItem> attributeItemList = new ArrayList<>();
    private ArrayList<String> previewImageUrls = new ArrayList<>();

    private String resultString;


    /**
     *
     */
    public BookResponse(){

    }

    public BookResponse(Parcel in) {
        id = in.readString();
        title = in.readString();
        price = in.readString();
        description = in.readString();
        imageUrl = in.readString();
        attributeItemList = in.createTypedArrayList(AttributeListItem.CREATOR);
        previewImageUrls = in.createStringArrayList();
        resultString = in.readString();

    }

    public static final Creator<BookResponse> CREATOR = new Creator<BookResponse>() {
        @Override
        public BookResponse createFromParcel(Parcel in) {
            return new BookResponse(in);
        }

        @Override
        public BookResponse[] newArray(int size) {
            return new BookResponse[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getResultString() {
        return resultString;
    }

    public void setResultString(String resultString) {
        this.resultString = resultString;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeString(id);
        parcel.writeString(title);
        parcel.writeString(price);
        parcel.writeString(description);
        parcel.writeString(imageUrl);
        parcel.writeTypedList(attributeItemList);
        parcel.writeStringList(previewImageUrls);
        parcel.writeString(resultString);
    }


    public ArrayList<String> getPreviewImageUrls() {
        return previewImageUrls;
    }

    public void setPreviewImageUrls(ArrayList<String> previewImageUrls) {
        this.previewImageUrls = previewImageUrls;
    }

    public ArrayList<AttributeListItem> getAttributeItemList() {
        return attributeItemList;
    }

    public void setAttributeItemList(ArrayList<AttributeListItem> attributeItemList) {
        this.attributeItemList = attributeItemList;
    }
}