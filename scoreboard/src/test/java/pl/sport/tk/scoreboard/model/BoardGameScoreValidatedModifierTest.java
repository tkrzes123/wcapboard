package pl.sport.tk.scoreboard.model;

import org.junit.jupiter.api.Test;
import pl.sport.tk.scoreboard.model.ex.IllegalScoreChangeException;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class BoardGameScoreValidatedModifierTest {

    private final BoardGameScoreValidatedModifier modifier = new BoardGameScoreValidatedModifier();

    private final BoardGame boardGame = new BoardGameDefault(
            Game.of(Team.of("Arg"), Team.of("Mex")),
            Instant.now(), GameScore.of(Score.of(1), Score.of(2)));

    @Test
    void updateScoreByOneTest() {
        BoardGame modifiedBoardGame = modifier.updateScore(boardGame, GameScore.of(Score.of(2), Score.of(2)));
        assertNotEquals(modifiedBoardGame, boardGame);
        assertNotSame(modifiedBoardGame, boardGame);
        assertEquals(GameScore.of(Score.of(2), Score.of(2)), modifiedBoardGame.getScore());
    }

    @Test
    void skipUpdateScoreForTheSameTest() {
        BoardGame modifiedBoardGame = modifier.updateScore(boardGame, GameScore.of(Score.of(1), Score.of(2)));
        assertSame(modifiedBoardGame, boardGame);
        assertEquals(GameScore.of(Score.of(1), Score.of(2)), modifiedBoardGame.getScore());
    }

    @Test
    void updateScoreByTwoHomeTest() {
        IllegalScoreChangeException exception = assertThrowsExactly(
                IllegalScoreChangeException.class,
                () -> modifier.updateScore(boardGame, GameScore.of(Score.of(3), Score.of(2))));
        assertEquals(GameScore.of(Score.of(1), Score.of(2)), boardGame.getScore());

        ScoreUpdate expectedScoreUpdate = ScoreUpdate.of(Game.of(Team.of("Arg"), Team.of("Mex")), GameScore.of(Score.of(3), Score.of(2)));
        assertEquals(expectedScoreUpdate, exception.getScoreUpdate());
        assertEquals(GameScore.of(Score.of(1), Score.of(2)), exception.getGameScore());
    }

    @Test
    void updateScoreByTwoAwayTest() {
        IllegalScoreChangeException exception = assertThrowsExactly(
                IllegalScoreChangeException.class,
                () -> modifier.updateScore(boardGame, GameScore.of(Score.of(1), Score.of(4))));
        assertEquals(GameScore.of(Score.of(1), Score.of(2)), boardGame.getScore());

        ScoreUpdate expectedScoreUpdate = ScoreUpdate.of(Game.of(Team.of("Arg"), Team.of("Mex")), GameScore.of(Score.of(1), Score.of(4)));
        assertEquals(expectedScoreUpdate, exception.getScoreUpdate());
        assertEquals(GameScore.of(Score.of(1), Score.of(2)), exception.getGameScore());
    }

    @Test
    void updateScoreToLessTest() {
        IllegalScoreChangeException exception = assertThrowsExactly(
                IllegalScoreChangeException.class,
                () -> modifier.updateScore(boardGame, GameScore.of(Score.of(1), Score.of(1))));
        assertEquals(GameScore.of(Score.of(1), Score.of(2)), boardGame.getScore());

        ScoreUpdate expectedScoreUpdate = ScoreUpdate.of(Game.of(Team.of("Arg"), Team.of("Mex")), GameScore.of(Score.of(1), Score.of(1)));
        assertEquals(expectedScoreUpdate, exception.getScoreUpdate());
        assertEquals(GameScore.of(Score.of(1), Score.of(2)), exception.getGameScore());
    }
}