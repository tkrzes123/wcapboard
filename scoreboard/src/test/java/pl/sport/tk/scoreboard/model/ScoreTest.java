package pl.sport.tk.scoreboard.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScoreTest {

    @Test
    void scoreCannotLessThanZeroTest() {
        assertThrowsExactly(IllegalArgumentException.class, () -> Score.of(-1));
    }
}