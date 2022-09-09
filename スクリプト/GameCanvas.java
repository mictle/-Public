
package project.graphics;

import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

import javax.swing.*;
//import java.util.*;
import project.tools.*;
import project.objects.*;
import project.*;
public class GameCanvas extends JPanel{
  private Vector2 screenCenter = new Vector2(600,450);
  private Vector2 screenSize = new Vector2(600,450);
  private int blockSize = 64;
  private ItemBinder itemBinder;
  private Image[] blockImages = new Image[3];
  private Image[] hpBar = new Image[4];
  private int maxblockNum = 1;
  private GameManager manager;
  private Image backImage;
  public GameCanvas(GameManager gm){
    this.setBackground(Color.yellow);
    blockImages[1] = Toolkit.getDefaultToolkit().getImage("./irusts/grassBlock.png");
    blockImages[2] = Toolkit.getDefaultToolkit().getImage("./irusts/dirtBlock.png");
    backImage = Toolkit.getDefaultToolkit().getImage("./irusts/BackImage.jpg");
    hpBar[0] =  Toolkit.getDefaultToolkit().getImage("./irusts/HP.png");//プレイヤーのhpバー
    manager = gm;
    itemBinder = new ItemBinder(manager);

  }
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Vector2 playerPos = GameManager.charaList.get(0).getCenterPos();
    if(playerPos.x<=screenSize.x) screenCenter.x=screenSize.x;
    else if(playerPos.x>=(Stage.stageSizeX*64-screenSize.x)) screenCenter.x=Stage.stageSizeX*64-screenSize.x;
    else screenCenter.x = playerPos.x;
    if(playerPos.y<=screenSize.y) screenCenter.y=screenSize.y;
    else if(playerPos.y>=(Stage.stageSizeY*64-screenSize.y)) screenCenter.y=Stage.stageSizeY*64-screenSize.y;
    else screenCenter.y = playerPos.y;
    drawBack(g);
    drawBlocks(g);
    drawCharacters(g);
    drawItems(g);
    drawHpbar(g);
    //for(int i = 0;i<800;i+=64)
       //g.drawImage(img,i,736,64,64,this);

  }
  public void drawCharacters(Graphics g){
    for(CharacterData c : GameManager.charaList){
      Vector2 drawPos = new Vector2().add(new Vector2().add(c.getCenterPos(),c.getImagePos()),new Vector2().sub(screenSize,screenCenter));
      g.drawImage(c.getNowImage(),(int)drawPos.x,(int)drawPos.y,(int)c.getImageSize().x,(int)c.getImageSize().y,this);

    }
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
          int b = Stage.block.get(j).get(i);

          Vector2 drawPos = new Vector2().add(new Vector2(i*blockSize,j*blockSize),new Vector2().sub(screenSize,screenCenter));
          g.drawImage(blockImages[b], (int)drawPos.x, (int)drawPos.y, blockSize,blockSize, this);
        }
      }
    }
  }

  public void drawItems(Graphics g){
    int minXdraw = (int)(screenCenter.x-screenSize.x)/blockSize;
    int minYdraw = (int)(screenCenter.y-screenSize.y)/blockSize;
    int maxXdraw = (int)(screenCenter.x+screenSize.x)/blockSize;
    int maxYdraw = (int)(screenCenter.y+screenSize.y)/blockSize;
    for(int i=minXdraw;i<=maxXdraw;i++){
      for(int j=minYdraw;j<=maxYdraw;j++){
        if(i>=0 && j>=0 && i<Stage.stageSizeX && j<Stage.stageSizeY){
          int b = Stage.items.get(j).get(i);
          if(b>0){
            Vector2 drawPos = new Vector2().add(new Vector2(i*blockSize, j*blockSize),new Vector2().sub(screenSize, screenCenter));
            Image image = itemBinder.getItemImage(b);
            g.drawImage(image, (int)drawPos.x, (int)drawPos.y, blockSize,blockSize, this);
          }
        }
      }
    }
  }
  public void drawHpbar(Graphics g){
    int playerhp = GameManager.charaList.get(0).gethp();
    for(CharacterData c : GameManager.charaList){
      if (c.getId() != 0 && c.getcoolTimeDmg() >0) {
        Vector2 drawPos = new Vector2().add(c.getCenterPos(),new Vector2().sub(screenSize,screenCenter)),
                hpYpos = c.getPhyslu();
        g.setColor(Color.red);
        g.fillRect((int)drawPos.x, (int)(drawPos.y+hpYpos.y), c.getMaxhp(), 10);
        g.setColor(Color.green);
        g.fillRect((int)drawPos.x, (int)(drawPos.y+hpYpos.y), c.gethp(), 10);
      }
    }
    g.setColor(Color.red);
    g.fillRect(95, 35, 400, 30);
    g.setColor(Color.green);
    g.fillRect(95, 35, 4*playerhp, 30);
    g.drawImage(hpBar[0], 0, 0, 500, 100, this);
  }
  public void drawBack(Graphics g){
    g.drawImage(backImage, 0, 0, 2409, 930, this);
  }
}
