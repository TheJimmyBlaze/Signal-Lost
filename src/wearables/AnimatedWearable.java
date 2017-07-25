package wearables;

import java.awt.Graphics;
import java.awt.Point;

import main.Main;
import sprite.AnimatedSprite;
import sprite.Sprite;
import sprite.SpriteLibrary;

public class AnimatedWearable {
	protected Sprite current;
	protected String name;
	
	protected Sprite image;
	protected Sprite imageF;
	protected Sprite imageJ;
	protected Sprite imageJF;
	protected Sprite imageW;
	protected Sprite imageWF;
	protected Sprite imageBW;
	protected Sprite imageBWF;
	
	public Sprite getImage(){return image;}
	public Sprite getImageF(){return imageF;}
	public Sprite getImageJ(){return imageJ;}
	public Sprite getImageJF(){return imageJF;}
	public Sprite getImageW(){return imageW;}
	public Sprite getImageWF(){return imageWF;}
	public Sprite getImageBW(){return imageBW;}
	public Sprite getImageBWF(){return imageBWF;}
	
	public AnimatedWearable(){}
	
	public void setCurrent(int i){
		switch(i){
			case 1: current = image;
				break;
			case 2: current = imageF;
				break;
			case 3: current = imageJ;
				break;
			case 4: current = imageJF;
				break;
			case 5: current = imageW;
				break;
			case 6: current = imageWF;
				break;
			case 7: current = imageBW;
				break;
			case 8: current = imageBWF;
				break;
			default: System.out.println("Non specified Armour sprite index Error: " + i);
				break;
		}
	}
	
	public void updateFrame(int i){
		if(current instanceof AnimatedSprite){
			((AnimatedSprite) current).setFrameIndex(i);
		}
	}
	
	public AnimatedWearable(String namePass, Sprite imagePass){
		name = namePass;
		
		image = imagePass.clone();
		(imageF = image.flip()).setOffset(new Point(12,0));
		imageW = SpriteLibrary.collectSpriteName(image.getName() + "A").clone();
		imageWF = SpriteLibrary.collectSpriteName(image.getName() + "AF").clone();
		imageBW = SpriteLibrary.collectSpriteName(image.getName() + "RA").clone();
		imageBWF = SpriteLibrary.collectSpriteName(image.getName() + "RAF").clone();
		imageJ = SpriteLibrary.collectSpriteName(image.getName() + "J").clone();
		(imageJF = imageJ.flip()).setOffset(new Point(12,0));
		current = image;
	}
	
	public void draw(Graphics g, int x, int y){
		g.drawImage(current.getImage(), x + (int)Main.activeCamera.getOffset().getX(), y + (int)Main.activeCamera.getOffset().getY(), null);
	}
}
