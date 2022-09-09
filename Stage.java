package project.objects;

import project.tools.*;
import java.util.*;
import java.io.*;

public class Stage{
    public static int stageSizeX;
    public static int stageSizeY;
    public static int playerPosX;
    public static int playerPosY;
    public static String background;
    public static int[][] block;
    public static int[][] enemy;
    public static int[][] item;
    public void prepareStage(String stageName){
      //https://nompor.com/2017/11/23/post-82/　ファイル読み込み用ソース使用
      String fileName = "./StageData/"+stageName+".txt";
      try(FileReader file = new FileReader(fileName); BufferedReader br = new BufferedReader(file)){
        br.readLine();
        stageSizeX = Integer.parseInt(br.readLine());
        stageSizeY = Integer.parseInt(br.readLine());
        block = new int[stageSizeX][stageSizeY];
        enemy = new int[stageSizeX][stageSizeY];
        item = new int[stageSizeX][stageSizeY];
        br.readLine();
        playerPosX = Integer.parseInt(br.readLine());
        playerPosY = Integer.parseInt(br.readLine());
        br.readLine();
        background = br.readLine();
        br.readLine();
        for(int j=0;j<stageSizeY;j++){
          String tmp=br.readLine();
          String[] nums = tmp.split(" ", 0);
          for(int i=0;i<stageSizeX;i++){
            block[i][j] = Integer.parseInt(nums[i]);
          }
        }
        br.readLine();
        for(int j=0;j<stageSizeY;j++){
          String tmp=br.readLine();
          String[] nums = tmp.split(" ", 0);
          for(int i=0;i<stageSizeX;i++){
            enemy[i][j] = Integer.parseInt(nums[i]);
          }
        }
        br.readLine();
        for(int j=0;j<stageSizeY;j++){
          String tmp=br.readLine();
          String[] nums = tmp.split(" ", 0);
          for(int i=0;i<stageSizeX;i++){
            item[i][j] = Integer.parseInt(nums[i]);
          }
        }
      }catch(IOException e){
        e.printStackTrace();
        System.out.println("Stage Import Error!");
        System.exit(1);
      }
    }
    public void prepareNewStage(int sx, int sy, String bi){
      stageSizeX = sx;
      stageSizeY = sy;
      playerPosX = 128;
      playerPosY = 128;
      background=bi;
      block = new int[stageSizeX][stageSizeY];
      enemy = new int[stageSizeX][stageSizeY];
      item = new int[stageSizeX][stageSizeY];
      for(int i=0;i<stageSizeX;i++){
        for(int j=0;j<stageSizeY;j++){
          block[i][j]=0;
          enemy[i][j]=0;
          item[i][j]=0;
          if(i==0 || i==stageSizeX-1 || j==0 || j==stageSizeY-1 || j==stageSizeY-3){
            block[i][j]=1;
          }else if(j==stageSizeY-2)block[i][j]=2;
        }
      }
    }
}
