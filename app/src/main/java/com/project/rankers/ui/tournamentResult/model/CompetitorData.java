package com.project.rankers.ui.tournamentResult.model;

import java.io.Serializable;

/**
 * Created by Emil on 21/10/17.
 */

public class CompetitorData implements Serializable {

    private String name;
    private String score;
    private String title;
    private String date;

    public CompetitorData(String name, String score, String title, String date){
        this.name = name;
        this.score = score;
        this.title = title;
        this.date = date;
    }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
