package com.app.ryan.ptapp;

/**
 * Created by Ryan on 2/24/2016.
 */
public class Exercise {
    private String title;
    private String info;
    private String imageURL;
    private String repCount;
    private String holdCount;
    private String completeCount;
    private String performCount;
    private String timeCount;

    public Exercise(String title, String info, String imageURL, String repCount, String holdCount, String completeCount, String performCount, String timeCount)
    {
        this.title = title;
        this.info = info;
        this.imageURL = imageURL;
        this.repCount = repCount;
        this.holdCount = holdCount;
        this.completeCount = completeCount;
        this.performCount = performCount;
        this.timeCount = timeCount;
    }

    public String getTitle() {
        return title;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getRepCount() {
        return repCount;
    }

    public String getInfo() {
        return info;
    }

    public String getHoldCount() {
        return holdCount;
    }

    public String getPerformCount() {
        return performCount;
    }

    public String getCompleteCount() {
        return completeCount;
    }

    public String getTimeCount() {
        return timeCount;
    }
}
