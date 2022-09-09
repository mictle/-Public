package project.objects;

import project.objects.*;
import project.objects.enemy.*;
import project.tools.*;
import project.*;

public class EnemyBinder{
  private CharacterData cData;
  public EnemyBinder(){

  }
  public void summonEnemy(int i, Vector2 pos){
    switch (i) {
      case 1:
        cData = new BlueSlime(pos);
        break;
      default:
        System.out.println("unknown enemy Data");
        cData = new BlueSlime(pos);
        break;
    }
    GameManager.charaList.add(cData);

  }
  public void prepareEnemies(){
    for(int j=0;j<Stage.stageSizeY;j++){
      for(int i=0;i<Stage.stageSizeX;i++){

        if(Stage.enemy.get(j).get(i)!=0){
          System.out.println(i + ", "+ (j));
          summonEnemy(Stage.enemy.get(j).get(i), new Vector2(i*64 + 32, j*64 + 32));
        }
      }
    }
  }
}
