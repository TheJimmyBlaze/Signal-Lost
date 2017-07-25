package level;

import java.awt.Graphics;
import java.util.ArrayList;

import weapon.Bullet;

public class BulletRetainer {

	private ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	
	public BulletRetainer(){}
	public void clear(){bullets.clear();}
	
	public void add(Bullet b){
		bullets.add(b);
	}
	
	public void calculateCollisions(){
		for(Bullet b: bullets){
			b.calculateCollision();
			b.calculateHitBoxCollision();
		}
	}
	
	public void draw(Graphics g){
		for(int i = 0; i < bullets.size(); i++){
			bullets.get(i).draw(g);
		}
	}
}
