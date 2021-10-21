package au.edu.jcu.cp3406.edugame;

import android.provider.BaseColumns;



class LeaderboardDatabase {
    private LeaderboardDatabase() {
    }

    static class LEADERBOARD_TABLE implements BaseColumns {
        static final String TABLE_NAME = "LEADERBOARD_TABLE";
        static final String COLUMN_PLAYER_NAME = "player_name";
        static final String COLUMN_SCORE = "score";
    }
}
