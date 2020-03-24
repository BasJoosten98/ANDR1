package com.example.fhictcompanion;

import android.graphics.Bitmap;

public class NewsPost {
    private String title;
    private String author;
    private String content;

    public NewsPost(String title, String author, String content) {
        this.title = title;
        this.author = author;
        this.content = content;
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
}
