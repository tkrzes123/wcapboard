package pl.sport.tk.scoreboard;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.sport.tk.scoreboard.model.*;
import pl.sport.tk.scoreboard.model.ex.GameAlreadyStartedException;
import pl.sport.tk.scoreboard.model.ex.IllegalScoreChangeException;

import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

class WCupBoardTest {
    private final Game argMex = Game.of(Team.of("Argentina"), Team.of("Mexico"));
    private final Game polArg = Game.of(Team.of("Poland"), Team.of("Argentina"));
    private final Game braFra = Game.of(Team.of("Brazil"), Team.of("France"));
    private final Game mexArg = Game.of(Team.of("Mexico"), Team.of("Argentina"));

    private final Game mexCan = Game.of(Team.of("Mexico"), Team.of("Canada"));
    private final Game spaBra = Game.of(Team.of("Spain"), Team.of("Brazil"));
    private final Game gerFra = Game.of(Team.of("Germany"), Team.of("France"));
    private final Game uruIta = Game.of(Team.of("Uruguay"), Team.of("Italy"));
    private final Game argAus = Game.of(Team.of("Argentina"), Team.of("Australia"));


    private final Score zero = Score.of(0);
    private final Score one = Score.of(1);
    private final Score two = Score.of(2);
    private final Score three = Score.of(3);
    private final Score four = Score.of(4);
    private final Score five = Score.of(5);
    private final Score six = Score.of(6);
    private final Score ten = Score.of(10);

    private final Board board = new WCupBoard(new BoardGameFactoryDefault(), new BoardGameScoreValidatedModifier());

    @BeforeEach
    void setUp() {
    }

    @Test
    void startTwoGamesTest() {
        //given
        BoardGame argMexAtBoard = board.startGame(argMex);
        BoardGame braFraAtBoard = board.startGame(braFra);

        //when
        Collection<BoardGame> boardGames = board.getGamesByStartTime();

        //then
        assertEquals(2, boardGames.size());
        Iterator<BoardGame> it = boardGames.iterator();
        BoardGame game1 = it.next();
        BoardGame game2 = it.next();
        assertEquals(argMexAtBoard, game1);
        assertEquals(braFraAtBoard, game2);
    }

    @Test
    void throwExceptionWhenStartSameGameTest() {
        //given
        BoardGame argMexAtBoard = board.startGame(argMex);
        GameAlreadyStartedException exception =
                Assertions.assertThrowsExactly(GameAlreadyStartedException.class, () -> board.startGame(argMex));

        //when
        Collection<BoardGame> boardGames = board.getGamesByStartTime();

        //then
        assertEquals(1, boardGames.size());
        Iterator<BoardGame> it = boardGames.iterator();
        BoardGame game1 = it.next();
        assertEquals(argMexAtBoard, game1);
        assertEquals(argMex, exception.getGame());
        assertEquals("Game Argentina vs Mexico already started", exception.getMessage());
    }

    @Test
    void throwExceptionWhenStartTheSameHomeAwayTeamTest() {

        //given
        BoardGame argMexAtBoard = board.startGame(argMex);
        GameAlreadyStartedException exception =
                Assertions.assertThrowsExactly(GameAlreadyStartedException.class, () -> board.startGame(mexArg));

        //when
        Collection<BoardGame> boardGames = board.getGamesByStartTime();

        //then
        assertEquals(1, boardGames.size());
        Iterator<BoardGame> it = boardGames.iterator();
        BoardGame game1 = it.next();
        assertEquals(argMexAtBoard, game1);
        assertEquals(argMex, exception.getGame());
        assertEquals("Game Argentina vs Mexico already started", exception.getMessage());
    }

    @Test
    void throwExceptionWhenStartTheSameHomeOrAwayTeamTest() {

        //given
        BoardGame argMexAtBoard = board.startGame(argMex);
        GameAlreadyStartedException exception =
                Assertions.assertThrowsExactly(GameAlreadyStartedException.class, () -> board.startGame(polArg));

        //when
        Collection<BoardGame> boardGames = board.getGamesByStartTime();

        //then
        assertEquals(1, boardGames.size());
        Iterator<BoardGame> it = boardGames.iterator();
        BoardGame game1 = it.next();
        assertEquals(argMexAtBoard, game1);
        assertEquals(argMex, exception.getGame());
        assertEquals("Game Argentina vs Mexico already started", exception.getMessage());
    }

