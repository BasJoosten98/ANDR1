package com.example.fhictcompanion.News;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

public class NewsPost {
    private String title;
    private String author;
    private String content;
    private Drawable image;

    public NewsPost(String title, String author, String content, Drawable image) {
        this.title = title;
        this.author = author;
        this.content = content;
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
}
