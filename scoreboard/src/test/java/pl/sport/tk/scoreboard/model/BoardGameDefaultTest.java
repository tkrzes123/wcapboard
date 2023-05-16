package pl.sport.tk.scoreboard.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class BoardGameDefaultTest {

    private Instant instant1;

    private Instant instant2;

    private BoardGameDefault boardGame;
    private BoardGameDefault boardGameEq;

    private BoardGameDefault boardGameDiffInstant;

    private BoardGameDefault boardGameDiffTeam;

    private BoardGameDefault boardGameDiffScore;

    @BeforeEach
    void setUp() {
        instant1 = Instant.now();
        instant2 = Instant.ofEpochMilli(instant1.toEpochMilli() + 1);
        boardGame = new BoardGameDefault(
                Game.of(Team.of("Arg"), Team.of("Mex")),
                instant1, GameScore.of(Score.of(1), Score.of(2)));
        boardGameEq = new BoardGameDefault(
                Game.of(Team.of("Arg"), Team.of("Mex")),
                instant1, GameScore.of(Score.of(1), Score.of(2)));
        boardGameDiffInstant = new BoardGameDefault(
                Game.of(Team.of("Arg"), Team.of("Mex")),
                instant2, GameScore.of(Score.of(1), Score.of(2)));
        boardGameDiffTeam = new BoardGameDefault(
                Game.of(Team.of("Pol"), Team.of("Mex")),
                instant2, GameScore.of(Score.of(1), Score.of(2)));
        boardGameDiffScore = new BoardGameDefault(
                Game.of(Team.of("Pol"), Team.of("Mex")),
                instant2, GameScore.of(Score.of(1), Score.of(2)));
    }

    @Test
    void equalsTest() {
        assertEquals(boardGame, boardGameEq);
        assertNotEquals(boardGame, boardGameDiffInstant);
        assertNotEquals(boardGameEq, boardGameDiffTeam);
        assertNotEquals(boardGame, boardGameDiffScore);
    }

    @Test
    void hashCodeTest() {
        assertEquals(boardGame.hashCode(), boardGameEq.hashCode());
        assertNotEquals(boardGame.hashCode(), boardGameDiffInstant.hashCode());
        assertNotEquals(boardGameEq.hashCode(), boardGameDiffTeam.hashCode());
        assertNotEquals(boardGame.hashCode(), boardGameDiffScore.hashCode());
    }

    @Test
    void defaultScoreTest() {
        BoardGameDefault bGame = new BoardGameDefault(Game.of(Team.of("Pol"), Team.of("Arg")));
        assertEquals(GameScore.of(Score.of(0), Score.of(0)), bGame.getScore());
    }

    @Test
    void gameCannotBeNullTest() {
        assertThrowsExactly(NullPointerException.class,
                () -> new BoardGameDefault(
                        null, instant1, GameScore.of(Score.of(1), Score.of(2))));
    }

    @Test
    void startTimeCannotBeNullTest() {
        assertThrowsExactly(NullPointerException.class,
                () -> new BoardGameDefault(
                        Game.of(Team.of("Arg"), Team.of("Mex")),
                        null, GameScore.of(Score.of(1), Score.of(2))));
    }

    @Test
    void scoreCannotBeNullTest() {
        assertThrowsExactly(NullPointerException.class,
                () -> new BoardGameDefault(
                        Game.of(Team.of("Arg"), Team.of("Mex")),
                        instant1, null));
    }
}