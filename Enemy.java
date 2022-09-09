package project.objects;

import project.objects.*;
import project.tools.*;
import project.*;

public class Enemy extends CharacterData{
    //位置は敵ごとに違うのでコンストラクタ引数にする
    //敵の攻撃力
    //public int coolTimeDmg;
    public Enemy(int hp, int atk, int score, boolean fly, int lr, Vector2 centerPos, Vector2 imagePos, Vector2 imageSize, Vector2 physlu, Vector2 physrd){
        super(1, hp, atk, score, fly, lr, centerPos, imagePos, imageSize, physlu, physrd);
        //coolTimeDmg = 0;
    }
    //プレイヤーとの距離を求める関数
    public double distanceOfPlayer(){
        Vector2 d = new Vector2(); CharacterData p = GameManager.charaList.get(0);
        d.sub(p.getCenterPos(), this.getCenterPos());
        return d.Abs();
    }
    @Override
    public int update() {
        super.update();
        return 0;
    }
    @Override
  public void damage(int atk){
	  coolTimeDmg = 20;
	  super.damage(atk);
  }
}