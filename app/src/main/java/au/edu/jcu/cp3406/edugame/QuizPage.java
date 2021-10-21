package au.edu.jcu.cp3406.edugame;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class QuizPage extends AppCompatActivity {
    private final questionsLibrary quizLibrary = new questionsLibrary();
    private TextView q_text;
    private TextView score_num;
    private Button choice1;
    private Button choice2;
    private Button choice3;
    private String correctAnswer;
    private int score = 0;
    private int q_number = 0;
    private int seconds = Integer.parseInt(SettingsActivity.set_seconds);
    private final String player_name = SettingsActivity.player_name;
    private boolean running;
    private LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_page);




        q_text = findViewById(R.id.qtext);
        score_num = findViewById(R.id.scoreCount);
        choice1 = findViewById(R.id.choice1);
        choice2 = findViewById(R.id.choice2);
        choice3 = findViewById(R.id.choice3);
        Button skip_button = findViewById(R.id.skip);

        layout = findViewById(R.id.question_layout);

        running = true;
        runTimer();
        update_question_set();

        //verifying choices
        choice1.setOnClickListener(v -> {
            if (choice1.getText() == correctAnswer) {
                score = score + 1;
                update_score(score);
                Toast.makeText(QuizPage.this, "Correct Answer", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(QuizPage.this, "InCorrect Answer", Toast.LENGTH_SHORT).show();
            }
            update_question_set();
        });

        choice2.setOnClickListener(v -> {
            if (choice2.getText() == correctAnswer) {
                score = score + 1;
                update_score(score);
                Toast.makeText(QuizPage.this, "Correct Answer", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(QuizPage.this, "InCorrect Answer", Toast.LENGTH_SHORT).show();
            }
            update_question_set();
        });

        choice3.setOnClickListener(v -> {
            if (choice3.getText() == correctAnswer) {
                score = score + 1;
                update_score(score);
                update_question_set();
                Toast.makeText(QuizPage.this, "Correct Answer", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(QuizPage.this, "InCorrect Answer", Toast.LENGTH_SHORT).show();
                update_question_set();
            }
        });


//        skip button
        skip_button.setOnClickListener(v -> {

            Toast.makeText(QuizPage.this, "Skipped", Toast.LENGTH_SHORT ).show();
            update_question_set();
        });


    }

//     updating score count
    private void update_score(int score) {
        score_num.setText(String.format(Locale.getDefault(), "%d", score));
    }

//    fetching questions and answers from questionsLibrary java class
    private void update_question_set() {
        q_text.setText(quizLibrary.getques(q_number));
        choice1.setText(quizLibrary.getch1(q_number));
        choice2.setText(quizLibrary.getch2(q_number));
        choice3.setText(quizLibrary.getch3(q_number));
        correctAnswer = quizLibrary.getcorrAn(q_number);
        q_number++;
        if (q_number == 11) {
            completeTask();
        }
    }

//    running timer for the test
    private void runTimer() {
        final TextView textViewWatch = findViewById(R.id.text_view_countdown);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int minutes = seconds / 60;
                int secs = seconds % 60;

                String time = String.format(Locale.getDefault(),
                        "%02d:%02d", minutes, secs);
                textViewWatch.setText(time);
                if (running) {
                    seconds = seconds - 1;
                }
                if (seconds == 0) {
                    seconds = -99;
                    running = false;
                    completeTask();
                }
                handler.postDelayed(this, 1000);
            }
        });
    }

//    updating score in LeaderboardPage activity using SQliteHelper class
    private void completeTask() {
        running = false;
        String message;

        message = "Your score is " + score;
        Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
        toast.show();

        SQLite_Helper sqLite_helper = new SQLite_Helper(this);
        sqLite_helper.insert_player_score(player_name, score);

        Intent score_intent = new Intent(this, LeaderboardActivity.class);
        score_intent.putExtra("score", "" + score);
        startActivity(score_intent);
    }


}
