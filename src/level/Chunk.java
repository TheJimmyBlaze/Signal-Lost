package level;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import main.Global;
import main.Main;
import object.DynamicObject;
import object.Object;
import object.ObjectLibrary;

public class Chunk {
	
	private ArrayList<ArrayList<Object>> layers = new ArrayList<ArrayList<Object>>(4);
	private ArrayList<ArrayList<Object>> dynamicLayers = new ArrayList<ArrayList<Object>>(4);
	
	private int width = Global.chunkSize;
	private int height = Global.chunkSize;
	private int posX;
	private int posY;
	
	private ArrayList<BufferedImage> layerImage = new ArrayList<BufferedImage>(4);
	private ArrayList<Integer> layerImageSnapSize = new ArrayList<Integer>(4);
	
	public int[] getPos(){return new int[]{posX, posY};}
	public int getWidth(){return width;}
	public int getHeight(){return height;}
	
	public ArrayList<ArrayList<Object>> getLayers(){return layers;}
	public ArrayList<ArrayList<Object>> getDynamic(){return dynamicLayers;}
	
	public Rectangle getCollision(){return new Rectangle(posX + (int)Main.activeCamera.getOffset().x, posY + (int)Main.activeCamera.getOffset().y, width, height);}
	
	public int getDrawnTally(){
		int r = 0;
		for(int x = 0; x < layers.size(); x ++){
			r += layers.get(x).size();
		}
		return r;
	}
	
	public Chunk(int shelfIndex, int uid, int x, int y, int layer){
		int xDelta = 0;
		int yDelta = 0;
		if(x < 0){
			xDelta = Global.chunkSize;
		}
		if(y < 0){
			yDelta = Global.chunkSize;
		}
		
		posX = (int)(x/Global.chunkSize) * Global.chunkSize - xDelta;
		posY = (int)(y/Global.chunkSize) * Global.chunkSize - yDelta;
		
		for(int i = 0; i < 4; i++){
			layers.add(new ArrayList<Object>());
			dynamicLayers.add(new ArrayList<Object>());
			
			layerImage.add(i, new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB));
			layerImageSnapSize.add(0);
		}
		
		add(shelfIndex, uid, x, y, layer);
	}
	
	public boolean add(int shelfIndex, int uid, int x, int y, int layer){		
		int xDelta = 0;
		int yDelta = 0;
		if(x < 0){
			xDelta = Global.chunkSize;
		}
		if(y < 0){
			yDelta = Global.chunkSize;
		}
		
		if((int)(x/Global.chunkSize) * Global.chunkSize - xDelta >= posX && (int)(x/Global.chunkSize) * Global.chunkSize - xDelta < posX + Global.chunkSize){
			if((int)(y/Global.chunkSize) * Global.chunkSize - yDelta >= posY && (int)(y/Global.chunkSize) * Global.chunkSize - yDelta < posY + Global.chunkSize){
			
				int space;
				if(ObjectLibrary.collectObjectUID(shelfIndex, uid, x, y) instanceof DynamicObject){
					dynamicLayers.get(layer).add(ObjectLibrary.collectObjectUID(shelfIndex, uid, x, y));
					if((space = (int)dynamicLayers.get(layer).get(dynamicLayers.get(layer).size() -1).getPos()[0] - posX + 
							dynamicLayers.get(layer).get(dynamicLayers.get(layer).size() -1).getSprite().getImage().getWidth()) > width)
						width = space;
					if((space = (int)dynamicLayers.get(layer).get(dynamicLayers.get(layer).size() -1).getPos()[1] - posY + 
							dynamicLayers.get(layer).get(dynamicLayers.get(layer).size() -1).getSprite().getImage().getHeight()) > height)
						height = space;
				}
				else{
					getLayer(layer).add(ObjectLibrary.collectObjectUID(shelfIndex, uid, x, y));
					if((space = (int)getLayer(layer).get(getLayer(layer).size() -1).getPos()[0] - posX + 
							getLayer(layer).get(getLayer(layer).size() -1).getSprite().getImage().getWidth()) > width)
						width = space;
					if((space = (int)getLayer(layer).get(getLayer(layer).size() -1).getPos()[1] - posY + 
							getLayer(layer).get(getLayer(layer).size() -1).getSprite().getImage().getHeight()) > height)
						height = space;
				}
							
				return true;
			}
		}
		return false;
	}
	
	public boolean remove(int x, int y, int layer){
		if(x >= posX && x < posX + width && y >= posY && y < posY + height){
			Rectangle r = new Rectangle(x, y, Global.gridInterval, Global.gridInterval);
			
			ArrayList<Object> querry = getLayer(layer);
			for(int i = querry.size() -1; i > -1; i--){
				if(querry.get(i).check(r)){
					querry.remove(i);
					
					return true;
				}
			}
			
			querry = dynamicLayers.get(layer);
			for(int i = querry.size() -1; i > -1; i--){
				if(querry.get(i).check(r)){
					querry.remove(i);
					
					return true;
				}
			}
		}
		return false;
	}
	
	public void tick(){
		for(int i = 0; i < layers.size(); i++){
			if(layerImageSnapSize.get(i) != layers.get(i).size()){
				rebuildLayerImage(i);
				layerImageSnapSize.set(i, layers.get(i).size());
			}
		}
		
		for(int x = 0; x < dynamicLayers.size(); x++){
			for(int y = 0; y < dynamicLayers.get(x).size(); y++){
				 dynamicLayers.get(x).get(y).tick();
			}
		}
	}
	
	public void rebuildLayerImage(int layer){
		BufferedImage flip = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics g = flip.createGraphics();
		for(int i = 0; i < layers.get(layer).size(); i++){
			layers.get(layer).get(i).draw(g, posX, posY);			
		}
		g.dispose();
		layerImage.set(layer, flip);
	}
	
	private ArrayList<Object> getLayer(int layer){
		return layers.get(layer);
	}
	
	public void draw(Graphics g, int index){
		g.drawImage(layerImage.get(index), posX + (int)Main.activeCamera.getOffset().x, posY + (int)Main.activeCamera.getOffset().y, null);
		
		for(int i = 0; i < dynamicLayers.get(index).size(); i++){
			dynamicLayers.get(index).get(i).draw(g);
		}
		
		if(Global.showCollisionMaps){
			g.setColor(Color.GREEN);
			g.drawRect(posX + (int)Main.activeCamera.getOffset().x, posY + (int)Main.activeCamera.getOffset().y, width, height);
		}
	}
}
