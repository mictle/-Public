package project.objects.items;

import project.tools.*;
import project.objects.*;
import project.GameManager;
import java.awt.*;
import java.util.*;

public class GoalFlag implements Item{
  private Image image;
  public GoalFlag(){
    image = Toolkit.getDefaultToolkit().getImage("./irusts/clearball.gif");
  }
  public void itemEffect(int posX, int posY, GameManager gm, Player p){
    System.out.println("getClearBall!");
    gm.setClearFlg(true);
    Stage.item[posX][posY]=0;
  };
  public Image getImage(){

    return image;
  }
}
