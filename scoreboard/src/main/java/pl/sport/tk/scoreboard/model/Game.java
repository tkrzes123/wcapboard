package pl.sport.tk.scoreboard.model;

import java.util.Objects;

public record Game(Team home, Team away) implements Comparable<Game> {

    public static Game of(Team home, Team away){
        return new Game(home, away);
    }

    public Game {
        Objects.requireNonNull(home, "Home team cannot be null");
        Objects.requireNonNull(away, "Away team cannot be null");
        if(home.equals(away)) {
            throw new IllegalArgumentException("Home team name cannot be equal away team name");
        }
    }

    @Override
    public int compareTo(Game o) {
        int homeCompare = home.compareTo(o.home());
        return homeCompare == 0 ? away.compareTo(o.away()) : homeCompare;
    }

    public boolean isHomeOrAwayTeamEquals(Game game) {
        return this.away().equals(game.home()) || this.home().equals(game.away());
    }

}
