package main;

import java.awt.Color;
import java.awt.Point;

import constructionTools.BuildMode;
import sprite.SpriteLibrary;
import window.WindowHandler;

public class CommandHandler {
	
	private static String[] lastCommand = {""};
	
	private static String booleanRepresentation(Boolean b){
		if(b)
			return "enabled";
		else
			return"disabled";
	}
	
	public static String executeCommand(String[] command){
		
		lastCommand = command;
		
		try{
			if(command[0].toLowerCase().equals("overlay")){
				if(command[1].toLowerCase().equals("collision")){
					Global.showCollisionMaps = !Global.showCollisionMaps;
					return "Showing of " + command[1] + " maps: " + booleanRepresentation(Global.showCollisionMaps);
				}
				if(command[1].toLowerCase().equals("fps")){
					Global.showFPS = !Global.showFPS;
					return "Showing of " + command[1] + " maps: " + booleanRepresentation(Global.showFPS);
				}
				if(command[1].toLowerCase().equals("weaponcones")){
					Global.showWeaponCones = !Global.showWeaponCones;
					return "Showing of " + command[1] + ": " + booleanRepresentation(Global.showWeaponCones);
				}
				if(command[1].toLowerCase().equals("hitboxes")){
					Global.showHitBoxes = !Global.showHitBoxes;
					return "Showing of " + command[1] + ": " + booleanRepresentation(Global.showHitBoxes);
				}
				if(command[1].toLowerCase().equals("grid")){
					if(command.length == 2){
						Global.showGridMaps = !Global.showGridMaps;
						return "Showing of " + command[1] + " maps: " + booleanRepresentation(Global.showGridMaps);
					}
					else if(command[2].toLowerCase().equals("set")){
						Global.gridInterval = Integer.parseInt(command[3]);
						return "Grid interval set to: " + command[3];
					}
				}
			}
			
			if(command[0].toLowerCase().equals("grid")){
				if(command[1].toLowerCase().equals("set")){
					Global.gridInterval = Integer.parseInt(command[2]);
					return "Grid interval set to: " + command[2];
				}
			}
			
			if(command[0].toLowerCase().equals("build")){
				if(command[1].toLowerCase().equals("layer")){
					if(Integer.parseInt(command[2]) >= 0 && Integer.parseInt(command[2]) < 4){
						Global.currentLevelBuildlayer = Integer.parseInt(command[2]);
						return "Current building layer is now: " + command[2];
					}
					return "Specified layer is not valid";
				}
				if(command[1].toLowerCase().equals("mode")){
					Global.building = !Global.building;
					if(Global.building == true){
						Main.activeCamera = new Camera(new Point(Main.playerCamera.getOffset().x, Main.playerCamera.getOffset().y));
						WindowHandler.activateWindow("SelectorWindow");
					}
					else{
						Main.activeCamera = Main.playerCamera;
						WindowHandler.deactivateWindow("SelectorWindow");
					}

					return "Building mode: " + booleanRepresentation(Global.building);
				}
			}
			
			if(command[0].toLowerCase().equals("fontcolor")){
				boolean changed = false;
				if(command[1].toLowerCase().equals("basic")){
					Global.fontColors[0] = new Color(Integer.parseInt(command[2]), Integer.parseInt(command[3]), Integer.parseInt(command[4]));
					changed = true;
				}
				if(command[1].toLowerCase().equals("system")){
					Global.fontColors[1] = new Color(Integer.parseInt(command[2]), Integer.parseInt(command[3]), Integer.parseInt(command[4]));
					changed = true;
				}
				if(command[1].toLowerCase().equals("error")){
					Global.fontColors[2] = new Color(Integer.parseInt(command[2]), Integer.parseInt(command[3]), Integer.parseInt(command[4]));
					changed = true;
				}
				if(changed)
					return "Color of " + command[1] + " font changed";
			}
			
			if(command[0].toLowerCase().equals("drawn")){
				return String.valueOf(Main.levelHandler.getDrawnTally());
			}
			
			if(command[0].toLowerCase().equals("force")){
				if(command[1].toLowerCase().equals("save")){
					return Main.levelHandler.saveCurrent() + " saved";
				}
				if(command[1].toLowerCase().equals("load")){
					Main.levelHandler.unloadCurrent();
					return Main.levelHandler.loadCurrent() + " reloaded";
				}
				
				if(command[1].toLowerCase().equals("clear")){
					Main.levelHandler.unloadCurrent();
					return Main.levelHandler.saveCurrent() + " overriten";
				}
			}
			
			if(command[0].toLowerCase().equals("player")){
				if(command[1].toLowerCase().equals("weapon")){
					if(command[2].toLowerCase().equals("random")){
						Main.levelHandler.getPlayer().randomiseWeapon();
						Global.showWeaponCones = false;
						return "Weapon randomized";
					}
				}
			}
		}
		catch(Exception ex){
			return "Improper usage of " + command[0];
		}
			
		return error();
	}
	
	private static String error(){
		
		return  "Error: " + lastCommand[0] + " not recognised." + (char)13 + "For a comprehensive list of commands," + (char)13 + "check Commands.txt";
	}
}
