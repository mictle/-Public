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
    public static ArrayList<ArrayList<Integer>> block;
    public static ArrayList<ArrayList<Integer>> enemy;
    public static ArrayList<ArrayList<Integer>> items;
    public void prepareStage(String stageName){
      block = new ArrayList<ArrayList<Integer>>();
      enemy = new ArrayList<ArrayList<Integer>>();
      //https://nompor.com/2017/11/23/post-82/　ファイル読み込み用ソース使用
      String fileName = "./StageData/"+stageName+".txt";
      try(FileReader file = new FileReader(fileName); BufferedReader br = new BufferedReader(file)){
        br.readLine();
        stageSizeX = Integer.parseInt(br.readLine());
        stageSizeY = Integer.parseInt(br.readLine());
        br.readLine();
        playerPosX = Integer.parseInt(br.readLine());
        playerPosY = Integer.parseInt(br.readLine());
        br.readLine();
        for(int j=0;j<stageSizeY;j++){
          ArrayList<Integer> listLine = new ArrayList<Integer>();
          String tmp = br.readLine();
          String[] datas = tmp.split(" ", 0);
          for(int i=0;i<stageSizeX;i++){
            listLine.add(Integer.parseInt(datas[i]));
          }
          block.add(listLine);
        }
        br.readLine();
        for(int j=0;j<stageSizeY;j++){
          ArrayList<Integer> listLine = new ArrayList<Integer>();
          String tmp = br.readLine();
          String[] datas = tmp.split(" ", 0);
          for(int i=0;i<stageSizeX;i++){
            listLine.add(Integer.parseInt(datas[i]));
          }
          enemy.add(listLine);
        }
        br.readLine();
        for(int j=0;j<stageSizeY;j++){
          ArrayList<Integer> listLine = new ArrayList<Integer>();
          String tmp = br.readLine();
          String[] datas = tmp.split(" ", 0);
          for(int i=0;i<stageSizeX;i++){
            listLine.add(Integer.parseInt(datas[i]));
          }
          items.add(listLine);
        }
        for(int j=0;j<stageSizeY;j++){
          for(int i=0;i<stageSizeX;i++){
            System.out.print(enemy.get(j).get(i)+" ");
          }
          System.out.println("");
        }
      }catch(IOException e){
        e.printStackTrace();
        System.out.println("Stage Import Error!");
        System.exit(1);
      }
    }
}
