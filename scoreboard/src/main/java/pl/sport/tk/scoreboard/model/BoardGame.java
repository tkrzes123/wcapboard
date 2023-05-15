package pl.sport.tk.scoreboard.model;

import java.time.Instant;
import java.util.Comparator;

import static java.util.Comparator.comparing;
import static java.util.Comparator.comparingInt;

public interface BoardGame {

    Comparator<BoardGame> SCORE_DESC_COMPARATOR = comparingInt(BoardGame::getTotalScore).reversed();
    Comparator<BoardGame> START_TIME_DESC_COMPARATOR = comparing(BoardGame::getStartTime).reversed();

    Comparator<BoardGame> SUMMARY_COMPARATOR = SCORE_DESC_COMPARATOR.thenComparing(START_TIME_DESC_COMPARATOR)
                    .thenComparing(BoardGame::getGame);

    Comparator<BoardGame> START_TIME_AND_GAME_COMPARATOR = comparing(BoardGame::getStartTime)
            .thenComparing(BoardGame::getGame);

    private int getTotalScore() {
        return getScore().getTotalScoreValue();
    }

    Game getGame();

    Instant getStartTime();

    GameScore getScore();

}
