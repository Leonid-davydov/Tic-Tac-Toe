package by.incubator.application.players.impl;

import by.incubator.application.game.Board;
import by.incubator.application.players.AbstractPlayer;
import by.incubator.application.players.Role;

public class HumanPlayer extends AbstractPlayer {
    public HumanPlayer() {}

    public HumanPlayer(Board board, Role role) {
        super(board, role);
    }

    public void move(int field) {
        board.move(role, field);
    }
}
