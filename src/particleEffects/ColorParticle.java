package particleEffects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import object.CollisionMap;
import sprite.Sprite;
import sprite.ColorByte;

public class ColorParticle extends Particle{

	public ColorParticle(int shelfIndexPass, int uidPass, String namePass, Point pos, int vSpeedPass, int hSpeedPass, int fadePass, int bouncinessPass, boolean fallsPass, Color givenColorPass, double gravityMultiplyer, double vSpeedDecay){
		super(shelfIndexPass, uidPass, namePass, null, pos, vSpeedPass, hSpeedPass, fadePass, bouncinessPass, fallsPass, gravityMultiplyer, vSpeedDecay);
		
		BufferedImage canvas = new BufferedImage(4, 4, BufferedImage.TYPE_INT_ARGB);
		ColorByte color = new ColorByte(new Point(0,0), givenColorPass, 1);
		Graphics g = canvas.createGraphics();
		color.draw(g);
		g.dispose();
		image = new Sprite(canvas, null, "Color", 0, 0);
		
		image.makeRotateImage(image.getImage().getWidth()/2, image.getImage().getHeight()/2, true);
		collision = new CollisionMap(new Rectangle(x + image.getImage().getWidth()/4, y + image.getImage().getHeight()/4, image.getImage().getWidth()/2, image.getImage().getHeight()/2), 0, 0);
	}
}
