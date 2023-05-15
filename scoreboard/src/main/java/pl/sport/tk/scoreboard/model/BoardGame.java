package pl.sport.tk.scoreboard.model;

import java.time.Instant;
import java.util.Comparator;

import static java.util.Comparator.comparing;
import static java.util.Comparator.comparingInt;

public interface BoardGame {

    Comparator<BoardGame> SCORE_COMPARATOR =
            comparingInt(BoardGame::getTotalScore).thenComparing(BoardGame::getStartTime).thenComparing(BoardGame::getGame);

    Comparator<BoardGame> START_TIME_COMPARATOR = comparing(BoardGame::getStartTime).thenComparing(BoardGame::getGame);

    private int getTotalScore() {
        return getScore().getTotalScoreValue();
    }

    Game getGame();

    Instant getStartTime();

    GameScore getScore();

}
