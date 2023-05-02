package main;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.List;

public class ShowingSolutionState implements DrawingBoardState {

    private final DrawingBoard drawingBoardContext;
    private boolean shouldExit;

    public ShowingSolutionState(DrawingBoard drawingBoardContext, MazeSolution solution) {
        this.drawingBoardContext = drawingBoardContext;
        shouldExit = false;
        drawSolution(solution);
    }

    private void drawSolution(MazeSolution solution) {
        Thread t = new Thread(() -> {
            drawVisited(solution.getVisited());
            drawSolution(solution.getSolution());
        });
        t.start();
    }

    private void drawVisited(List<Point> visited) {
        Board board = Board.getBoardInstance();
        for (Point p : visited) {
            if (shouldExit)
                break;
            board.setNodeAt(p.x, p.y, new Node("visited"));
            try {
                Thread.sleep(drawingBoardContext.getShowingVisitedSpeed());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            drawingBoardContext.repaint();
        }
    }

    private void drawSolution(List<Point> solution) {
        Board board = Board.getBoardInstance();
        long SOLUTION_SHOWING_INTERVAL = 8;
        for (Point p : solution) {
            if (shouldExit)
                break;
            board.setNodeAt(p.x, p.y, new Node("solution"));
            try {
                Thread.sleep(SOLUTION_SHOWING_INTERVAL);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            drawingBoardContext.repaint();
        }
    }

    private void stopSolutionThread() {
        shouldExit = true;
    }

    @Override
    public void exitState() {
        stopSolutionThread();
        clear();
    }

    @Override
    public void onMouseClick(MouseEvent e) {
        // do nothing
    }

    @Override
    public void onKeyPressed(KeyEvent e) {
        // do nothing
    }

    @Override
    public void onKeyReleased(KeyEvent e) {
        // do nothing
    }

    @Override
    public void clear() {
        clearSolution();
        drawingBoardContext.repaint();
    }

    private void clearSolution() {
        // clears only the solution on the board
        Board board = Board.getBoardInstance();
        for (int i = 0; i < board.height(); i++) {
            for (int j = 0; j < board.width(); j++) {
                if (board.nodeAt(j, i).type.equals("solution") || board.nodeAt(j, i).type.equals("visited")) {
                    board.setNodeAt(j, i, new Node("empty"));
                }
            }
        }
    }
}
