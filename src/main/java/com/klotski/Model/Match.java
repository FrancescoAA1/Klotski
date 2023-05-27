package com.klotski.Model;

import java.time.LocalDateTime;

/**
 * Represents the object used to describe a Match
 */
public class Match {
    private int score;
    private boolean isTerminated;
    // describe the match's name and by default is automatically calculated using the current date
    private String name;

    // describe the number of hints used in the match
    private int hintsNumber;

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
        name = nameFormatter(LocalDateTime.now().toString());
    }

    private String nameFormatter(String name)
    {
        String[] dateTimeParts = name.split("T");
        String datePart = dateTimeParts[0];
        String timePart = dateTimeParts[1].substring(0, 7);
        String[] timeParts = timePart.split(":");
        return datePart + "_" + timeParts[0] + "-" + timeParts[1] + "-" + (timeParts[2].length() == 1 ? "0" + timeParts[2] : timeParts[2]);
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
    public int getHintsNumber() {
        return hintsNumber;
    }
    public void setHintsNumber(int hints) {
        hintsNumber = hints;
    }
    public void increaseHints() {
        hintsNumber++;
    }
    public void decreaseHints() {
        hintsNumber--;
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