    @Test
    void updateTwoGamesScoreTest() {

        //given
        BoardGame argMexAtBoard = board.startGame(argMex);
        BoardGame braFraAtBoard = board.startGame(braFra);

        //when
        board.updateScore(ScoreUpdate.of(argMex, GameScore.of(one, zero)));
        board.updateScore(ScoreUpdate.of(braFra, GameScore.of(zero, one)));
        board.updateScore(ScoreUpdate.of(argMex, GameScore.of(two, zero)));

        Collection<BoardGame> boardGames = board.getGamesByStartTime();

        //then
        assertNotNull(argMexAtBoard);
        assertNotNull(braFraAtBoard);
        assertNotEquals(argMexAtBoard, braFraAtBoard);
        assertEquals(2, boardGames.size());
        Iterator<BoardGame> it = boardGames.iterator();
        BoardGame game1 = it.next();
        BoardGame game2 = it.next();
        assertEquals(GameScore.of(two, zero), game1.getScore());
        assertEquals(GameScore.of(zero, one), game2.getScore());
    }

    @Test
    void skipUpdateByTheSameScoreTest() {

        //given
        BoardGame argMexAtBoard = board.startGame(argMex);

        //when
        Optional<BoardGame> argMex01 = board.updateScore(ScoreUpdate.of(argMex, GameScore.of(one, zero)));
        Optional<BoardGame> argMex01Second = board.updateScore(ScoreUpdate.of(argMex, GameScore.of(one, zero)));

        Collection<BoardGame> boardGames = board.getGamesByStartTime();

        //then
        assertNotNull(argMexAtBoard);
        assertFalse(argMex01.isEmpty());
        assertFalse(argMex01Second.isEmpty());
        assertNotEquals(argMexAtBoard, argMex01Second.get());
        assertSame(argMex01.get(), argMex01Second.get());
        BoardGame game1 = boardGames.iterator().next();
        assertSame(game1, argMex01.get());
    }

    @Test
    void skipUpdateNotStartedGameScoreTest() {

        //given
        board.startGame(argMex);

        //when
        board.updateScore(ScoreUpdate.of(argMex, GameScore.of(one, zero)));
        Optional<BoardGame> polArgUpdatedScore = board.updateScore(ScoreUpdate.of(polArg, GameScore.of(two, zero)));

        Collection<BoardGame> boardGames = board.getGamesByStartTime();

        //then
        assertEquals(1, boardGames.size());
        Iterator<BoardGame> it = boardGames.iterator();
        BoardGame game1 = it.next();
        assertEquals(GameScore.of(one, zero), game1.getScore());
        assertTrue(polArgUpdatedScore.isEmpty());
    }

    @Test
    void throwExceptionWhenIllegalScoreUpdateByMoreThanOneTest() {

        //given
        board.startGame(argMex);

        //when
        board.updateScore(ScoreUpdate.of(argMex, GameScore.of(one, zero)));
        board.updateScore(ScoreUpdate.of(argMex, GameScore.of(two, zero)));

        IllegalScoreChangeException exception =
                Assertions.assertThrowsExactly(IllegalScoreChangeException.class,
                        () -> board.updateScore(ScoreUpdate.of(argMex, GameScore.of(four, zero))));

        Collection<BoardGame> boardGames = board.getGamesByStartTime();

        //then
        BoardGame game1 = boardGames.iterator().next();
        assertEquals(GameScore.of(two, zero), game1.getScore());
        assertEquals(GameScore.of(two, zero), exception.getGameScore());
        assertEquals(GameScore.of(four, zero), exception.getScoreUpdate().gameScore());
        assertEquals("Score cannot change for Argentina vs Mexico from: 2-0 to: 4-0", exception.getMessage());
    }

