package sprite;

public class SpriteFrame {
	
	private Sprite image;
	private int lifeSpan;
	
	public Sprite getSprite(){return image;}
	public int getLifeSpan(){return lifeSpan;}
	
	public SpriteFrame(Sprite imagePass,  int lifeSpanPass){
		image = imagePass;
		lifeSpan = lifeSpanPass;
	}
	
	public SpriteFrame clone(){
		return new SpriteFrame(image.clone(), lifeSpan);
	}
}