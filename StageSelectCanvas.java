
package project.graphics;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
//import java.util.*;
import project.tools.*;
import project.objects.*;
import project.*;
import project.stageselect.*;
public class StageSelectCanvas extends JPanel{
  private Vector2 screenCenter = new Vector2(600,450);
  private Vector2 screenSize = new Vector2(600,450);
  private int blockSize = 64;
  private Image[] blockImages = new Image[7];
  private Image[] doorImages = new Image[3];
  private Image doorOpen;
  private int maxblockNum = 1;
  private StageSelectManager manager;
  private StageSelectPlayer player;
  private Image backImage;

  public StageSelectCanvas(StageSelectManager ssm){
    this.setBackground(Color.yellow);
    blockImages[1] = Toolkit.getDefaultToolkit().getImage("./irusts/grassBlock.png");
    blockImages[2] = Toolkit.getDefaultToolkit().getImage("./irusts/dirtBlock.png");
    blockImages[3] = Toolkit.getDefaultToolkit().getImage("./irusts/orangeBlock.png");
    blockImages[4] = Toolkit.getDefaultToolkit().getImage("./irusts/yellowBlock.png");
    blockImages[5] = Toolkit.getDefaultToolkit().getImage("./irusts/blueBlock.png");
    blockImages[6] = Toolkit.getDefaultToolkit().getImage("./irusts/greenBlock.png");
    doorImages[0] = Toolkit.getDefaultToolkit().getImage("./irusts/door_closed.png");
    doorImages[1] = Toolkit.getDefaultToolkit().getImage("./irusts/door.png");
    doorOpen = Toolkit.getDefaultToolkit().getImage("./irusts/door_open.png");
    backImage = Toolkit.getDefaultToolkit().getImage("./irusts/"+Stage.background);
    manager = ssm;
    player = ssm.getPlayer();
  }
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Vector2 playerPos = manager.getPlayer().getCenterPos();
    if(playerPos.x<=screenSize.x+64) screenCenter.x=screenSize.x+64;
    else if(playerPos.x>=(Stage.stageSizeX*64-screenSize.x)-64) screenCenter.x=Stage.stageSizeX*64-screenSize.x-64;
    else screenCenter.x = playerPos.x;
    if(playerPos.y<=screenSize.y+64) screenCenter.y=screenSize.y+64;
    else if(playerPos.y>=(Stage.stageSizeY*64-screenSize.y)-64) screenCenter.y=Stage.stageSizeY*64-screenSize.y-64;
    else screenCenter.y = playerPos.y;

    drawBack(g);
    drawBlocks(g);
    drawDoors(g);
    drawPlayer(g);


    //for(int i = 0;i<800;i+=64)
       //g.drawImage(img,i,736,64,64,this);

  }
  public void drawPlayer(Graphics g){
    CharacterData p = manager.getPlayer();
      Vector2 drawPos = new Vector2().add(new Vector2().add(p.getCenterPos(),p.getImagePos()),new Vector2().sub(screenSize,screenCenter));
      g.drawImage(p.getNowImage(),(int)drawPos.x,(int)drawPos.y,(int)p.getImageSize().x,(int)p.getImageSize().y,this);
  }

  public void drawBlocks(Graphics g){
    /*
      minX,Ydraw : 画面の左端、上端にかかるブロック列
      maxX,Ydraw : 画面の右端、下端にかかるブロック列
      この変数で指定した範囲のブロックのみ描画する。(軽量化のため)
    */
    int minXdraw = (int)(screenCenter.x-screenSize.x)/blockSize;
    int minYdraw = (int)(screenCenter.y-screenSize.y)/blockSize;
    int maxXdraw = (int)(screenCenter.x+screenSize.x)/blockSize;
    int maxYdraw = (int)(screenCenter.y+screenSize.y)/blockSize;

    for(int i=minXdraw;i<=maxXdraw;i++){
      for(int j=minYdraw;j<=maxYdraw;j++){
        if(i>=0 && j>=0 && i<Stage.stageSizeX && j<Stage.stageSizeY){
          int b = Stage.block[i][j];

          Vector2 drawPos = new Vector2().add(new Vector2(i*blockSize,j*blockSize),new Vector2().sub(screenSize,screenCenter));
          g.drawImage(blockImages[b], (int)drawPos.x, (int)drawPos.y, blockSize,blockSize, this);
        }
      }
    }
  }
  public void drawBack(Graphics g){
    g.drawImage(backImage, 0, 0, 1200, 900, this);
  }
  public void drawDoors(Graphics g){
    Door[] doors = manager.getDoors();
    int type;
    for(int i=0; i<doors.length;i++){
      if(doors[i].getIsLocked())type=0;
      else if(i==0)type=1;
      else type=1;
      Vector2 drawPos = new Vector2().add(new Vector2(doors[i].getPos().x*blockSize,(doors[i].getPos().y-1)*blockSize),new Vector2().sub(screenSize,screenCenter));
      g.drawImage(doorOpen, (int)drawPos.x, (int)drawPos.y, blockSize,blockSize*2, this);
      drawPos.add(drawPos, doors[i].getDoorPos());
      g.drawImage(doorImages[type], (int)drawPos.x, (int)drawPos.y, blockSize,blockSize*2, this);
    }
  }
}
