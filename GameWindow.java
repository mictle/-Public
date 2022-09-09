
package project.graphics;

import project.graphics.GameCanvas;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class GameWindow extends JFrame{
  public GameWindow(){
    super("GameWindow");
    this.setBackground(Color.black);
    this.setSize(1200,900);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setVisible(true);
  }
  public void changeCanvas(JPanel canvas){
    getContentPane().removeAll();
    super.add(canvas);
    validate();
    repaint();
  }


}
