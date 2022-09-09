package project.objects.enemy;

import project.tools.*;
import project.*;
import project.objects.*;
import java.awt.*;
import project.objects.bullet.*;
/*飛び道具を持つ敵。普段はブルースライムみたいな動きをするが、プレイヤーが近づくと炎による攻撃を行ってくる。なかなか手ごわい。*/
public class FireEnemy extends Enemy{
  /*二重配列の使い方
    [行動(歩行中、空中、停止等)][左右]って感じで管理するとやりやすいと思います。
    左右: 0:左, 1:右
    行動: 0:移動, 1:ダメージ
  */
  int count=0;
  int coolTimeBlt;
  Image[][] images= new Image[4][4];
  public FireEnemy(Vector2 summonPos){
    super(30, 10, 100, false, 0, summonPos, new Vector2(-32,-32), new Vector2(64, 64), new Vector2(-30,-30), new Vector2(30,30));
    images[0][0] = Toolkit.getDefaultToolkit().getImage("./irusts/enemy_fire_walk_R.gif");
    images[0][1] = Toolkit.getDefaultToolkit().getImage("./irusts/enemy_fire_walk.gif");
    images[1][0] = Toolkit.getDefaultToolkit().getImage("./irusts/enemy_fire_attack_R.gif");
    images[1][1] = Toolkit.getDefaultToolkit().getImage("./irusts/enemy_fire_attack.gif");
    images[2][0] = Toolkit.getDefaultToolkit().getImage("./irusts/enemy_fire_R.gif");
    images[2][1] = Toolkit.getDefaultToolkit().getImage("./irusts/enemy_fire.gif");
    nowImage=images[0][0];coolTimeBlt = 0;
  }
  void shootFire(){
    if (coolTimeBlt == 0) {
      CharacterData p = GameManager.charaList.get(0);
      lr = (p.getCenterPos().x < centerPos.x) ? 1:0;
      coolTimeBlt = 180;
      GameManager.bulletList.add(new Fireball(1, centerPos, lr, 30));
      GameManager.seList.add("fire1.wav");
    }else if (coolTimeBlt > 160) {
      nowImage=images[1][lr];
    }else{
      nowImage=images[2][lr];
    }
  }
  @Override
  public int update(){
    super.update();
    if (coolTimeDie > 0 && coolTimeDmg == 0) {
      coolTimeDie--;
      if (coolTimeDie == 0) {
          return 1;
      }
    }else if (coolTimeDmg > 0) {
      nowImage = images[1][lr];
      //ノックバック
      if (lr == 1) {
        if (!onWallDitectL(this)) {
          centerPos.x-=1;
        }
      }else{
        if (!onWallDitectR(this)) {
          centerPos.x+=1;
        }
      }
      coolTimeDmg--;
    }else{
      //System.out.println((int)centerPos.x/64 + ", " + (int)centerPos.y/64);
      if (coolTimeBlt > 0) {
        coolTimeBlt--;
      }
      if (distanceOfPlayer() < 300.0) {
          shootFire();
      }else if(onFloor){
        nowImage = images[0][lr];
        if(lr==0){
          //System.out.println("r:"+((int)centerPos.x/64)+1+", "+ ((int)centerPos.x/64)+1+"   :"+Stage.block.get(((int)centerPos.x/64)+1).get(((int)centerPos.x/64)+1));
          if(((int)centerPos.x%64>=39 && Stage.block[((int)centerPos.x/64)+1][((int)centerPos.y/64)+1]==0) || onWallDitectR(this)){
            lr=1;
            nowImage = images[0][lr];
          }else centerPos.x+=1;
        }else{
          //System.out.println("l:"+((int)centerPos.x/64)+1+", "+ (((int)centerPos.x/64)-1)+"   :"+Stage.block.get(((int)centerPos.x/64)+1).get(((int)centerPos.x/64)-1));
          if(((int)centerPos.x%64<=25 && Stage.block[((int)centerPos.x/64)-1][((int)centerPos.y/64)+1]==0) || onWallDitectL(this)){
            lr=0;
            nowImage=images[0][lr];
          }else centerPos.x-=1;
        }
      }
    }

    //count++;
    return 0;
  }

}
