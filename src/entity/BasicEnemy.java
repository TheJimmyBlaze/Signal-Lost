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

public class BasicEnemy extends Humanoid{
	
	private int randomUpdateTime = 0;
	private int direction = 0;
	
	public BasicEnemy(int shelfIndexPass, int uidPass, String namePass, Sprite imagePass, Point pos) {
		super(shelfIndexPass, uidPass, namePass, imagePass, pos);
		health = 1;
		collision = new CollisionMap(new Rectangle(x, y, 40, (int)collision.getCollision().getHeight()), 20, 0);

		weapon = new Rifle(WeaponModLibrary.rifle.collectReceiver(0), WeaponModLibrary.rifle.collectBarrel(0), WeaponModLibrary.rifle.collectStock(0),
				WeaponModLibrary.rifle.collectGrip(0), WeaponModLibrary.rifle.collectMag(0), WeaponModLibrary.rifle.collectSight(0), WeaponModLibrary.rifle.collectExtension(0));
		
		legs = new AnimatedWearable("BanditLeg1", SpriteLibrary.collectSpriteName("BanditLeg1"));
		chest = new Wearable("BanditChest1", SpriteLibrary.collectSpriteName("BanditChest1"));
		mask = new Wearable("BanditMask1", SpriteLibrary.collectSpriteName("BanditMask1"), false);

		hair = new Wearable("Quiff", SpriteLibrary.collectSpriteName("Quiff"), false);

		BufferedImage armUnderImage = SpriteLibrary.collectSpriteName("TemplateArm").getImage();
		BufferedImage armImage = new BufferedImage(armUnderImage.getWidth(), armUnderImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics g = armImage.createGraphics();
		g.drawImage(armUnderImage, 0, 0, null);
		g.drawImage(chest.getArm().getImage(), 0, 0, null);
		g.dispose();
		Sprite armSprite = new Sprite(armImage, null, "null/unimportant", 0, 0);
		arm = new Armature("Template", armSprite, weapon);
	}
	
	public void tick(){
		super.tick();
		setCursorOffset(new Point(Main.levelHandler.getPlayer().getPos()[0] - x, 
				Main.levelHandler.getPlayer().getPos()[1] - y));
	}
	
	public void input(){
		if((int)(Math.random()*20) == 1 && !falling){ 
			vSpeed = -28; 
			falling = true;
		}
		if((int)(Math.random()*40) == 1){ 
			weapon.fire();
		}
		if((int)(Math.random()*10) == 1){ 
			weapon.stopFiring();
		}
		if(randomUpdateTime == 0)
			direction = (int)(Math.random()*3);
		randomUpdateTime++;
		if(randomUpdateTime == 20)
			randomUpdateTime = 0;
		if(direction == 1){
			if(hSpeed < 10)
				hSpeed += 4;
		}
		else if(direction == 2){
			if(hSpeed > -10)
				hSpeed -= 4;
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
