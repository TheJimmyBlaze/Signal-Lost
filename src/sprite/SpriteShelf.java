package sprite;

import java.util.ArrayList;

public class SpriteShelf {
	
	private String name;
	private int number;
	
	private ArrayList<Sprite> spriteList = new ArrayList<Sprite>();
	private ArrayList<Integer> UIDlist = new ArrayList<Integer>();
	
	public int getSpriteCount(){return spriteList.size();}
	public String getName(){return name;}
	public int getNumber(){return number;}
	
	public Sprite collectSprite(int SID){return spriteList.get(SID);}
	public int collectUID(int SID){return UIDlist.get(SID);}
	
	public SpriteShelf(String namePass, int numberPass){
		name = namePass;
		number = numberPass;
	}
	
	public void add(Sprite sprite, int UID){
		spriteList.add(sprite);
		UIDlist.add(UID);
	}
}
