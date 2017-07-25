package weapon;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;

import sprite.Sprite;

public class Rifle extends Weapon{
	
	protected Point RECIEVER_OFFSET = new Point(30, 12);
	protected Point BARREL_OFFSET = new Point(0, 12);
	protected Point STOCK_OFFSET = new Point(0, 12);
	protected Point GRIP_OFFSET = new Point(30, 26);
	protected Point MAG_OFFSET = new Point(64, 26);
	protected Point EXTENSION_OFFSET = new Point(0, 12);
	
	protected Point sightOffset;
	
	private final int Height = 50;
	
	public Rifle(WeaponReceiver recieverPass, WeaponBarrel barrelPass,
			WeaponMod stockPass, WeaponMod gripPass, WeaponMod magPass,
			WeaponMod sightPass, WeaponMod extensionPass) {
		super(recieverPass, barrelPass, stockPass, gripPass, magPass, sightPass,
				extensionPass);
		offset = new Point(16, -26);
		sightOffset = reciever.getSightOffset();
		
		assemble();
	}

	public void assemble(){
		
		int extensionLength = 0;
		if(extension != null)
			extensionLength = extension.getSprite().getImage().getWidth();
		BufferedImage canvas = new BufferedImage(stock.getSprite().getImage().getWidth() + reciever.getSprite().getImage().getWidth() +
												barrel.getSprite().getImage().getWidth() + extensionLength, Height, BufferedImage.TYPE_INT_ARGB);
		Graphics g = canvas.createGraphics();
		g.drawImage(grip.getSprite().getImage(), (int)GRIP_OFFSET.getX(), (int)GRIP_OFFSET.getY(), null);
		g.drawImage(mag.getSprite().getImage(), (int)MAG_OFFSET.getX(), (int)MAG_OFFSET.getY(), null);
		g.drawImage(barrel.getSprite().getImage(), (int)RECIEVER_OFFSET.getX() + reciever.getSprite().getImage().getWidth(), (int)BARREL_OFFSET.getY(), null);
		g.drawImage(extension.getSprite().getImage(), (int)RECIEVER_OFFSET.getX() + reciever.getSprite().getImage().getWidth() +
					barrel.getSprite().getImage().getWidth(), (int)EXTENSION_OFFSET.getY(), null);
		g.drawImage(reciever.getSprite().getImage(), (int)RECIEVER_OFFSET.getX(), (int)RECIEVER_OFFSET.getY(), null);
		g.drawImage(stock.getSprite().getImage(), (int)(STOCK_OFFSET.getX() + reciever.getStockOffset().getX()), (int)STOCK_OFFSET.getY(), null);
		g.drawImage(sight.getSprite().getImage(), (int)sightOffset.getX(), (int)reciever.getSightOffset().getY(), null);
		g.dispose();
		
		right = new Sprite(canvas, canvas, "null/weapon", 0, 0);
		left = right.flip();
	}
}
