package project.objects;

import java.awt.*;
import project.tools.*;
import project.*;
import project.objects.*;
import project.objects.items.*;

public class ItemBinder{
  private Vector2 physPos[] = new Vector2[2];
  private Item[] items = new Item[6];
  private GameManager gameManager;
  public ItemBinder(GameManager gm){
    items[1] = new GoalFlag();
    items[2] = new Star();
    items[3] = new Apple();
    items[4] = new MaxHeart();
    items[5] = new TobihaGyoku();
    gameManager = gm;
  }
  public void itemGet(Player player){
     physPos[0] = new Vector2().add(player.getCenterPos(), player.getPhyslu());
     physPos[1] = new Vector2().add(player.getCenterPos(), player.getPhysrd());
     for(int i=0;i<2;i++){
       for(int j=0;j<2;j++){
         int tmpX = (int)(physPos[i].x)/64; int tmpY = (int)(physPos[i].y)/64;
         if(Stage.item[tmpX][tmpY]!=0){
           items[Stage.item[tmpX][tmpY]].itemEffect(tmpX, tmpY,gameManager,player);
         }
       }
     }
   }
   public Image getItemImage(int i){
     return items[i].getImage();
   }
}
