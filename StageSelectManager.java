
package project.stageselect;

import project.graphics.*;
import project.objects.*;
import project.tools.*;
import project.*;
import project.stageselect.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
public class StageSelectManager {
  private int fps = 60, score = 0, star = 0;
  private StageSelectCanvas canvas;
  private GameWindow window;
  private Controller controller;
  private boolean clearFlg;
  private Sound soundManager;
  private StageSelectPlayer player;
  public static int clearStage;
  private Door[] doors = new Door[5];
  private Stage stage;
  public static LinkedList<String> seList = new LinkedList<String>();
  public StageSelectManager(GameWindow gw,Controller c, Sound sm){
    //nowStage = s;
    stage = new Stage();
    stage.prepareStage("Story/SelectStage");
    player = new StageSelectPlayer(c, this);
    window = gw;

    controller = c;
    clearFlg = false;

    doors[0] = new Door(new Vector2(5,23),"testNewStage2");
    doors[1] = new Door(new Vector2(15,23),"testNewStage2");
    doors[2] = new Door(new Vector2(30,23),"demo1_1");
    doors[3] = new Door(new Vector2(35,23),"testStage4");
    doors[4] = new Door(new Vector2(40,23),"testStage4");
    for(int i=0;i<doors.length;i++){
      if(World.clearData>=i){
        doors[i].setIsLocked(false);
      }
    }
    canvas = new StageSelectCanvas(this);
    window.changeCanvas(canvas);
    window.setVisible(true);
    window.addKeyListener(c);
    window.requestFocus();
    clearFlg=false;
    soundManager = sm;
    soundManager.playBgm("./sounds/Medip_stagemap_02.wav");
    mainLoop();
  }


  /*
  メインループ、処理はほぼすべてこのループに同期される。
  */
  public void mainLoop(){
    LinkedList<Integer> deleteIndex = new LinkedList<Integer>();
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
          int door =player.update();
          if(door==0){
            soundManager.closeBGMClip();
            new StartMenu(window,controller,soundManager);
            break;
          }else if(door>=1){
            soundManager.closeBGMClip();
            World.nowStage = door;
            Stage stage = new Stage();
            stage.prepareStage(doors[door].getStagePath());
            new GameManager(window,controller,soundManager);
            break;
          }




        for(int i=0;i<doors.length;i++){
          doors[i].update();
        }
        while(seList.size()!=0){
          soundManager.playSE(seList.poll());
        }
        canvas.repaint();
        testCount++;
        if(testCount>=60){
          testCount=0;
          System.out.println("hi!");
        }

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

  public void setClearFlg(boolean b){
    clearFlg = b;
  }
  public int getFps(){
    return fps;
  }
  public Door[] getDoors(){
    return doors;
  }
  public StageSelectPlayer getPlayer(){
    return player;
  }
  public void resetStageData(){

  }
  public StageSelectPlayer getStageSelectPlayer(){
    return player;
  }
  //test用
}
