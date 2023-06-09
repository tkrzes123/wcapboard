package pl.sport.tk.scoreboard;

import pl.sport.tk.scoreboard.model.*;
import pl.sport.tk.scoreboard.model.ex.GameAlreadyStartedException;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class WCupBoard implements Board {

    private final Map<Game, BoardGame> gamesInProgress;

    private final BoardGameFactory boardGameFactory;

    private final BoardGameScoreModifier scoreModifier;

    public WCupBoard(BoardGameFactory boardGameFactory, BoardGameScoreModifier scoreModifier) {
        this.boardGameFactory = Objects.requireNonNull(boardGameFactory);
        this.scoreModifier = Objects.requireNonNull(scoreModifier);
        this.gamesInProgress = new ConcurrentHashMap<>();
    }

    @Override
    public BoardGame startGame(Game game) throws GameAlreadyStartedException {
        return gamesInProgress.compute(game, this::createBoardGameOrThrow);
    }

    private BoardGame createBoardGameOrThrow(Game game, BoardGame boardGame) {
        if(boardGame == null) {
            validateHomeAwayNotEquals(game);
            return boardGameFactory.startNewGame(game);
        }
        throw new GameAlreadyStartedException(game);
    }

    private void validateHomeAwayNotEquals(Game game) {
        gamesInProgress.keySet().stream().filter(g -> g.isHomeOrAwayTeamEquals(game))
                .findAny().ifPresent(g -> {throw new GameAlreadyStartedException(g);});
    }

    @Override
    public boolean finishGame(Game game) {
        return gamesInProgress.remove(game) != null;
    }

    @Override
    public Optional<BoardGame> updateScore(ScoreUpdate scoreUpdate) {
        Objects.requireNonNull(scoreUpdate, "Score update cannot be null");
        return Optional.ofNullable(gamesInProgress.computeIfPresent(scoreUpdate.game(),
                (game, boardGame) -> scoreModifier.updateScore(boardGame, scoreUpdate.gameScore())));
    }

    @Override
    public Collection<BoardGame> getGamesByStartTime() {
        Set<BoardGame> summaryByScore = new TreeSet<>(BoardGame.START_TIME_AND_GAME_COMPARATOR);
        summaryByScore.addAll(gamesInProgress.values());
        return summaryByScore;
    }

    @Override
    public Collection<BoardGame> getSummaryByScore() {
        Set<BoardGame> summaryByScore = new TreeSet<>(BoardGame.SUMMARY_COMPARATOR);
        summaryByScore.addAll(gamesInProgress.values());
        return summaryByScore;
    }
}
