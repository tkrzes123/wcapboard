package pl.sport.tk.scoreboard.model;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

public record Team(String name) implements Comparable<Team> {

    public Team(String name) {
        Objects.requireNonNull(name, "Team name cannot be null");
        if(StringUtils.isAllBlank(name)) {
            throw new IllegalArgumentException("Team name cannot be blank");
        }
        this.name = name.strip();
    }

    public static Team of(String teamName) {
        return new Team(teamName);
    }

    @Override
    public int compareTo(Team o) {
        return name.compareTo(o.name());
    }
}
