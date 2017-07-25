package font;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

public class FontDispatcher {
	
	public FontDispatcher(){};

	public static SpriteFontLib splitImage(String fontDIR){
		BufferedImage fontSet = null;
		String contents ="";
		try {
			fontSet = ImageIO.read(FontDispatcher.class.getResource("/res/" + fontDIR + ".png"));
			BufferedReader reader = new BufferedReader(new InputStreamReader(FontDispatcher.class.getResourceAsStream("/res/" + fontDIR + ".fcs")));
			contents = reader.readLine();
		} catch (Exception ex) {
			System.out.println("Image Split IO Error: " + ex);
		}
		
		SpriteFontLib constructedLib = new SpriteFontLib(contents);

		Point lastPoint = new Point(0,0);
		int lineLongest = 0;
		
		for(int i = 1; i <= contents.length(); i++){
			Point start = findStart(lastPoint, fontSet);
			int width = findWidth(start, fontSet);
			int height = findHeight(start, fontSet);
			
			if(height > lineLongest)
				lineLongest = height;

			constructedLib.addCharacter(new SpriteFontChar(fontSet.getSubimage(start.x, start.y, width, height), contents.charAt(i - 1)));
			if( i < contents.length()){
				lastPoint = new Point(start.x + width, start.y);
				if(i % 8 == 0){
					lastPoint.y += lineLongest;
					lineLongest = 0;
				}
			}
		}
		
		return constructedLib;
	}
	
	private static Point findStart(Point i, BufferedImage image){
		
		for(int iy = i.y; iy < image.getHeight(); iy++){
			int ix = 0;
			if(iy == i.y)
				ix = i.x;
			while(ix < image.getWidth()){
				if(image.getRGB(ix, iy) == 0){
					return new Point(ix, iy);
				}
				ix++;
			}
		}
		return null;
	}
	
	private static int findWidth(Point i, BufferedImage image){
		
		for(int ix = i.x; ix < image.getWidth(); ix++){
			if(image.getRGB(ix, i.y) == -65281){
				return ix - i.x;
			}
		}
		return -99;
	}
	
	private static int findHeight(Point i, BufferedImage image){
		
		for(int iy = i.y; iy < image.getHeight(); iy++){
			if(image.getRGB(i.x, iy) == -65281){
				return iy - i.y;
			}
		}
		return -99;
	}
}
