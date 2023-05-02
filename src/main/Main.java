package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {

    private JMenuBar menuBar;
    private JFrame window = new JFrame();
    private DrawingBoard drawingBoard;

    public static void main(String[] args) {
        Main m = new Main();
        m.init();
    }

    private void init() {
        initMenuBar();
        int width = 1000;
        int height = 600;
        drawingBoard = new DrawingBoard(new Dimension(width, height));
        window.setContentPane(drawingBoard);
        window.setJMenuBar(menuBar);
        window.setDefaultCloseOperation(window.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.pack();
        window.setVisible(true);
    }

    private void initMenuBar() {
        menuBar = new JMenuBar();
        menuBar.add(createAlgorithmsMenu());
        menuBar.add(createNodeTypeMenu());
        menuBar.add(createButton("Drawing Mode"));
        menuBar.add(createButton("Run"));
        menuBar.add(createSpeedMenu());
        menuBar.add(createButton("+"));
        menuBar.add(createButton("-"));
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawingBoard.onButtonClick(button.getText());
            }
        });
        button.setFocusable(false);
        return button;
    }

    private JMenu createSpeedMenu() {
        long FAST = 2;
        long NORMAL = 8;
        long SLOW = 14;
        long VERY_SLOW = 20;
        JMenuItem fast = new JMenuItem("Fast");
        fast.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawingBoard.setSpeed(FAST);
            }
        });
        JMenuItem normal = new JMenuItem("Normal");
        normal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawingBoard.setSpeed(NORMAL);
            }
        });
        JMenuItem slow = new JMenuItem("Slow");
        slow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawingBoard.setSpeed(SLOW);
            }
        });
        JMenuItem verySlow = new JMenuItem("Very-Slow");
        verySlow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawingBoard.setSpeed(VERY_SLOW);
            }
        });
        JMenu speedMenu = new JMenu("Speed");
        speedMenu.add(fast);
        speedMenu.add(normal);
        speedMenu.add(slow);
        speedMenu.add(verySlow);
        return speedMenu;
    }

    private JMenu createAlgorithmsMenu() {
        JMenu algorithmChooseMenu = new JMenu("Algorithms");
        JMenuItem dfs = new JMenuItem("DFS");
        dfs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawingBoard.onAlgorithmChoose("DFS");
            }
        });
        algorithmChooseMenu.add(dfs);
        JMenuItem bfs = new JMenuItem("BFS");
        bfs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawingBoard.onAlgorithmChoose("BFS");
            }
        });
        algorithmChooseMenu.add(bfs);
        JMenuItem biDirBFS = new JMenuItem("Bidirectional-BFS");
        biDirBFS.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawingBoard.onAlgorithmChoose("Bidirectional-BFS");
            }
        });
        algorithmChooseMenu.add(biDirBFS);
        JMenuItem dijkstra = new JMenuItem("Dijkstra");
        dijkstra.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawingBoard.onAlgorithmChoose("Dijkstra");
            }
        });
        algorithmChooseMenu.add(dijkstra);
        JMenuItem AStar = new JMenuItem("AStar");
        AStar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawingBoard.onAlgorithmChoose("AStar");
            }
        });
        algorithmChooseMenu.add(AStar);
        return algorithmChooseMenu;
    }

    private JMenu createNodeTypeMenu() {
        JMenu nodeTypeMenu = new JMenu("Node Types");
        JMenuItem wall = new JMenuItem("Wall");
        wall.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawingBoard.onNodeTypeSelect("wall");
            }
        });
        JMenuItem start = new JMenuItem("Start");
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawingBoard.onNodeTypeSelect("start");
            }
        });
        JMenuItem end = new JMenuItem("End");
        end.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawingBoard.onNodeTypeSelect("end");
            }
        });
        nodeTypeMenu.add(wall);
        nodeTypeMenu.add(start);
        nodeTypeMenu.add(end);
        return nodeTypeMenu;
    }
}