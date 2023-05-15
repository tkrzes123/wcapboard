package pl.sport.tk.scoreboard.model.ex;

import pl.sport.tk.scoreboard.model.Game;

public class GameAlreadyStartedException extends RuntimeException {

    private final Game game;

    public GameAlreadyStartedException(Game game) {
        super(String.format("Game %s vs %s already started", game.home().name(), game.away().name()));
        this.game = game;
    }

    public Game getGame() {
        return game;
    }
}
