package main.algorithms;

import main.MazeAlgorithm;
import main.MazeSolution;

import java.awt.*;
import java.util.*;
import java.util.List;

public class BreadthFirstSearch extends MazeAlgorithm {

    private Set<Point> visited;

    public BreadthFirstSearch() {
        super();
        visited = new LinkedHashSet<>();
    }

    private List<Point> bfs() {
        Map<Point, Point> parents = new HashMap<>();
        ArrayDeque<Point> queue = new ArrayDeque<>();
        queue.add(board.getStartPos());
        while (!queue.isEmpty()) {
            Point currentPos = queue.pop();
            if (isEndNode(currentPos)) {
                return constructShortestPath(parents);
            }
            visited.add(currentPos);
            List<Point> neighbors = neighbors(currentPos);
            for (Point neighborPos : neighbors) {
                if (validSpace(neighborPos.x, neighborPos.y)) {
                    queue.add(neighborPos);
                    visited.add(neighborPos);
                    parents.put(neighborPos, currentPos);
                }
            }
        }
        // if here then no solution
        visited = new LinkedHashSet<>();
        return new LinkedList<>();
    }

    @Override
    public MazeSolution solve() {
        List<Point> shortestPath = bfs();
        visited.remove(board.getEndPos());
        visited.remove(board.getStartPos());
        return new MazeSolution(shortestPath, visited.stream().toList());
    }

    private List<Point> constructShortestPath(Map<Point, Point> parents) {
        List<Point> shortestPath = new LinkedList<>();
        Point current = board.getEndPos();
        while (parents.containsKey(current)) {
            shortestPath.add(current);
            current = parents.get(current);
        }
        Collections.reverse(shortestPath);
        shortestPath.remove(board.getEndPos());
        shortestPath.remove(board.getStartPos());
        return shortestPath;
    }

    private boolean validSpace(int col, int row) {
        return
                !visited.contains(new Point(col, row)) &&
                !board.nodeAt(col, row).type.equals("wall");
    }
}
