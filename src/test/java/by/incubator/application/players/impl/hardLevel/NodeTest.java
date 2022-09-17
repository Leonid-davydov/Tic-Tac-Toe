package by.incubator.application.players.impl.hardLevel;

import by.incubator.application.players.Role;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

public class NodeTest {
    @Test
    public void testGrades() {
        int[][] testCases = {
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {1, 0, -1, 0, 0, 0, 0, 0, 0},
                {1, 0, 0, 0, -1, 0, 0, 0, 0},
                {1, -1, 1, -1, -1, 1, 0, 1, -1},
                {1, -1, 1, -1, -1, 1, 0, 1, 0},
                {1, -1, 1, -1, -1, 0, 0, 1, 1},
                {1, -1, 1, -1, -1, 0, -1, 1, 1}
        };

        int[] grades = {2, 1, 2, 2, 2, 3, 1};

        for (int i = 0; i < grades.length; i++) {
            Assert.assertEquals(grades[i], new Node(testCases[i], Role.CROSS).getGrade());
        }

        for (int i = 0; i < grades.length; i++) {
            Assert.assertEquals(grades[i], new Node(testCases[i], Role.ZERO).getGrade());
        }
    }

    @Test
    public void testMoves() {
        int[][] crossTestCases = {
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {1, 0, 0, 0, -1, 0, 0, 0, 0},
                {1, -1, 1, -1, -1, 1, 0, 1, -1},
                {1, -1, 1, -1, -1, 0, -1, 1, 1},
                {1, 0, -1, 0, -1, 0, 0, 0, 1}
        };

        List<List<Integer>> crossGoodMoves = new ArrayList<>(6);
        crossGoodMoves.add(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8));
        crossGoodMoves.add(Arrays.asList(1, 2, 3, 5, 6, 7, 8));
        crossGoodMoves.add(Arrays.asList(6));
        crossGoodMoves.add(Arrays.asList(5));
        crossGoodMoves.add(Arrays.asList(6));

        int[][] zeroTestCases = {
                {1, -1, 1, -1, -1, 1, 0, 1, 0},
                {1, -1, 1, -1, -1, 0, 0, 1, 1}
        };

        List<List<Integer>> zeroGoodMoves = new ArrayList<>(6);
        zeroGoodMoves.add(Arrays.asList(8));
        zeroGoodMoves.add(Arrays.asList(5));

        Node node;
        List<Integer> moves;

        for (int i = 0; i < crossTestCases.length; i++) {
            node = new Node(crossTestCases[i], Role.CROSS);
            moves = new ArrayList<>();

            for (Node n : node.getMoves()) {
                moves.add(Node.findMove(node, n));
            }

            Assert.assertEquals(moves, crossGoodMoves.get(i));
        }

        for (int i = 0; i < zeroTestCases.length; i++) {
            node = new Node(zeroTestCases[i], Role.ZERO);
            moves = new ArrayList<>();

            for (Node n : node.getMoves()) {
                moves.add(Node.findMove(node, n));
            }

            Assert.assertEquals(moves, zeroGoodMoves.get(i));
        }
    }
}