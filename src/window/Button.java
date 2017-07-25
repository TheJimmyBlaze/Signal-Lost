package window;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import main.Global;
import sprite.Sprite;
import sprite.SpriteLibrary;

public class Button {

	protected Sprite image;
	protected Rectangle collision;
	
	protected int x;
	protected int y;
	private String name;
	
	public Rectangle getCollision(){return collision;}
	public void setLocation(int xPass, int yPass){collision.setLocation(xPass, yPass); x = xPass; y = yPass;}
	
	public boolean click(Rectangle r){
		if (collision.intersects(r)){
			image = SpriteLibrary.collectSpriteName(name);
			return true;
		}
		return false;
	}
	public Button(String namePass, int xPass, int yPass){
		name = namePass;
		image = SpriteLibrary.collectSpriteName(namePass);
		
		x = xPass;
		y = yPass;
		
		collision = new Rectangle(x, y, image.getSize()[0], image.getSize()[1]);
	}
	
	public void tick(Rectangle r){
		if(collision.intersects(r)){
			image = SpriteLibrary.collectSpriteName(name + "Inv");
		}
		else{
			image = SpriteLibrary.collectSpriteName(name);
		}
	}
	
	public void draw(Graphics g){
		g.drawImage(image.getImage(), x, y, null);
		
		if(Global.showCollisionMaps){
			g.setColor(Color.BLUE);
			g.drawRect((int)collision.getX(), (int)collision.getY(), (int)collision.getWidth(), (int)collision.getHeight());
		}
	}
}
