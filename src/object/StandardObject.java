package object;

import java.awt.Point;

import sprite.Sprite;

public class StandardObject extends Object{

	public StandardObject(int shelfIndexPass, int uidPass, String namePass, Sprite imagePass, Point pos) {
		super(shelfIndexPass, uidPass, namePass, imagePass, pos);
	}
	
	public Object clone(Point pos){
		return new StandardObject(shelfIndex, uid, name, image.clone(), pos);
	}

	public void tick() {
	}
}
