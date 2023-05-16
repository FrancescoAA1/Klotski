package com.klotski.model;

import java.time.LocalDateTime;

/**
 * Represents the object used to describe a Match
 */
public class Match {
    private int score;
    private boolean isTerminated;
    // describe the match's name and by default is automatically calculated using the current date
    private String name;

    public Match(String name)
    {
        isTerminated = false;
        score = 0;
        this.name = name;
    }

    public Match()
    {
        isTerminated = false;
        score = 0;
        // obtain the current time
        name = LocalDateTime.now().toString();
    }
    public int getScore() {
        return score;
    }
    public String getName() {
        return name;
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
    public void decrementScore() { score--; }
}