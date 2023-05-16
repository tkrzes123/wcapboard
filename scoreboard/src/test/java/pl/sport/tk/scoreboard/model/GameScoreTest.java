package pl.sport.tk.scoreboard.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameScoreTest {

    private final GameScore oneTwo = GameScore.of(Score.of(1), Score.of(2));
    private final GameScore tenZero = GameScore.of(Score.of(10), Score.ZERO);

    @Test
    void totalScoreValueTest() {
        assertEquals(3, oneTwo.getTotalScoreValue());
        assertEquals(10, tenZero.getTotalScoreValue());
    }

    @Test
    void scoreDiffTest() {
        assertEquals(-7, oneTwo.getScoreDiff(tenZero));
        assertEquals(7, tenZero.getScoreDiff(oneTwo));
    }

    @Test
    void homeCannotBeNullTest() {
        assertThrowsExactly(NullPointerException.class, () -> GameScore.of(null, Score.of(5)));
    }

    @Test
    void awayCannotBeNullTest() {
        assertThrowsExactly(NullPointerException.class, () -> GameScore.of(Score.of(2), null));
    }
}