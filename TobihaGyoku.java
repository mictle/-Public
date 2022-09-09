package project.objects.items;

import project.tools.*;
import project.objects.*;
import project.GameManager;
import java.awt.*;
import java.util.*;

public class TobihaGyoku implements Item{
  private Image image;
  public TobihaGyoku(){
    image = Toolkit.getDefaultToolkit().getImage("./irusts/Gyoku_tobiha.png");
  }
  public void itemEffect(int posX, int posY, GameManager gm, Player p){
    //System.out.println(gm.getstar());
    //効果音
    gm.addscore(1000);
    p.setZangeki(true);
    Stage.item[posX][posY]=0;
  };
  public Image getImage(){
    return image;
  }
}
