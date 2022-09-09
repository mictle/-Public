package project;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import project.tools.*;
import project.graphics.*;
import project.objects.*;
import project.stageeditor.*;
import project.stageselect.*;

public class StartMenu extends JPanel{
  private int nowSelect,coolTime,fromS, ToS;
  private GameCanvas canvas;
  private GameWindow window;
  private Sound soundManager;
  private Controller ct;
  private int fps = 60;
  private Image[][] subIrusts = {{Toolkit.getDefaultToolkit().getImage("./irusts/gameStart_dark.png"),       Toolkit.getDefaultToolkit().getImage("./irusts/gameStart_highlighted.png")},
  {Toolkit.getDefaultToolkit().getImage("./irusts/createStage_dark.png"), Toolkit.getDefaultToolkit().getImage("./irusts/createStage_highlighted.png")},{Toolkit.getDefaultToolkit().getImage("./irusts/exit_dark.png"), Toolkit.getDefaultToolkit().getImage("./irusts/exit_highlighted.png")}};
  private Image backImage = Toolkit.getDefaultToolkit().getImage("./irusts/Title_background.jpg");
  private Image logoImage = Toolkit.getDefaultToolkit().getImage("./irusts/titleLogo.png");
  public StartMenu(GameWindow w, Controller c,Sound sm){
    soundManager = sm;
    window = w;
    this.setBackground(Color.yellow);
    w.changeCanvas(this);
    w.setVisible(true);
    nowSelect = 0;
    nowSelect = 0; coolTime = -1; fromS = 0; ToS = 0;
    ct = c;
    soundManager.playBgm("./sounds/Medip_title_02.wav");
    mainLoop();
  }
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    g.drawImage(backImage, 0, 0, 1200, 900, this);
    g.drawImage(logoImage, 0,0,600,600,this);
    g.drawImage(subIrusts[0][(nowSelect==0)? 1 : 0], 550, 500, 500, 128, this);
    g.drawImage(subIrusts[1][(nowSelect==1)? 1 : 0], 570, 600, 500, 128, this);
    g.drawImage(subIrusts[2][(nowSelect==2)? 1 : 0], 570, 700, 500, 128, this);
  }
  public void mainLoop(){
    System.out.println("mainLoop");
    Thread thread = new Thread(() -> {
      long nowTime = System.currentTimeMillis() << 16;
      long oldTime;
      long errTime = 0;
      long sleepTime;
      long frameTime = (1000 << 16) / getFps();
      int testCount = 0;
      while(true){
        oldTime = nowTime;
        //同期処理ここから---------------------------------------------------
        if(coolTime>=0){
          coolTime--;
        }
        if(ct.getup()){
          if(coolTime<0){
            soundManager.playSE("cursor1.wav");
            if(nowSelect==0){
              nowSelect=2;
            }else{
              nowSelect--;

            }
            coolTime=20;
          }
        }
        if(ct.getdown()){
          if(coolTime<0){
            soundManager.playSE("cursor1.wav");

            if(nowSelect==2){
              nowSelect=0;
            }else{
              nowSelect++;
            }
            coolTime=20;
          }
        }
        if(ct.getatk()){
          soundManager.playSE("decision3.wav");
          soundManager.closeBGMClip();
          if(nowSelect==0){
            
            new StageSelectManager(window, ct, soundManager);
            //new GameManager(window, ct, soundManager);
            break;
          }else if(nowSelect==1){
            new StageEditorPrepare(window, ct, soundManager);
            break;
          }else if(nowSelect==2){
            window.dispose();
            break;
          }
        }
        this.repaint();
        //同期処理ここまで---------------------------------------------------
        nowTime = System.currentTimeMillis() << 16;
        sleepTime = frameTime - (nowTime - oldTime) - errTime;
        if(sleepTime < 0x20000) sleepTime = 0x20000;
        oldTime = nowTime;
        try{
          Thread.sleep(sleepTime >> 16);
        }catch(Exception e){
        }
        nowTime = System.currentTimeMillis() << 16;
        errTime = nowTime - oldTime -sleepTime;
      }
    });
    thread.setDaemon(true);
    thread.start();
  }
  public int getFps(){
    return fps;
  }
}
