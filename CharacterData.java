
package project.objects;

import project.tools.*;
import project.GameManager;
import project.objects.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class CharacterData extends ColliderDitect{
  /*
    変数の説明
    imgName : 現在描画する画像の名前
    hp : hp
    fly : 飛行するかどうか。しなければ落下処理が適用
    centerPos : キャラクターの中心点。以下の座標はこの座標箱の座標との相対座標で設定
    imagePos : 描画の描画の左上の点。
    physlu : 当たり判定の四角の左上の点。
    physrd : 当たり判定の四角の右下の点。
    lr : キャラクターの向き。trueで右、falseで左の予定。
  */
  protected int hp, lr, id, maxhp, score;
  protected int tCount;
  protected int atk, coolTimeDie, coolTimeDmg;
  protected boolean fly, onFloor,tCountStart;
  protected Vector2 centerPos = new Vector2();
  protected Vector2 imagePos = new Vector2();
  protected Vector2 imageSize = new Vector2();
  protected Vector2 physlu = new Vector2();
  protected Vector2 physrd = new Vector2();
  protected Image nowImage;
  public CharacterData(int id, int hp, int atk, int score, boolean fly, int lr, Vector2 centerPos, Vector2 imagePos, Vector2 imageSize, Vector2 physlu, Vector2 physrd){
    tCount=0; coolTimeDie = 0; coolTimeDmg = 0;
    onFloor = true;
    this.fly = fly;
    this.id = id;
    this.hp = hp;
    this.maxhp = hp;
    this.atk = atk;
    this.score = score;
    this.centerPos.copyFrom(centerPos);
    this.imagePos.copyFrom(imagePos);
    this.imageSize.copyFrom(imageSize);
    this.physlu.copyFrom(physlu);
    this.physrd.copyFrom(physrd);
  }
  public int gethp(){
    return hp;
  }
  public void recovery(int addhp){
    hp += addhp;
    if (hp > maxhp) {
      hp = maxhp;
    }
  }
  public int getMaxhp(){
    return maxhp;
  }
  public int getscore(){
    return score;
  }
  public int getcoolTimeDmg() {
      return coolTimeDmg;
  }
  public int getcoolTimeDie() {
    return coolTimeDie;
  }
  public int getId(){
    return id;
  }
  public boolean getOnFloor(){
    return onFloor;
  }
  public Vector2 getCenterPos(){
    return centerPos;
  }

  public Vector2 getPhyslu(){
    return physlu;
  }

  public Vector2 getPhysrd(){
    return physrd;
  }
  public Vector2 getImagePos(){
    return imagePos;
  }
  public Image getNowImage(){
    return nowImage;
  }
  public Vector2 getImageSize(){
    return imageSize;
  }
  public void setnowImage(Image i){
    nowImage=i;
  }
  public int update(){
    fall();
    return 0;
  }
  public void fall(){

    onFloor=onFloorDitect(this);
    //onFloor=true;

    if(!tCountStart && !onFloor){
			tCountStart=true;
		}
		if(tCountStart && !onFloor){
			tCount+=1;
		}else{
			tCountStart=false;
			tCount=0;
		}

    if(!fly && !onFloor){
      //落下時処理4
      if(isBlock(new Vector2(centerPos.x+physlu.x,centerPos.y+physrd.y+(tCount*0.4))) || isBlock(new Vector2(centerPos.x+physrd.x,centerPos.y+physrd.y+(tCount*0.4)))){

        centerPos.y = ((int)(centerPos.y+physrd.y+(tCount*0.4))/64 -1)*64 -1;
        System.out.println("ddd");
      }else{
        this.centerPos.y += tCount*0.4;
      }
    }
  }

  public void damage(int atk){
    //ダメージ処理
    System.out.println(atk);
    this.hp -= atk;
    if (hp <= 0) {
      died();
    }
  }
  public void died(){
    coolTimeDie = 60;
    System.out.println("死");
    //死亡時処理。主人公やボスキャラ等はオーバーライド?
  }
  //スコア加算
  public void Exp(GameManager gm, int score){
    System.out.println(score);
    gm.addscore(score);
  }
}
