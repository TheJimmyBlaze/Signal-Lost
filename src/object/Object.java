package object;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.PrintWriter;

import main.Global;
import main.Main;
import sprite.Sprite;

public abstract class Object {

	protected Sprite image;
	
	protected CollisionMap collision;
	
	protected int shelfIndex;
	protected int uid;
	protected String name;
	
	protected int x;
	protected int y;
	
	public int getShelfIndex(){return shelfIndex;}
	public int getUID(){return uid;}
	public String getName(){return name;}
	public int[] getPos(){return new int[]{x, y};}
	public Sprite getSprite(){return image;}
	public Rectangle getCollision(){return collision.getCollision();}
	
	public boolean check(Rectangle collider){
		return collision.check(collider);
	}
	
	public Object(){}
	
	public Object(int shelfIndexPass, int uidPass, String namePass, Sprite imagePass, Point pos){
		
		if(imagePass != null)
			image = imagePass.clone();
		
		x = (int)pos.getX();
		y = (int)pos.getY();
		
		shelfIndex = shelfIndexPass;
		uid = uidPass;
		name = namePass;
		
		if(image != null)
			updateCollision();
	}
	
	public void updateCollision(){
		collision = new CollisionMap(new Rectangle(x, y, image.getImage().getWidth(), image.getImage().getHeight()), 0, 0);
	}
	
	public void tick(){}
	
	public abstract Object clone(Point pos);
	
	public void shift(int xPass, int yPass){
		x += xPass;
		y += yPass;
	}
	
	public void save(PrintWriter writer){
		writer.println(shelfIndex + "," + uid + "," + x + "," + y);
	}
	
	public void draw(Graphics g){
		g.drawImage(image.getImage(), x + Main.activeCamera.getOffset().x, y + Main.activeCamera.getOffset().y, null);
		
		if(collision != null && Global.showCollisionMaps)
			collision.draw(g);
	}
	
	public void draw(Graphics g, int xOffset, int yOffset){
		g.drawImage(image.getImage(), x - xOffset, y - yOffset, null);
		
		if(collision != null && Global.showCollisionMaps)
			collision.draw(g, xOffset, yOffset);
	}
}
