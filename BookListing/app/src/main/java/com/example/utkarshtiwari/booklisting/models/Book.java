package com.example.utkarshtiwari.booklisting.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by utkarshtiwari on 10/10/17.
 */

public class Book implements Parcelable {

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getAuthors() {
        return authors;
    }

    public void setAuthors(ArrayList<String> authors) {
        this.authors = authors;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    private String name;

    public String getInfoLink() {
        return infoLink;
    }

    public void setInfoLink(String infoLink) {
        this.infoLink = infoLink;
    }

    private String infoLink;
    private ArrayList<String> authors;
    private String language;
    private String currency;
    private double price;

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    private String imageURL;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Book(String name, ArrayList<String> authors, String language, double price, String currency, String imageURL, String infoLink) {
        this.name = name;
        this.authors = authors;
        this.language = language;
        this.price = price;
        this.currency = currency;
        this.imageURL = imageURL;
        this.infoLink = infoLink;
    }

    protected Book(Parcel in) {
        name = in.readString();
        if (in.readByte() == 0x01) {
            authors = new ArrayList<String>();
            in.readList(authors, String.class.getClassLoader());
        } else {
            authors = null;
        }
        language = in.readString();
        price = in.readFloat();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        if (authors == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(authors);
        }
        dest.writeString(language);
        dest.writeDouble(price);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Book> CREATOR = new Parcelable.Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };
}