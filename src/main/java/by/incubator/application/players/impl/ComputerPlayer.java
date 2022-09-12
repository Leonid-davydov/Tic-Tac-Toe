package by.incubator.application.players.impl;

import by.incubator.application.game.Board;
import by.incubator.application.players.AbstractPlayer;
import by.incubator.application.players.Role;

import java.util.List;

public class ComputerPlayer extends AbstractPlayer {
    public ComputerPlayer(Board board, Role role) {
        super(board, role);
    }

    public void move(int field) {
        List<Integer> freeFields = board.getFreeFields();
        field = freeFields.get((int) (Math.random() * (freeFields.size() - 1)));
        board.move(role, field);
    }
}
