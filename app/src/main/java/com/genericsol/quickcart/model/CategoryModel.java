package com.genericsol.quickcart.model;

import com.google.gson.annotations.SerializedName;

public class CategoryModel {

    @SerializedName("_id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("imageLink")
    private String imageLink;

    @SerializedName("__v")
    private int version;

    public CategoryModel(String id, String name, String imageLink, int version) {
        this.id = id;
        this.name = name;
        this.imageLink = imageLink;
        this.version = version;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImageLink() {
        return imageLink;
    }

    public int getVersion() {
        return version;
    }
}
