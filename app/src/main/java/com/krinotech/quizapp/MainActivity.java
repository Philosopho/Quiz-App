package com.krinotech.quizapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.krinotech.quizapp.databinding.ActivityMainBinding;

import static com.krinotech.quizapp.QuizOfficial.convertToString;
import static com.krinotech.quizapp.QuizOfficial.formatMultipleAnswers;
import static com.krinotech.quizapp.QuizOfficial.getQuiz;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding activityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        final Resources resources = getResources();
        final Quiz quiz = getQuiz(resources);

        String[] questions = quiz.getQuestions();
        setQuestions(questions);

        final String[][] answerChoices = quiz.getMultipleAnswerChoices();

        String[] checkBoxSet = answerChoices[0];
        setCheckboxChoices(checkBoxSet);

        String[] multipleChoiceSet = answerChoices[1];
        setMultipleChoices(multipleChoiceSet);

        activityMainBinding.submitTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstAnswer = activityMainBinding
                        .etFillInAnswerQuestion4
                        .et
                        .getText()
                        .toString();
                String secondAnswer = activityMainBinding
                        .etFillInAnswerQuestionTwo
                        .et
                        .getText()
                        .toString();
                String thirdAnswer = getThirdAnswer();
                String fourthAnswer = getFourthAnswer();
                String[] answers = {
                        firstAnswer, secondAnswer,
                        thirdAnswer, fourthAnswer
                };

                quiz.setSubmittedAnswers(answers);

                QuizOfficial.gradeQuiz(resources, quiz);

                Toast.makeText(MainActivity.this, getString(R.string.grade, quiz.getGrade()), Toast.LENGTH_LONG).show();
            }
        });
    }

    private String getFourthAnswer() {
        String first = convertToString(activityMainBinding.cbAnswerA.checkbox.isChecked());
        String second = convertToString(activityMainBinding.cbAnswerB.checkbox.isChecked());
        String third = convertToString(activityMainBinding.cbAnswerC.checkbox.isChecked());
        String fourth = convertToString(activityMainBinding.cbAnswerD.checkbox.isChecked());
        return formatMultipleAnswers(first, second, third, fourth);
    }

    private String getThirdAnswer() {
        RadioGroup rgMultipleChoice = activityMainBinding.rgMultipleChoice;
        int id = rgMultipleChoice.getCheckedRadioButtonId();
        View view = rgMultipleChoice.findViewById(id);
        return convertToString(rgMultipleChoice.indexOfChild(view));
    }

    private void setMultipleChoices(String[] multipleChoiceSet) {
        activityMainBinding.radioAnswer1.radio.setText(multipleChoiceSet[0]);
        activityMainBinding.radioAnswer2.radio.setText(multipleChoiceSet[1]);
        activityMainBinding.radioAnswer3.radio.setText(multipleChoiceSet[2]);
        activityMainBinding.radioAnswer4.radio.setText(multipleChoiceSet[3]);
    }

    private void setCheckboxChoices(String[] checkBoxSet) {
        activityMainBinding.cbAnswerA.checkbox.setText(checkBoxSet[0]);
        activityMainBinding.cbAnswerB.checkbox.setText(checkBoxSet[1]);
        activityMainBinding.cbAnswerC.checkbox.setText(checkBoxSet[2]);
        activityMainBinding.cbAnswerD.checkbox.setText(checkBoxSet[3]);
    }

    private void setQuestions(String[] questions) {
        activityMainBinding.tvQuestion1.tv.setText(questions[0]);
        activityMainBinding.tvQuestion2.tv.setText(questions[1]);
        activityMainBinding.tvQuestion3.tv.setText(questions[2]);
        activityMainBinding.tvQuestion4.tv.setText(questions[3]);
    }
}
