package project.stageeditor;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import project.tools.*;
import project.graphics.*;
import project.objects.*;
import project.*;


public class StageEditorPrepare extends JPanel implements ActionListener{
  JLabel l1,l2,l3,l4,l5,l6,l7,l8;
  JTextField txFieldX, txFieldY, txFieldI, txFieldF1, txFieldF2;
  NumButton button1,button2;
  Sound soundManager;
  GameWindow gameWindow;
  Controller controller;
  public StageEditorPrepare(GameWindow w, Controller c,Sound sm){
    gameWindow=w;
    controller = c;
    soundManager = sm;
    w.changeCanvas(this);

    this.setLayout(null);
    this.setBackground(Color.yellow);

    txFieldX = new JTextField();
    txFieldY = new JTextField();
    txFieldI = new JTextField();
    txFieldF1 = new JTextField();
    txFieldF2 = new JTextField();

    l1=new JLabel("<html><p><font size=20>新規作成</font></p></html>");
    l1.setBounds(20,10,1000,100);
    this.add(l1);
    l2=new JLabel("<html><p><font size=16>ステージサイズ</font></p></html>");
    l2.setBounds(50,120,300,60);
    this.add(l2);
    l3=new JLabel("<html><p><font size=16>X</font></p></html>");
    l3.setBounds(350,120,100,60);
    this.add(l3);
    txFieldX.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 24));
    txFieldX.setBounds(400,125,70,60);
    this.add(txFieldX);
    l4=new JLabel("<html><p><font size=16>Y</font></p></html>");
    l4.setBounds(500,120,100,60);
    this.add(l4);
    txFieldY.setBounds(550,125,70,60);
    txFieldY.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 24));
    this.add(txFieldY);
    l5=new JLabel("<html><p><font size=16>背景画像ファイル</font></p></html>");
    l5.setBounds(50,220,400,60);
    this.add(l5);
    txFieldI.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 24));
    txFieldI.setBounds(400,225,400,60);
    this.add(txFieldI);
    l6=new JLabel("<html><p><font size=16>ステージ名</font></p></html>");
    l6.setBounds(50,320,400,60);
    this.add(l6);
    txFieldF1.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 24));
    txFieldF1.setBounds(400,325,400,60);
    this.add(txFieldF1);

    button1 = new NumButton(0,"<html><p><font size=16>ステージ新規作成</font></p></html>");
    button1.addActionListener(this);
    button1.setBounds(700,420,400,50);
    this.add(button1);
    l7=new JLabel("<html><p><font size=20>ステージを開く</font></p></html>");
    l7.setBounds(20,470,1000,100);
    this.add(l7);
    l8=new JLabel("<html><p><font size=16>ステージ名</font></p></html>");
    l8.setBounds(50,570,400,60);
    this.add(l8);
    txFieldF2.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 24));
    txFieldF2.setBounds(400,575,400,60);
    this.add(txFieldF2);
    button2 = new NumButton(1,"<html><p><font size=16>ステージを開く</font></p></html>");
    button2.addActionListener(this);
    button2.setBounds(700,650,400,50);
    this.add(button2);

    repaint();
    w.setVisible(true);

  }
  public void paintComponent(Graphics g) {
    g.setColor(new Color(255,249,177));
    g.fillRect(0,0,1200,900);
  }
  public void actionPerformed(ActionEvent e) {
    NumButton b = (NumButton)e.getSource();
    if(b.getNum()==0){
      try{
        File file = new File("./StageData/"+txFieldF1.getText()+".txt");
        file.createNewFile();
        Stage stage = new Stage();
        stage.prepareNewStage(Integer.parseInt(txFieldX.getText())+2, Integer.parseInt(txFieldY.getText())+2, txFieldI.getText());
        new StageEditor(gameWindow, controller, soundManager, stage,txFieldF1.getText());
      }catch(IOException io){
        System.out.println("File Write Error!");
      }
    }else{
      Stage stage = new Stage();
      stage.prepareStage(txFieldF2.getText());
      new StageEditor(gameWindow, controller, soundManager, stage,txFieldF2.getText());
    }
  }

}
