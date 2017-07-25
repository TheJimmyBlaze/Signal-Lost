package entity;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;

import main.Main;
import sprite.Sprite;
import sprite.SpriteLibrary;
import weapon.Weapon;

public class Armature{
	
	private Sprite rightArm;
	private Sprite leftArm;
	
	private Weapon weapon;
	
	private Sprite imageRight;
	private Sprite imageLeft;
	
	private int armatureLength;
	private Point offset = new Point(0,0);
	private Point rightRotationCentre;
	private Point leftRotationCentre;
	
	public int getArmatureLength(){return armatureLength;}
	public Point getOffset(){return offset;}
	
	public Point getRotationCentre(boolean right){
		if(right)
			return rightRotationCentre;
		return leftRotationCentre;
	}
	
	public void setOffset(Point o){
		offset = o;
		if(imageRight != null)
			imageRight.setOffset(o);
		if(imageLeft != null)
			imageLeft.setOffset(o);
	}

	public Armature(String name, Sprite arm, Weapon w){
		rightArm = arm;
		leftArm = rightArm.flip();
		
		weapon = w;
		
		BufferedImage concat = new BufferedImage((int)(weapon.getRight().getWidth() + weapon.getOffset().getX()), (int)(weapon.getRight().getHeight() - weapon.getOffset().getY()), BufferedImage.TYPE_INT_ARGB);
		Graphics c = concat.createGraphics();
		c.drawImage(weapon.getRight(), weapon.getOffset().x, 0, null);
		c.drawImage(rightArm.getImage(), 0, -weapon.getOffset().y, null);
		c.dispose();
		imageRight = new Sprite(concat, null, name + "Armature", -99, -99);
		armatureLength = imageRight.getImage().getWidth();

		imageRight.makeRotateImage(rightArm.getImage().getHeight()/2, rightArm.getImage().getHeight()/2 - weapon.getOffset().y, true);
		rightRotationCentre = new Point(24, 72);
		
		concat = new BufferedImage((int)(weapon.getRight().getWidth() + weapon.getOffset().getX()), (int)(weapon.getRight().getHeight() - weapon.getOffset().getY()), BufferedImage.TYPE_INT_ARGB);
		Graphics cl = concat.createGraphics();
		cl.drawImage(weapon.getLeft(), 0, 0, null);
		cl.drawImage(leftArm.getImage(), concat.getWidth() - leftArm.getImage().getWidth(), -weapon.getOffset().y, null);
		cl.dispose();
		imageLeft = new Sprite(concat, null, name + "Armature", -99, -99);
		
		imageLeft.makeRotateImage(concat.getWidth() - rightArm.getImage().getHeight()/2, rightArm.getImage().getHeight()/2 - weapon.getOffset().y, false);
		leftRotationCentre = new Point(56, 72);
	}
	
	public Point getOriginPoint(boolean facingRight, double rotationalCorrection, int length, Point cursorOffset){
		
		double rotation = getRotation(facingRight, rotationalCorrection, length, cursorOffset);
		int x;int y;
		if(facingRight){
	        x = (int) (rightRotationCentre.x + (armatureLength + length) * Math.cos(rotation));
	        y = (int) (rightRotationCentre.y + (armatureLength + length) * Math.sin(rotation));
		}
		else{
	        x = (int) (leftRotationCentre.x + (armatureLength + length) * Math.cos(rotation));
	        y = (int) (leftRotationCentre.y + (armatureLength + length) * Math.sin(rotation));
		}
		x*=0.99;
        
        return new Point(x, y);
	}
	
	public double getRotation(boolean facingRight, double rotationalCorrection, int length, Point cursorOffset){
		int xArmOffset = 8;
		Point rotationPoint = new Point((int)(cursorOffset.getX() - xArmOffset),
				(int)(cursorOffset.getY()));
		
		double correction = 0;
		if(length == 0)
			correction = weapon.getMuzzleCorrection();
		double rotation;
		if(facingRight)
			rotation = (-Math.atan2(rotationPoint.getX(), rotationPoint.getY()) + Math.PI/2 - correction * (1 - rotationalCorrection))
				- weapon.getVerticleRecoil()/100* 0.98;
		else
			rotation = (-Math.atan2(rotationPoint.getX(), rotationPoint.getY()) + Math.PI/2 + correction * (1 - rotationalCorrection))
				+ weapon.getVerticleRecoil()/100* 0.995;
		
		return rotation;
	}
	
	public void rotate(boolean facingRight, double rotation){
		if(facingRight){
			imageRight.rotateImage(rotation);
			weapon.getFlashRight().rotateImage(rotation);
		}
		else{
			imageLeft.rotateImage(rotation);
			weapon.getFlashLeft().rotateImage(rotation);
		}
	}
	
	public void draw(Graphics g, boolean face, int x, int y){
		if(face)
			g.drawImage(imageRight.getImage(), -imageRight.getImage().getWidth()/2 + x + 24 + Main.activeCamera.getOffset().x, y + 72 - imageRight.getImage().getHeight()/2 + Main.activeCamera.getOffset().y, null);
		else
			g.drawImage(imageLeft.getImage(), imageRight.getImage().getWidth()/2 + x + 56 - imageLeft.getImage().getWidth() + Main.activeCamera.getOffset().x, y + 72 - imageLeft.getImage().getHeight()/2 + Main.activeCamera.getOffset().y, null);
		
		weapon.draw(g, face);
	}
}
