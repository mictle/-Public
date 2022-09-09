package project.objects.bullet;

import java.awt.*;
import project.objects.*;
import project.tools.Vector2;

public class Fireball extends Bullet{
  public Fireball(int id, Vector2 firstPos, int lr, int attack){
    super(id, attack, 60,firstPos, new Vector2(-20,-20), new Vector2(40,40),new Vector2(-20,-20), new Vector2(20, 20), (lr==0)? new Vector2(4, 0) : new Vector2(-4, 0), (lr==0) ? "fire_bullet_R.png" : "fire_bullet.png");
  }
  public int update(){
    return super.update();
  }
}
