package pl.sport.tk.scoreboard.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BoardGameFactoryDefaultTest {

    private final BoardGameFactoryDefault factory = new BoardGameFactoryDefault();

    @Test
    void startNewGame() {
        BoardGameDefault boardGame = new BoardGameDefault(Game.of(Team.of("Arg"), Team.of("Mex")));
        BoardGame factoryBoardGame = factory.startNewGame(Game.of(Team.of("Arg"), Team.of("Mex")));
        assertEquals(boardGame.getGame(), factoryBoardGame.getGame());
        assertEquals(boardGame.getScore(), factoryBoardGame.getScore());
        //start time may be different
    }
}