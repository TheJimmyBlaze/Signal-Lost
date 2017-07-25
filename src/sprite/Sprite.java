package sprite;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class Sprite {
	
	private BufferedImage image;
	private BufferedImage icon;
	private BufferedImage rotationImage;
	
	protected int shelfIndex;
	protected int uid;
	protected String name;
	private Point offset;
	
	private int tallestSide;
	private double rotationSnap;
	private double rotateX;
	private double rotateY;
	
	public int getShelfIndex(){return shelfIndex;}
	public int getUID(){return uid;}
	public String getName(){return name;}
	public Point getOffset(){return offset;}
	public void setOffset(Point p){offset = p;}
	public int[] getSize(){return new int[]{image.getWidth(), image.getHeight()};}
	
	public void makeRotateImage(double rxp, double ryp, boolean right){
		
		tallestSide = image.getWidth();
		if(image.getHeight() > image.getWidth())
			tallestSide = image.getHeight();
		
		rotationImage = new BufferedImage(tallestSide*2, tallestSide*2, BufferedImage.TYPE_INT_ARGB);
		if(right)
			rotateX = rxp + rotationImage.getWidth()/2;
		else
			rotateX = rxp;
		rotateY = ryp + rotationImage.getHeight()/2 - image.getHeight()/2;
		
		Graphics g = rotationImage.createGraphics();
		if(right)
			g.drawImage(image, rotationImage.getWidth()/2, rotationImage.getHeight()/2 - image.getHeight()/2, null);
		else
			g.drawImage(image, 0, rotationImage.getHeight()/2 - image.getHeight()/2, null);
		g.dispose();
		image = rotationImage;
	}
	
	public void rotateImage(double rotation){
		
		if(rotationSnap != rotation){
			rotationSnap = rotation;
		
			AffineTransform tx = AffineTransform.getRotateInstance(rotation, rotateX, rotateY);
			AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
		
			BufferedImage rotatedImage = new BufferedImage(tallestSide*2, tallestSide*2, BufferedImage.TYPE_INT_ARGB);
			Graphics g = rotatedImage.createGraphics();
			g.drawImage(op.filter(rotationImage, null), 0, 0, null);
			g.dispose();
			image = rotatedImage;
		}
	}
	
	public BufferedImage getImage(){
		BufferedImage returned = new BufferedImage(image.getWidth() + (int)offset.getX(), image.getHeight() + (int)offset.getY(), BufferedImage.TYPE_INT_ARGB);
		Graphics g = returned.getGraphics();
		g.drawImage(image, (int)offset.getX(), (int)offset.getY(), null);
		g.dispose();
		return returned;
	}
	public BufferedImage getIcon(){
		BufferedImage returned = new BufferedImage(icon.getWidth() + (int)offset.getX()/4, icon.getHeight() + (int)offset.getY()/4, BufferedImage.TYPE_INT_ARGB);
		Graphics g = returned.getGraphics();
		g.drawImage(icon, (int)offset.getX()/4, (int)offset.getY()/4, null);
		g.dispose();
		return returned;
	}
	
	public Sprite clone(){
		if(offset == null)
			return new Sprite(image, icon, name, shelfIndex, uid);
		return new Sprite(image, icon, name, shelfIndex, uid, offset);
	}
	
	public Sprite(BufferedImage imagePass, BufferedImage iconPass, String namePass, int shelfIndexPass, int uidPass){
		image = imagePass;
		icon = iconPass;
		shelfIndex = shelfIndexPass;
		uid = uidPass;
		String[] nameSplit = namePass.split("/");
		if(nameSplit.length > 1)
			name = namePass.split("/")[1];
		else
			name = nameSplit[0];
		offset = new Point(0, 0);
	}
	
	public Sprite(BufferedImage imagePass, BufferedImage iconPass, String namePass, int shelfIndexPass, int uidPass, Point offsetPass){
		image = imagePass;
		icon = iconPass;
		shelfIndex = shelfIndexPass;
		uid = uidPass;
		String[] nameSplit = namePass.split("/");
		if(nameSplit.length > 1)
			name = namePass.split("/")[1];
		else
			name = nameSplit[0];
		offset = offsetPass;
	}
	
	public Sprite flip(){
		
		BufferedImage imageReturn = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
		for(int x = 0; x < imageReturn.getWidth(); x++){
			for(int y = 0; y < imageReturn.getHeight(); y++){
				imageReturn.setRGB(x, y, image.getRGB(image.getWidth() - x-1, y));
			}
		}
		BufferedImage iconReturn = null;
		if(icon != null){
			iconReturn = new BufferedImage(icon.getWidth(), icon.getHeight(), BufferedImage.TYPE_INT_ARGB);
			for(int x = 0; x < iconReturn.getWidth(); x++){
				for(int y = 0; y < iconReturn.getHeight(); y++){
					iconReturn.setRGB(x, y, icon.getRGB(icon.getWidth() - x-1, y));
				}
			}
		}

		if(offset == null)
			return new Sprite(imageReturn, iconReturn, name, shelfIndex, uid);
		return new Sprite(imageReturn, iconReturn, name, shelfIndex, uid, offset);
	}
}