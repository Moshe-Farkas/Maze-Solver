package main;

import java.awt.Point;
import java.util.List;

public class MazeSolution{

    private final List<Point> solution;
    private final List<Point> visited;

    public MazeSolution(List<Point> solution, List<Point> visited) {
        this.solution = solution;
        this.visited = visited;
    }

    public boolean hasSolution() {
        return solution.size() > 0 || visited.size() > 0;
    }

    public List<Point> getSolution() {
        return solution;
    }

    public List<Point> getVisited() {
        return visited;
    }
}