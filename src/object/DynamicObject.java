package object;

import java.awt.Point;

import sprite.AnimatedSprite;
import sprite.Sprite;

public class DynamicObject extends Object{

	public DynamicObject(){}
	
	public DynamicObject(int shelfIndexPass, int uidPass, String namePass, Sprite imagePass, Point pos) {
		super(shelfIndexPass, uidPass, namePass, imagePass, pos);
	}
	
	public Object clone(Point pos) {
		return new DynamicObject(shelfIndex, uid, name, image.clone(), pos);
	}
	
	public void animate(){
		if(image instanceof AnimatedSprite){
			((AnimatedSprite) image).tick();
		}
	}
	
	public void tick(){
		animate();
	}
}
