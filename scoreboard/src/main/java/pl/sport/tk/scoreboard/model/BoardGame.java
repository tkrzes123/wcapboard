package pl.sport.tk.scoreboard.model;

import java.time.Instant;

public interface BoardGame {

    Game getGame();

    Instant getStartTime();

    GameScore getScore();

}
