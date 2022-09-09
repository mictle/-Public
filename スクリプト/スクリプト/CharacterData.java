
package project.objects;

import project.tools.*;
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
  protected int hp, lr, id;
  protected boolean fly, onFloor;
  protected Vector2 centerPos = new Vector2();
  protected Vector2 imagePos = new Vector2();
  protected Vector2 imageSize = new Vector2();
  protected Vector2 physlu = new Vector2();
  protected Vector2 physrd = new Vector2();
  protected Image nowImage;
  public CharacterData(int id, int hp, boolean fly, int lr, Vector2 centerPos, Vector2 imagePos, Vector2 imageSize, Vector2 physlu, Vector2 physrd){
    onFloor = true;
    this.id = id;
    this.hp = hp;
    this.centerPos.copyFrom(centerPos);
    this.imagePos.copyFrom(imagePos);
    this.imageSize.copyFrom(imageSize);
    this.physlu.copyFrom(physlu);
    this.physrd.copyFrom(physrd);
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
    if(!fly && !onFloor){
      //落下時処理
      this.centerPos.y += 3;
    }
  }

  public void damage(int atk){
    //ダメージ処理
    this.hp -= atk;
    if (hp <= 0) {
      died();
    }
  }
  public void died(){
    //死亡時処理。主人公やボスキャラ等はオーバーライド?
  }
}
