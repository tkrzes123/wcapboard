package pl.sport.tk.scoreboard.model.ex;

import pl.sport.tk.scoreboard.model.GameScore;
import pl.sport.tk.scoreboard.model.ScoreUpdate;

public class IllegalScoreChangeException extends IllegalArgumentException {

    private final ScoreUpdate scoreUpdate;
    private final GameScore gameScore;

    public IllegalScoreChangeException(ScoreUpdate scoreUpdate, GameScore gameScore) {
        super(String.format("Score cannot change for %s vs %s from: %d-%d to: %d-%d",
                scoreUpdate.game().home().name(), scoreUpdate.game().away().name(),
                gameScore.home().value(), gameScore.away().value(),
                scoreUpdate.homeScore().value(), scoreUpdate.awayScore().value()));
        this.scoreUpdate = scoreUpdate;
        this.gameScore = gameScore;
    }

    public ScoreUpdate getScoreUpdate() {
        return scoreUpdate;
    }

    public GameScore getGameScore() {
        return gameScore;
    }
}
