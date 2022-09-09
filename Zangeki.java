package project.objects.bullet;

import java.awt.*;
import project.objects.*;
import project.tools.Vector2;

public class Zangeki extends Bullet{
  public Zangeki(int id, Vector2 firstPos, int lr, int attack){
    super(id, attack, 60,firstPos, new Vector2(-20,-40), new Vector2(40,80),new Vector2(-20,-40), new Vector2(20, 40), (lr==0)? new Vector2(6, 0) : new Vector2(-6, 0), (lr==0) ? "zangeki_r.png" : "zangeki_l.png");
  }
  public int update(){
    return super.update();
  }
}
