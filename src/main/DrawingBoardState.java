package main;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public interface DrawingBoardState {
    void onMouseClick(MouseEvent e);
    void onKeyPressed(KeyEvent e);
    void onKeyReleased(KeyEvent e);
    void clear();
    void exitState();   // needs to be called before each state transition
}
