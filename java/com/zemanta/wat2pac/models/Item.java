package com.zemanta.wat2pac.models;

/**
 * Created by dusano on 16/10/15.
 */
public class Item {
    private String id;
    private String name;
    private String imageURL;

    public Item(String id, String name, String imageURL) {
        this.id = id;
        this.name = name;
        this.imageURL = imageURL;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImageURL() {
        return imageURL;
    }
}
