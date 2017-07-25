package sprite;

import java.awt.Point;
import java.awt.image.BufferedImage;

public class AnimatedSprite extends Sprite{
	
	private SpriteFrame[] frames;
	private int currentFrameIndex = 0;
	private int currentFrameLifeTime = 0;
	public Point getOffset(){return frames[currentFrameIndex].getSprite().getOffset();}
	
	public AnimatedSprite(SpriteFrame[] framesPass, String namePass, int shelfIndexPass, int uidPass) {
		super(null, null, namePass, shelfIndexPass, uidPass);
		frames = framesPass;
	}
	
	public Sprite getFrame(int i){return frames[i].getSprite();}
	public void setFrameIndex(int i){currentFrameIndex = i;}
	public int getFrameIndex(){return currentFrameIndex;}
	
	public  BufferedImage getImage(){
		return frames[currentFrameIndex].getSprite().getImage();
	}
	public  BufferedImage getIcon(){
		return frames[currentFrameIndex].getSprite().getIcon();
	}
	
	public void tick(){
		
		currentFrameLifeTime++;
		if(currentFrameLifeTime == frames[currentFrameIndex].getLifeSpan()){
			currentFrameIndex++;
			if(currentFrameIndex == frames.length)
				currentFrameIndex = 0;
			currentFrameLifeTime = 0;
		}
	}
	
	public Sprite clone(){
		SpriteFrame[] clonedFrames = new SpriteFrame[frames.length];
		for(int i = 0; i < frames.length; i++)
			clonedFrames[i] = frames[i].clone();
		
		return new AnimatedSprite(clonedFrames, name, shelfIndex, uid);
	}
}
