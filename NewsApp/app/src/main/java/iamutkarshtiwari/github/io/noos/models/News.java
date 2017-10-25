package iamutkarshtiwari.github.io.noos.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by utkarshtiwari on 14/10/17.
 */

public class News implements Parcelable {

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String data) {
        this.date = date;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    String category;
    String title;
    String type;
    String date;
    String url;

    public News(String category, String title, String type, String date, String url) {
        this.category = category;
        this.title = title;
        this.type = type;
        this.date = date;
        this.url = url;
    }

    protected News(Parcel in) {
        category = in.readString();
        title = in.readString();
        type = in.readString();
        date = in.readString();
        url = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(category);
        dest.writeString(title);
        dest.writeString(type);
        dest.writeString(date);
        dest.writeString(url);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<News> CREATOR = new Parcelable.Creator<News>() {
        @Override
        public News createFromParcel(Parcel in) {
            return new News(in);
        }

        @Override
        public News[] newArray(int size) {
            return new News[size];
        }
    };
}