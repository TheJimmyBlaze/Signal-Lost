package object;

import java.awt.Point;
import java.util.ArrayList;

import sprite.AnimatedSprite;
import sprite.Sprite;
import sprite.SpriteLibrary;
import sprite.SpriteShelf;

public class ObjectLibrary {
	
	private static ArrayList<Object> objectList = new ArrayList<Object>();
	private static ArrayList<ObjectShelf> objectShelves = new ArrayList<ObjectShelf>();
	
	public static int getObjectCount(){return objectList.size();}
	
	public static ObjectShelf collectShelf(String name){
		for(int i = 0; i < objectShelves.size(); i++){
			if(objectShelves.get(i).getName().equals(name)){
				return objectShelves.get(i);
			}
		}
		return null;
	}
	
	public static Object collectObjectUID(int shelfIndex, int uid, int x, int y){
		for(int i = 0; i < objectList.size(); i++){
			if(objectList.get(i).getShelfIndex() == shelfIndex && objectList.get(i).getUID() == uid){
				return objectList.get(i).clone(new Point(x, y));
			}
		}
		
		System.out.println("Unspecified Object Name Error: " + uid);
		return null;
	}
	
	public static Object collectObjectName(String name, int x, int y){
		for(int i = 0; i < objectList.size(); i++){
			if(objectList.get(i).getName().equals(name)){
				return objectList.get(i).clone(new Point(x, y));
			}
		}
		
		System.out.println("Unspecified Object Name Error: " + name);
		return null;
	}
	
	public ObjectLibrary(){
		objectShelves.add(new ObjectShelf("ruins", 0));
		objectShelves.add(new ObjectShelf("wood", 1));
		objectShelves.add(new ObjectShelf("decoration", 2));
		objectShelves.add(new ObjectShelf("store", 3));
		objectShelves.add(new ObjectShelf("plant", 4));
		objectShelves.add(new ObjectShelf("ground", 5));
		objectShelves.add(new ObjectShelf("building", 6));
		objectShelves.add(new ObjectShelf("moss", 7));
		buildLibrary();
	}
	
	private void addObject(Object o, int shelfIndex){
		objectList.add(o);
		objectShelves.get(shelfIndex).add(o);
	}
	
	private void addFromShelf(String name, int shelfIndex){
		SpriteShelf shelf = SpriteLibrary.collectShelf(name);
		
		for(int i = 0; i < shelf.getSpriteCount(); i++){
			Sprite sprite = shelf.collectSprite(i);
			if(sprite instanceof AnimatedSprite)
				addObject(new DynamicObject(shelfIndex, objectShelves.get(shelfIndex).getSpriteCount(), sprite.getName(), SpriteLibrary.collectSpriteUID(SpriteLibrary.collectShelf(name).getNumber(), SpriteLibrary.collectShelf(name).collectUID(i)), new Point(0,0)), shelfIndex);
			else
				addObject(new StandardObject(shelfIndex, objectShelves.get(shelfIndex).getSpriteCount(), sprite.getName(), SpriteLibrary.collectSpriteUID(SpriteLibrary.collectShelf(name).getNumber(), SpriteLibrary.collectShelf(name).collectUID(i)), new Point(0,0)), shelfIndex);
		}
	}
	
	private void buildLibrary(){
		addFromShelf("ruins", 0);
		addFromShelf("wood", 1);
		addFromShelf("decoration", 2);
		addFromShelf("store", 3);
		addFromShelf("plant", 4);
		addFromShelf("ground", 5);
		addFromShelf("building", 6);
		addFromShelf("moss", 7);
	}
}
