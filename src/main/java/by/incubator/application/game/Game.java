package by.incubator.application.game;

import by.incubator.application.players.AbstractPlayer;
import by.incubator.application.players.impl.HumanPlayer;
import by.incubator.application.players.Role;

public class Game {
    private final Board board;
    private final AbstractPlayer crossPlayer;
    private final AbstractPlayer zeroPlayer;
    private int turn = 1;
    private boolean isFinished;
    private Role winner = null;

    public Role getWinner() {
        return winner;
    }

    public Game(AbstractPlayer player1, AbstractPlayer player2) {
        if(player1.getRole() == Role.CROSS) {
            crossPlayer = player1;
            zeroPlayer = player2;
        } else {
            crossPlayer = player2;
            zeroPlayer = player1;
        }

        board = player1.getBoard();
    }

    public void nextTurn(int field) {
        if(turn % 2 == 1) {
            crossPlayer.move(field);
            turn++;

            if (WinChecker.check(getBoard())) {
                winner = Role.CROSS;
                return;
            }
            if (! (zeroPlayer instanceof HumanPlayer)) {
                zeroPlayer.move(-1);
                turn++;
                if (WinChecker.check(getBoard())) {
                    winner = Role.ZERO;
                }
            }
        } else {
            zeroPlayer.move(field);
            turn++;

            if (WinChecker.check(getBoard())) {
                winner = Role.ZERO;
                return;
            }
            if (! (crossPlayer instanceof HumanPlayer)) {
                crossPlayer.move(-1);
                turn++;
                if (WinChecker.check(getBoard())) {
                    winner = Role.CROSS;
                }
            }
        }
    }

    public Board getBoard() {
        return board;
    }

    public boolean isFinished() {
        if (turn == 10) {
            isFinished = true;
        }
        return isFinished;
    }
}
