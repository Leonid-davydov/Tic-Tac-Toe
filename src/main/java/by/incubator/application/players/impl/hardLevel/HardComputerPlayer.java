package by.incubator.application.players.impl.hardLevel;

import by.incubator.application.players.AbstractPlayer;
import by.incubator.application.players.Role;

import java.io.*;
import java.util.Arrays;
import java.util.List;

public class HardComputerPlayer extends AbstractPlayer {
    private static final String paht = "D:\\Programming\\Incubator\\Java Prod\\Projects\\TicTac\\src\\main\\resources\\nodes\\node";
    Node node;

    public HardComputerPlayer() {
    }

    public void init() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(paht))) {
            node = ((Node) ois.readObject());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        if (node == null) {
            node = new Node(board.getDto(), true);
        }
    }

    @Override
    public void move(int field) {
        Node currentNode = findCurrentNode(node, board.getDto());
        Node bestNode;
        List<Node> moves = currentNode.getMoves();
        int bestMove;

        bestNode = moves.stream().filter(node -> node.getGrade() == currentNode.getGrade()).findAny().get();
        bestMove = findMove(currentNode, bestNode);
        board.move(role, bestMove);
        node = bestNode;
    }

    private Node findCurrentNode(Node oldNode, int[] currentBoard) {
        if (role == Role.CROSS && node.getTurn() == 1) {
            return node;
        }

        List<Node> moves = oldNode.getMoves();
        return moves.stream().filter(a -> Arrays.equals(a.getBoard(), currentBoard)).findAny().get();
    }

    private int findMove(Node current, Node wanted) {
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
}
