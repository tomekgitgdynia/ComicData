package com.marvel.developer.comicdata.data;

import android.os.Parcel;
import android.os.Parcelable;

public class AttributeListItem implements Parcelable {

    private String attributeName;
    private String attributeValue;

    public AttributeListItem(String name, String value)
    {
        this.attributeName = name;
        this.attributeValue = value;
    }

    public AttributeListItem(Parcel in) {
        attributeName = in.readString();
        attributeValue = in.readString();
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(attributeName);
        dest.writeString(attributeValue);
    }

    public static final Creator<AttributeListItem> CREATOR = new Creator<AttributeListItem>() {
        @Override
        public AttributeListItem createFromParcel(Parcel in) {
            return new AttributeListItem(in);
        }

        @Override
        public AttributeListItem[] newArray(int size) {
            return new AttributeListItem[size];
        }
    };

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public String getAttributeValue() {
        return attributeValue;
    }

    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
    }

    @Override
    public int describeContents() {
        return 0;
    }


}
