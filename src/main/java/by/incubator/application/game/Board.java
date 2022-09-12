package by.incubator.application.game;

import by.incubator.application.players.Role;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Board {
    private final Role[] board;

    public List<Integer> getFreeFields() {
        return freeFields;
    }

    private final List<Integer> freeFields = new ArrayList<>();

    {
        for (int i = 0; i < 9; i++) {
            freeFields.add(i);
        }
    }

    public Board() {
        board = new Role[9];
    }

    public void move(Role role, int field) {
        if (!freeFields.contains(field)) {
            throw new IllegalArgumentException("Incorrect field: " + field);
        } else {
            board[field] = role;
            freeFields.remove(new Integer(field));
        }
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
