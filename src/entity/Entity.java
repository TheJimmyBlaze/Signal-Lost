package entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import main.Global;
import main.Main;
import object.DynamicObject;
import object.Object;
import sprite.Sprite;

public class Entity extends DynamicObject{
	
	protected int yPrevious;
	protected int xPrevious;
	
	protected boolean rightFacing = true;
	protected boolean falling = true;
	protected int lastGround = 0;
	
	protected double vSpeed = 0d;
	protected double hSpeed = 0d;
	
	protected boolean falls;
	protected double gravityMultiplyer;
	
	protected double health = 100;
	protected boolean dead = false;
	protected Rectangle hitBox;
	
	public boolean isDead(){return dead;}
	public void damage(double d){health -= d;}
	protected boolean frozen = false;
	public void freeze(boolean b){frozen = b;}
	public boolean isFrozen(){return frozen;}
	public boolean checkHit(Rectangle r){return r.intersects(new Rectangle(x + hitBox.x, y + hitBox.y, hitBox.width, hitBox.height));}
	
	public Entity(){}
	
	public Entity(int shelfIndexPass, int uidPass, String namePass, Sprite imagePass, Point pos, boolean fallsPass, double gravityMultiplyerPass) {
		super(shelfIndexPass, uidPass, namePass, imagePass, pos);
		xPrevious = (int)pos.getX();
		yPrevious = (int)pos.getY();
		falls = fallsPass;
		gravityMultiplyer = gravityMultiplyerPass;
	}
	
	public Object clone(Point pos) {
		return new Entity(shelfIndex, uid, name, image.clone(), pos, falls, gravityMultiplyer);
	}
	
	public void tick(){
		if(!frozen){
			
			super.tick();
			input();
			
			collision.updateX((int)hSpeed);
			
			for(Object o: Main.levelHandler.getLayer(collision.getCollision(), 0)){
				if(collision.check(o.getCollision())){
					if(o.getPos()[0] >= xPrevious + collision.getCollision().getWidth()){
						collision.setX(o.getPos()[0] - (int)collision.getCollision().getWidth());
					}
					if(o.getPos()[0] + o.getCollision().getWidth() <= xPrevious){
						collision.setX(o.getPos()[0] + (int)o.getCollision().getWidth());
					}
				}
			}
	
			falling = true;
			if(falls && vSpeed < Global.gravityConstant)
				vSpeed += Global.gravitySpeed*gravityMultiplyer;
			
			for(int i = 0; i < 4; i++){
				collision.updateY((int)vSpeed/4);
				for(Object o: Main.levelHandler.getLayer(collision.getCollision(), 0)){
					if(collision.check(o.getCollision())){
						if(o.getPos()[1] > yPrevious + collision.getCollision().getHeight() -1){
							falling = false;
							vSpeed = 0;
							collision.setY(o.getPos()[1] - (int)collision.getCollision().getHeight());
							lastGround = collision.getY();
						}
						if(o.getPos()[1] + o.getCollision().getHeight() < yPrevious -1){
							vSpeed = 0;
							collision.setY(o.getPos()[1] + (int)o.getCollision().getHeight());
						}
					}
				}
			}
			xPrevious = collision.getX();
			yPrevious = collision.getY();
			
			x = collision.collectX();
			y = collision.collectY();
		}
	}
	
	public void input(){}
	
	public void draw(Graphics g){
		super.draw(g);
		
		if(Global.showHitBoxes && hitBox != null){
			g.setColor(Color.CYAN);
			g.drawRect(x + hitBox.x + Main.activeCamera.getOffset().x, y + hitBox.y + Main.activeCamera.getOffset().y, hitBox.width, hitBox.height);
		}
	}
}
