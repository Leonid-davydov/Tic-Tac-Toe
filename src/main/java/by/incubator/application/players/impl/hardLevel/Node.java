package by.incubator.application.players.impl.hardLevel;

import by.incubator.application.game.WinChecker;
import by.incubator.application.players.Role;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Node {
    boolean isSmart;
    int[] board;
    int turn = 1;
    int grade;
    boolean isFinished;
    List<Node> moves = new ArrayList<>();

    public Node(int[] board, boolean isSmart) {
        this.board = board;
        this.isSmart = isSmart;

        for (int i : board) {
            if (i != 0) {
                turn++;
            }
        }

        if (WinChecker.checkInt(board)) {
            isFinished = true;

            if(turn % 2 == 1) {
                grade = 3;
            } else {
                grade = 1;
            }
        }
        if(turn == 10 && !isFinished) {
            isFinished = true;
            grade = 2;
        }

        if(!isFinished) {
            init();
        }
    }

    @Override
    public String toString() {
        return "Node{" +
                "board=" + Arrays.toString(board) +
                ", turn=" + turn +
                ", grade=" + grade +
                ", isFinished=" + isFinished +
                '}';
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

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    public List<Node> getMoves() {
        return moves;
    }

    public void setMoves(List<Node> moves) {
        this.moves = moves;
    }

    public int getTurn() {
        return turn;
    }

    public void init() {
        if (turn % 2 == 1){
            for (int i = 0; i < 9; i++) {
                if (board[i] == 0) {
                    int[] newBoard = Arrays.copyOfRange(board, 0, 9);
                    newBoard[i] = 1;
                    Node newNode = new Node(newBoard, !isSmart);

                    if (!isSmart || (moves.isEmpty() || newNode.getGrade() <= moves.stream().mapToInt(a -> a.getGrade()).filter(b -> b > 0).min().getAsInt())) {
                        moves.add(newNode);
                    }

                    if (turn == 1){
                        System.out.println("Посчитан " + i + " ходов из 9 " + new Date());
                    }
                }
            }
        } else {
            for (int i = 0; i < 9; i++) {
                if (board[i] == 0) {
                    int[] newBoard = Arrays.copyOfRange(board, 0, 9);
                    newBoard[i] = -1;
                    Node newNode = new Node(newBoard, !isSmart);

                    if (!isSmart || (moves.isEmpty() || newNode.getGrade() >= moves.stream().mapToInt(a -> a.getGrade()).max().getAsInt())) {
                        moves.add(newNode);
                    }
                }
            }
        }

        if (grade == 0) {
            if (turn % 2 == 1) {
                grade = moves.stream().mapToInt(a -> a.getGrade()).filter(b -> b > 0).min().getAsInt();
            } else {
                grade = moves.stream().mapToInt(a -> a.getGrade()).max().getAsInt();
            }
        }
    }
}
