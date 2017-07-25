package font;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import main.Global;

public class SpriteFontLib {
	
	private String contents = "";
	private ArrayList<SpriteFontChar> characters = new ArrayList<SpriteFontChar>();
	
	public String getContents(){return contents;}
	
	public SpriteFontLib(String contentsPass){
		contents = contentsPass;
	}
	
	public void addCharacter(SpriteFontChar addition){
		characters.add(addition);
	}
	
	public BufferedImage assemblePhrase(String text, int c){
		BufferedImage phraseImage = new BufferedImage (1, 1, BufferedImage.TYPE_INT_ARGB);
		
		String phrase = "";
		for(int i = 0; i < text.length(); i++){
			if(contents.contains(String.valueOf(text.charAt(i))))
				phrase += text.charAt(i);
		}
		
		for(int i = 0; i < phrase.length(); i++){
			BufferedImage characterImage = characters.get(contents.indexOf(phrase.charAt(i))).getImage();
			
			int tallest = phraseImage.getHeight();
			if(characterImage.getHeight() > tallest)
				tallest = characterImage.getHeight();
			
			BufferedImage newPhraseImage = new BufferedImage(phraseImage.getWidth() + 2 + characterImage.getWidth(), tallest, BufferedImage.TYPE_INT_ARGB);
			
			Graphics e = newPhraseImage.createGraphics();
			
			e.drawImage(phraseImage, 0, 0, null);
			e.drawImage(characterImage, phraseImage.getWidth(), 0, null);
			e.dispose();
			
			phraseImage = newPhraseImage;
		}

		BufferedImage processedImage = new BufferedImage(phraseImage.getWidth(), phraseImage.getHeight(), BufferedImage.OPAQUE);
		Graphics p = processedImage.createGraphics();
		p.setXORMode(Global.fontColors[c]);
		p.drawImage(phraseImage, 0, 0, null);
		p.dispose();
		
		BufferedImage returnImage = new BufferedImage(phraseImage.getWidth(), phraseImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics r = returnImage.createGraphics();
		r.drawImage(processedImage, 0, 0, null);
		r.dispose();
		
		for(int x = 0; x < returnImage.getWidth(); x++){
			for(int y = 0; y < returnImage.getHeight(); y++){
				if(returnImage.getRGB(x, y) == -16777216){
					returnImage.setRGB(x, y, 0x00FFFFFF);
				}
			}
		}
		
		return returnImage;
	}
}
