package au.edu.jcu.cp3406.edugame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
//    connecting to other three activities
    public void startTest(View view) {
        Intent intentQuizPage = new Intent(this, QuizPage.class);
        startActivity(intentQuizPage);
    }

    public void setting(View view) {
        Intent intent_settings = new Intent(this, SettingsActivity.class);
        startActivity(intent_settings);
    }

    public void leaderboard(View view) {
        Intent intent_leaderboard = new Intent(this, LeaderboardActivity.class);
        intent_leaderboard.putExtra("score", "0");
        startActivity(intent_leaderboard);
    }
}