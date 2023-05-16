package pl.sport.tk.scoreboard.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    private final Game argMex = Game.of(Team.of("Argentina"), Team.of("Mexico"));
    private final Game polArg = Game.of(Team.of("Poland"), Team.of("Argentina"));
    private final Game mexArg = Game.of(Team.of("Mexico"), Team.of("Argentina"));
    private final Game spaBra = Game.of(Team.of("Spain"), Team.of("Brazil"));

    @Test
    void homeAndAwayTeamEqualsTest() {
        assertTrue(argMex.isHomeOrAwayTeamEquals(mexArg));
    }

    @Test
    void homeOrAwayTeamEqualsTest() {
        assertTrue(argMex.isHomeOrAwayTeamEquals(polArg));
    }

    @Test
    void homeAndAwayTeamNotEqualsTest() {
        assertFalse(argMex.isHomeOrAwayTeamEquals(spaBra));
    }

    @Test
    void compareHomeAndAwayEqualsTest() {
        assertEquals(0, argMex.compareTo(Game.of(Team.of("Argentina"), Team.of("Mexico"))));
    }

    @Test
    void compareHomeLessAwayEqualsTest() {
        assertTrue(argMex.compareTo(mexArg) < 0);
    }

    @Test
    void compareHomeBiggerAwayEqualsTest() {
        assertTrue(polArg.compareTo(mexArg) > 0);
    }

    @Test
    void homeAndAwayCannotBeEqualsTest() {
        //given
        Team team1 = Team.of("team");
        Team team2 = Team.of("team");

        //when/then
        assertThrowsExactly(IllegalArgumentException.class, () -> Game.of(team1, team2));
    }

    @Test
    void homeCannotBeNullTest() {
        assertThrowsExactly(NullPointerException.class, () -> Game.of(null, Team.of("team")));
    }

    @Test
    void awayCannotBeNullTest() {
        assertThrowsExactly(NullPointerException.class, () -> Game.of(Team.of("team"), null));
    }

}