package main.algorithms;

import main.MazeAlgorithm;
import main.MazeSolution;

import java.awt.*;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;


public class DepthFirstSearch extends MazeAlgorithm {

    private final LinkedHashSet<Point> visited;

    public DepthFirstSearch() {
        super();
        visited = new LinkedHashSet<>();
    }

    private Stack<Point> recurDFS(Stack<Point> stack, Point currentPos) {
        if (isEndNode(currentPos)) {
            return stack;
        }

        visited.add(currentPos);
        List<Point> neighbors = neighbors(currentPos);
        for (Point neighborPos : neighbors) {
            if (validSpace(neighborPos)) {
                stack.push(currentPos);
                return recurDFS(stack, neighborPos);
            }
        }
        // if here then nowhere to go
        if (stack.isEmpty()) {
            return stack;
        }
        currentPos = stack.pop();
        return recurDFS(stack, currentPos);
    }

    private List<Point> DFS() {
        Stack<Point> stack = new Stack<>();
        stack.push(board.getStartPos());
        Point current = stack.pop();
        do {
            if (isEndNode(current)) {
                break;
            }
            visited.add(current);
            List<Point> neighbors = neighbors(current);
            for (Point neighborPos : neighbors) {
                if (validSpace(neighborPos)) {
                    stack.push(current);
                    stack.push(neighborPos);
                }
            }
            current = stack.pop();
        } while (!stack.isEmpty());

        return stack;
    }

    @Override
    public MazeSolution solve() {
        Stack<Point> solution = recurDFS(new Stack<>(), board.getStartPos());
        if (solution.isEmpty())
            return new MazeSolution(new LinkedList<>(), new LinkedList<>());

        solution.remove(board.getStartPos());
        solution.remove(board.getEndPos());
        visited.remove(board.getStartPos());
        visited.remove(board.getEndPos());
        return new MazeSolution(solution, visited.stream().toList());
    }

    private boolean validSpace(Point pos) {
        return
                !visited.contains(pos) &&
                !board.nodeAt(pos.x, pos.y).type.equals("wall");
    }
}
