
package project.graphics;

import project.graphics.GameCanvas;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class GameWindow extends JFrame{
  public GameWindow(GameCanvas canvas){
    super("GameWindow");
    this.setBackground(Color.black);
    this.setSize(1200,900);
    this.add(canvas);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setVisible(true);
  }
}
