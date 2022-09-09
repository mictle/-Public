package project.objects.bullet;

import java.awt.*;
import project.objects.*;
import project.tools.Vector2;

public class SniperBullet extends Bullet{
  public SniperBullet(int id, Vector2 firstPos, Vector2 enemyToPlayer, int lr, int attack){
    super(id, attack, 180, firstPos, new Vector2(-20,-20), new Vector2(30,30),new Vector2(-20,-20), new Vector2(20, 20), enemyToPlayer, "sniperBullet.png");
  }
  public int update(){
    return super.update();
  }
}
