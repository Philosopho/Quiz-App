package com.krinotech.quizapp;

import android.content.res.Resources;

public class QuizOfficial {
    public static final int MULTIPLE_CHOICE_AMOUNT = 4;
    public static final double MAX_SCORE_PER_QUESTION = 25.0;

    public static Quiz getQuiz(Resources resources) {
        return createMidterm(resources);
    }

    private static Quiz createMidterm(Resources resources) {
        String[] questions = resources.getStringArray(R.array.midterm_questions);

        String[] answerText = resources.getStringArray(R.array.midterm_answersText);
        String[][] fourMultipleAnswers = new String[answerText.length][MULTIPLE_CHOICE_AMOUNT];
        for (int index = 0; index < answerText.length; index++) {
            String[] answerChoices = answerText[index].split(",");
            fourMultipleAnswers[index] = answerChoices;
        }

        return new Quiz(questions, fourMultipleAnswers);
    }

    public static void gradeQuiz(Resources resources, Quiz quiz) {
        final String COMMA = ",";
        String[] answers = resources.getStringArray(R.array.midterm_answers);
        String[] submittedAnswers = quiz.getSubmittedAnswers();
        double maxGrade = 100.00;

        for(int index = 0; index < answers.length; index++) {
            String[] splitAnswers = answers[index].split(COMMA);
            String[] submittedAnswer = submittedAnswers[index].split(COMMA);
            if(index <= 1) {
                maxGrade -= getWriteInAnswer(splitAnswers, submittedAnswer);
            }
            else if(index > 2) {
                maxGrade -= getMultipleAnswersDeductions(splitAnswers, submittedAnswer);
            }
            else {
                maxGrade -= getMultipleChoiceDeduction(splitAnswers, submittedAnswer);
            }
        }

        quiz.setGrade(maxGrade);
    }

    private static double getWriteInAnswer(String[] splitAnswers, String[] submittedAnswer) {
        double scorePart = MAX_SCORE_PER_QUESTION / splitAnswers.length;
        double deduction = MAX_SCORE_PER_QUESTION;
        for (String answer : splitAnswers) {
            for (String s : submittedAnswer) {
                if (answer.equalsIgnoreCase(s)) {
                    deduction -= scorePart;
                    break;
                }
            }
        }
        return deduction;
    }

    private static double getMultipleChoiceDeduction(String[] splitAnswers, String[] submittedAnswer) {
        return splitAnswers[0].equalsIgnoreCase(submittedAnswer[0]) ? 0 : 25;
    }

    private static double getMultipleAnswersDeductions(String[] splitAnswers, String[] submittedAnswer) {
        double checkboxDeduction = 25.0 / MULTIPLE_CHOICE_AMOUNT;
        double total = 0;
        for(int index = 0; index < splitAnswers.length; index++) {
            if(!splitAnswers[index].equalsIgnoreCase(submittedAnswer[index])) {
                total += checkboxDeduction;
            }
        }
        return total;
    }

    public static  <T> String convertToString(T thing) {
        return String.valueOf(thing);
    }

    public static String formatMultipleAnswers(String... answers) {
        StringBuilder stringBuilder = new StringBuilder();
        String comma = "";

        for(String answer: answers) {
            stringBuilder.append(comma);
            comma = ",";
            stringBuilder.append(answer);
        }
        return stringBuilder.toString();
    }
}
