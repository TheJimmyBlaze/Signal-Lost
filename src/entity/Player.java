package entity;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.Main;
import object.CollisionMap;
import sprite.Sprite;
import sprite.SpriteLibrary;
import weapon.Rifle;
import weapon.WeaponModLibrary;
import wearables.AnimatedWearable;
import wearables.Wearable;

public class Player extends Humanoid{
	
	public Player(int shelfIndexPass, int uidPass, String namePass, Sprite imagePass, Point pos) {
		super(shelfIndexPass, uidPass, namePass, imagePass, pos);
		collision = new CollisionMap(new Rectangle(x, y, 40, (int)collision.getCollision().getHeight()), 20, 0);

		weapon = new Rifle(WeaponModLibrary.rifle.collectReceiver(0), WeaponModLibrary.rifle.collectBarrel(0), WeaponModLibrary.rifle.collectStock(0),
				WeaponModLibrary.rifle.collectGrip(0), WeaponModLibrary.rifle.collectMag(0), WeaponModLibrary.rifle.collectSight(0), WeaponModLibrary.rifle.collectExtension(0));
		weapon.setOwned();
		
		legs = new AnimatedWearable("WarLordLeg", SpriteLibrary.collectSpriteName("WarLordLeg"));
		chest = new Wearable("WarLordChest", SpriteLibrary.collectSpriteName("WarLordChest"));
		mask = new Wearable("WarLordCrown", SpriteLibrary.collectSpriteName("WarLordCrown"), false);
		
		hair = new Wearable("Slick", SpriteLibrary.collectSpriteName("Slick"), false);
		eyes = new Wearable("GreenEyes", SpriteLibrary.collectSpriteName("GreenEyes"), false);
		
		BufferedImage armUnderImage = SpriteLibrary.collectSpriteName("TemplateArm").getImage();
		BufferedImage armImage = new BufferedImage(armUnderImage.getWidth(), armUnderImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics g = armImage.createGraphics();
		g.drawImage(armUnderImage, 0, 0, null);
		g.drawImage(chest.getArm().getImage(), 0, 0, null);
		g.dispose();
		armSprite = new Sprite(armImage, null, "null/unimportant", 0, 0);
		arm = new Armature("Template", armSprite, weapon);
		
		health = 999999999;
	}
	
	public void randomiseWeapon(){
		weapon = new Rifle(WeaponModLibrary.rifle.collectReceiver(0), WeaponModLibrary.rifle.collectBarrel(0), WeaponModLibrary.rifle.collectStock(0),
				WeaponModLibrary.rifle.collectGrip(0), WeaponModLibrary.rifle.collectMag(0), WeaponModLibrary.rifle.collectSight(0), WeaponModLibrary.rifle.collectExtension(0));
		weapon.setOwned();
		arm = new Armature("Template", armSprite, weapon);
	}
	
	public void tick(){
		int xDelta = x;
		int yDelta = y;
		
		double xMouseDistance;
		double yMouseDistance;
		if(cursorOffset.getX() > 0){xMouseDistance = cursorOffset.getX() / 1097;}
		else{xMouseDistance = cursorOffset.getX() / -1095;}
		if(cursorOffset.getY() > 0){yMouseDistance = cursorOffset.getY() / 464;}
		else{yMouseDistance = cursorOffset.getY() / -1095;}
		rotationalCorrection = 1 - (xMouseDistance + yMouseDistance);
		if(rotationalCorrection < 0)
			rotationalCorrection = 0;
		
		super.tick();
		
		xDelta = x - xDelta;
		yDelta = y - yDelta;
		Main.gameCursor.step(xDelta, yDelta);
		
		int xArmOffset;
		if(rightFacing){xArmOffset = 8;}
		else{xArmOffset = 32;}
		setCursorOffset(new Point((int)(Main.gameCursor.getPos().getX() - xArmOffset - Main.levelHandler.getPlayer().getCollision().getX()),
				(int)(Main.gameCursor.getPos().getY() - 88 - Main.levelHandler.getPlayer().getCollision().getY())));
	}
	
	protected void disableCursor(boolean val){
		super.disableCursor(val);
		Main.gameCursor.disable(val);
	}
	
	public void input(){
		if(Main.keyHandler.up() && !falling){ 
			vSpeed = -28; 
			falling = true;
		}
		if(Main.keyHandler.right()){
			if(hSpeed < 8)
				hSpeed += 3;
		}
		else if(Main.keyHandler.left()){
			if(hSpeed > -8)
				hSpeed -= 3;
		}
		else{
			if(hSpeed < 5 && hSpeed > -5)
				hSpeed = 0;
			if(hSpeed > 0)
				hSpeed -= 4;
			if(hSpeed < 0)
				hSpeed += 4;
		}
	}
}
