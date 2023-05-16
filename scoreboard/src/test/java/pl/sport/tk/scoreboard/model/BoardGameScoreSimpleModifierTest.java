package pl.sport.tk.scoreboard.model;

import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class BoardGameScoreSimpleModifierTest {

    private final BoardGameScoreSimpleModifier modifier = new BoardGameScoreSimpleModifier();

    private final BoardGame boardGame = new BoardGameDefault(
            Game.of(Team.of("Arg"), Team.of("Mex")),
            Instant.now(), GameScore.of(Score.of(1), Score.of(2)));

    @Test
    void updateScoreTest() {
        BoardGame modifiedBoardGame = modifier.updateScore(boardGame, GameScore.of(Score.of(10), Score.of(5)));
        assertNotEquals(modifiedBoardGame, boardGame);
        assertNotSame(modifiedBoardGame, boardGame);
        assertEquals(GameScore.of(Score.of(10), Score.of(5)), modifiedBoardGame.getScore());
    }

    @Test
    void skipUpdateScoreForTheSameTest() {
        BoardGame modifiedBoardGame = modifier.updateScore(boardGame, GameScore.of(Score.of(1), Score.of(2)));
        assertSame(modifiedBoardGame, boardGame);
        assertEquals(GameScore.of(Score.of(1), Score.of(2)), modifiedBoardGame.getScore());
    }
}