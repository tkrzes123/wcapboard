package pl.sport.tk.scoreboard.model;

public record Score(int value) {

    public static final Score ZERO = new Score(0);

    public Score {
        if(value < 0) {
            throw new IllegalArgumentException("Score cannot be less than 0");
        }
    }

    public static Score of(int i) {
        return new Score(i);
    }

}
