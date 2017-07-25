package main;

import java.awt.Point;

public class Camera {

	private Point offset = new Point(0,0);
	private int shakiness = 0;
	
	public Point getOffset(){return offset;}
	public void Shake(int shakinessPass){shakiness = shakinessPass;}
	
	public Camera(Point offsetPass){
		moveOffset(offsetPass);
	}
	
	public void tick(){
		if(shakiness != 0){
			stepOffset(new Point((int)(Math.random() *shakiness - shakiness/2), (int)Math.random() * shakiness - shakiness/2));
			shakiness --;
		}
	}

	public void moveOffset(Point shift){
		offset.x = shift.x;
		offset.y = shift.y;
	}
	public void stepOffset(Point shift){
		offset.x += shift.x;
		offset.y += shift.y;
	}
}
