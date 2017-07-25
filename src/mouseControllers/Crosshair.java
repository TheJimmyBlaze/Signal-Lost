package mouseControllers;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;

import main.Global;
import main.Main;

public class Crosshair {	
	private CrosshairSprite image;	
	private Point pos = new Point(0,0);
	private Point targetIndicator = new Point(0,0);
	
	private boolean mouseLock;
	public boolean fireDisabled = false;
	
	public void disable(boolean v){fireDisabled = v;}
	public boolean isDisabled(){return fireDisabled;}
	public BufferedImage getImage(){return image.getImage();}
	public CrosshairSprite getSprite(){return image;}
	public Point getTargetIndicator(){return targetIndicator;}
	public boolean getLock(){return mouseLock;}
	public void move(Point p){
		pos = p;
	}
	public void step(int x, int y){
		pos.x += x;
		pos.y += y;
	}
	public Point getPos(){return pos;}
	public void setSprite(CrosshairSprite sprite){image = sprite;}
	
	public void setTargetIndicator(Point p){targetIndicator = p;}
	
	public Crosshair(int x, int y, boolean mouseLockPass){
		move(new Point(x,y));
		mouseLock = mouseLockPass;
	}
	
	public void draw(Graphics g){
		g.setColor(Global.cursorColor);
		if(!fireDisabled)
			g.fillRect(pos.x + Main.activeCamera.getOffset().x -1, pos.y + Main.activeCamera.getOffset().y -14, 2, 4);
		else{
			g.drawLine(pos.x + Main.activeCamera.getOffset().x - 2, pos.y + Main.activeCamera.getOffset().y - 16, pos.x + Main.activeCamera.getOffset().x + 2, pos.y + Main.activeCamera.getOffset().y - 12);
			g.drawLine(pos.x + Main.activeCamera.getOffset().x - 2, pos.y + Main.activeCamera.getOffset().y - 12, pos.x + Main.activeCamera.getOffset().x + 2, pos.y + Main.activeCamera.getOffset().y - 16);
		}
		
		if(!fireDisabled)
			g.drawImage(image.getImage(), (int)targetIndicator.getX() - image.getImage().getWidth()/2 + Main.activeCamera.getOffset().x,
					(int)targetIndicator.getY() - image.getImage().getHeight()/2 + Main.activeCamera.getOffset().y, null);
	}
}
