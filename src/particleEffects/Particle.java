package particleEffects;

import java.awt.Point;
import java.awt.Rectangle;

import entity.Entity;
import object.CollisionMap;
import sprite.Sprite;

public class Particle extends Entity{
	
	protected int fade;
	private int currentFade;
	private double rotationSpeed;
	private double rotation;
	private int bounces;
	private int currentBounces = 0;
	private double bounceSpeed;
	private double vSpeedDecay;
	
	public int getFade(){return currentFade;}
	
	public Particle(){}

	public Particle(int shelfIndexPass, int uidPass, String namePass,
			Sprite imagePass, Point pos, int vSpeedPass, int hSpeedPass, int fadePass, int bouncinessPass, boolean fallsPass, double gravityMultiplyer, double vSpeedDecayPass) {
		super(shelfIndexPass, uidPass, namePass, imagePass, pos, fallsPass, gravityMultiplyer);
		vSpeed = vSpeedPass;
		hSpeed = hSpeedPass;
		bounceSpeed = -bouncinessPass - Math.random()*bouncinessPass;
		fade = fadePass;
		currentFade = fade;
		vSpeedDecay = vSpeedDecayPass;
		
		rotationSpeed = Math.random();
		bounces = (int)(Math.random()*2)+1;
		
		if(imagePass != null){
			image.makeRotateImage(image.getImage().getWidth()/2, image.getImage().getHeight()/2, true);
			collision = new CollisionMap(new Rectangle(x + image.getImage().getWidth()/4, y + image.getImage().getHeight()/4, image.getImage().getWidth()/2, image.getImage().getHeight()/2), 0, 0);
		}
	}
	
	public void tick(){
		super.tick();
		
		if(!falling && currentBounces != bounces){
			y-=2;
			vSpeed = bounceSpeed/2;
			bounceSpeed = vSpeed;
			rotationSpeed = Math.random()*2;
			hSpeed /= 2;
			falling = true;
			currentBounces++;
		}
		else if(!falling){
			hSpeed = 0;
			rotationSpeed = 0;
		}
		else hSpeed *= vSpeedDecay;
		
		image.rotateImage(rotation += rotationSpeed);
		if(currentFade != 0)
			currentFade--;
	}
}
