package player;

public class Best {
    Move move;
    int score;

    public Best() {
        move = null;
        score = 0;
    }

    public Best(Move move, int score) {
        this.move = move;
        this.score = score;
    }

}
