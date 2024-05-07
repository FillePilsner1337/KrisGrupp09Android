package com.example.krisapp;

public class VMAMessageObject {
    private String headline;
    private String published;
    private String bodyText;

    public VMAMessageObject(String headline, String published, String bodyText) {
        this.headline = headline;
        this.published = published;
        this.bodyText = bodyText;
    }

    public String getHeadline() {
        return headline;
    }

    public String getPublished() {
        return published;
    }

    public String getBodyText() {
        return bodyText;
    }

    @Override
    public String toString() {
        return "Headline: " + headline + "\nPublished: " + published + "\nBodyText: " + bodyText;
    }
}
