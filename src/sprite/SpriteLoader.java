package sprite;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class SpriteLoader {

	private Color[] palette;
	
	private Sprite sprite;
	public Sprite getSprite(){return sprite;}

	public SpriteLoader(String namePass, int shelfIndex, int uid){
		buildPalette();
		sprite = new Sprite(buildFile(namePass, 1), buildFile(namePass, 4), namePass, shelfIndex, uid);
	}
	public SpriteLoader(String namePass, int shelfIndex, int uid, boolean flag){
		buildPalette();
		if(flag = true)
			sprite = new Sprite(buildFile(namePass, 2), buildFile(namePass, 4), namePass, shelfIndex, uid);
		else
			sprite = new Sprite(buildFile(namePass, 1), buildFile(namePass, 4), namePass, shelfIndex, uid);
	}
	public SpriteLoader(String namePass, int shelfIndex, int uid, Point offsetPass, boolean flag){
		buildPalette();
		if(flag)
			sprite = new Sprite(buildFile(namePass, 2), buildFile(namePass, 4), namePass, shelfIndex, uid, offsetPass);
		else
			sprite = new Sprite(buildFile(namePass, 1), buildFile(namePass, 4), namePass, shelfIndex, uid, offsetPass);
	}
	public SpriteLoader(String namePass, int shelfIndex, int uid, Point offsetPass){
		buildPalette();
		sprite = new Sprite(buildFile(namePass, 1), buildFile(namePass, 4), namePass, shelfIndex, uid, offsetPass);
	}
	 
	private void buildPalette(){
		try{
			BufferedImage paletteImage = ImageIO.read(getClass().getResource("/res/palette.png"));
			int size = paletteImage.getWidth() * paletteImage.getHeight();
			palette = new Color[size];

			for(int i = 0; i < size; i++){
				Color color = new Color(paletteImage.getRGB(i%8, i/8), true);

				palette[i] = color;
			}
		}
		catch(Exception ex){
			System.out.println("Pallete Build IO Error: " + ex);
		}
	}
	
	private BufferedImage buildFile(String name, int scale){
		
		try{
			ArrayList<ColorByte> colorByteArray = new ArrayList<ColorByte>();
			BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/" + name + ".cba")));
			
			String line = reader.readLine();
			String[] split = line.split(",");

			int width = Integer.parseInt(split[2]) / scale;
			int height = Integer.parseInt(split[3]) / scale;
			
			while((line = reader.readLine()) != null){
				split = line.split(",");
				int colorIndex =  Integer.parseInt(split[2]);
				Color color = palette[colorIndex];
				
				colorByteArray.add(new ColorByte(new Point(Integer.parseInt(split[0]) /scale, Integer.parseInt(split[1]) /scale), color, scale));
			}
			
			BufferedImage canvas = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			
			Graphics g = canvas.createGraphics();
			
			for(int i = 0; i < colorByteArray.size(); i++){
				colorByteArray.get(i).draw(g);
			}
			g.dispose();
			
			return canvas;
		}
		catch(Exception ex){
			System.out.println("ColorByte Load IO Error in " + name + ":" + ex);
		}
		return null;
	}
}
