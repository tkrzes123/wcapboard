package pl.sport.tk.scoreboard.model;

public class BoardGameScoreSimpleModifier implements BoardGameScoreModifier {
    @Override
    public BoardGame updateScore(BoardGame boardGame, GameScore newScore) {
        if(scoreChanged(boardGame, newScore)) {
            return new BoardGameDefault(boardGame.getGame(), boardGame.getStartTime(), newScore);
        }
        return boardGame;
    }

    private boolean scoreChanged(BoardGame boardGame, GameScore newScore) {
        return !boardGame.getScore().equals(newScore);
    }
}
