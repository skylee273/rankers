package com.project.rankers.data.model.db;

import java.io.Serializable;

/**
 * Created by Emil on 21/10/17.
 */

public class CompetitorData implements Serializable {

    private String name;
    private String score;
    private String round;
    private String number;

    public CompetitorData(String name, String score, String round, String number){
        this.name = name;
        this.score = score;
        this.round = round;
        this.number = number;
    }

    public String getRound() {
        return round;
    }

    public void setRound(String round) {
        this.round = round;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
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
