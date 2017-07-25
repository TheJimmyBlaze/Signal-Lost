package mouseControllers;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.Global;
import main.Main;
import window.WindowHandler;
import constructionTools.BuildMode;

public class MouseHandler implements MouseListener{	
	
	private Point lastPointer = new Point(0,0);
	private BufferedImage pointerSprite;
	
	public void swap(int index){
		if(index == 0)
			swapCursorIcon(null);
		if(index == 1)
			swapCursorIcon(pointerSprite);
	}
	
	public MouseHandler(){
		Main.frame.addMouseListener(this);
		try {
			pointerSprite = ImageIO.read(getClass().getResource("/cursors/Arrow.png"));
		} catch (IOException e) {
			System.out.println("Error loading pointer sprite: default");
		}
		swapCursorIcon(null);
	}

	public static Point getMouseLocation(){
		Point real = MouseInfo.getPointerInfo().getLocation();
		Point windowCorner = new Point((int)Main.frame.getLocation().getX(), (int)Main.frame.getLocation().getY());

		return new Point((int)(real.getX() - windowCorner.getX()), (int)(real.getY() - windowCorner.getY()));
	}	
	
	public void swapCursorIcon(BufferedImage image){
		if(image == null){
			centreMouse();
			Main.frame.setCursor(Main.frame.getToolkit().createCustomCursor(
		            new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB), new Point(0, 0),
		            "null"));
		}
		else
			Main.frame.setCursor(Main.frame.getToolkit().createCustomCursor(image, new Point(0,0), "pointer"));
	}
	
	public void tick(){
		if(getMouseLocation() != lastPointer && !Global.paused && !Global.altPaused){
			
			double xDelta = getMouseLocation().getX() - Main.WIDTH/2;
			double yDelta = getMouseLocation().getY() - Main.HEIGHT/2;
			
			Main.gameCursor.step((int)xDelta, (int)yDelta);
			
			if(Main.gameCursor.getPos().getX() - Main.levelHandler.getPlayer().getCollision().getX() - Main.levelHandler.getPlayer().getCollision().getWidth() > Main.WIDTH - 135)
				Main.gameCursor.move(new Point((int)(Main.levelHandler.getPlayer().getCollision().getX() + Main.levelHandler.getPlayer().getCollision().getWidth() + Main.WIDTH - 135), (int)Main.gameCursor.getPos().getY()));
			if(Main.gameCursor.getPos().getX() - Main.levelHandler.getPlayer().getCollision().getX() < -Main.WIDTH + 137)
				Main.gameCursor.move(new Point((int)(Main.levelHandler.getPlayer().getCollision().getX() - Main.WIDTH + 137), (int)Main.gameCursor.getPos().getY()));
			
			if(Main.gameCursor.getPos().getY() - Main.levelHandler.getPlayer().getCollision().getY() - Main.levelHandler.getPlayer().getCollision().getHeight() -32 > Main.HEIGHT/2 + 22)
				Main.gameCursor.move(new Point((int)Main.gameCursor.getPos().getX(), (int)(Main.levelHandler.getPlayer().getCollision().getY()
						+ (int)Main.levelHandler.getPlayer().getCollision().getHeight() + Main.HEIGHT/2 + 22 + 32)));
			if(Main.gameCursor.getPos().getY() - Main.levelHandler.getPlayer().getCollision().getY() - 16 < - Main.HEIGHT/2 + 2)
				Main.gameCursor.move(new Point((int)Main.gameCursor.getPos().getX(), (int)(Main.levelHandler.getPlayer().getCollision().getY() - Main.HEIGHT/2 + 2 +16)));
			
			Main.playerCamera.moveOffset(new Point((int)(-(Main.gameCursor.getPos().getX() - Main.levelHandler.getPlayer().getCollision().getWidth()/2 - Main.levelHandler.getPlayer().getCollision().getX())*0.45
					- Main.levelHandler.getPlayer().getCollision().getX() - Main.levelHandler.getPlayer().getCollision().getWidth()/2 + Main.WIDTH/2),
					(int)(-(Main.gameCursor.getPos().getY() - Main.levelHandler.getPlayer().getCollision().getHeight()/2 - Main.levelHandler.getPlayer().getCollision().getY())*0.25
					- Main.levelHandler.getPlayer().getCollision().getY() - Main.levelHandler.getPlayer().getCollision().getHeight()/2 + Main.HEIGHT/2)));
			
			centreMouse();
			
			lastPointer = getMouseLocation();
		}
	}
	
	private void centreMouse(){
		Point windowCorner = new Point((int)Main.frame.getLocation().getX(), (int)Main.frame.getLocation().getY());
		
		try {
			Robot bot = new Robot();
			bot.mouseMove((int)windowCorner.getX() + Main.WIDTH/2, (int)windowCorner.getY() + Main.HEIGHT/2);
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}
	
	public void mousePressed(MouseEvent e) {
		if(Global.altPaused)
			Global.altPaused = false;
		
		if(!WindowHandler.click(new Rectangle(e.getX(), e.getY(), 1, 1))){

			if(Global.building){
				if(e.getButton() == MouseEvent.BUTTON1)
					BuildMode.placeObject();
				
				if(e.getButton() == MouseEvent.BUTTON3)
					BuildMode.removeObject();
			}
			
			if(!Global.paused && !Global.altPaused)
				Main.levelHandler.getPlayer().firing();
		}
		
		if(Global.building){
			if(e.getButton() == MouseEvent.BUTTON2)
				BuildMode.shiftCentre(true);
		}
	}

	public void mouseReleased(MouseEvent e) {
		WindowHandler.release();
		
		if(Global.building){
			if(e.getButton() == MouseEvent.BUTTON2)
				BuildMode.shiftCentre(false);
		}
		
		if(!Global.paused && !Global.altPaused)
			Main.levelHandler.getPlayer().stopFiring();
	}

	public void mouseClicked(MouseEvent e) {}

	public void mouseEntered(MouseEvent e) {}

	public void mouseExited(MouseEvent e) {}

}
