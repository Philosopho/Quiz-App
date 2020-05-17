package com.krinotech.quizapp;

public class Quiz {

    private String[] questions;
    private String[][] multipleAnswerChoices;
    private String[] submittedAnswers;
    private double grade;


    public Quiz(String[] questions, String[][] multipleAnswerChoices) {
        this.questions = questions;
        this.multipleAnswerChoices = multipleAnswerChoices;
    }

    public String[] getSubmittedAnswers() {
        return submittedAnswers;
    }

    public void setSubmittedAnswers(String[] submittedAnswers) {
        this.submittedAnswers = submittedAnswers;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }


    public String[] getQuestions() {
        return questions;
    }

    public String[][] getMultipleAnswerChoices() {
        return multipleAnswerChoices;
    }


}
