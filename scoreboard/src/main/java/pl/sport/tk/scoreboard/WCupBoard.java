package pl.sport.tk.scoreboard;

import pl.sport.tk.scoreboard.model.*;
import pl.sport.tk.scoreboard.model.ex.GameAlreadyStartedException;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class WCupBoard implements Board {

    private final Map<Game, BoardGame> gamesInProgress = new ConcurrentHashMap<>();

    @Override
    public BoardGame startGame(Game game) throws GameAlreadyStartedException {
        return gamesInProgress.compute(game, this::createBoardGameOrThrow);
    }

    private BoardGame createBoardGameOrThrow(Game game, BoardGame boardGame) {
        if(boardGame == null) {
            return new BoardGameDefault(game);
        }
        throw new GameAlreadyStartedException(game);
    }

    @Override
    public boolean finishGame(Game game) {
        return gamesInProgress.remove(game) != null;
    }

    @Override
    public Optional<BoardGame> updateScore(ScoreUpdate scoreUpdate) {
        Objects.requireNonNull(scoreUpdate, "Score update cannot be null");
        return Optional.empty();
    }

    @Override
    public Collection<BoardGame> getGamesByStartTime() {
        Set<BoardGame> summaryByScore = new TreeSet<>(BoardGame.START_TIME_COMPARATOR);
        summaryByScore.addAll(gamesInProgress.values());
        return summaryByScore;
    }

    @Override
    public Collection<BoardGame> getSummaryByScore() {
        Set<BoardGame> summaryByScore = new TreeSet<>(BoardGame.SCORE_COMPARATOR);
        summaryByScore.addAll(gamesInProgress.values());
        return summaryByScore;
    }
}
