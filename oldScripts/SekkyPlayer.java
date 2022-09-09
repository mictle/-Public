package project.objects;

import project.tools.*;
import java.awt.*;

import project.*;
import project.graphics.GameCanvas;
import project.objects.*;
import javax.swing.*;

import project.objects.*;
import java.awt.event.*;
import java.security.KeyException;

public class Player extends CharacterData{
	/*
    変数説明
	coolTimeAtk : 攻撃から次の攻撃までのクールタイム
	フレーム単位で調節したい
	*/
        //Image testIrust = Toolkit.getDefaultToolkit().getImage("./irusts/player_walk_r.gif");
	private boolean tCountStart;
	private Controller ct;
  Image[][] ilusts = {
	  				{	Toolkit.getDefaultToolkit().getImage("./irusts/player_Standing_r.gif"),//右向きの立ち絵。デフォルト。
						Toolkit.getDefaultToolkit().getImage("./irusts/player_walk_r.gif"),//右歩き
						Toolkit.getDefaultToolkit().getImage("./irusts/player_swordAttack_r.gif"),//攻撃
						Toolkit.getDefaultToolkit().getImage("./irusts/player_fall_r.gif")//落下
					},
                    {	Toolkit.getDefaultToolkit().getImage("./irusts/player_Standing_l.gif"),//左向き立ち絵
						Toolkit.getDefaultToolkit().getImage("./irusts/player_walk_l.gif"),//左歩き
						Toolkit.getDefaultToolkit().getImage("./irusts/player_swordAttack_l.gif"),
						Toolkit.getDefaultToolkit().getImage("./irusts/player_fall_l.gif")
					}
                };
  private int coolTimeAtk;
  public Player(GameCanvas gc, Controller ct){
    //superの引数　: int lr, int hp, boolean fly, Vector2 centerPos, Vector2 imagePos, Vector2 imageSize, Vector physlu, Vector physrd
    //適当な数値を入れているので変更
    super(0, 20, false, 0, new Vector2(Stage.playerPosX, Stage.playerPosY), new Vector2(-64,-64), new Vector2(128,128), new Vector2(-64, -62), new Vector2(0,64));
    coolTimeAtk = 0; nowImage = ilusts[lr][0];
	tCountStart = false; this.ct = ct;
  }
  @Override
  public int update(){
	  super.update();
	//centerPos.x+=2;
	//向き判定
	if (coolTimeAtk > 0) {
		coolTimeAtk--;
		//nowImage = ilusts[lr][2];
	}
	if ((ct.atk && coolTimeAtk == 0) || coolTimeAtk > 40) {
		coolTimeAtk = attack(coolTimeAtk);
	}else if (ct.rwalk && !ct.lwalk) {
		rightwalk();
	}else if(ct.lwalk && !ct.rwalk){
		leftwalk();
	}else{
		nowImage = ilusts[lr][0];
	}
	if (ct.jmp) {
		jump();
	}
	if(!tCountStart && !onFloorDitect(GameManager.charaList.get(0))){
		tCountStart=true;
	}
	if(tCountStart && !onFloorDitect(GameManager.charaList.get(0))){
		tCount+=1;
	}else{
		tCountStart=false;
		tCount=0;
	}
		//nowImage = testIrust;
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
		centerPos.y-=200;
    }
  }
  public int attack(int cta){
  	if (cta == 0) {
		nowImage = ilusts[lr][2];
		cta = 60;
	}
	//判定発生
	return cta;
  }
  	
}