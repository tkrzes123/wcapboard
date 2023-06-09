package pl.sport.tk.scoreboard.model;

import java.util.Objects;

public record GameScore(Score home, Score away) {

    public static final GameScore ZERO_ZERO = new GameScore(Score.ZERO, Score.ZERO);

    public GameScore {
        Objects.requireNonNull(home, "Home score cannot be null");
        Objects.requireNonNull(away, "Away score cannot be null");
    }

    public static GameScore of(Score home, Score away) {
        return new GameScore(home, away);
    }

    public int getTotalScoreValue() {
        return home.value() + away.value();
    }

    public int getScoreDiff(GameScore other) {
        return this.getTotalScoreValue() - other.getTotalScoreValue();
    }
}
