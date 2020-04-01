package com.example.fhictcompanion.News;

import android.graphics.drawable.Drawable;

public class NewsPost {
    private String title;
    private String author;
    private String content;
    private String link;
    private Drawable image;

    public NewsPost(String title, String author, String content, String link, Drawable image) {
        this.title = title;
        this.author = author;
        this.content = content;
        this.link = link;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public Drawable getImage() {
        return image;
    }

    public String getLink() {
        return link;
    }
}
