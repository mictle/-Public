package project.stageselect;

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
import project.stageselect.*;

/**
Controller:プレイヤーキャラクターの操作。押されたボタンに対応してboolを切り替える。
*/
public class StageSelectPlayer extends CharacterData{
	/*
	変数説明
	coolTimeAtk : 攻撃から次の攻撃までのクールタイム
	フレーム単位で調節したい
	*/
	//Image testIrust = Toolkit.getDefaultToolkit().getImage("./irusts/player_walk_r.gif");
	private boolean tCountStart,jumping,zangeki,inDoor;
	Image nullImage = Toolkit.getDefaultToolkit().getImage("./irusts/nullIrust.png");
	Image[][] ilusts = {
		{	Toolkit.getDefaultToolkit().getImage("./irusts/player_Standing_r.gif"),//右向きの立ち絵。デフォルト。
		Toolkit.getDefaultToolkit().getImage("./irusts/player_walk_r.gif"),//右歩き
		Toolkit.getDefaultToolkit().getImage("./irusts/player_swordAttack_r1.png"),//構え
		Toolkit.getDefaultToolkit().getImage("./irusts/player_swordAttack_r2.png"),//抜刀
		Toolkit.getDefaultToolkit().getImage("./irusts/player_fall_r.gif"),//落下
		Toolkit.getDefaultToolkit().getImage("./irusts/player_damage_r.gif"),//ダメージ
		Toolkit.getDefaultToolkit().getImage("./irusts/player_jump_r.png")//ジャンプ

	},
	{	Toolkit.getDefaultToolkit().getImage("./irusts/player_Standing_l.gif"),//左向き立ち絵
	Toolkit.getDefaultToolkit().getImage("./irusts/player_walk_l.gif"),//左歩き
	Toolkit.getDefaultToolkit().getImage("./irusts/player_swordAttack_l1.png"),//構え
	Toolkit.getDefaultToolkit().getImage("./irusts/player_swordAttack_l2.png"),//抜刀
	Toolkit.getDefaultToolkit().getImage("./irusts/player_fall_l.gif"),
	Toolkit.getDefaultToolkit().getImage("./irusts/player_damage_l.gif"),
	Toolkit.getDefaultToolkit().getImage("./irusts/player_jump_l.png")//ジャンプ
}
};
Image playerBackImage = Toolkit.getDefaultToolkit().getImage("./irusts/ushiro.png");
private int coolTimeAtk,inDoorCount,doorNum;
private Controller ct;
private StageSelectManager manager;
private StageSelectPlayer player;
public StageSelectPlayer(Controller ct, StageSelectManager ssm){
	//superの引数　: int lr, int hp, int atk, boolean fly, Vector2 centerPos, Vector2 imagePos, Vector2 imageSize, Vector physlu, Vector physrd
	super(0, 100, 10, 0, false, 0, new Vector2(Stage.playerPosX, Stage.playerPosY), new Vector2(-64,-64), new Vector2(160,128), new Vector2(-20, -50), new Vector2(20,64));
	coolTimeAtk = 0;
	imagePos=new Vector2(-54,-64);
	nowImage = ilusts[lr][0];
	tCountStart = false;
	jumping=false;
	zangeki=true;
	inDoor=false;
	inDoorCount=-1;
	manager=ssm;
	atk = 10;
	this.ct = ct;

}
@Override
public int update(){
	if(lr==0)imagePos=new Vector2(-54,-64);
	else imagePos=new Vector2(-110,-64);
	//if(onFloor)System.out.println("true");
	//else System.out.println("false");
	if (coolTimeDie > 0) {
		coolTimeDie--;
		if (coolTimeDie == 0) {
			return 2;
		}
	}else{
		if(!inDoor){
			super.update();
			//centerPos.x+=2;

			if (coolTimeAtk > 0) {
				coolTimeAtk--;
				//nowImage = ilusts[lr][2];
			}
			if ((ct.getatk() && coolTimeAtk == 0) || coolTimeAtk > 30) {
				attack();
			}else if (ct.getrwalk() && !ct.getlwalk()) {
				rightwalk();
			}else if(ct.getlwalk() && !ct.getrwalk()){
				leftwalk();
			}else{
				nowImage = ilusts[lr][(onFloorDitect(this))? 0:4];
			}
			if(ct.getjmp()){
				jumpStart();
			}
			if (jumping) {
				nowImage=ilusts[lr][6];
				if(lr==0){
					imagePos=new Vector2(-64,-64);
				}else imagePos=new Vector2(-89,-64);
				jump();
			}
			if(ct.getup()){
				Door[] doors = manager.getDoors();
				for(int i=0;i<doors.length;i++){
					if(!doors[i].getIsLocked() && (int)centerPos.x/64==doors[i].getPos().x && ((int)centerPos.y/64==doors[i].getPos().y || (int)centerPos.y/64==doors[i].getPos().y - 1) ){
						StageSelectManager.seList.add("door_open.wav");
						inDoor=true;
						doorNum=i;
						inDoorCount=120;
						doors[i].setTCount(64);
						nowImage=playerBackImage;
						//manager.setClearFlg(true);
					}
				}
			}
			/*if(ct.getup()){
			for(Door d : StageSelectManager.doors){
			if(d.getPos().x*64)
		}
	}*/
	//nowImage = testIrust;
	//nowImage = testIrust;
}else{
	//扉を選択した後の演出
	inDoorCount--;
	if(inDoorCount==60)nowImage = nullImage;
	if(inDoorCount==0)return doorNum;
}
}
boolean b = !jumping && !onFloorDitect(this);
if(b){
	imagePos=new Vector2(-74,-64);
}

return -1;
}
public void rightwalk(){
	lr = 0;
	imagePos=new Vector2(-54,-64);
	nowImage = ilusts[lr][(onFloorDitect(this))? 1:4];
	if (!onWallDitectR(this)) {
		centerPos.x+=6;
	}
}
public void leftwalk(){
	lr = 1;
	imagePos=new Vector2(-110,-64);
	nowImage = ilusts[lr][(onFloorDitect(this))? 1:4];
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
	if(!ct.getjmp() || onCeilingDitect(this)){
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
		if(lr==0)imagePos=new Vector2(-54,-64);
		else imagePos=new Vector2(-110,-64);
		coolTimeAtk = 45;
		nowImage = ilusts[lr][2];
	}
	if(coolTimeAtk < 45)
	nowImage = ilusts[lr][2];
	if(coolTimeAtk < 40)
	nowImage = ilusts[lr][3];
}
@Override
public void died(){
	super.died();
	//死亡時演出

}
public void setZangeki(boolean b){
	zangeki=b;
}

}
