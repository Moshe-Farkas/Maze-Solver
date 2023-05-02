package main.algorithms;


import main.MazeAlgorithm;
import main.MazeSolution;

import java.awt.*;


public class AStar extends MazeAlgorithm {

    private int EDGE_TO_NEIGHBOR = 1;

    @Override
    public MazeSolution solve() {
        Dijkstra aStar = new Dijkstra() {
            @Override
            protected int heuristic(Point pos) {
                return euclideanDistance(pos) + EDGE_TO_NEIGHBOR;
//                return manhattanDistance(pos) + EDGE_TO_NEIGHBOR;
            }
        };
        return aStar.solve();
    }

    private int euclideanDistance(Point pos) {
        return (int) Math.sqrt(
                Math.pow(pos.x - board.getEndPos().x, 2) +
                Math.pow(pos.y - board.getEndPos().y, 2)
        );
    }

    private int manhattanDistance(Point pos) {
        int x;
        int y;
        if (pos.x > board.getEndPos().x)
            x = pos.x - board.getEndPos().x;
        else
            x = board.getEndPos().x - pos.x;

        if (pos.y > board.getEndPos().y)
            y = pos.y - board.getEndPos().y;
        else
            y = board.getEndPos().y - pos.y;
        return x + y;
    }
}