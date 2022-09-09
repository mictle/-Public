package project.stageeditor;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import project.tools.*;
import project.graphics.*;
import project.objects.*;
import project.*;

public class StageEditor extends JPanel implements MouseListener, ActionListener{
  private int type, number;
  private GameCanvas canvas;
  private GameWindow window;
  private Sound soundManager;
  private Controller ct;
  private Image gridImage;
  private Image[] blockImages, characterImages, itemImages;
  private int fps = 60;
  private JScrollPane scrollPane;
  private JPanel panel1;
  private JPanel subButtonPanel,subButtonPanel2;
  private JPanel subSelectWorkPanel;
  private SelectObjectsPanel subCenterPanel;
  private JPanel subFilePanel;
  private int pointX,pointY;
  private int[][] blocks, characters, items;
  private JFrame subFrame;
  private NumButton[] changeTypeButtons = new NumButton[6];
  private ImageIcon[] buttonIcons;
  private String[] buttonNames={"タイトルに戻る","保存","テストプレイ"};
  private String stageName;
  public StageEditor(GameWindow w, Controller c,Sound sm,Stage stage,String sn){
    type = 0; number = 1;
    stageName = sn;
    gridImage = Toolkit.getDefaultToolkit().getImage("./irusts/createGrid.png");
    blockImages = new Image[7];
    blockImages[0] = Toolkit.getDefaultToolkit().getImage("./irusts/nullIrust.png");
    blockImages[1] = Toolkit.getDefaultToolkit().getImage("./irusts/grassBlock.png");
    blockImages[2] = Toolkit.getDefaultToolkit().getImage("./irusts/dirtBlock.png");
    blockImages[3] = Toolkit.getDefaultToolkit().getImage("./irusts/orangeBlock.png");
    blockImages[4] = Toolkit.getDefaultToolkit().getImage("./irusts/yellowBlock.png");
    blockImages[5] = Toolkit.getDefaultToolkit().getImage("./irusts/blueBlock.png");
    blockImages[6] = Toolkit.getDefaultToolkit().getImage("./irusts/greenBlock.png");
    characterImages = new Image[6];
    characterImages[0] = Toolkit.getDefaultToolkit().getImage("./irusts/nullIrust.png");
    characterImages[1] = Toolkit.getDefaultToolkit().getImage("./irusts/blueSlime_walk_l.gif");
    characterImages[2] = Toolkit.getDefaultToolkit().getImage("./irusts/redSlime_walk_l.gif");
    characterImages[3] = Toolkit.getDefaultToolkit().getImage("./irusts/redSlime_walk_r.gif");
    characterImages[4] = Toolkit.getDefaultToolkit().getImage("./irusts/enemy_fire.gif");
    characterImages[5] = Toolkit.getDefaultToolkit().getImage("./irusts/sniper_fly_l.gif");

    itemImages  = new Image[6];
    itemImages[0] = Toolkit.getDefaultToolkit().getImage("./irusts/nullIrust.png");
    itemImages[1] = Toolkit.getDefaultToolkit().getImage("./irusts/clearball.gif");
    itemImages[2] = Toolkit.getDefaultToolkit().getImage("./irusts/star.png");
    itemImages[3] = Toolkit.getDefaultToolkit().getImage("./irusts/apple.png");
    itemImages[4] = Toolkit.getDefaultToolkit().getImage("");
    itemImages[5] = Toolkit.getDefaultToolkit().getImage("./irusts/Gyoku_tobiha.png");
    buttonIcons = new ImageIcon[3];
    buttonIcons[0] = new ImageIcon("./irusts/grassBlock.png");
    buttonIcons[1] = new ImageIcon("./irusts/blueSlime_walk_l.gif");
    buttonIcons[2] = new ImageIcon("./irusts/clearball.gif");
    soundManager = sm;
    soundManager.playBgm("./sounds/Medip_stagemap_02.wav");
    window = w;
    this.setBackground(Color.yellow);
    this.setPreferredSize(new Dimension((Stage.stageSizeX-2)*64, (Stage.stageSizeY-2)*64));
    panel1 = new JPanel();
    w.changeCanvas(panel1);
    scrollPane = new JScrollPane(this,
                      ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                      ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
    scrollPane.setPreferredSize(new Dimension(1200,900));
    panel1.setLayout(new GridLayout(1,1));
    panel1.add(scrollPane);
    addMouseListener(this);
    w.setVisible(true);
    //配置するオブジェクトを選択するパレットウィンドウ
    subFrame = new JFrame("パレット");
    subFrame.setBackground(Color.black);
    subFrame.setSize(384,700);
    subFrame.setLayout(new BorderLayout());
    subButtonPanel = new JPanel();
    subButtonPanel.setLayout(new GridLayout(1,3));
    for(int i=0;i<3;i++){
      changeTypeButtons[i] = new NumButton(i, buttonIcons[i]);
      changeTypeButtons[i].addActionListener(this);
      subButtonPanel.add(changeTypeButtons[i]);
    }
    subFrame.add("North",subButtonPanel);
    subButtonPanel2=new JPanel();
    subButtonPanel2.setLayout(new GridLayout(1,3));
    for(int i=3;i<6;i++){
      changeTypeButtons[i] = new NumButton(i, buttonNames[i-3]);
      changeTypeButtons[i].addActionListener(this);
      subButtonPanel2.add(changeTypeButtons[i]);
    }
    subFrame.add("South",subButtonPanel2);
    subCenterPanel = new SelectObjectsPanel(this);
    subFrame.add("Center",subCenterPanel);
    subFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    subFrame.setVisible(true);
    ct = c;
  }
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    for(int i=1;i<Stage.stageSizeX-1;i++){
      for(int j=1;j<Stage.stageSizeY-1;j++){
        g.drawImage(gridImage, (i-1)*64, (j-1)*64, 64, 64, this);
      }
    }
    for(int i=1;i<Stage.stageSizeX-1;i++){
      for(int j=1;j<Stage.stageSizeY-1;j++){
        g.drawImage(blockImages[Stage.block[i][j]], (i-1)*64, (j-1)*64, 64, 64, this);
      }
    }
    for(int i=1;i<Stage.stageSizeX-1;i++){
      for(int j=1;j<Stage.stageSizeY-1;j++){
        g.drawImage(characterImages[Stage.enemy[i][j]], (i-1)*64, (j-1)*64, 64, 64, this);
      }
    }
    for(int i=1;i<Stage.stageSizeX-1;i++){
      for(int j=1;j<Stage.stageSizeY-1;j++){
        g.drawImage(itemImages[Stage.item[i][j]], (i-1)*64, (j-1)*64, 64, 64, this);
      }
    }

  }
  public void drawSubSelectObjectsPanel(Graphics g){
    if(type==0){
      for(int i=0;i<blockImages.length;i++){
        g.drawImage(blockImages[i],(i%6)*64,(i/6)*64,64,64,subCenterPanel);
      }
    }else if(type==1){
      for(int i=0;i<characterImages.length;i++){
        g.drawImage(characterImages[i],(i%6)*64,(i/6)*64,64,64,subCenterPanel);
      }
    }else if(type==2){
      for(int i=0;i<itemImages.length;i++){
        g.drawImage(itemImages[i],(i%6)*64,(i/6)*64,64,64,subCenterPanel);
      }
    }
  }
  public void setNumber(int i){
    number = i;
  }
  public int getMaxObject(){
    if(type==0) return blockImages.length;
    if(type==1) return characterImages.length;
    if(type==2) return itemImages.length;
    return 0;
  }
  public void actionPerformed(ActionEvent e){
    NumButton b = (NumButton)e.getSource();
    int n = b.getNum();
    System.out.println("button"+n);
    if(n>=0 && n<=2){
      type = n;
      subCenterPanel.resetCursor();
      subCenterPanel.repaint();
    }
    if(n==3){
      subFrame.dispose();
      soundManager.closeBGMClip();
      new StartMenu(window,ct,soundManager);
    }
    if(n==4){
      //保存　編集中データをtxtファイルに変換
      try{
        //txtファイル書き込み
        File file = new File("./StageData/"+stageName+".txt");
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write("//stageSize X,Y");
        writer.newLine();
        writer.write(Stage.stageSizeX+"");
        writer.newLine();
        writer.write(Stage.stageSizeY+"");
        writer.newLine();
        writer.write("//playerfirstPos X,Y");
        writer.newLine();
        writer.write(Stage.playerPosX+"");
        writer.newLine();
        writer.write(Stage.playerPosY+"");
        writer.newLine();
        writer.write("//BackImage");
        writer.newLine();
        writer.write(Stage.background+"");
        writer.newLine();
        writer.write("//block");
        writer.newLine();
        for(int j=0;j<Stage.stageSizeY;j++){
          String tmpLine="";
          for(int i=0;i<Stage.stageSizeX;i++){
             tmpLine = tmpLine + Stage.block[i][j];
             if(i<Stage.stageSizeX-1) tmpLine = tmpLine + " ";
          }
          writer.write(tmpLine);
          writer.newLine();
        }
        writer.write("//enemy");
        writer.newLine();
        for(int j=0;j<Stage.stageSizeY;j++){
          String tmpLine="";
          for(int i=0;i<Stage.stageSizeX;i++){
             tmpLine = tmpLine + Stage.enemy[i][j];
             if(i<Stage.stageSizeX-1) tmpLine = tmpLine + " ";
          }
          writer.write(tmpLine);
          writer.newLine();
        }
        writer.write("//item");
        writer.newLine();
        for(int j=0;j<Stage.stageSizeY;j++){
          String tmpLine="";
          for(int i=0;i<Stage.stageSizeX;i++){
             tmpLine = tmpLine + Stage.item[i][j];
             if(i<Stage.stageSizeX-1) tmpLine = tmpLine + " ";
          }
          writer.write(tmpLine);
          writer.newLine();
        }
        writer.close();
      }catch(IOException io){
        System.out.println("File Write Error!");
      }
    }
  }
  public void mouseClicked(MouseEvent e) {
    System.out.println("clicked!");
            // 位置を取得

            int pointX = (e.getX() / 64)+1;
            int pointY = (e.getY() / 64)+1;
            // 再描画要求
            if(type==0){
              Stage.block[pointX][pointY]=number;
            }else if(type==1){
              Stage.enemy[pointX][pointY]=number;
            }else if(type==2){
              Stage.item[pointX][pointY]=number;
            }
            repaint();
  }
  public void mousePressed(MouseEvent e) {

  }

