package main;

import java.awt.Color;

public class Global {
	
	public static int currentLevelBuildlayer = 0;
	public static boolean building = false;
	public static int gridInterval = 16;
	
	public static boolean showCollisionMaps = false;
	public static boolean showGridMaps = false;
	public static boolean showFPS = false;
	public static boolean showWeaponCones = false;
	public static boolean showHitBoxes = false;
	
	public static boolean running = true;
	
	public static void pause(boolean val){
		if(val){
			paused = true;
			Main.mouseHandler.swap(1);
		}
		else{
			paused = false;
			Main.mouseHandler.swap(0);
		}
	}
	public static boolean paused = false;
	public static boolean altPaused = false;
	
	public static Color[] fontColors = {new Color(204, 204, 204),	//Basic
										new Color(16, 16, 16),		//System
										new Color(204, 32, 32),		//Error
										new Color(204, 204, 32)};	//Overlay
	
	public static Color cursorColor = new Color(204, 32, 32);
	
	public static int chunksLoaded = 6;
	public static int chunkSize = 512;
	
	public static int gravityConstant = 32;
	public static int gravitySpeed = 3;
}
