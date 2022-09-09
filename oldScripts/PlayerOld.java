package project.objects;

import project.tools.*;
import java.awt.*;
import project.objects.*;

public class Player extends CharacterData{
  /*
    変数説明
    coolTimeAtk : 攻撃から次の攻撃までのクールタイム
  */
  Image walk;
  boolean walking;
  private double coolTimeAtk;
  public Player(){
    //superの引数　: String imgName, int hp, boolean fly, Vector2 centerPos, Vector2 imagePos, Vector2 imageSize, Vector physlu, Vector physrd
    //適当な数値を入れているので変更
    super(0, 20, false, 1, new Vector2(Stage.playerPosX, Stage.playerPosY), new Vector2(-64,-64), new Vector2(128,128), new Vector2(-30, 30), new Vector2(30,-30));
    coolTimeAtk = 5;
    nowImage = Toolkit.getDefaultToolkit().getImage("./irusts/movie.gif");
  }

  public int update(){
    centerPos.x+=2;
    return 0;
  }

  public void jump(){
    if(onFloor){

    }
  }
  public void attack(){

  }
}
