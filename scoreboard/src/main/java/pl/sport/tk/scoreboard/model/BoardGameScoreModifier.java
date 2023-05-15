package pl.sport.tk.scoreboard.model;

public interface BoardGameScoreModifier {

    BoardGame updateScore(BoardGame boardGame, GameScore newScore);
}
