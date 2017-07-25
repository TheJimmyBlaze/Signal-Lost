package entity;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.Global;
import main.Main;
import object.Object;
import sprite.AnimatedSprite;
import sprite.Sprite;
import sprite.SpriteLibrary;
import weapon.Weapon;
import wearables.AnimatedWearable;
import wearables.Wearable;

public class Humanoid extends Entity{
	
	protected Sprite still;
	protected Sprite reverseStill;
	protected Sprite run;
	protected Sprite reverseRun;
	protected Sprite backRun;
	protected Sprite reverseBackRun;
	protected Sprite jump;
	protected Sprite reverseJump;
	
	protected Armature arm;
	protected Sprite armSprite;
	protected double armatureAngle;
	protected Weapon weapon;
	protected double rotationalCorrection;
	
	protected AnimatedWearable legs;
	protected Wearable chest;
	protected Wearable hair;
	protected Wearable eyes;
	protected Wearable mask = null;
	protected final int LEGS_OFFSET = 104;
	protected final int CHEST_OFFSET = 56;
	protected final int HAIR_OFFSET = -64;
	protected final int EYES_OFFSET = 35;
	protected final int MASK_OFFSET = -64;
	
	protected Point cursorOffset = new Point(0,0);
	public void setCursorOffset(Point p){cursorOffset = p;}
	
	public void firing(){weapon.fire();}
	public void stopFiring(){weapon.stopFiring();}

	public Humanoid(int shelfIndexPass, int uidPass, String namePass,
			Sprite imagePass, Point pos) {
		super(shelfIndexPass, uidPass, namePass, imagePass, pos, true, 1);
		
		hair = new Wearable("Slick", SpriteLibrary.collectSpriteName("Slick"), false);
		eyes = new Wearable("BlueEyes", SpriteLibrary.collectSpriteName("BlueEyes"), false);
		
		still = image.clone();
		(reverseStill = still.flip()).setOffset(new Point(12,0));
		run = SpriteLibrary.collectSpriteName(image.getName() + "A").clone();
		reverseRun = SpriteLibrary.collectSpriteName(image.getName() + "AF").clone();
		backRun = SpriteLibrary.collectSpriteName(image.getName() + "RA").clone();
		reverseBackRun = SpriteLibrary.collectSpriteName(image.getName() + "RAF").clone();
		jump = SpriteLibrary.collectSpriteName(image.getName() + "J").clone();
		(reverseJump = jump.flip()).setOffset(new Point(12,0));
	}