    @Test
    void throwExceptionWhenIllegalScoreChangeWithDiffOneTest() {

        //given
        board.startGame(argMex);

        //when
        board.updateScore(ScoreUpdate.of(argMex, GameScore.of(one, zero)));
        board.updateScore(ScoreUpdate.of(argMex, GameScore.of(two, zero)));

        IllegalScoreChangeException exception =
                Assertions.assertThrowsExactly(IllegalScoreChangeException.class,
                        () -> board.updateScore(ScoreUpdate.of(argMex, GameScore.of(zero, three))));

        Collection<BoardGame> boardGames = board.getGamesByStartTime();

        //then
        BoardGame game1 = boardGames.iterator().next();
        assertEquals(GameScore.of(two, zero), game1.getScore());
        assertEquals(GameScore.of(two, zero), exception.getGameScore());
        assertEquals(GameScore.of(zero, three), exception.getScoreUpdate().gameScore());
        assertEquals("Score cannot change for Argentina vs Mexico from: 2-0 to: 0-3", exception.getMessage());
    }

    @Test
    void throwExceptionWhenIllegalScoreChangeWithoutDiffTest() {

        //given
        board.startGame(argMex);

        //when
        board.updateScore(ScoreUpdate.of(argMex, GameScore.of(one, zero)));

        IllegalScoreChangeException exception =
                Assertions.assertThrowsExactly(IllegalScoreChangeException.class,
                        () -> board.updateScore(ScoreUpdate.of(argMex, GameScore.of(zero, one))));

        Collection<BoardGame> boardGames = board.getGamesByStartTime();

        //then
        BoardGame game1 = boardGames.iterator().next();
        assertEquals(GameScore.of(one, zero), game1.getScore());
        assertEquals(GameScore.of(one, zero), exception.getGameScore());
        assertEquals(GameScore.of(zero, one), exception.getScoreUpdate().gameScore());
        assertEquals("Score cannot change for Argentina vs Mexico from: 1-0 to: 0-1", exception.getMessage());
    }

    @Test
    void finishGameTest() {
        //given
        board.startGame(argMex);
        Collection<BoardGame> boardGamesWithArgMex = board.getGamesByStartTime();

        //when
        boolean gameFinished = board.finishGame(argMex);
        Collection<BoardGame> boardGamesEmpty = board.getGamesByStartTime();

        //then
        assertTrue(gameFinished);
        assertEquals(1, boardGamesWithArgMex.size());
        assertTrue(boardGamesEmpty.isEmpty());
    }

    @Test
    void finishNotAddedGameTest() {
        //given
        board.startGame(argMex);
        Collection<BoardGame> boardGamesWithArgMex = board.getGamesByStartTime();

        //when
        boolean gameFinished = board.finishGame(mexArg);
        Collection<BoardGame> boardGamesAfterFinish = board.getGamesByStartTime();

        //then
        assertFalse(gameFinished);
        assertEquals(1, boardGamesWithArgMex.size());
        assertEquals(1, boardGamesAfterFinish.size());
    }

    @Test
    void summaryFromExampleTest() {
        //given
        Board board = new WCupBoard(new BoardGameFactoryDefault(), new BoardGameScoreSimpleModifier());
        board.startGame(mexCan);
        board.updateScore(ScoreUpdate.of(mexCan, GameScore.of(zero, five)));
        board.startGame(spaBra);
        board.updateScore(ScoreUpdate.of(spaBra, GameScore.of(ten, two)));
        board.startGame(gerFra);
        board.updateScore(ScoreUpdate.of(gerFra, GameScore.of(two, two)));
        slowDown();
        board.startGame(uruIta);
        board.updateScore(ScoreUpdate.of(uruIta, GameScore.of(six, six)));
        board.startGame(argAus);
        board.updateScore(ScoreUpdate.of(argAus, GameScore.of(three, one)));

        //when
        Collection<BoardGame> summary = board.getSummaryByScore();

        //then
        assertEquals(5, summary.size());
        Iterator<BoardGame> it = summary.iterator();

        assertSummaryOfGame(uruIta, it.next(), 12);
        assertSummaryOfGame(spaBra, it.next(), 12);
        assertSummaryOfGame(mexCan, it.next(), 5);
        assertSummaryOfGame(argAus, it.next(), 4);
        assertSummaryOfGame(gerFra, it.next(), 4);
    }

    private void assertSummaryOfGame(Game game, BoardGame boardGame, int totalScore) {
        assertAll(
                () -> assertEquals(game, boardGame.getGame()),
                () -> assertEquals(totalScore, boardGame.getScore().getTotalScoreValue())
        );
    }

    private void slowDown() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}