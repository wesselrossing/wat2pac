package com.zemanta.wat2pac.models;

/**
 * Created by dusano on 16/10/15.
 */
public class Item {
    private String name;
    private String imageURL;

    public Item(String name, String imageURL) {
        this.name = name;
        this.imageURL = imageURL;
    }

    public String getName() {
        return name;
    }

    public String getImageURL() {
        return imageURL;
    }
}