  public void mouseReleased(MouseEvent e) {
  }

  public void mouseEntered(MouseEvent e) {
  }

  public void mouseExited(MouseEvent e) {
  }

}



class SelectObjectsPanel extends JPanel implements MouseListener{
  StageEditor stageEditor;
  int pointX;
  int pointY;
  public SelectObjectsPanel(StageEditor s){
    stageEditor = s;
    setBackground(Color.black);
    addMouseListener(this);
  }
  //パレットの種類変更時、カーソルを初期位置にリセット
  public void resetCursor(){
    pointX = 0;
    pointY = 0;
    stageEditor.setNumber(0);
  }

  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    stageEditor.drawSubSelectObjectsPanel(g);
    g.setColor(Color.white);
    g.drawRect(pointX*64,pointY*64,64,64);
  }

  public void mouseClicked(MouseEvent e) {
            System.out.println("subClicked!");
            pointX = e.getX() / 64;
            pointY = e.getY() / 64;
            int maxObject = stageEditor.getMaxObject();
            if((pointX+pointY*6)<maxObject) stageEditor.setNumber(pointX+pointY*6);
            else stageEditor.setNumber(0);
            repaint();
  }
  public void mousePressed(MouseEvent e) {

  }

  public void mouseReleased(MouseEvent e) {
  }

  public void mouseEntered(MouseEvent e) {
  }

  public void mouseExited(MouseEvent e) {
  }
}
