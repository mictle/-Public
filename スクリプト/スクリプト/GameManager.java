
package project;
import project.graphics.*;
import project.objects.*;
import project.tools.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
public class GameManager {
  private Stage nowStage;
  private int fps = 60;
  private GameCanvas canvas;
  private GameWindow window;
  private EnemyBinder enemyBinder;
  public static LinkedList<CharacterData> charaList = new LinkedList<CharacterData>();
  public GameManager(){
    //nowStage = s;
    canvas = new GameCanvas();
    Player p = new Player(canvas);
    charaList.add(p);
    enemyBinder= new EnemyBinder();
    enemyBinder.prepareEnemies();
    window = new GameWindow(canvas);
    window.addKeyListener(p);
    window.requestFocus();

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
        int count=0;
        for(CharacterData c : charaList){
          if(c.update()==1){
            deleteIndex.offerFirst(count);
          }
          count++;
        }
        while(deleteIndex.size()!=0){
          charaList.remove((int)deleteIndex.poll());
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


  public int getFps(){
    return fps;
  }
  //test用
}

class testPlay{
  public static void main(String[] args){
    Stage stage = new Stage();
    stage.prepareStage("testStage");
    new GameManager();

  }
}
