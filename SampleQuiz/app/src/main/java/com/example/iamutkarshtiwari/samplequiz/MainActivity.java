package com.example.iamutkarshtiwari.samplequiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button submit;
    int correctAnswers = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        submit = (Button) findViewById(R.id.submit_button);
        submit.setOnClickListener(this);
    }

    /**
     * Question 1 evaluation
     */
    private void checkQuestionOneAnswers(){
        CheckBox answer1 = (CheckBox) findViewById(R.id.cb_answer_raphael);
        CheckBox answer2 = (CheckBox) findViewById(R.id.cb_answer_leonardo);
        CheckBox answer3 = (CheckBox) findViewById(R.id.cb_answer_michael);
        boolean answer1Checked = answer1.isChecked();
        boolean answer2Checked = answer2.isChecked();
        boolean answer3Checked = answer3.isChecked();

        if (!answer1Checked && !answer2Checked && answer3Checked){
            correctAnswers += 1;
        } else {
            correctAnswers += 0;
        }
    }

    /**
     * Question 2 evaluation
     */
    private void checkQuestionTwoAnswers(){
        RadioButton radioButtonBruce = (RadioButton) findViewById(R.id.radio_bruce);
        boolean isRadioButtonBruceChecked = radioButtonBruce.isChecked();
        if (isRadioButtonBruceChecked){
            correctAnswers += 1;
        } else {
            correctAnswers += 0;
        }
    }

    /**
     * Fetches answer entered by user
     * @return
     */
    private String getQuestionThreeUserInput() {
        EditText userInputLastName = (EditText) findViewById(R.id.answerInput);
        String name = userInputLastName.getText().toString();
        return name;
    }

    /**
     * Question 3 evaluation
     */
    private void checkQuestionThreeAnswer(){
        String name = getQuestionThreeUserInput();
        if (name.trim().equalsIgnoreCase("drake")){
            correctAnswers += 1;
        } else {
            correctAnswers += 0;
        }
    }

    /**
     * Question 4 evaluation
     */
    private void checkQuestionFourAnswers(){
        RadioButton radioButton2007 = (RadioButton) findViewById(R.id.radio_2007);
        boolean isRadioButton2007Checked = radioButton2007.isChecked();
        if (isRadioButton2007Checked){
            correctAnswers += 1;
        } else {
            correctAnswers += 0;
        }
    }

    /**
     * Question 5 evaluation
     */
    private void checkQuestionFiveAnswers(){
        CheckBox answerNeville = (CheckBox) findViewById(R.id.cb_answer_neville);
        CheckBox answerRubeus = (CheckBox) findViewById(R.id.cb_answer_rubeus);
        CheckBox answerPeter = (CheckBox) findViewById(R.id.cb_answer_peter);
        boolean isAnswerNevilleChecked = answerNeville.isChecked();
        boolean isAnswerRubeusChecked = answerRubeus.isChecked();
        boolean isAnswerPeterChecked = answerPeter.isChecked();

        if (isAnswerNevilleChecked && isAnswerRubeusChecked && !isAnswerPeterChecked){
            correctAnswers += 1;
        } else {
            correctAnswers += 0;
        }
    }

    /**
     * Check all questions one by one
     */
    private void checkAll(){
        checkQuestionOneAnswers();
        checkQuestionTwoAnswers();
        checkQuestionThreeAnswer();
        checkQuestionFourAnswers();
        checkQuestionFiveAnswers();
    }

    /**
     * Resets counter value
     */
    private void resetCounterCorrectAnswers(){
        correctAnswers = 0;
    }

    /**
     * On click listener for this activity
     * @param view
     */
    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.submit_button) {
            checkAll();
            Toast.makeText(MainActivity.this, getStringFromID(R.string.correct_answer) + correctAnswers + getStringFromID(R.string.by_5),
                    Toast.LENGTH_LONG).show();
            resetCounterCorrectAnswers();
        }
    }

    /**
     * Gets string from res ID
     * @param id res id
     * @return string value
     */
    private String getStringFromID(int id) {
        return getResources().getString(id);
    }

}