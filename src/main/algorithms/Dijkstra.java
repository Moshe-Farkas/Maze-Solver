package main.algorithms;
import main.MazeAlgorithm;
import main.MazeSolution;
import java.awt.*;
import java.util.*;
import java.util.List;

public class Dijkstra extends MazeAlgorithm {

    private final Map<Point, CustomNode> nodes;
    private final Set<Point> visited;
    private final int EDGE_TO_NEIGHBOR = 1;

    public Dijkstra() {
        super();
        nodes = new LinkedHashMap<>();
        visited = new LinkedHashSet<>();
        initNodes();
    }

    @Override
    public MazeSolution solve() {
        // check if there even is a solution
        MazeAlgorithm dfs = new DepthFirstSearch();
        if (!dfs.solve().hasSolution()) {
            return new MazeSolution(new LinkedList<>(), new LinkedList<>());
        }
        Point current = nextPos();
        while (!current.equals(board.getEndPos())) {
            visited.add(current);
            List<Point> neighbors = neighbors(current);

            for (Point neighborPos : neighbors) {
                if (validSpace(neighborPos)) {
                    if (shouldUpdateDistance(current, neighborPos)) {
                        nodes.get(neighborPos).distance = nodes.get(current).distance + heuristic(neighborPos);
                        nodes.get(neighborPos).prev = nodes.get(current);
                    }
                }
            }
            current = nextPos();
        }
        visited.remove(board.getStartPos());
        visited.remove(board.getStartPos());
        return new MazeSolution(constructShortestPath(), visited.stream().toList());
    }

    protected int heuristic(Point pos) {
        // default heuristic
        return EDGE_TO_NEIGHBOR;
    }

    private void initNodes() {
        for (int i = 0; i < board.height(); i++) {
            for (int j = 0 ; j < board.width(); j++) {
                if (board.nodeAt(j, i).type.equals("wall"))
                    continue;
                Point p = new Point(j, i);
                CustomNode node = new CustomNode(p);
                nodes.put(p, node);
            }
        }
        // set start to 0
        nodes.get(board.getStartPos()).distance = 0;
    }

    private boolean validSpace(Point pos) {
        return !visited.contains(pos) && !board.nodeAt(pos.x, pos.y).type.equals("wall");
    }

    private Point nextPos() {
        Point minPos = null;
        for (Point p : nodes.keySet()) {
            if (visited.contains(p))
                continue;
            minPos = p;
            break;
        }
        for (Point p : nodes.keySet()) {
            if (visited.contains(p))
                continue;
            if (nodes.get(minPos).distance > nodes.get(p).distance) {
                minPos = p;
            }
        }
        return minPos;
    }

    private List<Point> constructShortestPath() {
        List<Point> shortestPath = new LinkedList<>();
        CustomNode current = nodes.get(board.getEndPos());
        while (current != null) {
            shortestPath.add(current.pos);
            current = current.prev;
        }
        shortestPath.remove(board.getStartPos());
        shortestPath.remove(board.getEndPos());
        Collections.reverse(shortestPath);
        return shortestPath;
    }

    private boolean shouldUpdateDistance(Point current, Point neighbor) {
        return nodes.get(current).distance + EDGE_TO_NEIGHBOR < nodes.get(neighbor).distance;
    }

    protected class CustomNode {
        public int distance = Integer.MAX_VALUE;
        public CustomNode prev = null;
        public Point pos;

        public CustomNode(Point pos) {
            this.pos = pos;
        }

        public String toString() {
            return pos.toString() + ", " + distance;
        }
    }
}