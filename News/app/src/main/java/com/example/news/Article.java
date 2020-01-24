package com.example.news;


/*
    Contains data on an individual article to be used in the ArrayList for the Adapter
 */

public class Article {

    private String source;
    private String author;
    private String preview;
    private String title;
    private String url;

    public Article(String source, String author, String preview, String title, String url){
        this.source = source;
        this.author = author;
        this.preview  = preview;
        this.title = title;
        this.url = url;
    }

    public String getSource(){
        return this.source;
    }

    public String getAuthor(){
        return this.author;
    }

    public String getPreview(){
        return this.preview;
    }

    public String getTitle(){
        return this.title;
    }

    public String getUrl(){
        return this.url;
    }
}
