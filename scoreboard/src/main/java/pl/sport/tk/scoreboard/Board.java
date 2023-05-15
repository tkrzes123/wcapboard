package pl.sport.tk.scoreboard;

import pl.sport.tk.scoreboard.model.BoardGame;
import pl.sport.tk.scoreboard.model.Game;
import pl.sport.tk.scoreboard.model.ScoreUpdate;

import java.util.Collection;
import java.util.Optional;

public interface Board {

    BoardGame startGame(Game game);

    boolean finishGame(Game game);

    Optional<BoardGame> updateScore(ScoreUpdate scoreUpdate);

    Collection<BoardGame> getGamesByStartTime();

    Collection<BoardGame> getSummaryByScore();
}
