package project.objects;

import java.awt.*;
import project.tools.Vector2;
import project.tools.ColliderDitect;
import project.GameManager;

public class Bullet extends ColliderDitect{
  protected int damage, id, limitTime, count;
  protected Vector2 speed, centerPos, physlu, physrd,imagePos,imageSize;
  protected Image image;
  public Bullet(int id, int damage, int limitTime, Vector2 centerPos, Vector2 imagePos, Vector2 imageSize,Vector2 physlu, Vector2 physrd, Vector2 speed, String imagePath){
    this.damage = damage;
    this.limitTime = limitTime;
    count=0;
    this.centerPos = new Vector2(centerPos);
    this.imagePos = new Vector2(imagePos);
    this.imageSize = new Vector2(imageSize);
    this.physlu = new Vector2(physlu);
    this.physrd = new Vector2(physrd);
    this.speed = new Vector2(speed);
    this.image = Toolkit.getDefaultToolkit().getImage("./irusts/"+imagePath);
    this.id = id;
  }
  public int update(){
    centerPos.add(speed);
    count++;
    if(count>limitTime)return 1;
    if(id==0){
      CharacterData c = attackToEnemyByBullet(this);
      if(c.getId()!=0){
        c.damage(damage);
        return 1;
      }
    }else{
      if(bulletDitect(this)){
        GameManager.charaList.get(0).damage(damage);
        return 1;
      }
    }
    return 0;
  }
  public Vector2 getPhyslu(){
    return physlu;
  }
  public Vector2 getPhysrd(){
   return physrd;
  }
  public void hitAction(){
    if(id==0){

    }
  }
  public Vector2 getCenterPos(){
    return centerPos;
  }
  public Vector2 getImagePos(){
    return imagePos;
  }
  public Vector2 getImageSize(){
    return imageSize;
  }
  public Image getImage(){
    return image;
  }
  public int getId(){
    return id;
  }
}
