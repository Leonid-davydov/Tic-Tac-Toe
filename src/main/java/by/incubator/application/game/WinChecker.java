package by.incubator.application.game;

import by.incubator.application.players.Role;

public class WinChecker {
    private WinChecker() {
    }

    public static boolean check(Board board) {
        Role[] b = board.getBoard();

        if (b[0] != null && b[0] == b[1] && b[1] == b[2]) {
            return true;
        } else if (b[3] != null && b[3] == b[4] && b[4] == b[5]) {
            return true;
        } else if (b[6] != null && b[6] == b[7] && b[7] == b[8]) {
            return true;
        } else if (b[0] != null && b[0] == b[3] && b[3] == b[6]) {
            return true;
        } else if (b[1] != null && b[1] == b[4] && b[4] == b[7]) {
            return true;
        } else if (b[2] != null && b[2] == b[5] && b[5] == b[8]) {
            return true;
        } else if (b[0] != null && b[0] == b[4] && b[4] == b[8]) {
            return true;
        } else if (b[2] != null && b[2] == b[4] && b[4] == b[6]) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean checkInt(int[] b) {


        if (b[0] != 0 && b[0] == b[1] && b[1] == b[2]) {
            return true;
        } else if (b[3] != 0 && b[3] == b[4] && b[4] == b[5]) {
            return true;
        } else if (b[6] != 0 && b[6] == b[7] && b[7] == b[8]) {
            return true;
        } else if (b[0] != 0 && b[0] == b[3] && b[3] == b[6]) {
            return true;
        } else if (b[1] != 0 && b[1] == b[4] && b[4] == b[7]) {
            return true;
        } else if (b[2] != 0 && b[2] == b[5] && b[5] == b[8]) {
            return true;
        } else if (b[0] != 0 && b[0] == b[4] && b[4] == b[8]) {
            return true;
        } else if (b[2] != 0 && b[2] == b[4] && b[4] == b[6]) {
            return true;
        } else {
            return false;
        }
    }
}
