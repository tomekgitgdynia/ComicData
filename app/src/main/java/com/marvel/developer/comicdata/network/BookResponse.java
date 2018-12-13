package com.marvel.developer.comicdata.network;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class BookResponse  implements Parcelable
{

    private String id;
    private String title;
    private String price;
    private String writtenBy;
    private String artBy;
    private String releaseDate;
    private String numberOfPages;
    private String description;
    private String copyRight;
    private String imageUrl;
    private ArrayList<String> previewImageUrls = new ArrayList<>();

    private String resultString;


    /**
     *
     */
    public BookResponse(){
        super();

    }

    protected BookResponse(Parcel in) {
        id = in.readString();
        title = in.readString();
        price = in.readString();
        writtenBy = in.readString();
        artBy = in.readString();
        releaseDate = in.readString();
        numberOfPages = in.readString();
        description = in.readString();
        copyRight = in.readString();
        imageUrl = in.readString();
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

    public String getWrittenBy() {
        return writtenBy;
    }

    public void setWrittenBy(String writtenBy) {
        this.writtenBy = writtenBy;
    }

    public String getArtBy() {
        return artBy;
    }

    public void setArtBy(String artBy) {
        this.artBy = artBy;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(String numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public String getCopyRight() {
        return copyRight;
    }

    public void setCopyRight(String copyRight) {
        this.copyRight = copyRight;
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
        parcel.writeString(writtenBy);
        parcel.writeString(artBy);
        parcel.writeString(releaseDate);
        parcel.writeString(numberOfPages);
        parcel.writeString(description);
        parcel.writeString(copyRight);
        parcel.writeString(imageUrl);
        parcel.writeStringList(previewImageUrls);
        parcel.writeString(resultString);
    }

    public ArrayList<String> getPreviewImageUrls() {
        return previewImageUrls;
    }

    public void setPreviewImageUrls(ArrayList<String> previewImageUrls) {
        this.previewImageUrls = previewImageUrls;
    }
}