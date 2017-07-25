package weapon;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import entity.Entity;
import object.Object;
import particleEffects.ColorParticle;
import main.Main;

public class Bullet {

	protected Point destination;
	protected Point origin;
	protected Point closest;
	protected Line2D pointLine;
	
	protected Rectangle collision;
	
	protected double recoil;
	protected double damage;
	protected double pierce;
	protected int shake;
	protected int blood;
	
	protected Color trailColor;
	protected int trailThickness;
	
	public double getRecoil(){return recoil;}
	public double getDamage(){return damage;}
	public double getPierce(){return pierce;}
	public int getShake(){return shake;}
	
	public Bullet(double recoilPass, double damagePass, double piercePass, int shakePass, int bloodPass){
		recoil = recoilPass; damage = damagePass; pierce = piercePass; shake = shakePass; blood = bloodPass;
	}
	
	public Bullet(Point originPass, Point destinationPass,
				double recoilPass, double damagePass, double piercePass, int shakePass, int bloodPass){
		recoil = recoilPass;
		damage = damagePass;
		pierce = piercePass;
		shake = shakePass;
		blood = bloodPass;
		
		origin = originPass; 
		destination = destinationPass;
		closest = destination;
		pointLine = new Line2D.Double(origin.getX(), origin.getY(), destination.getX(), destination.getY());
		
		trailColor = new Color(203, 204, 96, 40);
		trailThickness = 1;

		int left = (int)origin.getX();
		int top = (int)origin.getY();
		int width = (int)destination.getX() - (int)origin.getX();
		int height = (int)destination.getY() - (int)origin.getY();
		
		if(destination.getX() < left){
			left = (int)destination.getX();
			width = (int)origin.getX() - (int)destination.getX();
		}
		if(destination.getY() < top){
			top = (int)destination.getY();
			height = (int)origin.getY() - (int)destination.getY();
		}
		
		collision = new Rectangle(left, top, width, height);
	}
	
	public Bullet clone(Point originPass, Point destinationPass){
		return new Bullet(originPass, destinationPass, recoil, damage, pierce, shake, blood);
	}
	
	public void calculateHitBoxCollision(){
		for(Entity e: Main.levelHandler.getEntities()){
			if(e.checkHit(new Rectangle(destination.x, destination.y, 1, 1))){
				e.damage(damage);
				Color c = new Color(100, 30, 30);
				int length = (int)Math.sqrt(Math.pow(origin.x - destination.x, 2) + Math.pow(origin.y - destination.y, 2));
				if(length == 0)length = 1;
				int xLength = length/(blood+4); int yLength = length/48;
				if(xLength == 0)xLength = 1; if(yLength == 0)yLength = 1;
				Point speedDestination = new Point((destination.x + (destination.x - origin.x) /xLength) - destination.x, 
												(destination.y + (destination.y - origin.y) /yLength) - destination.y);
				int particleCount = blood + (int)(Math.random()*8);
				int bounce = 0;
				if(Math.random()*4 > 3)
					bounce = (int)(Math.random()*10)+5;
				for(int i = 0; i < particleCount; i++){
					Main.levelHandler.addParticle(new ColorParticle(0, 0, "splat"
							, destination, speedDestination.y - (int)(Math.random()*4) + 2, speedDestination.x + (int)(Math.random()*8) - 4, 20, bounce, true,
							new Color(c.getRed() + (int)(Math.random()*120)-60,
									c.getGreen() + (int)(Math.random()*40)-20,
									c.getBlue() + (int)(Math.random()*40)-20), 1 - Math.random()*0.15, 1 - Math.random()*0.15));
				}
			}
			Point destinationCorrected = new Point((int)(destination.getX() + Main.activeCamera.getOffset().getX()), (int)(destination.getY() + Main.activeCamera.getOffset().getY()));
			if(destinationCorrected.getX() > 0 && destinationCorrected.getX() < Main.WIDTH && destinationCorrected.getY() > 0 && destinationCorrected.getY() < Main.HEIGHT){
				int extractedColor = Main.lastImage.getRGB((int)destinationCorrected.getX(), (int)destinationCorrected.getY());
				for(int i = 0; i < 1 + Math.random() * 2; i++){
					ColorParticle terrain = new ColorParticle(0, 0, "terrain"
							, destination, (int)(Math.random() * -16), (int)(Math.random() * 16)-8, 32, 24, true, new Color(extractedColor), 1, 1);
					Main.levelHandler.addParticle(terrain);
				}
			}
		}
	}
	
	public void calculateCollision(){

		ArrayList<Object> objects = Main.levelHandler.getLayer(collision, 0);
		
		for(Object o: objects){
			
			Rectangle r = o.getCollision();
			
			Line2D top = new Line2D.Double(r.getX() -1, r.getY(), r.getX() + r.getWidth() +2, r.getY());
			Line2D bottom = new Line2D.Double(r.getX() -1, r.getY() + r.getHeight(), r.getX() + r.getWidth() +2, r.getY() + r.getHeight());
			Line2D left = new Line2D.Double(r.getX(), r.getY(), r.getX(), r.getY() + r.getHeight());
			Line2D right = new Line2D.Double(r.getX() + r.getWidth(), r.getY(), r.getX() + r.getWidth(), r.getY() + r.getHeight());
			
			if(r.intersectsLine(new Line2D.Double(origin.getX(), origin.getY(), destination.getX(), destination.getY()))){
				
				Point intersectionPoint = null;
				if((intersectionPoint = getIntersectionPoint(top, pointLine)) != null && intersectionPoint.distance(origin) < closest.distance(origin))
					closest = intersectionPoint;
	
				if((intersectionPoint = getIntersectionPoint(bottom, pointLine)) != null && intersectionPoint.distance(origin) < closest.distance(origin))
					closest = intersectionPoint;
	
				if((intersectionPoint = getIntersectionPoint(left, pointLine)) != null && intersectionPoint.distance(origin) < closest.distance(origin))
					closest = intersectionPoint;
	
				if((intersectionPoint = getIntersectionPoint(right, pointLine)) != null && intersectionPoint.distance(origin) < closest.distance(origin))
					closest = intersectionPoint;
			}
		}

		if(destination != closest){
			closest.x *= 1.01;
			closest.y *= 1.01;
		}
		destination = closest;
	}
	
	private Point getIntersectionPoint(Line2D line1, Line2D line2) {
	    if (! line1.intersectsLine(line2) ) return null;
	      double px = line1.getX1(),
	            py = line1.getY1(),
	            rx = line1.getX2()-px,
	            ry = line1.getY2()-py;
	      double qx = line2.getX1(),
	            qy = line2.getY1(),
	            sx = line2.getX2()-qx,
	            sy = line2.getY2()-qy;

	      double det = sx*ry - sy*rx;
	      if (det == 0) {
	        return null;
	      } else {
	        double z = (sx*(qy-py)+sy*(px-qx))/det;
	        if (z==0 ||  z==1) return null;
	        return new Point(
	          (int)(px+z*rx), (int)(py+z*ry));
	      }
	 }

	
	public void draw(Graphics g){
		g.setColor(trailColor);
		((Graphics2D) g).setStroke(new BasicStroke(trailThickness));
		g.drawLine(origin.x + Main.playerCamera.getOffset().x, origin.y + Main.playerCamera.getOffset().y, destination.x + Main.playerCamera.getOffset().x, destination.y + Main.playerCamera.getOffset().y);
	}
}
