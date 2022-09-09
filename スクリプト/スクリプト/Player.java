package project.objects;

import project.tools.*;
import java.awt.*;

import project.graphics.GameCanvas;
import project.objects.*;
import javax.swing.*;

import project.objects.*;
import java.awt.event.*;
import java.security.KeyException;

/**
 Controller:プレイヤーキャラクターの操作。押されたボタンに対応してboolを切り替える。
 */
/*public class Controller implements ActionListener {
  private boolean lwalk, rwalk, atk, jump;
  public Controller(){
	  lwalk = false;
	  rwalk = false;
	  atk = false;
	  jump = false;
  }
}*/
public class Player extends CharacterData implements KeyListener{
	/*
    変数説明
	coolTimeAtk : 攻撃から次の攻撃までのクールタイム
	フレーム単位で調節したい
	*/
        //Image testIrust = Toolkit.getDefaultToolkit().getImage("./irusts/player_walk_r.gif");
	private boolean lwalk, rwalk, atk, jmp;
  Image[][] ilusts = {
	  				{	Toolkit.getDefaultToolkit().getImage("./irusts/mediaP_standing.png"),//右向きの立ち絵。デフォルト。
						Toolkit.getDefaultToolkit().getImage("./irusts/player_walk_r.gif"),//右歩き
						Toolkit.getDefaultToolkit().getImage("./irusts/mediaP1-3b.png")	
					},//攻撃モーションを割り当てる予定
                    {	Toolkit.getDefaultToolkit().getImage("./irusts/mediaP_standingL.png"),//左向き立ち絵
						Toolkit.getDefaultToolkit().getImage("./irusts/player_walk_l.gif"),//左歩き
						Toolkit.getDefaultToolkit().getImage("./irusts/mediaP1-3b.png")
					}
                };
  private int coolTimeAtk;
  public Player(GameCanvas gc){
    //superの引数　: int lr, int hp, boolean fly, Vector2 centerPos, Vector2 imagePos, Vector2 imageSize, Vector physlu, Vector physrd
    //適当な数値を入れているので変更
    super(0, 20, false, 0, new Vector2(Stage.playerPosX, Stage.playerPosY), new Vector2(-64,-64), new Vector2(128,128), new Vector2(-64, -64), new Vector2(0,64));
    coolTimeAtk = 0; nowImage = ilusts[lr][0];
	lwalk = false; rwalk = false; atk = false; jmp = false;
	gc.addKeyListener(this);

  }
  @Override
  public int update(){
	  super.update();
	//centerPos.x+=2;
	//向き判定
	if (coolTimeAtk > 0) {
		coolTimeAtk--;
	}
	if (rwalk && !lwalk) {
		rightwalk();
	}else if(lwalk && !rwalk){
		leftwalk();
	}else{
		nowImage = ilusts[lr][0];
	}
	if (jmp) {
		jump();
	}
	if (atk) {
		attack();
	}
        //nowImage = testIrust;
    return 0;
  }
  public void rightwalk(){
	lr = 0;
	nowImage = ilusts[lr][1];
	if (!onWallDitectR(this)) {
		centerPos.x+=2;
	}
  }
  public void leftwalk(){
	lr = 1;
	nowImage = ilusts[lr][1];
	if (!onWallDitectL(this)) {
		centerPos.x-=2;
	}
  }
  public void jump(){
    if(onFloor){
		centerPos.y-=20;
    }
  }
  public void attack(){
	  	if (coolTimeAtk == 0) {
			nowImage = ilusts[lr][2];
			//判定発生
			coolTimeAtk = 3;
		}  
  }
  public void keyTyped(KeyEvent e) {
	}

	public void keyPressed(KeyEvent e) {
		System.out.println("keypressed");
		switch (e.getKeyCode()) {
		case KeyEvent.VK_RIGHT:
			System.out.println("rwalk");
			rwalk = true;
			break;
		case KeyEvent.VK_LEFT:
			System.out.println("lwalk");
			lwalk = true;
			break;
		case KeyEvent.VK_SPACE:
			System.out.println("jump");
			jmp = true;
			break;
		case KeyEvent.VK_SHIFT:
			System.out.println("attack");
			atk = true;
			break;
		}
	}

	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_RIGHT:
				System.out.println("rwalk");
				rwalk = false;
				break;
			case KeyEvent.VK_LEFT:
				System.out.println("lwalk");
				lwalk = false;
				break;
			case KeyEvent.VK_SPACE:
				System.out.println("jump");
				jmp = false;
				break;
			case KeyEvent.VK_SHIFT:
				System.out.println("attack");
				atk = false;
				break;
		}
	}
}
