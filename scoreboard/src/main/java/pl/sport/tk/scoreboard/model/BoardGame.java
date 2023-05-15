package pl.sport.tk.scoreboard.model;

import java.time.Instant;
import java.util.Comparator;

import static java.util.Comparator.comparing;

public interface BoardGame {

    Comparator<BoardGame> SCORE_COMPARATOR =
            comparing(BoardGame::getTotalScore).thenComparing(BoardGame::getStartTime);

    Comparator<BoardGame> START_TIME_COMPARATOR = comparing(BoardGame::getStartTime);

    private int getTotalScore() {
        return getScore().getTotalScoreValue();
    }

    Game getGame();

    Instant getStartTime();

    GameScore getScore();

}
