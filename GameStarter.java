package project;

import project.graphics.*;
import project.tools.*;
import project.*;
public class GameStarter{
  public static void main(String args[]){
    GameWindow gameWindow = new GameWindow();
    Sound sm = new Sound();
    Controller controller = new Controller(gameWindow);
    World world = new World();
    StartMenu startMenu = new StartMenu(gameWindow,controller,sm);
  }
}
