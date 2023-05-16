package pl.sport.tk.scoreboard.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TeamTest {

    private final Team arg = Team.of("Argentina");
    private final Team mex = Team.of("Mexico");

    @Test
    void compareTo() {
    }

    @Test
    void compareEqualsTest() {
        assertEquals(0, arg.compareTo(Team.of("Argentina")));
    }

    @Test
    void compareLessTest() {
        assertTrue(arg.compareTo(mex) < 0);
    }

    @Test
    void compareBiggerTest() {
        assertTrue(mex.compareTo(arg) > 0);
    }

    @Test
    void nameCannotBeNullTest() {
        assertThrowsExactly(NullPointerException.class, () -> Team.of(null));
    }

    @Test
    void nameCannotBeEmptyTest() {
        assertThrowsExactly(IllegalArgumentException.class, () -> Team.of("   \n"));
    }

    @Test
    void whiteSpaceRemovalTest() {
        assertEquals(Team.of("   \n Spain   "), Team.of("Spain"));
    }
}