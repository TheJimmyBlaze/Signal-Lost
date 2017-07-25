package entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import main.Global;
import main.Main;
import object.Object;

public class HumanoidDeathModel {
	
	private final int OFFSET = 64;
	private final int BOUNCE_SPEED = -28;
	
	private BufferedImage[] images0 = new BufferedImage[3];
	private BufferedImage[] images1 = new BufferedImage[3];
	private BufferedImage image;
	private int currentImage = 0;
	private Point[] pivots = new Point[2];
	private int currentPivot, imageHeight, imageDimension;
	private int bounces = 1;
	
	private double vSpeed, hSpeed, currentAngle, rotationSpeed;
	
	public HumanoidDeathModel(BufferedImage[] imagesPass, Point p, boolean rightFacing){
		
		pivots[0] = new Point(p.x + imagesPass[0].getWidth()/2 -OFFSET, p.y + imagesPass[0].getHeight() -OFFSET);
		pivots[1] = new Point(p.x + imagesPass[0].getWidth()/2 -OFFSET, p.y);
		imageDimension = (int) Math.sqrt(Math.pow(imagesPass[0].getWidth(), 2) + Math.pow(imagesPass[0].getHeight(), 2));
		imageHeight = imagesPass[0].getHeight() -OFFSET;
		
		images0[0] = createRotationalImage(imagesPass[0], 0);
		images1[0] = createRotationalImage(imagesPass[0], 1);
		image = images0[0];
		
		currentAngle = -1.5;
		if(rightFacing){
			rotationSpeed = 0.02;
			hSpeed = 20;
		}
		else{
			rotationSpeed = -0.02;
			hSpeed = -20;
		}
		vSpeed = BOUNCE_SPEED +8;
	}
	
	private BufferedImage createRotationalImage(BufferedImage pass, int pivot){
		
		BufferedImage toReturn = new BufferedImage(imageDimension*2, imageDimension*2, BufferedImage.TYPE_INT_ARGB);
		Graphics g = toReturn.createGraphics();
		g.drawImage(pass, imageDimension - pass.getWidth()/2, imageDimension *pivot +OFFSET - OFFSET*pivot, null);
		g.dispose();
		return toReturn;
	}
	
	public void tick(){

		Point[] pivotsPrevious = pivots;
		
		if(vSpeed != -99 && vSpeed < Global.gravityConstant)
			vSpeed += Global.gravitySpeed;
		if(vSpeed == -99)
			vSpeed = 0;
		
		if(hSpeed > 1) hSpeed -= 0.5;
		else if(hSpeed < -1) hSpeed += 0.5;
		else hSpeed = 0;
		
		currentAngle += rotationSpeed;
		int floatingPivot = 0;if(currentPivot == 0)floatingPivot = 1;
		pivots[floatingPivot] = new Point((int)(pivots[currentPivot].x + imageHeight * Math.cos(currentAngle += rotationSpeed)),
				(int)(pivots[currentPivot].y + imageHeight * Math.sin(currentAngle += rotationSpeed)));
		
		pivots[currentPivot].y += vSpeed;
		Rectangle pointCollision = new Rectangle(pivots[currentPivot].x, pivots[currentPivot].y, 1, 1);
		ArrayList<Object> collisions = Main.levelHandler.getLayer(pointCollision, 0);
		for(Object o: collisions){
			if(o.check(pointCollision)){
				if(o.getCollision().y < pivotsPrevious[currentPivot].y){
					pivots[currentPivot].y = o.getCollision().y -1;
					bounces++;
					vSpeed = BOUNCE_SPEED / bounces;
					if(vSpeed > -8)
						vSpeed = -99;
					hSpeed *= 0.5;
				}
			}
		}
		
		pivots[currentPivot].x += hSpeed;
		pointCollision = new Rectangle(pivots[currentPivot].x, pivots[currentPivot].y, 1, 1);
		for(Object o: collisions){
			if(o.check(pointCollision)){
				if(o.getPos()[0] > pivotsPrevious[currentPivot].x){
					pivots[currentPivot].x = o.getCollision().x -1;
					hSpeed = 0;
				}
				if(o.getPos()[0] < pivotsPrevious[currentPivot].x){
					pivots[currentPivot].x = (int)(o.getCollision().x + o.getCollision().getWidth() +1);
					hSpeed = 0;
				}
			}
		}
		
		pointCollision = new Rectangle(pivots[floatingPivot].x, pivots[floatingPivot].y, 1, 1);
		collisions = Main.levelHandler.getLayer(pointCollision, 0);
		for(Object o: collisions){
			if(o.check(pointCollision)){
				if(vSpeed == -99)
					rotationSpeed = 0;
				else{
					currentPivot = 0; if(floatingPivot == 1) currentPivot = 1;
					floatingPivot = 1; if(currentPivot == 1) floatingPivot = 0;
					rotationSpeed = -rotationSpeed;
					currentAngle += 3;
				}
			}
		}
		
		AffineTransform tx = AffineTransform.getRotateInstance(currentAngle + 1.5 - 3*currentPivot, imageDimension, imageDimension);
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
		
		BufferedImage rotatedImage = new BufferedImage(imageDimension *2, imageDimension *2, BufferedImage.TYPE_INT_ARGB);
		Graphics g = rotatedImage.createGraphics();
		BufferedImage current = images0[currentImage];
		if(currentPivot == 1)
			current = images1[currentImage];
		g.drawImage(op.filter(current, null), 0, 0, null);
		g.dispose();
		image = rotatedImage;
	}
	
	public void Draw(Graphics g){
		g.drawImage(image, pivots[currentPivot].x - imageDimension + Main.activeCamera.getOffset().x
						, pivots[currentPivot].y - imageDimension + Main.activeCamera.getOffset().y, null);
		
		if(Global.showCollisionMaps){
			for(Point p: pivots){
				g.setColor(Color.MAGENTA);
				g.drawRect(p.x -4 + Main.activeCamera.getOffset().x, p.y -4 + Main.activeCamera.getOffset().y, 8, 8);
			}
		}		
	}
}
