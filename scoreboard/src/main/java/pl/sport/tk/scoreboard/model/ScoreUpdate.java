package pl.sport.tk.scoreboard.model;

import java.util.Objects;

public record ScoreUpdate(Game game, GameScore gameScore) {

    public ScoreUpdate {
        Objects.requireNonNull(game, "Game cannot be null");
        Objects.requireNonNull(gameScore, "Game score cannot be null");
    }

    public static ScoreUpdate of(Game game, GameScore gameScore) {
        return new ScoreUpdate(game, gameScore);
    }

    public Score homeScore() {
        return gameScore.home();
    }

    public Score awayScore() {
        return gameScore.away();
    }
}
