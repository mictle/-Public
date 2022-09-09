
package project.graphics;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
//import java.util.*;
import project.tools.*;
import project.objects.*;
import project.*;
public class GameCanvas extends JPanel{
  Vector2 screenCenter = new Vector2(600,450);
  Vector2 screenSize = new Vector2(600,450);
  int blockSize = 64;
  Image[] blockImages = new Image[3];
  int maxblockNum = 1;
  GameManager manager;
  public GameCanvas(){
    this.setBackground(Color.yellow);
    blockImages[1] = Toolkit.getDefaultToolkit().getImage("./irusts/grassBlock.png");
    blockImages[2] = Toolkit.getDefaultToolkit().getImage("./irusts/dirtBlock.png");
  }
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Vector2 playerPos = GameManager.charaList.get(0).getCenterPos();
    if(playerPos.x<=screenSize.x) screenCenter.x=screenSize.x;
    else if(playerPos.x>=(Stage.stageSizeX*64-screenSize.x)) screenCenter.x=Stage.stageSizeX*64-screenSize.x;
    else screenCenter.x = playerPos.x;
    drawBlocks(g);
    drawCharacters(g);

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
    /*
      minX,Ydraw : 画面の左端、上端にかかるブロック列
      maxX,Ydraw : 画面の右端、下端にかかるブロック列
      この変数で指定した範囲のアイテムのみ描画する。(軽量化のため)
    */
    int minXdraw = (int)(screenCenter.x-screenSize.x)/blockSize;
    int minYdraw = (int)(screenCenter.y-screenSize.y)/blockSize;
    int maxXdraw = (int)(screenCenter.x+screenSize.x)/blockSize;
    int maxYdraw = (int)(screenCenter.y+screenSize.y)/blockSize;

    for(int i=minXdraw;i<=maxXdraw;i++){
      for(int j=minYdraw;j<=maxYdraw;j++){
        if(i>=0 && j>=0 && i<Stage.stageSizeX && j<Stage.stageSizeY){
          int b = Stage.items.get(j).get(i);

          Vector2 drawPos = new Vector2().add(new Vector2(i*blockSize,j*blockSize),new Vector2().sub(screenSize,screenCenter));
          g.drawImage(blockImages[b], (int)drawPos.x, (int)drawPos.y, blockSize,blockSize, this);
        }
      }
    }
  }
}
