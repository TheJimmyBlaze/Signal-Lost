package level;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

import object.Object;
import particleEffects.Particle;
import weapon.Bullet;
import entity.Entity;
import entity.HumanoidDeathModel;
import entity.Player;

public class LevelHandler {

	private ArrayList<Level> levels = new ArrayList<Level>();
	private Level currentLevel;
	
	private ArrayList<String> index = new ArrayList<String>();

	public Player getPlayer(){return currentLevel.getPlayer();}
	public ArrayList<Entity> getEntities(){return currentLevel.getEntities();} 
	public int getDrawnTally(){return currentLevel.getDrawnTally();}
	public ArrayList<Object> getLayer(Rectangle rect, int layer){return currentLevel.getLayer(rect, layer);}
	
	private void buildLevelIndex(){
		index.add("Test");
	}
	
	public LevelHandler(){
		buildLevelIndex();
		
		for(int i = 0; i < index.size(); i++){
			levels.add(new Level(index.get(i)));
			levels.get(i).load();
		}		
	}
	
	public void switchLevel(String name){
		for(int i = 0; i < levels.size(); i++){
			if(levels.get(i).getName().equals(name)){
				currentLevel = levels.get(i);
			}
		}
	}
	
	public void tick(){currentLevel.tick(false);}
	
	public String saveCurrent(){
		currentLevel.save();
		return currentLevel.getName();
	}
	public void unloadCurrent(){
		currentLevel.unload();
	}
	
	public String loadCurrent(){
		currentLevel.load();
		return currentLevel.getName();
	}
	
	public void add(int shelfIndex, int uid, int x, int y, int layer){
		currentLevel.add(shelfIndex, uid, x, y, layer);
	}
	
	public void addDeathModel(HumanoidDeathModel d){
		currentLevel.addDeathModel(d);
	}
	public void addBullet(Bullet b){
		currentLevel.addBullet(b);
	}
	public void addParticle(Particle p){
		currentLevel.addParticle(p);
	}

	public void remove(int x, int y, int layer){
		currentLevel.remove(x, y, layer);
	}
	
	public void draw(Graphics g){
		currentLevel.draw(g);
	}
}