	public void tick(){
		
		if(hitBox == null)
			hitBox = new Rectangle(2, 0, (int)collision.getCollision().getWidth() + 32, (int)collision.getCollision().getHeight());
		
		super.tick();
		input();
		
		if(cursorOffset.getX() >= 0){
			rightFacing = true;
		}
		else{
			rightFacing = false;
		}
		
		collision.updateX((int)hSpeed);

		if(cursorOffset.getX() >= 0)
			armatureAngle = -Math.atan2(cursorOffset.getX(), cursorOffset.getY()) + Math.PI/2 + weapon.getMuzzleCorrection() * rotationalCorrection;
		else
			armatureAngle = -Math.atan2(cursorOffset.getX(), cursorOffset.getY()) - Math.PI/2 - weapon.getMuzzleCorrection() * rotationalCorrection;
		
		Point origin;
		int casingEjectionOffset = 0;
		if(rightFacing){
			arm.rotate(rightFacing, armatureAngle - weapon.getVerticleRecoil()/100 * 1.028 );
			origin = arm.getOriginPoint(rightFacing, rotationalCorrection, 0, cursorOffset);
		}
		else{
			arm.rotate(rightFacing, armatureAngle + weapon.getVerticleRecoil()/100 * 1.028 );
			origin = arm.getOriginPoint(rightFacing, rotationalCorrection, 0, cursorOffset);
			casingEjectionOffset = -26;
		}
		Point casingEjection = arm.getOriginPoint(rightFacing, rotationalCorrection, weapon.getEjectionOffset() - casingEjectionOffset, cursorOffset);
		casingEjection.x+=x;casingEjection.y+=y-32;
		
		int length = (int)Math.sqrt(Math.pow(cursorOffset.getX() + image.getImage().getWidth()/2 - origin.getX(), 2) + Math.pow(cursorOffset.getY() + image.getImage().getHeight()/2  - origin.getY(), 2));
		if(Math.sqrt(Math.pow(cursorOffset.getX(), 2) + Math.pow(cursorOffset.getY(), 2)) <
				Math.sqrt(Math.pow(image.getImage().getWidth()/2 - origin.x, 2) + Math.pow(image.getImage().getHeight()/2 - origin.y, 2))*1.1)
			disableCursor(true);
		else
			disableCursor(false);
		
		Point destination = arm.getOriginPoint(rightFacing, rotationalCorrection, length, cursorOffset);
		destination.x+=x;destination.y+=y;
		weapon.tick(arm.getRotation(rightFacing, rotationalCorrection, length, cursorOffset)
				, new Point( origin.x + x + (int)hSpeed, origin.y + y + (int)vSpeed), destination, casingEjection);
		
		if(hSpeed > 0 && !falling){
			if(rightFacing && image != run){
				image = run;
				updateImage(5);
			}
			if(!rightFacing && image != reverseBackRun){
				image = reverseBackRun;
				updateImage(8);
			}
		}
		if(hSpeed < 0 && !falling){
			if(!rightFacing && image != reverseRun){
				image = reverseRun;
				updateImage(6);
			}
			if(rightFacing && image != backRun){
				image = backRun;
				updateImage(7);
			}
		}
		
		if(hSpeed == 0 && !falling){
			if(rightFacing && image != still){
				image = still;
				updateImage(1);
			}
			if(!rightFacing && image != reverseStill){
				image = reverseStill;
				updateImage(2);
			}
		}

		arm.setOffset(new Point(arm.getOffset().x, image.getOffset().y));
		
		for(Object o: Main.levelHandler.getLayer(collision.getCollision(), 0)){
			if(collision.check(o.getCollision())){
				if(o.getPos()[0] >= xPrevious + collision.getCollision().getWidth()){
					if(o.getPos()[1] > collision.getY() + collision.getCollision().getHeight() - 17 && !falling){
						collision.setY(o.getPos()[1] - (int)collision.getCollision().getHeight() -3);
						collision.updateX(2);
					}
					else
						collision.setX(o.getPos()[0] - (int)collision.getCollision().getWidth());
				}
				if(o.getPos()[0] + o.getCollision().getWidth() <= xPrevious){
					if(o.getPos()[1] > collision.getY() + collision.getCollision().getHeight() - 17 && !falling){
						collision.setY(o.getPos()[1] - (int)collision.getCollision().getHeight() -3);
						collision.updateX(-2);
					}
					else
						collision.setX(o.getPos()[0] + (int)o.getCollision().getWidth());
				}
			}
		}

		falling = true;
		if(vSpeed < Global.gravityConstant)
			vSpeed += Global.gravitySpeed;
		
		for(int i = 0; i < 8; i++){
			collision.updateY((int)vSpeed/8);
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
		
		if(falling && collision.getY() > lastGround + 20 || collision.getY() < lastGround - 20){
			if(rightFacing && image != jump){
				image = jump;
				updateImage(3);
			}
			if(!rightFacing && image != reverseJump){
				image = reverseJump;
				updateImage(4);
			}
		}
		
		xPrevious = collision.getX();
		yPrevious = collision.getY();
		
		x = collision.collectX();
		y = collision.collectY();
		
		if(health < 0){
			dead = true;
			BufferedImage[] images = new BufferedImage[3];
			BufferedImage workingImage = new BufferedImage(still.getImage().getWidth() - MASK_OFFSET*2, still.getImage().getHeight() - MASK_OFFSET, BufferedImage.TYPE_INT_ARGB);
			Graphics g = workingImage.createGraphics();
			g.drawImage(((AnimatedSprite)run).getFrame(3).getImage(), -MASK_OFFSET, -MASK_OFFSET, null);
			g.drawImage(((AnimatedSprite)legs.getImageW()).getFrame(3).getImage(), - MASK_OFFSET, LEGS_OFFSET - MASK_OFFSET, null);
			g.drawImage(((AnimatedSprite)chest.getImageW()).getFrame(3).getImage(), - MASK_OFFSET, CHEST_OFFSET - MASK_OFFSET, null);
			g.drawImage(((AnimatedSprite)hair.getImageW()).getFrame(3).getImage(), 0, 0, null);
			g.drawImage(((AnimatedSprite)eyes.getImageW()).getFrame(3).getImage(), -MASK_OFFSET, EYES_OFFSET - MASK_OFFSET, null);
			if(mask != null)
				g.drawImage(((AnimatedSprite)mask.getImageW()).getFrame(3).getImage(), 0, 0, null);
			g.dispose();
			images[0] = workingImage;
			images[1] = workingImage;
			images[2] = workingImage;
			Main.levelHandler.addDeathModel(new HumanoidDeathModel(images, new Point(x, y), Main.levelHandler.getPlayer().rightFacing));
		}
	}
	
	protected void disableCursor(boolean val){
		weapon.disable(val);
	}
	
	protected void updateImage(int i){
		legs.setCurrent(i);
		chest.setCurrent(i);
		hair.setCurrent(i);
		eyes.setCurrent(i);
		if(mask != null)
			mask.setCurrent(i);
	}
	
	public void draw(Graphics g){
		super.draw(g);
		if(image instanceof AnimatedSprite){
			legs.updateFrame(((AnimatedSprite)image).getFrameIndex()); 
			chest.updateFrame(((AnimatedSprite)image).getFrameIndex());
			hair.updateFrame(((AnimatedSprite)image).getFrameIndex());
			eyes.updateFrame(((AnimatedSprite)image).getFrameIndex());
			if(mask != null)
				mask.updateFrame(((AnimatedSprite)image).getFrameIndex());
		}
		
		legs.draw(g, x, y + LEGS_OFFSET);
		chest.draw(g, x, y + CHEST_OFFSET);
		hair.draw(g, x + HAIR_OFFSET, y + HAIR_OFFSET);
		eyes.draw(g, x, y + EYES_OFFSET);
		if(mask != null)
			mask.draw(g, x + MASK_OFFSET, y + MASK_OFFSET);
		
		arm.draw(g, rightFacing, x, y);
	}
}
