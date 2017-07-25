package mouseControllers;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.Global;

public class CrosshairSprite {

	private String name;

	private BufferedImage image;
	private BufferedImage[] images = new BufferedImage[4];
	private int parts;
	
	private boolean dot;
	private int accuracy = 0;
	
	public BufferedImage getImage(){return image;}
	public int getAccuracy(){return accuracy;}
	public void setAccuracy(int a){
		accuracy = a;
		reDrawImage();
	}

	public CrosshairSprite(String namePass, int partsPass, boolean dotPass){
		
		name = namePass;
		parts = partsPass;
		dot = dotPass;
		
		try {
			for(int i = 1; i <= parts; i++)
				images[i-1] = ImageIO.read(getClass().getResource("/cursors/" + name + i + ".png"));
		} catch (IOException e) {
			System.out.println("Cursor Error: " + namePass + " cannot be found.");
		}
		
		setAccuracy(0);
	}
	
	public void reDrawImage(){
		
		BufferedImage processedImage = new BufferedImage(images[0].getWidth()*2 + accuracy*2, images[0].getHeight()*2 + accuracy*2, BufferedImage.TYPE_INT_ARGB);
		Graphics g = processedImage.createGraphics();
		
		if(parts == 4){
			g.drawImage(images[0], processedImage.getWidth() - images[0].getWidth(), 0, null);
			g.drawImage(images[1], processedImage.getWidth() - images[1].getWidth(), processedImage.getHeight() - images[1].getHeight(), null);
			g.drawImage(images[2], 0, processedImage.getHeight() - images[2].getHeight(), null);
			g.drawImage(images[3], 0, 0, null);
		}
		
		g.dispose();

		BufferedImage correctedImage = new BufferedImage(processedImage.getWidth(), processedImage.getHeight(), BufferedImage.OPAQUE);
		Graphics p = correctedImage.createGraphics();
		p.setXORMode(Global.cursorColor);
		p.drawImage(processedImage, 0, 0, null);
		p.dispose();
		
		BufferedImage returnImage = new BufferedImage(correctedImage.getWidth(), correctedImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics r = returnImage.createGraphics();
		r.drawImage(correctedImage, 0, 0, null);
		r.dispose();
		
		for(int x = 0; x < returnImage.getWidth(); x++){
			for(int y = 0; y < returnImage.getHeight(); y++){
				if(returnImage.getRGB(x, y) == -16777216){
					returnImage.setRGB(x, y, 0x00FFFFFF);
				}
			}
		}
		
		image = returnImage;
	}
}
