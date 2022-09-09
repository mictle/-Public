package project;

import project.tools.*;
import java.awt.*;

import project.*;
import project.graphics.GameWindow;
import project.objects.*;
import javax.swing.*;

import project.objects.*;
import java.awt.event.*;
import java.security.KeyException;

/**
 Controller:プレイヤーキャラクターの操作。押されたボタンに対応してboolを切り替える。
 */
public class Controller implements KeyListener {
  	private boolean lwalk, rwalk, up, down, atk, jmp;
  	public Controller(GameWindow gw){
		lwalk = false; rwalk = false; atk = false; jmp = false; up = false; down = false;
		gw.addKeyListener(this);
	}
	public boolean getlwalk(){
		return lwalk;
	}
	public boolean getrwalk(){
		return rwalk;
	}
  public boolean getup(){
    return up;
  }
  public boolean getdown(){
    return down;
  }
	public boolean getatk(){
		return atk;
	}
	public boolean getjmp(){
		return jmp;
	}
  	public void keyTyped(KeyEvent e) {
	}
	public void keyPressed(KeyEvent e) {
		//System.out.println("keypressed");
		switch (e.getKeyCode()) {
			case KeyEvent.VK_RIGHT:
				//System.out.println("rwalk");
				rwalk = true;
				break;
			case KeyEvent.VK_LEFT:
				//System.out.println("lwalk");
				lwalk = true;
				break;
      case KeyEvent.VK_UP:
  			//System.out.println("up");
  			up = true;
  			break;
      case KeyEvent.VK_DOWN:
  			//System.out.println("down");
  			down = true;
  			break;
			case KeyEvent.VK_X:
				//System.out.println("jump");
				jmp = true;
				break;
			case KeyEvent.VK_Z:
				//System.out.println("attack");
				atk = true;
				break;
		}
	}
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_RIGHT:
				//System.out.println("rwalk");
				rwalk = false;
				break;
			case KeyEvent.VK_LEFT:
				//System.out.println("lwalk");
				lwalk = false;
				break;
      case KeyEvent.VK_UP:
  			//System.out.println("up");
  			up = false;
  			break;
      case KeyEvent.VK_DOWN:
  			//System.out.println("down");
  			down = false;
  			break;
			case KeyEvent.VK_X:
				//System.out.println("jump");
				jmp = false;
				break;
			case KeyEvent.VK_Z:
				//System.out.println("attack");
				atk = false;
				break;
		}
	}
}
