package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import window.WindowHandler;

public class KeyboardHandler implements KeyListener{
	
	private boolean left = false;
	private boolean right = false;
	private boolean up = false;
	
	public boolean left(){return left;}
	public boolean right(){return right;}
	public boolean up(){return up;}

	public KeyboardHandler(){
		Main.frame.addKeyListener(this);
	}
	
	public void keyPressed(KeyEvent e){
		if(e.getKeyCode() == 123){
			if(WindowHandler.isVisible("ConsoleWindow")){
				WindowHandler.deactivateWindow("ConsoleWindow");
			}
			else{
				WindowHandler.activateWindow("ConsoleWindow");
				WindowHandler.getFocusWindow().textSelected = true;
			}
		}
		
		if(e.getKeyCode() == 65)
			left = true;
		if(e.getKeyCode() == 68)
			right = true;
		if(e.getKeyCode() == 87)
			up = true;
		
		if(e.getKeyCode() == 18){
			Global.altPaused = true;
		}
		
		if(WindowHandler.getFocusWindow() != null){
			WindowHandler.getFocusWindow().keyPress(e);
		}
	}

	public void keyReleased(KeyEvent e) {

		if(e.getKeyChar() == 'a')
			left = false;
		if(e.getKeyChar() == 'd')
			right = false;
		if(e.getKeyChar() == 'w')
			up = false;
	}
	
	public void keyTyped(KeyEvent e) {
	}
}
