package com.example.news;

public class News {
    private String title;
    private String imageUrl;
    private String infoURL;


    public News(String title, String imageUrl, String infoURL) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.infoURL = infoURL;
    }

    public String getTitle() {
        return title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getInfoURL() { return infoURL; }
}
