package com.example.finalprojectpro;

import android.widget.RatingBar;

public class Review {
    String reviewer, review, timestamp;
    float reviewbar;
 public Review(){

 }
    public Review(String reviewer, String review, String timestamp, float reviewbar) {
        this.reviewer = reviewer;
        this.review = review;
        this.timestamp = timestamp;
        this.reviewbar = reviewbar;
    }

    public String getReviewer() {
        return reviewer;
    }

    public void setReviewer(String reviewer) {
        this.reviewer = reviewer;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public float getReviewbar() {
        return reviewbar;
    }

    public void setReviewbar(float reviewbar) {
        this.reviewbar = reviewbar;
    }
}
