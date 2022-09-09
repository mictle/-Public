
package project.graphics;

import java.awt.*;
import java.awt.event.*;
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
  private Image[] blockImages = new Image[7];
  private Image[] hpBar = new Image[4];
  private Image[] scoreImages = new Image[13];
  private int maxblockNum = 1;
  private GameManager manager;
  private Image backImage;
  public GameCanvas(GameManager gm){
    this.setBackground(Color.yellow);
    blockImages[1] = Toolkit.getDefaultToolkit().getImage("./irusts/grassBlock.png");
    blockImages[2] = Toolkit.getDefaultToolkit().getImage("./irusts/dirtBlock.png");
    blockImages[3] = Toolkit.getDefaultToolkit().getImage("./irusts/orangeBlock.png");
    blockImages[4] = Toolkit.getDefaultToolkit().getImage("./irusts/yellowBlock.png");
    blockImages[5] = Toolkit.getDefaultToolkit().getImage("./irusts/blueBlock.png");
    blockImages[6] = Toolkit.getDefaultToolkit().getImage("./irusts/greenBlock.png");
    backImage = Toolkit.getDefaultToolkit().getImage("./irusts/"+Stage.background);
    scoreImages[0] = Toolkit.getDefaultToolkit().getImage("./irusts/num0.png");
    scoreImages[1] = Toolkit.getDefaultToolkit().getImage("./irusts/num1.png");
    scoreImages[2] = Toolkit.getDefaultToolkit().getImage("./irusts/num2.png");
    scoreImages[3] = Toolkit.getDefaultToolkit().getImage("./irusts/num3.png");
    scoreImages[4] = Toolkit.getDefaultToolkit().getImage("./irusts/num4.png");
    scoreImages[5] = Toolkit.getDefaultToolkit().getImage("./irusts/num5.png");
    scoreImages[6] = Toolkit.getDefaultToolkit().getImage("./irusts/num6.png");
    scoreImages[7] = Toolkit.getDefaultToolkit().getImage("./irusts/num7.png");
    scoreImages[8] = Toolkit.getDefaultToolkit().getImage("./irusts/num8.png");
    scoreImages[9] = Toolkit.getDefaultToolkit().getImage("./irusts/num9.png");
    scoreImages[10] = Toolkit.getDefaultToolkit().getImage("./irusts/Star.png");
    scoreImages[11] = Toolkit.getDefaultToolkit().getImage("./irusts/scoreLabel.png");
    scoreImages[12] = Toolkit.getDefaultToolkit().getImage("./irusts/multisymbol.png");
    hpBar[0] =  Toolkit.getDefaultToolkit().getImage("./irusts/HP.png");//プレイヤーのhpバー
    manager = gm;
    itemBinder = new ItemBinder(manager);

  }
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Vector2 playerPos = GameManager.charaList.get(0).getCenterPos();
    if(playerPos.x<=screenSize.x+64) screenCenter.x=screenSize.x+64;
    else if(playerPos.x>=(Stage.stageSizeX*64-screenSize.x)-64) screenCenter.x=Stage.stageSizeX*64-screenSize.x-64;
    else screenCenter.x = playerPos.x;
    if(playerPos.y<=screenSize.y+64) screenCenter.y=screenSize.y+64;
    else if(playerPos.y>=(Stage.stageSizeY*64-screenSize.y)-64) screenCenter.y=Stage.stageSizeY*64-screenSize.y-64;
    else screenCenter.y = playerPos.y;
    drawBack(g);
    drawBlocks(g);
    drawCharacters(g);
    drawBullets(g);
    drawItems(g);
    drawHpbar(g);
    drawScore(g, manager.getscore(), manager.getstar());
    //以下、当たり判定可視化サンプル
    /*
    Vector2 attDif1 = new Vector2(15,-20);
    Vector2 attDif2 = new Vector2(100,60);
    Vector2 atari1 = new Vector2().add(GameManager.charaList.get(0).getCenterPos(),attDif1);
    Vector2 atari2 = new Vector2().sub(attDif2, attDif1);
    atari1.add(atari1, new Vector2().sub(screenSize,screenCenter));
    //g.fillRect((int)atari1.x, (int)atari1.y, (int)atari2.x,(int)atari2.y);
    Vector2 phylu = new Vector2().add(GameManager.charaList.get(0).getCenterPos(), new Vector2(-30,-62));
    phylu.add(phylu, new Vector2().sub(screenSize,screenCenter));
    g.drawRect((int)phylu.x, (int)phylu.y, 60,122);
    */
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
          int b = Stage.block[i][j];

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
          int b = Stage.item[i][j];
          if(b>0){
            Vector2 drawPos = new Vector2().add(new Vector2(i*blockSize, j*blockSize),new Vector2().sub(screenSize, screenCenter));
            Image image = itemBinder.getItemImage(b);
            g.drawImage(image, (int)drawPos.x, (int)drawPos.y, blockSize,blockSize, this);
          }
        }
      }
    }
  }

  public void drawBullets(Graphics g){
    for(Bullet b : GameManager.bulletList){
      Vector2 drawPos = new Vector2().add(new Vector2().add(b.getCenterPos(),b.getImagePos()),new Vector2().sub(screenSize,screenCenter));
      g.drawImage(b.getImage(),(int)drawPos.x,(int)drawPos.y,(int)b.getImageSize().x,(int)b.getImageSize().y,this);

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
  public void drawScore(Graphics g, int score, int star){
    g.drawImage(scoreImages[11], 510, 20, 125, 32, this);
    //String scoreString = String.valueOf(score), starString = String.valueOf(star);
    int digits;
    for (int i = 0; i < 8; i++) {
      digits  = score % 10;
      g.drawImage(scoreImages[digits], 768 - (i + 1)*16, 20, 16, 32, this);
      score /= 10;
    }
    g.drawImage(scoreImages[10], 510, 44, 24, 24, this);
    g.drawImage(scoreImages[12], 538, 40, 16, 32, this);
    for (int i = 0; i < 2; i++) {
      digits  = star % 10;
      g.drawImage(scoreImages[digits], 590 - (i + 1)*16, 40, 16, 32, this);
      star /= 10;
    }
  }
  public void drawBack(Graphics g){
    g.drawImage(backImage, 0, 0, 1200, 900, this);
  }
}
