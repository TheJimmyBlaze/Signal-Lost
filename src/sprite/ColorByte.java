package sprite;

import java.awt.*;

public class ColorByte{
	private Point point;
	private final int WIDTH = 4;
	private final int HEIGHT = 4;
	private Color color;
	private int scale;

	public Point getPoint(){return point;}
	public void setPoint(Point p){point = p;}

	public Boolean isAt(Point p){
		if(p.x*4/4 == point.x && p.y*4/4 == point.y){
			return true;
		}
		return false;
	}

	public ColorByte(Point pointPass, Color colorPass, int scalePass){
		point = pointPass;
		color = getNoise(colorPass);		
		scale = scalePass;
	}

	private Color getNoise(Color c){
		int R = c.getRed();
		int G = c.getGreen();
		int B = c.getBlue();

		R += (int)(Math.random()*8-4);
		G += (int)(Math.random()*8-4);
		B += (int)(Math.random()*8-4);

		return verifyColor(R,G,B);
	}

	private Color verifyColor(int R, int G, int B){
		if(R > 255){R=255;}
		if(R < 0){R=0;}
		if(G > 255){G=255;}
		if(G < 0){G=0;}
		if(B > 255){B=255;}
		if(B < 0){B=0;}

		return new Color(R,G,B);
	}

	private Color scanLine(Color c, int v){
		int volume = v;

		int R = c.getRed();
		int G = c.getGreen();
		int B = c.getBlue();

		R -= volume; G -= volume; B -= volume;

		return verifyColor(R,G,B);
	}
	
	public void draw(Graphics g){
		g.setColor(color);
		if(scale == 1){
			g.fillRect(point.x , point.y , WIDTH, HEIGHT);
			g.setColor(scanLine(color, -4));
			g.fillRect((point.x + WIDTH -1), point.y, 1, HEIGHT);
			g.setColor(scanLine(color, 12));
			g.fillRect(point.x, (point.y + HEIGHT -1), WIDTH, 1);
		}
		else{
			g.fillRect(point.x , point.y , WIDTH /scale, HEIGHT /scale);
		}
	}
}
