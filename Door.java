package project.objects;

import project.tools.Vector2;
import java.awt.*;
import project.tools.Vector2;

public class Door{
  private Vector2 pos;
  private String stagePath;
  private boolean islocked;
  private int tCount;
  public Door(Vector2 pos, String stagePath){
    this.pos = new Vector2(pos);
    this.stagePath = stagePath;
    islocked=true;
    tCount=-1;
  }
  public void update(){
    if(tCount>0)tCount--;
  }
  public int getTCount(){
    return tCount;
  }
  public void setTCount(int i){
    tCount=i;
  }
  public void setIsLocked(boolean b){
    islocked=b;
  }
  public boolean getIsLocked(){
    return islocked;
  }
  public Vector2 getPos(){
    return pos;
  }
  public Vector2 getDoorPos(){
    if(tCount==-1) return new Vector2(0,0);
    return new Vector2(64-tCount,0);
  }
  public String getStagePath(){
    return stagePath;
  }
}
