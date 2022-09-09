package project.objects.items;

import project.tools.*;
import project.objects.*;
import project.GameManager;
import java.awt.*;
import java.util.*;

public class MaxHeart implements Item{
  private Image image;
  public MaxHeart(){
    image = Toolkit.getDefaultToolkit().getImage("./irusts/maxheart.png");
  }
  public void itemEffect(int posX, int posY, GameManager gm, Player p){
    //効果音
    gm.addscore(200);
    //回復量(全回復)
    p.recovery(p.getMaxhp());
    Stage.item[posX][posY]=0;
  };
  public Image getImage(){
    return image;
  }
}
