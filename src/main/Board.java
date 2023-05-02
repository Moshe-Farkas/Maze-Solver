package main;


import java.awt.*;

public class Board {

    private Point startPos;
    private Point endPos;

    private static Board boardInstance;
    private Node[][] board;

    private Board(int rows, int cols) {
        initBoard(rows, cols);
    }

    public static Board newBoard(int rows, int cols) {
        boardInstance = new Board(rows, cols);
        return boardInstance;
    }
    public static Board getBoardInstance() {
        return boardInstance;
    }

    private void initBoard(int rows, int cols) {
        board = new Node[rows][cols];
        for (int i = 0; i < height(); i++) {
            for (int j = 0; j < width(); j++) {
                setNodeAt(j, i, new Node("empty"));
            }
        }
    }

    public Node nodeAt(int col, int row) {
        return board[row][col];
    }

    public void setNodeAt(int col, int row, Node node) {
        if (hasStart()) {
            if (overLapsStart(col, row)) {
                resetStart();
            }
        }
        if (hasEnd()) {
            if (overLapsEnd(col, row)) {
                resetEnd();
            }
        }
        switch (node.type) {
            case "start" -> handleStartPlacement(col, row);
            case "end" -> handleEndPlacement(col, row);
        }
        board[row][col] = node;
    }

    private void resetStart() {
        if (hasStart())
            board[startPos.y][startPos.x] = new Node("empty");
        startPos = null;
    }

    private void resetEnd() {
        if (hasEnd())
            board[endPos.y][endPos.x] = new Node("empty");
        endPos = null;
    }

    private void handleEndPlacement(int col, int row) {
        resetEnd();
        endPos = new Point(col, row);
    }

    private void handleStartPlacement(int col, int row) {
        resetStart();
        startPos = new Point(col, row);
    }

    private boolean overLapsStart(int col, int row) {
        return startPos.y == row && startPos.x == col;
    }

    private boolean overLapsEnd(int col, int row) {
        return endPos.y == row && endPos.x == col;
    }

    public int height() {
        return board.length;
    }

    public int width() {
        return board[0].length;
    }

    public Point getStartPos() {
        return startPos;
    }

    public Point getEndPos() {
        return endPos;
    }

    public boolean hasStart() {
        return startPos != null;
    }

    public boolean hasEnd() {
        return endPos != null;
    }

}
