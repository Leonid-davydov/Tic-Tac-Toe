package by.incubator.application.players.impl.hardLevel;

import by.incubator.application.game.WinChecker;
import by.incubator.application.players.Role;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Node implements Serializable {
    boolean isSmart;
    int[] board;
    int turn = 1;
    int grade;
    boolean isFinished;
    List<Node> moves = new ArrayList<>();

    public Node(int[] board, Role role) {
        this (board, initIsSmart(role, countTurn(board)));
    }

    private Node(int[] board, boolean isSmart) {
        this.board = board;
        this.isSmart = isSmart;

        turn = countTurn(board);
        initGrade();

        if (!isFinished) {
            init();
        }
    }

    public static int findMove(Node current, Node wanted) {
        int move = -1;

        int[] old = current.getBoard();
        int[] future = wanted.getBoard();

        for (int i = 0; i < 9; i++) {
            if (old[i] != future[i]) {
                move = i;
            }
        }

        return move;
    }

    public int[] getBoard() {
        return board;
    }

    public void setBoard(int[] board) {
        this.board = board;
    }

    public int getGrade() {
        return grade;
    }

    public List<Node> getMoves() {
        return moves;
    }

    public int getTurn() {
        return turn;
    }

    private static int countTurn(int[] board) {
        int turn = 1;

        for (int i : board) {
            if (i != 0) {
                turn++;
            }
        }

        return turn;
    }

    private static boolean initIsSmart(Role role, int turn) {
        return (role == Role.CROSS && turn % 2 == 1) || (role == Role.ZERO && turn % 2 == 0);
    }

    private void init() {
        initMoves();
        evaluateGrade();

        if (isSmart) {
            deleteBadMoves();
        }
    }

    public void initMoves() {
        for (int i = 0; i < 9; i++) {
            if (board[i] == 0) {
                Node newNode = getNextNode(i);
                addMove(newNode);
            }
        }
    }

    private void addMove(Node move) {
        if (!isSmart || isGood(move)) {
            moves.add(move);
        }
    }

    private void initGrade() {
        if (WinChecker.checkInt(board)) {
            isFinished = true;

            if (turn % 2 == 1) {
                grade = 3;
            } else {
                grade = 1;
            }
        }

        if (turn == 10 && !isFinished) {
            isFinished = true;
            grade = 2;
        }
    }

    private Node getNextNode(int field) {
        int[] newBoard = Arrays.copyOfRange(board, 0, 9);

        if (turn % 2 == 1) {
            newBoard[field] = 1;
        } else {
            newBoard[field] = -1;
        }

        return new Node(newBoard, !isSmart);
    }

    private boolean isGood(Node node) {
        if (moves.isEmpty()) {
            return true;
        } else if (turn % 2 == 1) {
            return node.getGrade() <= moves.stream().mapToInt(a -> a.getGrade()).filter(b -> b > 0).min().getAsInt();
        } else {
            return node.getGrade() >= moves.stream().mapToInt(Node::getGrade).max().getAsInt();
        }
    }

    private void deleteBadMoves() {
        List<Node> bestMoves = new ArrayList<>();

        for (Node node : moves) {
            if (isGood(node)) {
                bestMoves.add(node);
            }
        }

        moves = bestMoves;
    }

    private void evaluateGrade() {
        if (grade == 0) {
            if (turn % 2 == 1) {
                grade = moves.stream().mapToInt(a -> a.getGrade()).filter(b -> b > 0).min().getAsInt();
            } else {
                grade = moves.stream().mapToInt(a -> a.getGrade()).max().getAsInt();
            }
        }
    }
}
