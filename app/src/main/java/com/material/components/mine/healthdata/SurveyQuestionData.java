package com.material.components.mine.healthdata;

public class SurveyQuestionData {

    private String question;
    private String type;

    private int score = -1;


    public SurveyQuestionData() {
    }


    public boolean isAnswered() {
        return score != -1 && score >= 0 && score <= 5;
    }

    public SurveyQuestionData(String question, String type) {
        this.question = question;
        this.type = type;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
