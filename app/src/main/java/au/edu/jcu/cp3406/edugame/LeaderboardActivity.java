package au.edu.jcu.cp3406.edugame;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;
import java.util.Locale;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class LeaderboardActivity extends AppCompatActivity {

    private List<PlayerList> playerList;
    private final Twitter twitter = TwitterFactory.getSingleton();
    private String game_score;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

// SQL helper for database creation and management
        SQLite_Helper sqLite_helper = new SQLite_Helper(this);
        playerList = sqLite_helper.getTopPlayer();

        Bundle extras = getIntent().getExtras();
        assert extras != null;
        game_score = extras.getString("score");

        DisplayPlayerScores();
    }
//connecting main activity
    public void mainScreen(View view) {
        Intent score_intent = new Intent(this, MainActivity.class);
        startActivity(score_intent);
    }
//Displaying scores from the database
    private void DisplayPlayerScores() {
        int player_counts = playerList.size();
        String rank, playerName, score;
        TextView rank_h = findViewById(R.id.rank_header);
        TextView rank_1 = findViewById(R.id.rank_1);
        TextView rank_2 = findViewById(R.id.rank_2);
        TextView rank_3 = findViewById(R.id.rank_3);
        TextView rank_4 = findViewById(R.id.rank_4);
        TextView rank_5 = findViewById(R.id.rank_5);

        TextView player_name_h = findViewById(R.id.name_header);
        TextView player_name_1 = findViewById(R.id.name_1);
        TextView player_name_2 = findViewById(R.id.name_2);
        TextView player_name_3 = findViewById(R.id.name_3);
        TextView player_name_4 = findViewById(R.id.name_4);
        TextView player_name_5 = findViewById(R.id.name_5);

        TextView score_h = findViewById(R.id.score_header);
        TextView score_1 = findViewById(R.id.score_1);
        TextView score_2 = findViewById(R.id.score_2);
        TextView score_3 = findViewById(R.id.score_3);
        TextView score_4 = findViewById(R.id.score_4);
        TextView score_5 = findViewById(R.id.score_5);

        rank_h.setText(R.string.rank);
        player_name_h.setText(R.string.name);
        score_h.setText(R.string.score);

        //fill the score table
        for (int counter = 0; counter < player_counts; counter++) {
            PlayerList currentPlayer;
            switch (counter) {
                case 0:
                    currentPlayer = playerList.get(counter);
                    rank = String.valueOf(currentPlayer.getRank());
                    playerName = currentPlayer.getNameofPlayer();
                    score = String.valueOf(currentPlayer.getScore());
                    rank_1.setText(rank);
                    player_name_1.setText(playerName);
                    score_1.setText(score);
                    break;
                case 1:
                    currentPlayer = playerList.get(counter);
                    rank = String.valueOf(currentPlayer.getRank());
                    playerName = currentPlayer.getNameofPlayer();
                    score = String.valueOf(currentPlayer.getScore());
                    rank_2.setText(rank);
                    player_name_2.setText(playerName);
                    score_2.setText(score);
                    break;
                case 2:
                    currentPlayer = playerList.get(counter);
                    rank = String.valueOf(currentPlayer.getRank());
                    playerName = currentPlayer.getNameofPlayer();
                    score = String.valueOf(currentPlayer.getScore());
                    rank_3.setText(rank);
                    player_name_3.setText(playerName);
                    score_3.setText(score);
                    break;
                case 3:
                    currentPlayer = playerList.get(counter);
                    rank = String.valueOf(currentPlayer.getRank());
                    playerName = currentPlayer.getNameofPlayer();
                    score = String.valueOf(currentPlayer.getScore());
                    rank_4.setText(rank);
                    player_name_4.setText(playerName);
                    score_4.setText(score);
                    break;
                case 4:
                    currentPlayer = playerList.get(counter);
                    rank = String.valueOf(currentPlayer.getRank());
                    playerName = currentPlayer.getNameofPlayer();
                    score = String.valueOf(currentPlayer.getScore());
                    rank_5.setText(rank);
                    player_name_5.setText(playerName);
                    score_5.setText(score);
                    break;
            }
        }
    }

    public void post_on_twitter(View view) {
        //Post on Twitter
        final String twitter_msg = "Beat my score in QuizApp. " + game_score;

        Background.run(() -> {
            if (LeaderboardActivity.this.isAuthorised()) {
                try {
                    twitter.updateStatus(twitter_msg);
                    Log.i("LeaderboardActivity", String.format(Locale.getDefault(), "Status updated: %s", twitter_msg));
                } catch (TwitterException e) {
                    Log.i("LeaderboardActivity", String.format(Locale.getDefault(),
                            "Something bad happened while tweeting: %s", e.toString()));
                }
            } else {
                Log.i("LeaderboardActivity", "Unable to update status. User not authorised. Directing to twitter");
            }
        });
    }

    private boolean isAuthorised() {
        try {
            twitter.verifyCredentials();
            Log.i("LeaderboardActivity", "User is verified");
            return true;
        } catch (Exception e) {
            Log.i("LeaderboardActivity", "User is not verified");
            return false;
        }
    }
}

