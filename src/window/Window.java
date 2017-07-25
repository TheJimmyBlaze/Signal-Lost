package window;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.Global;
import main.Main;
import sprite.Sprite;
import sprite.SpriteLibrary;
import font.FontDispatcher;
import font.SpriteFontLib;

public abstract class Window {

	protected Sprite image;
	protected BufferedImage background;
	protected BufferedImage canvas;
	
	protected Rectangle handle;
	
	protected int x;
	protected int y;
	protected String name;
	
	protected boolean visible = false;
	
	protected SpriteFontLib fontSet;
	public Boolean textSelected = false;
	protected int blinkerInterval = 16;
	
	private int handlePointX;
	private int handlePointY;
	private boolean isHandled = false;
	
	public String getName(){return name;}
	public boolean isTextSelected(){return textSelected;}
	
	public void setVisible(Boolean value){visible = value;}
	
	public Window(String namePass){
		name = namePass;
		image = SpriteLibrary.collectSpriteName(namePass);
		
		handle = new Rectangle(x, y, image.getSize()[0],  7 * 4);
		
		x = 0; y = 0;
		
		try {
			background = ImageIO.read(getClass().getResource("/ui/RedCloth.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		canvas = new BufferedImage(image.getSize()[0], image.getSize()[1], BufferedImage.TYPE_INT_ARGB);
		Graphics e = canvas.createGraphics();
		for(int a = 0; a < canvas.getWidth() / background.getWidth() + 1; a++){
			for(int b = 0; b < canvas.getHeight() / background.getHeight() + 1; b++){
				e.drawImage(background, background.getWidth() * a, background.getHeight() * b, null);
			}
		}
		e.drawImage(image.getImage(), x, y, null);
		e.dispose();
		
		fontSet = FontDispatcher.splitImage("defaultFont");
	}
	
	public boolean click(Rectangle r){
		if(new Rectangle(x, y, image.getImage().getWidth(), image.getImage().getHeight()).intersects(r)){
			if(handle.intersects(r)){
				isHandled = true;
				handlePointX = (int) r.getX() - x;
				handlePointY = (int) r.getY() - y;
				return true;
			}
		}
		return false;
	}
	public void release(){
		if(isHandled){
			isHandled = false;
		}
	}
	
	public void tick(Rectangle r){
		if(isHandled){
			Rectangle frame = new Rectangle(Main.frame.getInsets().left,
											Main.frame.getInsets().top,
											Main.frame.getWidth() - Main.frame.getInsets().left * 2,
											Main.frame.getHeight() - Main.frame.getInsets().top - Main.frame.getInsets().bottom);

			y = (int) (r.getY() - handlePointY);
			x = (int) (r.getX() - handlePointX);
			moveComponants();
			
			if(!frame.contains(handle)){
				if(x > frame.getWidth() - image.getImage().getWidth())
					x = (int) (frame.getWidth() + frame.x -1) - image.getImage().getWidth();
				if(x < frame.x)
					x = frame.x + 1;
				
				if(y > frame.getHeight())
					y = (int) (frame.getHeight() + frame.y - 1 - handle.getHeight());
				if(y < frame.y)
					y = frame.y + 1;
			}
			moveComponants();
		}
	}
	
	public void keyPress(KeyEvent e){};
	
	protected void moveComponants(){
		handle.setLocation(x, y);
	}
	
	public void draw(Graphics g){
		g.drawImage(canvas, x, y, null);
		
		if(Global.showCollisionMaps){
			g.setColor(Color.BLUE);
			g.drawRect((int)handle.getX(), (int)handle.getY(), (int)handle.getWidth(), (int)handle.getHeight());
		}
	}
}
