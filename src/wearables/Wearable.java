package wearables;

import java.awt.Point;

import sprite.Sprite;
import sprite.SpriteLibrary;

public class Wearable extends AnimatedWearable{
	
	private Sprite arm;
	public Sprite getArm(){return arm;}

	public Wearable(String namePass, Sprite imagePass, boolean includesArm) {
		instantiateCommon(namePass, imagePass);
		(imageF = image.flip()).setOffset(new Point((int)imageWF.getOffset().getX(), (int)image.getOffset().getY()));
		(imageJ = image.clone()).setOffset(new Point((int)image.getOffset().getX() - 4, (int)image.getOffset().getY()));
		(imageJF = imageJ.flip()).setOffset(new Point((int)imageWF.getOffset().getX(), (int)image.getOffset().getY()));
		if(includesArm)
			arm = SpriteLibrary.collectSpriteName(image.getName() + "Arm").clone();
	}
	
	public Wearable(String namePass, Sprite imagePass) {
		instantiateCommon(namePass, imagePass);
		imageF = image.flip();
		(imageJ = image.clone()).setOffset(imageW.getOffset());
		(imageJF = imageJ.flip()).setOffset(image.getOffset());
		arm = SpriteLibrary.collectSpriteName(image.getName() + "Arm").clone();
	}
	
	private void instantiateCommon(String namePass, Sprite imagePass){
		name = namePass;
		image = imagePass.clone();
		current = image;
		imageW = SpriteLibrary.collectSpriteName(image.getName() + "A").clone();
		imageWF = SpriteLibrary.collectSpriteName(image.getName() + "AF").clone();
		imageBW = SpriteLibrary.collectSpriteName(image.getName() + "RA").clone();
		imageBWF = SpriteLibrary.collectSpriteName(image.getName() + "RAF").clone();
	}
}
