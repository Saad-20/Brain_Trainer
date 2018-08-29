package com.saadshahzad97.trainmybrain;

import android.os.CountDownTimer;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    int brainQuestions = 0;
    int points = 0;
    int correctAnswerLocation;

    //Calling an ArrayList of answers.
    ArrayList<Integer> correctAnswerList = new ArrayList<Integer>();

    //Calling out the textView
    TextView correctView;
    TextView pointsViewId;
    TextView sumViewId;
    TextView timerViewId;

    //Making the buttons to be default so they can be used.
    Button firstButton;
    Button secondButton;
    Button thirdButton;
    Button fourthButton;
    Button playAgainButton;
    Button startNowButton;

    //Layout to be defined
    ConstraintLayout gameLayout;

    /*When the game has ended, the playAgain method will run once the playAgain button is displayed
      on the screen
     */
    public void playAgain(View view){

        points = 0;
        brainQuestions = 0;
        timerViewId.setText("60s");
        pointsViewId.setText("0/0");
        correctView.setText("");
        playAgainButton.setVisibility(View.INVISIBLE);

        generateNewQuestions();

        new CountDownTimer(60100, 1000){
            @Override
            public void onTick(long secondsLeft) {

                timerViewId.setText(String.valueOf(secondsLeft / 1000) + "s");
                firstButton.setEnabled(true);
                secondButton.setEnabled(true);
                thirdButton.setEnabled(true);
                fourthButton.setEnabled(true);
            }

            @Override
            public void onFinish() {

                playAgainButton.setVisibility(View.VISIBLE);
                timerViewId.setText("0s");
                correctView.setText("Your Score: "+Integer.toString(points)+"/"+Integer.toString(brainQuestions));
                firstButton.setEnabled(false);
                secondButton.setEnabled(false);
                thirdButton.setEnabled(false);
                fourthButton.setEnabled(false);
            }

        }.start();

    }

    public void generateNewQuestions(){

        //To generate random numbers
        Random numberGenerator = new Random();

        /*The First number and Second number is defined below which each
          has a boundary of generating numbers between 0 to 50
         */
        int firstNumber  = numberGenerator.nextInt(35) + 1;
        int secondNumber = numberGenerator.nextInt(35) + 1;

        int generateOperation = numberGenerator.nextInt(4);

        switch(generateOperation)
        {
            case 2:  while(firstNumber < secondNumber)
            {
                firstNumber = numberGenerator.nextInt(35) + 1;
                secondNumber = numberGenerator.nextInt(35) + 1;
            } break;

            case 3:  while(firstNumber < secondNumber && secondNumber == 0)
            {
                firstNumber = numberGenerator.nextInt(35) + 1;
                secondNumber = numberGenerator.nextInt(35) + 1;
            } break;

            default: break;
        }

        /*
          To generate different questions of basic algebra for the user to be displayed
          in the sumView
         */

        switch (generateOperation){
            case 0 :
                sumViewId.setText(Integer.toString(firstNumber)+" + "+Integer.toString(secondNumber));
                break;

            case 1 :
                sumViewId.setText(Integer.toString(firstNumber)+" * "+Integer.toString(secondNumber));
                break;

            case 2:
                sumViewId.setText(Integer.toString(firstNumber)+" - "+Integer.toString(secondNumber));
                break;

            case 3:
                sumViewId.setText(Integer.toString(firstNumber)+" / "+Integer.toString(secondNumber));
                break;
        }

        //To clear all the answer stored in the ArrayList
        correctAnswerList.clear();

        //To generate four possible answers for the buttons.
        correctAnswerLocation = numberGenerator.nextInt(4);

        int incorrectAnswer;

        //The operation will run four times.
        for (int x = 0; x < 4; x++){

            /*if x matches to the random generated number of correctAnswerLocation then, the
              following case number will be performed. Thus that the correctAnswer matches with x,
              is correct
             */
            if(x == correctAnswerLocation){

                switch(generateOperation)
                {
                    case 0: correctAnswerList.add(firstNumber + secondNumber); break;
                    case 1: correctAnswerList.add(firstNumber * secondNumber); break;
                    case 2: correctAnswerList.add(firstNumber - secondNumber); break;
                    case 3: correctAnswerList.add(firstNumber / secondNumber); break;
                    default: break;
                }
            }
            //If the answer is not correct
            else {

                switch (generateOperation) {
                    case 0:
                        incorrectAnswer = numberGenerator.nextInt(71);
                        while (incorrectAnswer == firstNumber + secondNumber) {
                            incorrectAnswer = numberGenerator.nextInt(71);
                        }
                        correctAnswerList.add(incorrectAnswer);
                        break;

                    case 1:
                        incorrectAnswer = numberGenerator.nextInt(701);
                        while (incorrectAnswer == firstNumber * secondNumber) {
                            incorrectAnswer = numberGenerator.nextInt(701);
                        }
                        correctAnswerList.add(incorrectAnswer);
                        break;

                    case 2:
                        incorrectAnswer = numberGenerator.nextInt(35) + 1;
                        while (incorrectAnswer == firstNumber - secondNumber) {
                            incorrectAnswer = numberGenerator.nextInt(35) + 1;
                        }
                        correctAnswerList.add(incorrectAnswer);
                        break;

                    case 3:
                        incorrectAnswer = numberGenerator.nextInt(35) + 1;
                        while (incorrectAnswer == firstNumber / secondNumber) {
                            incorrectAnswer = numberGenerator.nextInt(35) + 1;
                        }
                        correctAnswerList.add(incorrectAnswer);
                        break;

                    default: break;
                }
            }

        }

        //To set random number on the buttons to allow the user to select the answer.
        firstButton.setText  (Integer.toString(correctAnswerList.get(0)));
        secondButton.setText (Integer.toString(correctAnswerList.get(1)));
        thirdButton.setText  (Integer.toString(correctAnswerList.get(2)));
        fourthButton.setText (Integer.toString(correctAnswerList.get(3)));

    }

    //After the user taps on one of the answers, the following method will be performed
    public void selectAnswerButton(View view){

        /*if the tag of each button matches to correctAnswerLocation, then the user will get a point
          and if not then the else statement will run displaying incorrect Answer.
         */

        if(view.getTag().toString().equals(Integer.toString(correctAnswerLocation))){

            points++;
            correctView.setText("Correct!");

        } else{

            correctView.setText("Incorrect!");

        }

        brainQuestions++;
        pointsViewId.setText(Integer.toString(points)+"/"+Integer.toString(brainQuestions));
        generateNewQuestions();

    }

    //In the beginning of the application starts, this method will run of the startNowButton,
    public void letsGo(View view){
        /*
         As soon as the startNowButton is clicked, the button will disappear and the gamelayout will
         appear on the user screen. Thus the game has started.
         */
        if(startNowButton.isPressed()){

            startNowButton.setVisibility(View.INVISIBLE);
            gameLayout.setVisibility(View.VISIBLE);
            playAgain(findViewById(R.id.playAgainButton));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Defining all variables
        startNowButton  =  (Button) findViewById(R.id.startingButtonId);
        sumViewId       =  (TextView) findViewById(R.id.sumViewId);
        firstButton     =  (Button)  findViewById(R.id.button1);
        secondButton    =  (Button)  findViewById(R.id.button2);
        thirdButton     =  (Button)  findViewById(R.id.button3);
        fourthButton    =  (Button)  findViewById(R.id.button4);
        pointsViewId    =  (TextView) findViewById(R.id.pointsViewId);
        correctView     =  (TextView) findViewById(R.id.correctView);
        timerViewId     =  (TextView) findViewById(R.id.timerViewId);
        playAgainButton =  (Button) findViewById(R.id.playAgainButton);
        gameLayout      =  (ConstraintLayout) findViewById(R.id.gameLayout);
    }
  }

