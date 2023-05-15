package pl.sport.tk.scoreboard.model;

import pl.sport.tk.scoreboard.model.ex.IllegalScoreChangeException;

public class BoardGameScoreValidatedModifier implements BoardGameScoreModifier {

    /**
     * Updates game score. Before update score is validated. Home and away score
     * cannot be less than respectively current score and score can be changed only by one goal.
     * @param boardGame game to score change
     * @param newScore new score
     * @throws IllegalScoreChangeException in case provided score update cannot be performed,
     * eg. change score from 0-0 to 2-0 or 1-0 to 0-1 or 2-0 to 1-0
     */
    @Override
    public BoardGame updateScore(BoardGame boardGame, GameScore newScore) {
        if(scoreChanged(boardGame, newScore)) {
            validateScoreChangeDiff(boardGame, newScore);
            validateOneScoreEquals(boardGame, newScore);
            return new BoardGameDefault(boardGame.getGame(), boardGame.getStartTime(), newScore);
        }
        return boardGame;
    }

    private boolean scoreChanged(BoardGame boardGame, GameScore newScore) {
        return !boardGame.getScore().equals(newScore);
    }

    private void validateScoreChangeDiff(BoardGame boardGame, GameScore newScore) {
        if(newScore.getScoreDiff(boardGame.getScore()) != 1) {
            throwIllegalScoreChangeException(boardGame, newScore);
        }
    }

    private void validateOneScoreEquals(BoardGame boardGame, GameScore newScore) {
        GameScore oldScore = boardGame.getScore();
        if(!oldScore.home().equals(newScore.home()) && !oldScore.away().equals(newScore.away())) {
            throwIllegalScoreChangeException(boardGame, newScore);
        }
    }

    private void throwIllegalScoreChangeException(BoardGame boardGame, GameScore newScore) {
        throw new IllegalScoreChangeException(ScoreUpdate.of(boardGame.getGame(), newScore), boardGame.getScore());
    }
}
