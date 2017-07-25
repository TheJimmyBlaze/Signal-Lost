package constructionTools;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import object.ObjectLibrary;
import main.Global;
import main.Main;
import mouseControllers.MouseHandler;

public class BuildMode {

	private static Point lastMouseLocation;
	
	private static int currentlySelectedUID;
	private static int currentlySelectedShelfIndex;
	
	private static boolean shiftingCentre = false;
	
	public static void setUID(int i){currentlySelectedUID = i;}
	public static void setShelfIndex(int i){currentlySelectedShelfIndex = i;}
	public static void shiftCentre(boolean val){shiftingCentre = val;}
	
	public static void tick(){
		if(shiftingCentre){
			Point currentMouseLocation = MouseHandler.getMouseLocation();
			int xDif = currentMouseLocation.x - lastMouseLocation.x;
			int yDif = currentMouseLocation.y - lastMouseLocation.y;
		
			Main.activeCamera.stepOffset(new Point(xDif, yDif));
		}
		lastMouseLocation = MouseHandler.getMouseLocation();
	}
	
	public static void placeObject(){
		int x = (MouseHandler.getMouseLocation().x - Main.activeCamera.getOffset().x);
		int y = (MouseHandler.getMouseLocation().y - Main.activeCamera.getOffset().y);
		
		int xDelta = 0;
		if(x < 0){
			xDelta = Global.gridInterval;
		}
		int yDelta = 0;
		if(y < 0){
			yDelta = Global.gridInterval;
		}
		
		Main.levelHandler.add(currentlySelectedShelfIndex, currentlySelectedUID, x/Global.gridInterval*Global.gridInterval - xDelta, y/Global.gridInterval*Global.gridInterval - yDelta, Global.currentLevelBuildlayer);
	}
	
	public static void removeObject(){
		int x = (MouseHandler.getMouseLocation().x - Main.activeCamera.getOffset().x);
		int y = (MouseHandler.getMouseLocation().y - Main.activeCamera.getOffset().y);
		
		int xDelta = 0;
		if(x < 0){
			xDelta = Global.gridInterval;
		}
		int yDelta = 0;
		if(y < 0){
			yDelta = Global.gridInterval;
		}
		
		Main.levelHandler.remove(x/Global.gridInterval*Global.gridInterval - xDelta, y/Global.gridInterval*Global.gridInterval - yDelta, Global.currentLevelBuildlayer);
	}
	
	public static void draw(Graphics g){
		if(Global.showGridMaps){
			g.setColor(Color.WHITE);
			for(int x = 0; x < Main.WIDTH + Global.gridInterval; x+=Global.gridInterval){
				for(int y = 0; y < Main.WIDTH + Global.gridInterval; y+=Global.gridInterval){
					g.drawLine(x + Main.activeCamera.getOffset().x % Global.gridInterval, y + Main.activeCamera.getOffset().y % Global.gridInterval,
								x + Main.activeCamera.getOffset().x % Global.gridInterval, y + Main.activeCamera.getOffset().y % Global.gridInterval);
				}
			}
		}
		
		if(Global.building){
			int x = (MouseHandler.getMouseLocation().x - Main.activeCamera.getOffset().x - Global.gridInterval);
			int y = (MouseHandler.getMouseLocation().y - Main.activeCamera.getOffset().y - Global.gridInterval);
			
			int xDelta = 0;
			if(x >= 0){
				xDelta = Global.gridInterval;
			}
			int yDelta = 0;
			if(y >= 0){
				yDelta = Global.gridInterval;
			}
			
			g.setColor(Global.fontColors[0]);
			g.drawRect((x + xDelta) / Global.gridInterval * Global.gridInterval + Main.activeCamera.getOffset().x, 
						(y + yDelta) / Global.gridInterval * Global.gridInterval + Main.activeCamera.getOffset().y,
						ObjectLibrary.collectObjectUID(currentlySelectedShelfIndex, currentlySelectedUID, 0, 0).getSprite().getImage().getWidth(), 
						ObjectLibrary.collectObjectUID(currentlySelectedShelfIndex, currentlySelectedUID, 0, 0).getSprite().getImage().getHeight());
		}
	}
}
