package pl.sport.tk.scoreboard.model;

public class BoardGameFactoryDefault implements BoardGameFactory {
    @Override
    public BoardGame startNewGame(Game game) {
        return new BoardGameDefault(game);
    }
}
