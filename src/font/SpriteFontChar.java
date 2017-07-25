package font;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class SpriteFontChar {
	
	private BufferedImage image; 
	private char name;
	
	public char getName(){return name;}
	public BufferedImage getImage(){return image;}
	
	public SpriteFontChar(BufferedImage imagePass, char namePass){
		
		image = imagePass;
		name = namePass;
	}
	public void draw(Graphics g, int x, int y){
		g.drawImage(image, x, y, null);
	}
}
