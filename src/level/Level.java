package level;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

import main.Global;
import main.Main;
import object.Object;
import particleEffects.Particle;
import sprite.SpriteLibrary;
import weapon.Bullet;
import entity.BasicEnemy;
import entity.Entity;
import entity.HumanoidDeathModel;
import entity.Player;

public class Level {
	
	private String name;
	
	private ArrayList<Chunk> chunks = new ArrayList<Chunk>();
	private ArrayList<Chunk> loadedChunks = new ArrayList<Chunk>();
	
	private BulletRetainer bullets = new BulletRetainer();
	private ParticleRetainer particles = new ParticleRetainer();
	private ArrayList<Entity> entities = new ArrayList<Entity>();
	private ArrayList<HumanoidDeathModel> deathModels = new ArrayList<HumanoidDeathModel>();
	
	private BufferedImage mistLayer = new BufferedImage(Main.WIDTH, Main.HEIGHT, BufferedImage.TYPE_INT_ARGB);
	
	public ArrayList<Entity> getEntities(){return entities;}
	public Player getPlayer(){return (Player) entities.get(0);}
	public String getName(){return name;}
	public int getDrawnTally(){
		int r = 0;
		for(int i = 0; i < loadedChunks.size(); i++){
			r += loadedChunks.get(i).getDrawnTally();
		}
		return r;
	}
	public ArrayList<Object> getLayer(Rectangle rect, int layer){
		ArrayList<Object> r = new ArrayList<Object>();
		for(int i = 0; i < chunks.size(); i++){
			if(rect.intersects(new Rectangle(chunks.get(i).getPos()[0], chunks.get(i).getPos()[1], 
					chunks.get(i).getWidth(), chunks.get(i).getHeight()))){
				r.addAll(chunks.get(i).getLayers().get(layer));
				r.addAll(chunks.get(i).getDynamic().get(layer));
			}
		}
		return r;
	}
	
	public void addDeathModel(HumanoidDeathModel d){
		deathModels.add(d);
	}
	public void addBullet(Bullet b){
		bullets.add(b);
	}
	public void addParticle(Particle p){
		particles.add(p);
	}
	
	public Level(String namePass){ 
		name = namePass;
		entities.add(new Player(0, 0, "Test", SpriteLibrary.collectSpriteName("Template"), new Point(0, -620)));
		entities.add(new BasicEnemy(0, 0, "Test", SpriteLibrary.collectSpriteName("Template"), new Point(400, -620)));
		entities.add(new BasicEnemy(0, 0, "Test", SpriteLibrary.collectSpriteName("Template"), new Point(-400, -620)));
		
		Graphics g = mistLayer.createGraphics();
		g.setColor(new Color(0, 0, 0, 86));
		g.fillRect(0, 0, mistLayer.getWidth(), mistLayer.getHeight());
		g.dispose();
	}
	
	
	public void add(int shelfIndex, int uid, int x, int y, int layer){
		boolean r = false;
		int i = 0;
		
		if(chunks.size() == 0)
			chunks.add(new Chunk(shelfIndex, uid, x, y, layer));
		else{
			while(!r && chunks.size() > i){
				r = chunks.get(i).add(shelfIndex, uid, x, y, layer);
				i++;
			}
			if(!r)
				chunks.add(new Chunk(shelfIndex, uid, x, y, layer));
		}
		if(Global.paused)
			tick(true);
	}
	
	public void remove(int x, int y, int layer){
		boolean r = false;
		int i = chunks.size() -1;
		
		while(!r && i >= 0){
			r = chunks.get(i).remove(x, y, layer);
			if(r == true && chunks.get(i).getDrawnTally() == 0)
				chunks.remove(i);
			i--;
		}
		if(Global.paused)
			tick(true);
	}
	
	public void tick(boolean all){
		evaluateDrawnObjects();
		bullets.clear();
		particles.tick();

		for(int i = 0; i < loadedChunks.size(); i++){
			loadedChunks.get(i).tick();
		}
		
		if(!all){
			for(Entity e: entities){
				e.tick();			
			}
			for(HumanoidDeathModel d: deathModels){
				d.tick();
			}
			
			for(int i = 0; i < entities.size(); i++){
				if(entities.get(i).isDead()){
					entities.remove(i);
					i--;
				}
			}
		}
		bullets.calculateCollisions();
	}
	
	public synchronized void evaluateDrawnObjects(){
		loadedChunks.clear();
		for(int i = 0; i < chunks.size(); i++){
			if(chunks.get(i).getPos()[0] + (int)Main.activeCamera.getOffset().x >= Main.WIDTH/2 - Global.chunksLoaded/2*512 &&
					chunks.get(i).getPos()[0] + (int)Main.activeCamera.getOffset().x < + Main.WIDTH/2 + Global.chunksLoaded/2*512){
				loadedChunks.add(chunks.get(i));
			}
		}
	}
	
	public void save(){
		try{
			PrintWriter writer = new PrintWriter(new BufferedWriter( new FileWriter("Resources/saves/" + name + ".lvl")));
			
			writer.println(name);
			
			for(int i = 0; i < chunks.size(); i++){
				for(int x = 0; x < chunks.get(i).getLayers().size(); x++){
					for(int y = 0; y < chunks.get(i).getLayers().get(x).size(); y++){
						chunks.get(i).getLayers().get(x).get(y).save(writer);
					}
					writer.println("/layer");
				}
			}
			
			writer.close();
		}
		catch(Exception ex){
			System.out.println("Map " + name + " Saving IO Error: " + ex);
		}
	}
	
	public void unload(){
		chunks.clear();
		if(Global.paused)
			tick(true);
	}
	
	public void load(){
		String line = "";
		try{
			BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/saves/" + name + ".lvl")));
	
			reader.readLine();
			
			do{
				for(int i = 0; i < 4; i++){
					while((line = reader.readLine()) != null && !(line.equalsIgnoreCase("/layer"))){
						String[] split = line.split(",");
						add(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]), Integer.parseInt(split[3]), i);
					}
				}
			}
			while(line != null);
			
			reader.close();         
		}
		catch(Exception ex){
			System.out.println("Map " + name + " Loading IO Error: " + ex + " " + line);
		}
	}
	
	public void draw(Graphics g){
		drawLayer(g, 3);
		drawLayer(g, 2);
		drawLayer(g, 1);
		drawLayer(g, 0);
		
		for(int i = 1; i < entities.size(); i++){
			entities.get(i).draw(g);
		}
		for(int i = 0; i < deathModels.size(); i++){
			deathModels.get(i).Draw(g);
		}
		entities.get(0).draw(g);
		
		bullets.draw(g);
		particles.draw(g);
	}
	
	private synchronized void drawLayer(Graphics g, int index){

		for(int i = 0; i < loadedChunks.size(); i++){
			loadedChunks.get(i).draw(g, index);
		}
		g.drawImage(mistLayer, 0, 0, null);
		
	}
}
