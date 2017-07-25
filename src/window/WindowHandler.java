package window;

import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

import main.Global;
import mouseControllers.MouseHandler;

public class WindowHandler {

		private static ArrayList<Window> windows = new ArrayList<Window>();
		
		private static ArrayList<Window> activeWindows = new ArrayList<Window>();
		
		private static Window focusWindow = null;
		
		public WindowHandler(){
			windows.add(new ConsoleWindow("ConsoleWindow"));
			windows.add(new SelectorWindow("SelectorWindow"));
		}
		
		public static int getActiveWindowCount(){return activeWindows.size();}
		public static int getWindowCount(){return windows.size();}
		public static boolean textSelected(){
			for(Window w: activeWindows){
				if(w.isTextSelected())
					return true;
			}
			return false;
		}
		
		public static Window getFocusWindow(){return focusWindow;}
		
		public static Window retrieveWindow(String name){
			if(hasElement(windows, name)){
				for(int i = 0; i < windows.size(); i++){
					if(windows.get(i).getName().equals(name)){
						return windows.get(i);
					}
				}
			}
			return null;
		}
		
		private static boolean hasElement(ArrayList<Window> testList, String name){
			for(int i = 0; i < testList.size(); i++){
				if(testList.get(i).getName().equals(name)){
					return true;
				}
			}
			return false;
		}
		
		public static boolean isVisible(String name){
			if(hasElement(windows, name)){
				for(int i = 0; i < windows.size(); i++){
					if(windows.get(i).getName().equals(name)){
						return windows.get(i).visible;
					}
				}
			}
			return false;
		}
		
		public static void activateWindow(String name){
			if(!hasElement(activeWindows, name)){
				for(int i = 0; i < windows.size(); i++){
					if(windows.get(i).getName().equals(name)){
						unsetTextFocus();
						activeWindows.add(windows.get(i)); 
						windows.get(i).setVisible(true);
						giveFocus(activeWindows.size() - 1);
					}
				}
			}
		}
		
		public static void deactivateWindow(String name){
			for(int i = 0; i < windows.size(); i++){
				if(windows.get(i).getName().equals(name)){
					activeWindows.remove(windows.get(i));
					windows.get(i).setVisible(false);
					if(focusWindow == windows.get(i)){
						focusWindow = null;
					}
				}
			}
		}
		
		public static boolean click(Rectangle r){
			if(focusWindow != null){
				if(focusWindow.click(r))
					return true;
			}
			focusWindow = null;
			for(int i = 0; i < activeWindows.size(); i ++){
				if(activeWindows.get(i).click(r)){
					giveFocus(i);
					return true;
				}
			}
			
			return false;
		}
		
		private static void giveFocus(int i){
			focusWindow = activeWindows.get(i);
			activeWindows.remove(focusWindow);
			activeWindows.add(focusWindow);
		}
		
		private static void unsetTextFocus(){
			for(int i = 0; i < activeWindows.size(); i++){
				activeWindows.get(i).textSelected = false;
			}
		}
		
		public static void release(){
			for(int i = 0; i < activeWindows.size(); i ++){
				activeWindows.get(i).release();
			}
		}
		
		public void tick(Frame frame){
			for(int i = 0; i < activeWindows.size(); i++){
				activeWindows.get(i).tick(new Rectangle(MouseHandler.getMouseLocation().x, MouseHandler.getMouseLocation().y, 1, 1));
			}
			if(activeWindows.size() != 0)
				Global.pause(true);
			else if (Global.paused)
				Global.pause(false);
		}
		
		public void draw(Graphics g){
			for(int i = 0; i < activeWindows.size(); i++){
				activeWindows.get(i).draw(g);
			}
		}
}
