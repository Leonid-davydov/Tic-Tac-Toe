package by.incubator.application.game;

import by.incubator.application.players.Role;

import java.util.Optional;

public class Board {
    private final Role[] board;

    public Board() {
        board = new Role[9];
    }

    public void move(Role role, int field) {
        board[field] = role;
    }

    public Role[] getBoard() {
        return board;
    }

    public Role getField(int field) {
        return board[field];
    }

    public int[] getDto() {
        int[] dto = new int[9];

        for (int i = 0; i < dto.length; i++) {
            if (board[i] == Role.CROSS) {
                dto[i] = 1;
            } else if (board[i] == Role.ZERO) {
                dto[i] = -1;
            } else {
                dto[i] = 0;
            }
        }

        return dto;
    }
}
