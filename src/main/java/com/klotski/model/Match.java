package com.klotski.model;

import java.time.LocalDateTime;

/**
 * Represents the object used to describe a Match
 */
public class Match {

    // describe the match's name and is automatically calculated using the current date
    private LocalDateTime date;
    private int score;
    private boolean isTerminated;
    private String disposition;

    public Match()
    {
        isTerminated = false;
        score = 0;
        // obtain the current time
        date = LocalDateTime.now();
    }
    public int getScore() {
        return score;
    }
    public String getName() {
        return date.toString();
    }
    public String getDisposition() {
        return disposition;
    }
    public boolean isTerminated() {
        return isTerminated;
    }
    public void setScore(int score) {
        this.score = score;
    }
    public void terminate()
    {
        isTerminated = true;
    }
    public void incrementScore()
    {
        score++;
    }
    public void setDisposition(String disposition) {
        this.disposition = disposition;
    }
}
