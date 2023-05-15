package pl.sport.tk.scoreboard;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.sport.tk.scoreboard.model.*;
import pl.sport.tk.scoreboard.model.ex.GameAlreadyStartedException;

import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WCupBoardTest {

    private Game argMex = Game.of(Team.of("Argentina"), Team.of("Mexico"));
    private Game polArg = Game.of(Team.of("Poland"), Team.of("Argentina"));
    private Game braFra = Game.of(Team.of("Brazil"), Team.of("France"));
    private Game qatEcu = Game.of(Team.of("Qatar"), Team.of("Ecuador"));

    private Score zero = Score.of(0);
    private Score one = Score.of(1);
    private Score two = Score.of(2);
    private Score three = Score.of(3);
    private Score four = Score.of(4);

    private Board board = new WCupBoard(new BoardGameFactoryDefault(), new BoardGameScoreSimpleModifier());

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
        assertEquals(2, boardGames.size());
        Iterator<BoardGame> it = boardGames.iterator();
        BoardGame game1 = it.next();
        BoardGame game2 = it.next();
        assertEquals(GameScore.of(two, zero), game1.getScore());
        assertEquals(GameScore.of(zero, one), game2.getScore());
    }

    @Test
    void skipUpdateNotStartedGameScoreTest() {

        //given
        BoardGame argMexAtBoard = board.startGame(argMex);

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


}