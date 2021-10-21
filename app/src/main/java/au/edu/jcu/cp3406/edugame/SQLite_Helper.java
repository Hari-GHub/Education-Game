package au.edu.jcu.cp3406.edugame;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import au.edu.jcu.cp3406.edugame.LeaderboardDatabase.LEADERBOARD_TABLE;


public class SQLite_Helper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "QuizApp.db";
    private SQLiteDatabase sqlite_db;

    SQLite_Helper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        this.sqlite_db = sqLiteDatabase;

        final String SQL_CREATE_LEADERBOARD_TABLE =
                "CREATE TABLE " +
                        LEADERBOARD_TABLE.TABLE_NAME + "( " +
                        LEADERBOARD_TABLE._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        LEADERBOARD_TABLE.COLUMN_PLAYER_NAME + " TEXT, " +
                        LEADERBOARD_TABLE.COLUMN_SCORE + " INTEGER )";

        sqLiteDatabase.execSQL(SQL_CREATE_LEADERBOARD_TABLE);
    }

    void insert_player_score(String playerName, int score) {
        sqlite_db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(LEADERBOARD_TABLE.COLUMN_PLAYER_NAME, playerName);
        values.put(LEADERBOARD_TABLE.COLUMN_SCORE, score);
        sqlite_db.insert(LEADERBOARD_TABLE.TABLE_NAME, null, values);
        sqlite_db.close();
    }

    List<PlayerList> getTopPlayer() {
        List<PlayerList> playerList = new ArrayList<>();
        int player_position = 1;
        sqlite_db = getReadableDatabase();
        Cursor cursor = sqlite_db.query(
                LEADERBOARD_TABLE.TABLE_NAME,
                new String[]{LEADERBOARD_TABLE.COLUMN_PLAYER_NAME, LEADERBOARD_TABLE.COLUMN_SCORE},
                null,
                null,
                null,
                null,
                LEADERBOARD_TABLE.COLUMN_SCORE + " DESC",
                "5"
        );
        if (cursor.moveToFirst()) {
            do {
                PlayerList highPlayer = new PlayerList();
                highPlayer.setRank(player_position);
                highPlayer.setPlayerName(cursor.getString(cursor.getColumnIndex(LEADERBOARD_TABLE.COLUMN_PLAYER_NAME)));
                highPlayer.setScore(cursor.getInt(cursor.getColumnIndex(LEADERBOARD_TABLE.COLUMN_SCORE)));
                player_position++;
                playerList.add(highPlayer);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return playerList;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

}
