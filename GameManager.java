
package project;
import project.graphics.*;
import project.objects.*;
import project.tools.*;
import project.stageselect.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
public class GameManager {
  private Stage nowStage;
  private int fps = 60, score = 0, star = 0;
  private GameCanvas canvas;
  private GameWindow window;
  private Controller controller;
  private EnemyBinder enemyBinder;
  private boolean clearFlg;
  private Sound soundManager;
  private Player p;
  public static LinkedList<CharacterData> charaList;
  public static LinkedList<Bullet> bulletList;
  public static LinkedList<String> seList;
  public GameManager(GameWindow gw,Controller c, Sound sm){
    //nowStage = s;
    score = 0;
    charaList = new LinkedList<CharacterData>();
    bulletList = new LinkedList<Bullet>();
    seList = new LinkedList<String>();
    p = new Player(c, this);
    charaList.add(p);
    enemyBinder= new EnemyBinder();
    enemyBinder.prepareEnemies();
    canvas = new GameCanvas(this);
    window = gw;
    controller = c;
    clearFlg = false;
    window.changeCanvas(canvas);

    window.setVisible(true);


    window.addKeyListener(c);
    window.requestFocus();
    clearFlg=false;
    soundManager = sm;
    soundManager.playBgm("./sounds/Stage1-1_01.wav");
    mainLoop();
  }
  public int getscore(){
    return score;
  }
  public int addscore(int s){
    score += s;
    return score;
  }
  public int getstar(){
    return star;
  }
  public int addstar(){
    star++;return star;
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
        if(!clearFlg){
          int judgeDeath=0;
          for(CharacterData c : charaList){
            //各キャラクターのUpdate処理を実行
            judgeDeath = c.update();
            //返り値確認、1 : 敵死亡、 2:主人公死亡 それぞれその操作を実行
            if(judgeDeath==1){
              //死亡キャラクター登録
              deleteIndex.offerFirst(count);
              c.Exp(this, c.getscore());
            }else if(judgeDeath==2){
              System.out.println("youaredead");
              //死亡直後処理
              soundManager.closeBGMClip();

            }else if(judgeDeath==3){

              break;
            }
            count++;
          }
          if(judgeDeath==3)break;
          //死亡キャラクター削除
          while(deleteIndex.size()!=0){
            System.out.println("slimeisdead");
            charaList.remove((int)deleteIndex.poll());
          }
          count=0;
          deleteIndex.clear();
          for(Bullet b : bulletList){
            //各弾のUpdate処理を実行
            judgeDeath = b.update();
            //返り値確認、0 : そのまま 1 : 弾消失
            if(judgeDeath==1){
              //消失する弾登録
              deleteIndex.offerFirst(count);
            }
            count++;
          }
          //消失弾削除
          while(deleteIndex.size()!=0){
            bulletList.remove((int)deleteIndex.poll());
          }
          while(seList.size()!=0){
            soundManager.playSE(seList.poll());
          }
        }else{
        //クリア時処理
          soundManager.closeBGMClip();
          if(World.nowStage==World.clearData){
            World.clearData++;
          }

          break;
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

        new StageSelectManager(window, controller, soundManager);

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
  //test用
}

class testPlay{
  public static void main(String[] args){
    Sound sm = new Sound();
    Stage stage = new Stage();
    stage.prepareStage("testStage");
    GameWindow gw = new GameWindow();
    Controller c = new Controller(gw);
    new GameManager(gw, c, sm);

  }
}
