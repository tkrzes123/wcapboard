package pl.sport.tk.scoreboard.model;

import java.util.Objects;

public record Game(Team home, Team away) {

    public static Game of(Team home, Team away){
        return new Game(home, away);
    }

    public Game {
        Objects.requireNonNull(home, "Home team cannot be null");
        Objects.requireNonNull(away, "Away team cannot be null");
    }
}
