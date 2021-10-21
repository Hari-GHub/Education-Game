package au.edu.jcu.cp3406.edugame;

//getting the players name to add to list
class PlayerList {
    private String playerName;
    private int score;
    private int rank;

    String getNameofPlayer() {
        return playerName;
    }

    void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    int getScore() {
        return score;
    }

    void setScore(int score) {
        this.score = score;
    }

    int getRank() {
        return rank;
    }

    void setRank(int rank) {
        this.rank = rank;
    }
}



