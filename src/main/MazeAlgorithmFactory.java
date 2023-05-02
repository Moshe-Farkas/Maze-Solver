package main;

import main.algorithms.*;

public class MazeAlgorithmFactory {

    public static MazeAlgorithm create(String algorithm) {
        return switch (algorithm) {
            case "DFS" -> new DepthFirstSearch();
            case "BFS" -> new BreadthFirstSearch();
            case "AStar" -> new AStar();
            case "Dijkstra" -> new Dijkstra();
            case "Bidirectional-BFS" -> new BiDirBFS();
            default -> null;
        };
    }
}
