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

    public String getDetailedInfo() {
        String date = published.substring(0,10);
        String time = published.substring(11,16);
        return "Datum: " + date + "\n" +
                "Tidpunkt: " + time + "\n\n"+
                headline + "\n\n" +
                bodyText + "Dummy text eftersom test VMA inte ger någon text. Dummy text eftersom test VMA inte ger någon text. Dummy text eftersom test VMA inte ger någon text. ";
    }
    @Override
    public String toString() {
        return  "NYTT VMA:" + "\n" + headline;
    }
}
