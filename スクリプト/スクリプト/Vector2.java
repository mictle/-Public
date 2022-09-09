/*
  Vector クラス
  JREでよく使うstruct pointと全く同じ使い方をします。
  javaでもVectorクラスはありましたが重そうだったので自作。
  copyFrom : 与えられたVector型引数のデータを自身にコピーします。
*/

package project.tools;

public class Vector2{
  public double x;
  public double y;
  public Vector2(){
    x=0;
    y=0;
  }
  public Vector2(double x, double y){
    this.x = x;
    this.y = y;
  }
  public Vector2(Vector2 v){
    x=v.x;
    y=v.y;
  }
  public void copyFrom(Vector2 v){
    x = v.x;
    y = v.y;
  }
  public Vector2 add(Vector2 v1, Vector2 v2){
    x=v1.x+v2.x;
    y=v1.y+v2.y;
    return this;
  }
  public Vector2 sub(Vector2 v1, Vector2 v2){
    x = v1.x-v2.x;
    y = v1.y-v2.y;
    return this;
  }

  public Vector2 addReturn(Vector2 v){
    return new Vector2(x+v.x, y+v.y);
  }

}
