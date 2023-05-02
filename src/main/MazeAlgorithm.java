package main;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class MazeAlgorithm {

    private final int width;
    private final int height;
    protected final Board board;

    public MazeAlgorithm() {
        board = Board.getBoardInstance();
        height = board.height();
        width = board.width();
    }

    public abstract MazeSolution solve();

    protected List<Point> neighbors(Point pos) {
        List<Point> neighbors = new ArrayList<>();
        int row = pos.y;
        int col = pos.x;
        // up
        if (!outOfBounds(col, row - 1)) {
            neighbors.add(new Point(col, row - 1));
        }
        // right
        if (!outOfBounds(col + 1, row)) {
            neighbors.add(new Point(col + 1, row));
        }
        // down
        if (!outOfBounds(col, row + 1)) {
            neighbors.add(new Point(col, row + 1));
        }
        // left
        if (!outOfBounds(col - 1, row)) {
            neighbors.add(new Point(col - 1, row));
        }
        return neighbors;
    }

    private boolean outOfBounds(int col, int row) {
        return col >= width || col < 0 || row >= height || row < 0;
    }

    protected boolean isEndNode(Point pos) {
        return pos.equals(board.getEndPos());
    }
}
