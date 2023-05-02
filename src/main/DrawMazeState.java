package main;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class DrawMazeState implements DrawingBoardState {

    private boolean isShiftDown = false;
    private boolean placedStart = false;
    private boolean placedEnd = false;
    private Point startPos;
    private Point endPos;
    private DrawingBoard drawingBoardContext;

    public DrawMazeState(DrawingBoard drawingBoardContext) {
        this.drawingBoardContext = drawingBoardContext;
        drawingBoardContext.setNodeTypeSelected("wall");
    }

    @Override
    public void exitState() {

    }

    @Override
    public void onMouseClick(MouseEvent e) {
        if (!validMouseEvent(e))
            return;
        int row = NodeCoordinator.pixelSpaceToBoardSpace(e.getY());
        int col = NodeCoordinator.pixelSpaceToBoardSpace(e.getX());

        if (isShiftDown) {
            Board.getBoardInstance().setNodeAt(col, row, new Node("empty"));
            drawingBoardContext.repaint();
            return;
        }
        String nodeTypeSelected = drawingBoardContext.getNodeTypeSelected();
        Board.getBoardInstance().setNodeAt(col, row, new Node(nodeTypeSelected));
        drawingBoardContext.repaint();
    }

    @Override
    public void onKeyPressed(KeyEvent e) {
        if (e.isShiftDown()) {
            isShiftDown = true;
        }
        if (e.getKeyChar() == 'c') {
            clear();
        }
        drawingBoardContext.repaint();
    }

    @Override
    public void onKeyReleased(KeyEvent e) {
        if (isShiftDown) {
            isShiftDown = false;
        }
    }

    @Override
    public void clear() {
        // needs to clear entire board
        Board board = Board.getBoardInstance();
        for (int i = 0; i < board.height(); i++) {
            for (int j = 0; j < board.width(); j++) {
                board.setNodeAt(j, i, new Node("empty"));
            }
        }
    }

    private boolean validMouseEvent(MouseEvent e) {
        int row = NodeCoordinator.pixelSpaceToBoardSpace(e.getY());
        int col = NodeCoordinator.pixelSpaceToBoardSpace(e.getX());
        if (row < 0)
            return false;
        if (row >= Board.getBoardInstance().height())
            return false;
        if (col < 0)
            return false;
        if (col >= Board.getBoardInstance().width())
            return false;
        return true;
    }
}
