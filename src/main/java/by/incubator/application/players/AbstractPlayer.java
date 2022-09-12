package by.incubator.application.players;

import by.incubator.application.game.Board;

public abstract class AbstractPlayer {
    protected Board board;
    protected Role role;

    public AbstractPlayer(Board board, Role role) {
        this.board = board;
        this.role = role;
    }

    public Board getBoard() {
        return board;
    }

    public Role getRole() {
        return role;
    }

    public abstract void move(int field);
}
