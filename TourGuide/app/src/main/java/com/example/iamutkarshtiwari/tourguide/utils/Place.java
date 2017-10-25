package com.example.iamutkarshtiwari.tourguide.utils;

/**
 * Created by iamutkarshtiwari on 01/10/17.
 */

public class Place {
    private String name;
    private String subtitle;
    private String description;
    private int imageID;

    public Place(String name, String subtitle, String desc, int imageId) {
        this.name = name;
        this.subtitle = subtitle;
        this.description = desc;
        imageID = imageId;
    }

    public String getName() {
        return name;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public String getDescription() {
        return description;
    }

    public int getImageID() {
        return imageID;
    }
}
