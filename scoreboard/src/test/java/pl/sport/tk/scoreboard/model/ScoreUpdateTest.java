package pl.sport.tk.scoreboard.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScoreUpdateTest {

    private final Game argMex = Game.of(Team.of("Argentina"), Team.of("Mexico"));

    @Test
    void gameScoreCannotBeNullTest() {
        assertThrowsExactly(NullPointerException.class, () -> ScoreUpdate.of(argMex, null));
    }

    @Test
    void gameCannotBeNullTest() {
        assertThrowsExactly(NullPointerException.class, () -> ScoreUpdate.of(null, GameScore.ZERO_ZERO));
    }
}