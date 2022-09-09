package project.objects.items;

import project.*;
import project.tools.*;
import project.objects.*;
import project.GameManager;
import java.awt.*;
import java.util.*;

public class Star implements Item{
  private Image image;
  public Star(){
    image = Toolkit.getDefaultToolkit().getImage("./irusts/star.png");
  }
  public void itemEffect(int posX, int posY, GameManager gm, Player p){
    System.out.println(gm.getstar());
    GameManager.seList.add("star.wav");//効果音
    gm.addscore(1000);
    gm.addstar();
    Stage.item[posX][posY]=0;
  };
  public Image getImage(){
    return image;
  }
}
