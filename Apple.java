package project.objects.items;

import project.tools.*;
import project.objects.*;
import project.GameManager;
import java.awt.*;
import java.util.*;

public class Apple implements Item{
  private Image image;
  public Apple(){
    image = Toolkit.getDefaultToolkit().getImage("./irusts/apple.png");
  }
  public void itemEffect(int posX, int posY, GameManager gm, Player p){
    //効果音
    gm.addscore(300);
    //回復量(1/4回復)
    p.recovery(p.getMaxhp() / 4);
    Stage.item[posX][posY]=0;
  };
  public Image getImage(){
    return image;
  }
}
