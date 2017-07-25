package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JFrame;

import level.LevelHandler;
import mouseControllers.Crosshair;
import mouseControllers.CrosshairSprite;
import mouseControllers.MouseHandler;
import object.ObjectLibrary;
import sprite.SpriteLibrary;
import weapon.BulletLibrary;
import weapon.WeaponModLibrary;
import window.WindowHandler;
import constructionTools.BuildMode;
import font.FontDispatcher;
import font.SpriteFontLib;

public class Main implements Runnable{
	
	public static int WIDTH = 1280;
	public static int HEIGHT = 720;
	public static final String NAME = "Signal_Lost";
	public static final int BUFFERS = 2;
	public static final int TPS = 18;
	public static int RTPS = 0;
	public static int ticks = 0;
	public static int FPS = 0;
	public static int frames = 0;
	public static boolean canPaint = true;
	public static BufferedImage lastImage;
	
	public static SpriteLibrary spriteLibrary;	
	public static ObjectLibrary objectLibrary;	
	public static WeaponModLibrary weaponModLibrary;
	public static BulletLibrary bulletLibrary;
	public static LevelHandler levelHandler;
	public static WindowHandler windowHandler;
	
	public static MouseHandler mouseHandler;
	public static KeyboardHandler keyHandler;
	
	public static JFrame frame;

	public static Camera activeCamera;
	public static Camera playerCamera;
	
	public static Crosshair gameCursor;
	public static SpriteFontLib defaultFont = FontDispatcher.splitImage("defaultFont");
	
	public static ArrayList<Point> points = new ArrayList<Point>();
	
	public Main(){
		
		frame = new JFrame(NAME);

		Dimension screenDimensions = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(screenDimensions.width/2-WIDTH/2, screenDimensions.height/2-HEIGHT/2);
		frame.setSize(WIDTH, HEIGHT);
		
		frame.setResizable(false);
		frame.setIgnoreRepaint(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.setVisible(true);
		
		frame.createBufferStrategy(Main.BUFFERS);
		
		init();
	}
	
	public void init(){
		spriteLibrary = new SpriteLibrary();
		objectLibrary = new ObjectLibrary();
		bulletLibrary = new BulletLibrary();
		weaponModLibrary = new WeaponModLibrary();
		levelHandler = new LevelHandler();
		windowHandler = new WindowHandler();
		
		mouseHandler = new MouseHandler();
		keyHandler = new KeyboardHandler();
		
		playerCamera = new Camera(new Point(WIDTH/2,HEIGHT/2 + 200));
		activeCamera = playerCamera;
		
		gameCursor = new Crosshair((int)MouseHandler.getMouseLocation().getX(),(int)MouseHandler.getMouseLocation().getY(), true);
		gameCursor.setSprite(new CrosshairSprite("Rifle", 4, false));
		
		levelHandler.switchLevel("Test");
	}
	
	public synchronized void start(){
		Global.running = true;
		new Thread(this).start();
		
		(new Thread() {
			public void run() {
				while(Global.running){
					Main.RTPS = ticks;
					ticks = 0;
	
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						System.out.println("Thread sleep exception: " + e);
					}
				}
			}
		}).start();
	}
	
	public static void main(String[] args) {
		new Main().start();
	}
	
	public void tick(){
		ticks++;
		Main.mouseHandler.tick();
		activeCamera.tick();
		if(!Global.paused && !Global.altPaused)
			levelHandler.tick();
		
		windowHandler.tick(frame);
		BuildMode.tick();
	}
	
	public void paintComponant(){
		canPaint = false;
		BufferStrategy bStrat = Main.frame.getBufferStrategy();
		frames++;
		
		try{
			Graphics s = bStrat.getDrawGraphics();
			BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
			Graphics g = image.createGraphics();
			
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, Main.frame.getWidth(), Main.frame.getHeight());
			Main.levelHandler.draw(g);
			
			g.dispose();
			s.drawImage(image, 0, 0, null);
			
			BuildMode.draw(s);
			Main.gameCursor.draw(s);
			Main.windowHandler.draw(s);
			
			if(Global.showFPS){
				s.drawImage(defaultFont.assemblePhrase(String.valueOf("FPS: " + Main.FPS), 3), Main.frame.getInsets().left + 2, 
						Main.frame.getInsets().top + 2, null);
				s.drawImage(defaultFont.assemblePhrase(String.valueOf("TPS: " + Main.RTPS), 3), Main.frame.getInsets().left + 2, 
						Main.frame.getInsets().top + 12, null);
			}
			
			for(int i = 0; i < points.size(); i++){
				s.setColor(Color.GREEN);
				s.drawRect(points.get(i).x-1 + Main.playerCamera.getOffset().x, points.get(i).y-1 + Main.playerCamera.getOffset().y, 2, 2);
			}
			
			s.dispose();
			lastImage = image;
			
			bStrat.show();
			canPaint = true;
		}
		catch(Exception ex){
			System.out.println("Main Rendering Error: " + ex);
		}
	}
	
	public void run() {
		final double tickDelta = 1000000000 / TPS;
		final int maxCatchUpTicks = 10;
		double lastUpdateTime = System.nanoTime();
		
		int lastSecondTime = (int)(lastUpdateTime / 1000000000);
		  
		while(Global.running){
			double now = System.nanoTime();
	        int updateCount = 0;
	        
           while(now - lastUpdateTime > tickDelta && updateCount < maxCatchUpTicks){
              tick();
              lastUpdateTime += tickDelta;
              updateCount++;
           }
  
           if(now - lastUpdateTime > tickDelta)
              lastUpdateTime = now - tickDelta;
           
           paintComponant();
        
           int second = (int)(lastUpdateTime / 1000000000);
           if(second > lastSecondTime){
              FPS = frames;
              frames = 0;
              lastSecondTime = second;
           }
		}
	}
}