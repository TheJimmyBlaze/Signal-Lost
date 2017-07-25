package object;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import main.Main;

public class CollisionMap {	
	private Rectangle collision;
	
	private int xOffset,yOffset;
	
	public Rectangle getCollision(){return collision;}
	public int getX(){return (int)collision.getX();}
	public int getY(){return (int)collision.getY();}
	
	public void updateX(int xPass){collision.x += xPass;}
	public void updateY(int yPass){collision.y += yPass;}
	
	public int collectX(){return (int)collision.getX() - xOffset;}
	public int collectY(){return (int)collision.getY() - yOffset;}
	
	public void setX(int xPass){collision.x = xPass;}
	public void setY(int yPass){collision.y = yPass;}
	
	public CollisionMap(Rectangle r, int xOffsetPass, int yOffsetPass){
		xOffset = xOffsetPass;
		yOffset = yOffsetPass;
		
		r.x += xOffsetPass;
		r.y += yOffsetPass;
		collision = r;
	}
	
	public boolean check(Rectangle collider){
		if(collider.intersects(collision))
			return true;
		return false;
	}
	
	public void draw(Graphics g){
		g.setColor(Color.RED);
		g.drawRect((int)collision.getX() + Main.activeCamera.getOffset().x, (int)collision.getY() + Main.activeCamera.getOffset().y, (int)collision.getWidth(), (int)collision.getHeight());
	}
	
	public void draw(Graphics g, int xOffset, int yOffset){
		g.setColor(Color.RED);
		g.drawRect((int)collision.getX() - xOffset, (int)collision.getY() - yOffset, (int)collision.getWidth(), (int)collision.getHeight());
	}
}
