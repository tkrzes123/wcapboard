package pl.sport.tk.scoreboard.model;

import java.time.Instant;
import java.util.Objects;

public class BoardGameDefault implements BoardGame {

    private final Game game;

    private final Instant startTime;

    private final GameScore score;

    BoardGameDefault(Game game, Instant startTime, GameScore score) {
        this.game = Objects.requireNonNull(game, "Game cannot be null");
        this.startTime = Objects.requireNonNull(startTime, "Start time cannot be null");
        this.score = Objects.requireNonNull(score, "Game score cannot be null");
    }

    public BoardGameDefault(Game game) {
        this(game, Instant.now(), GameScore.ZERO_ZERO);
    }

    @Override
    public Game getGame() {
        return game;
    }

    @Override
    public Instant getStartTime() {
        return startTime;
    }

    @Override
    public GameScore getScore() {
        return score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BoardGameDefault that = (BoardGameDefault) o;
        return Objects.equals(getGame(), that.getGame())
                && Objects.equals(getStartTime(), that.getStartTime())
                && Objects.equals(getScore(), that.getScore());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getGame(), getStartTime(), getScore());
    }
}
