package project.objects;

import java.awt.*;
import project.tools.*;
import project.*;


public interface Item{
  public void itemEffect(int posX, int posY, GameManager gm,Player player);
  public Image getImage();
}
