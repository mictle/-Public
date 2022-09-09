package project.objects;


import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.security.KeyException;
import project.*;
import project.graphics.GameCanvas;
import project.objects.*;
import project.tools.*;
import project.objects.bullet.Zangeki;
import project.objects.*;


/**
Controller:プレイヤーキャラクターの操作。押されたボタンに対応してboolを切り替える。
*/
public class Player extends CharacterData{
	/*
	変数説明
	coolTimeAtk : 攻撃から次の攻撃までのクールタイム
	フレーム単位で調節したい
	*/
	//Image testIrust = Toolkit.getDefaultToolkit().getImage("./irusts/player_walk_r.gif");
	private boolean tCountStart,jumping,zangeki;
	Image[][] ilusts = {
		{	Toolkit.getDefaultToolkit().getImage("./irusts/player_Standing_r.gif"),//右向きの立ち絵。デフォルト。
		Toolkit.getDefaultToolkit().getImage("./irusts/player_walk_r.gif"),//右歩き
		Toolkit.getDefaultToolkit().getImage("./irusts/player_swordAttack_r1.png"),//構え
		Toolkit.getDefaultToolkit().getImage("./irusts/player_swordAttack_r2.png"),//抜刀
		Toolkit.getDefaultToolkit().getImage("./irusts/player_fall_r.gif"),//落下
		Toolkit.getDefaultToolkit().getImage("./irusts/player_damage_r.gif"),//ダメージ
		Toolkit.getDefaultToolkit().getImage("./irusts/player_jump_r.png"),//ジャンプ
		Toolkit.getDefaultToolkit().getImage("./irusts/player_miss1_r.png"),//死亡1
		Toolkit.getDefaultToolkit().getImage("./irusts/player_miss2_r.png")//死亡2
	},
	{	Toolkit.getDefaultToolkit().getImage("./irusts/player_Standing_l.gif"),//左向き立ち絵
	Toolkit.getDefaultToolkit().getImage("./irusts/player_walk_l.gif"),//左歩き
	Toolkit.getDefaultToolkit().getImage("./irusts/player_swordAttack_l1.png"),//構え
	Toolkit.getDefaultToolkit().getImage("./irusts/player_swordAttack_l2.png"),//抜刀
	Toolkit.getDefaultToolkit().getImage("./irusts/player_fall_l.gif"),
	Toolkit.getDefaultToolkit().getImage("./irusts/player_damage_l.gif"),
	Toolkit.getDefaultToolkit().getImage("./irusts/player_jump_l.png"),//ジャンプ
	Toolkit.getDefaultToolkit().getImage("./irusts/player_miss1_l.png"),//死亡1
	Toolkit.getDefaultToolkit().getImage("./irusts/player_miss2_l.png")//死亡2
}
};
private int coolTimeAtk;
private ItemBinder itemBinder;
private Controller ct;
private GameManager gameManager;
public Player(Controller ct, GameManager gm){
	//superの引数　: int lr, int hp, int atk, boolean fly, Vector2 centerPos, Vector2 imagePos, Vector2 imageSize, Vector physlu, Vector physrd
	super(0, 100, 10, 0, false, 0, new Vector2(Stage.playerPosX, Stage.playerPosY), new Vector2(-64,-64), new Vector2(160,128), new Vector2(-20, -50), new Vector2(20,64));
	coolTimeAtk = 0;
	coolTimeDie=0;
	imagePos=new Vector2(-54,-64);
	nowImage = ilusts[lr][0];
	tCountStart = false;
	jumping=false;
	zangeki=false;
	gameManager = gm;
	itemBinder = new ItemBinder(gm);
	atk = 10;
	this.ct = ct;

}
@Override
public int update(){
	if(lr==0)imagePos=new Vector2(-54,-64);
	else imagePos=new Vector2(-110,-64);
	//if(onFloor)System.out.println("true");
	//else System.out.println("false");
	super.update();
	if (coolTimeDie > 0) {
		coolTimeDie--;
		if(coolTimeDie==290)return 2;

    if(coolTimeDie==275)nowImage=ilusts[lr][8];
		if (coolTimeDie == 1) {

			return 3;
		}
	}else{

		//centerPos.x+=2;
		//向き判定
		if (coolTimeAtk > 0) {
			coolTimeAtk--;
			//nowImage = ilusts[lr][2];
		}
		if (coolTimeDmg == 0 && enemyDitect() != GameManager.charaList.get(0)) {
			damage(enemyDitect().atk);
		}else {
			//無敵
			if (coolTimeDmg > 0) {
				if (coolTimeDmg > 40) {
					if(lr==0)imagePos=new Vector2(-54,-64);
					else imagePos=new Vector2(-110,-64);
					nowImage = ilusts[lr][5];
				}
				coolTimeDmg--;
			}
		}
		if ((ct.getatk() && coolTimeAtk == 0) || coolTimeAtk > 30) {
			attack();
		}else if (ct.getrwalk() && !ct.getlwalk()) {
			rightwalk();
		}else if(ct.getlwalk() && !ct.getrwalk()){
			leftwalk();
		}else{
			nowImage = ilusts[lr][onFloorDitect(GameManager.charaList.get(0))? 0:4];
		}
		if(ct.getjmp()){
			jumpStart();
		}
		if (jumping) {
			jump();
		}
    if(jumping){
			nowImage = ilusts[lr][6];
			if(lr==0){
			  imagePos=new Vector2(-64,-64);
			}else imagePos=new Vector2(-89,-64);
		}
		itemBinder.itemGet(this);
		//nowImage = testIrust;
		//nowImage = testIrust;
	}
	boolean b = !jumping && !onFloorDitect(GameManager.charaList.get(0));
	if(b){
		imagePos=new Vector2(-74,-64);
	}

	return 0;
}
public void rightwalk(){
	lr = 0;
	imagePos=new Vector2(-54,-64);
	nowImage = ilusts[lr][onFloorDitect(GameManager.charaList.get(0))? 1:4];
	if (!onWallDitectR(this)) {
		centerPos.x+=6;
	}
}
public void leftwalk(){
	lr = 1;
	imagePos=new Vector2(-110,-64);
	nowImage = ilusts[lr][onFloorDitect(GameManager.charaList.get(0))? 1:4];
	if (!onWallDitectL(this)) {
		centerPos.x-=6;
	}
}


public boolean jumpStart(){
	if(onFloor && !jumping){
		jumping=true;
		return true;
	}else{
		return false;
	}
}

public void jump(){
	if(!ct.getjmp() || onCeilingDitect(GameManager.charaList.get(0))){
		tCount=0;
		jumping=false;
	}else{
		centerPos.y-=20;
	}
}

/*public void attack(){
if (coolTimeAtk == 0) {
nowImage = ilusts[lr][2];
//判定発生
coolTimeAtk = 3;
}
}*/
public void attack(){
	if (coolTimeAtk == 0) {
		GameManager.seList.add("attack1.wav");
		if(lr==0)imagePos=new Vector2(-54,-64);
		else imagePos=new Vector2(-110,-64);
		coolTimeAtk = 45;
		nowImage = ilusts[lr][2];
		if(zangeki)GameManager.bulletList.add(new Zangeki(id, new Vector2().add(centerPos,new Vector2(44+lr*(-88),24)), lr, atk/2));
	}
	if(coolTimeAtk < 45)
	nowImage = ilusts[lr][2];
	if(coolTimeAtk < 40)
	nowImage = ilusts[lr][3];
	System.out.println("attack!");
	if(lr==0) attackToEnemy(new Vector2(15,-50), new Vector2(110,60),atk);
	else attackToEnemy(new Vector2(-110,-50), new Vector2(-15,60),atk);
	//判定発生
}
public void damage(int atk){
	//ダメージ処理
	if(coolTimeDie==0){
		GameManager.seList.add("damage2.wav");
	  coolTimeDmg = 60;
	  super.damage(atk);
  }
}
@Override
public void died(){
	GameManager.seList.add("miss(kari).wav");
	nowImage = ilusts[lr][7];
	//super.died();
	coolTimeDie=300;
	//死亡時演出

}
public void setZangeki(boolean b){
	zangeki=b;
}

}
