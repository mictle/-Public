package project.objects.enemy;

import project.tools.*;
import project.*;
import project.objects.*;
import java.awt.*;
import project.objects.bullet.*;
/*基本になる敵。一定軌道を飛行し、プレイヤーが近づくと弾を吐く。パタパタ+キラーみたいな立ち位置。*/
public class SniperFly extends Enemy{
  int count=0;
  int coolTimeBlt;
  Vector2 firstPos;
  Image[][] images= new Image[3][3];
  public SniperFly(Vector2 summonPos){
    super(15, 10, 100, true, 0, summonPos, new Vector2(-32,-32), new Vector2(64, 64), new Vector2(-30,-30), new Vector2(30,30));
    images[0][1] = Toolkit.getDefaultToolkit().getImage("./irusts/sniper_fly_l.gif");
    images[0][0] = Toolkit.getDefaultToolkit().getImage("./irusts/sniper_fly_r.gif");
    images[1][1] = Toolkit.getDefaultToolkit().getImage("./irusts/sniper_damage_l.png");
    images[1][0] = Toolkit.getDefaultToolkit().getImage("./irusts/sniper_damage_r.png");
    images[2][1] = Toolkit.getDefaultToolkit().getImage("./irusts/sniper_fly_attack_l.png");
    images[2][0] = Toolkit.getDefaultToolkit().getImage("./irusts/sniper_fly_attack_r.png");
    nowImage=images[0][1];coolTimeBlt = 0;firstPos = summonPos;
  }
  void shootBullet(){
      CharacterData p = GameManager.charaList.get(0);
      lr = (p.getCenterPos().x < centerPos.x) ? 1:0;
      Vector2 bullet = new Vector2().sub(p.getCenterPos(), centerPos).normalized();
      coolTimeBlt = 140;
      GameManager.bulletList.add(new SniperBullet(1, centerPos, new Vector2().multiInteger(bullet,4), lr, 20));
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
      coolTimeDmg--;
    }else{
      count++;
      if (coolTimeBlt > 0) {
        coolTimeBlt--;
      }else if (distanceOfPlayer() < 600.0) {
        shootBullet();
      }
      nowImage = images[0][lr];
      centerPos.y = firstPos.y + 150 * Math.cos((double)count * 0.05);
    }
    //count++;
    return 0;
  }

}
