package project.objects.enemy;

import project.tools.*;
import project.*;
import project.objects.*;
import java.awt.*;
/*基本になる敵。足場の上を往復する赤ノコノコと同じムーブをする。弱い。*/
public class BlueSlime extends Enemy{
  /*二重配列の使い方
    [行動(歩行中、空中、停止等)][左右]って感じで管理するとやりやすいと思います。
    左右: 0:左, 1:右
    行動: 0:移動, 1:ダメージ
  */
  int count=0;
  int damageCount;
  Image[][] images= new Image[2][2];
  public BlueSlime(Vector2 summonPos){
    super(10, 10, 100, false, 0, summonPos, new Vector2(-32,-32), new Vector2(64, 64), new Vector2(-30,-30), new Vector2(30,30));
    images[0][0] = Toolkit.getDefaultToolkit().getImage("./irusts/blueSlime_walk_l.gif");
    images[0][1] = Toolkit.getDefaultToolkit().getImage("./irusts/blueSlime_walk_r.gif");
    images[1][0] = Toolkit.getDefaultToolkit().getImage("./irusts/blueSlime_damage_l.png");
    images[1][1] = Toolkit.getDefaultToolkit().getImage("./irusts/blueSlime_damage_r.png");
    nowImage=images[0][0];
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
      //
      if(onFloor){
        if(lr==1){
          //System.out.println("r:"+((int)centerPos.x/64)+1+", "+ ((int)centerPos.x/64)+1+"   :"+Stage.block.get(((int)centerPos.x/64)+1).get(((int)centerPos.x/64)+1));
          if(((int)centerPos.x%64>=39 && Stage.block[((int)centerPos.x/64)+1][((int)centerPos.y/64)+1]==0) || onWallDitectR(this)){
            lr=0;
            nowImage = images[0][0];
          }else centerPos.x+=2;
        }else{
          //System.out.println("l:"+((int)centerPos.x/64)+1+", "+ (((int)centerPos.x/64)-1)+"   :"+Stage.block.get(((int)centerPos.x/64)+1).get(((int)centerPos.x/64)-1));
          if(((int)centerPos.x%64<=25 && Stage.block[((int)centerPos.x/64)-1][((int)centerPos.y/64)+1]==0) || onWallDitectL(this)){
            lr=1;
            nowImage=images[0][1];
          }else centerPos.x-=2;
        }
      }
      }
    //count++;
    return 0;
  }

}
