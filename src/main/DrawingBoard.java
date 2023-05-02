package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class DrawingBoard extends JPanel {

    private DrawingBoardState drawingBoardState;

    private Board board;
    private String chosenAlgo = "";
    private String nodeTypeSelected = "";
    private final Dimension windowDimensions;
    private MazeAlgorithm mazeAlgorithm;
    private long showingVisitedSpeed = 2;

    public DrawingBoard(Dimension dimensions) {
        windowDimensions = dimensions;
        Node.initImage();
        Node.rescaleImage();
        createBoard();
        setPreferredSize(dimensions);
        setBackground(Color.BLACK);
        setFocusable(true);

        drawingBoardState = new DrawMazeState(this);
        nodeTypeSelected = "wall";  // default

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                drawingBoardState.onMouseClick(e);
            }
        });
        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                drawingBoardState.onMouseClick(e);
            }
        });
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                drawingBoardState.onKeyPressed(e);
            }
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                drawingBoardState.onKeyReleased(e);
            }
        });
    }

    public void setSpeed(long speed) {
        showingVisitedSpeed = speed;
    }

    public long getShowingVisitedSpeed() {
        return showingVisitedSpeed;
    }

    public void onNodeTypeSelect(String nodeType) {
        nodeTypeSelected = nodeType;
    }

    public void onAlgorithmChoose(String choice) {
        chosenAlgo = choice;
    }

    public String getNodeTypeSelected() {
        return nodeTypeSelected;
    }

    public void setNodeTypeSelected(String nodeTypeSelected) {
        this.nodeTypeSelected = nodeTypeSelected;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBoard(g);
    }

    public void onButtonClick(String button) {
        switch (button) {
            case "Run" -> run();
            case "+" -> increaseBoardSize();
            case "-" -> decreaseBoardSize();
            case "Drawing Mode" -> drawingModeSelect();
        }
    }

    private void run() {
        if (chosenAlgo.equals(""))
            return;
        if (!Board.getBoardInstance().hasEnd() || !Board.getBoardInstance().hasStart())
            return;
        // start solving
        mazeAlgorithm = MazeAlgorithmFactory.create(chosenAlgo);
        MazeSolution solution = mazeAlgorithm.solve();
        if (!solution.hasSolution()) {
            return;
        }
        drawingBoardState.exitState();
        drawingBoardState = new ShowingSolutionState(this, solution);
        repaint();
    }

    private void drawingModeSelect() {
        drawingBoardState.exitState();
        drawingBoardState = new DrawMazeState(this);
        repaint();
    }

    private void increaseBoardSize() {
        // need to increase board size and repaint
        int scale = NodeSizeScales.getNextIncreasingScale();
        if (scale == -1)
            return;
        NodeCoordinator.NODE_SIZE = scale;
        createBoard();
        drawingBoardState.exitState();
        drawingBoardState = new DrawMazeState(this);
        repaint();
    }

    private void decreaseBoardSize() {
        // need to decrease board size and repaint
        int scale = NodeSizeScales.getNextDecreasingScale();
        if (scale == -1)
            return;
        NodeCoordinator.NODE_SIZE = scale;
        createBoard();
        drawingBoardState.exitState();
        drawingBoardState = new DrawMazeState(this);
        repaint();
    }

    private void createBoard() {
        // creates board based on windows bounds and node_size
        int rows = windowDimensions.height / NodeCoordinator.NODE_SIZE;
        int cols = windowDimensions.width / NodeCoordinator.NODE_SIZE;
        while (NodeCoordinator.boardSpaceToPixelSpace(rows) > windowDimensions.height)
            rows--;
        while (NodeCoordinator.boardSpaceToPixelSpace(cols) > windowDimensions.width)
            cols--;
        Node.rescaleImage();
        board = Board.newBoard(rows, cols);
    }
    
    private void drawBoard(Graphics g) {
        for (int i = 0; i < board.height(); i++) {
            for (int j = 0; j < board.width(); j++) {
                int x = NodeCoordinator.boardSpaceToPixelSpace(j);
                int y = NodeCoordinator.boardSpaceToPixelSpace(i);
                Node node = board.nodeAt(j, i);
                g.drawImage(node.image, x, y, null);
            }
        }
    }
}
