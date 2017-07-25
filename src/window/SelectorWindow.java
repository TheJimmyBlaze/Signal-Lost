package window;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import constructionTools.BuildMode;
import object.ObjectLibrary;
import object.ObjectShelf;
import main.Global;
import main.Main;
import sprite.Sprite;
import sprite.SpriteLibrary;

public class SelectorWindow extends Window{
	
	private Button up;
	private Button down;
	private Button send;
	
	private Button[] blockSelections = new Button[24];
	private Point[] blockPlacements = new Point[24];
	
	private Sprite blinkerImage;
	private int blinkerI = 0;
	
	private int selectedBlock = 0;
	private int offsetBlock = 0;
	private Sprite selectImage;
	
	private ObjectShelf currentShelf;
	private ObjectShelf imageShelfSnap;
	private BufferedImage imageShelf;
	
	private String textBoxContents = "";
	private String textBoxContentsOld = "old";
	private BufferedImage contentsImage;
	protected Rectangle textBox;

	public SelectorWindow(String namePass) {
		super(namePass);
		
		x = 8 + Main.frame.getInsets().right;
		y = Main.frame.getInsets().top + 8;

		for(int i = 0; i < blockPlacements.length; i++){
			blockPlacements[i] = new Point((4 + 17 * (i%6)) * 4, (10 + 17 * (i/6)) * 4);
		}
		moveComponants();
		
		blinkerImage = SpriteLibrary.collectSpriteName("Blinker");
		selectImage = SpriteLibrary.collectSpriteName("SelectorSelected");
		
		currentShelf = setShelf("ruins");
		BuildMode.setShelfIndex(currentShelf.getNumber());
		BuildMode.setUID(selectedBlock + offsetBlock);
	}
	
	private ObjectShelf setShelf(String name){
		return ObjectLibrary.collectShelf(name);
	}
	
	public boolean click(Rectangle r){
		if(visible && new Rectangle(x, y, image.getImage().getWidth(), image.getImage().getHeight()).intersects(r)){
			super.click(r);
			
			if(up.click(r)){
				if(offsetBlock > 0){
					offsetBlock -= 24;
					selectedBlock = 0;
					BuildMode.setShelfIndex(currentShelf.getNumber());
					BuildMode.setUID(selectedBlock + offsetBlock);
				}
			}
			if(down.click(r)){
				if(offsetBlock + 24< currentShelf.getSpriteCount()){
					offsetBlock += 24;
					selectedBlock = 0;
					BuildMode.setShelfIndex(currentShelf.getNumber());
					BuildMode.setUID(selectedBlock + offsetBlock);
				}
			}
			if(send.click(r) && textBoxContents.length() > 0){
				ObjectShelf searchedShelf = null;
				if((searchedShelf = setShelf(textBoxContents)) != null){
					currentShelf = searchedShelf;
				}
				textBoxContents = "";
			}
			
			for(int i = 0; i < blockSelections.length; i++){
				if(blockSelections[i].click(r) && currentShelf.getSpriteCount() > i + offsetBlock){
					selectedBlock = i;
					BuildMode.setShelfIndex(currentShelf.getNumber());
					BuildMode.setUID(selectedBlock + offsetBlock);
				}
			}
			
			if(textBox.intersects(r)){
				textSelected = true;
			}
			else{
				textSelected = false;
			}
			
			return true;
		}
		textSelected = false;
		
		return false;
	}
	
	public void tick(Rectangle r){
		if(visible){
			super.tick(r);
			up.tick(r);
			down.tick(r);
			send.tick(r);
			
			blinkerI ++;
			if (blinkerI > 2*blinkerInterval)
				blinkerI = 0;
			
			for(int i = 0; i < blockSelections.length; i++){
				blockSelections[i].tick(r);
			}
		}
	}
	
	public void keyPress(KeyEvent e){
		
		if(textSelected){
			char c = e.getKeyChar();
			
			if(fontSet.getContents().contains(String.valueOf(c))){
				textBoxContents += c;
			}
			if(c == (char)8 && textBoxContents.length() > 0){
				textBoxContents = textBoxContents.substring(0, textBoxContents.length() - 1);
			}
			if(c == (char)10 && textBoxContents.length() > 0){
				ObjectShelf searchedShelf = null;
				if((searchedShelf = setShelf(textBoxContents)) != null){
					currentShelf = searchedShelf;
					selectedBlock = 0;
					BuildMode.setShelfIndex(currentShelf.getNumber());
					BuildMode.setUID(selectedBlock + offsetBlock);
				}
				textBoxContents = "";
			}
		}
	}
	
	public void moveComponants(){
		super.moveComponants();
		textBox = new Rectangle(x + 3 * 4, y + image.getSize()[1] - 9 * 4, image.getSize()[0] - 14 * 4, 6 * 4);
		
		up = new Button("SelectorLeftButton", x + 3 * 4, y + image.getSize()[1] - 16 * 4);
		down = new Button("SelectorRightButton", x + image.getSize()[0] - SpriteLibrary.collectSpriteName("SelectorRightButton").getSize()[0] - 3 * 4,  
						y + image.getSize()[1] - 16 * 4);
		
		send = new Button("SendButton", x + image.getSize()[0] - 3 * 4 - SpriteLibrary.collectSpriteName("SendButton").getSize()[0],
				y + image.getSize()[1] - 3 * 4 - SpriteLibrary.collectSpriteName("SendButton").getSize()[1]);
		
		for(int i = 0; i < blockSelections.length; i++){
			blockSelections[i] = new Button("SelectorBlocking", blockPlacements[i].x + x, blockPlacements[i].y + y);
		}
	}

	public void draw(Graphics g){
		if(visible){
			super.draw(g);
			
			up.draw(g);
			down.draw(g);
			send.draw(g);
			
			if(imageShelfSnap != currentShelf){
				imageShelf = new BufferedImage(image.getImage().getWidth(), image.getImage().getHeight(), BufferedImage.TYPE_INT_ARGB);
				Graphics s = imageShelf.createGraphics();
				for(int i = 0; i < blockSelections.length; i++){
					if(currentShelf.getSpriteCount() > i + offsetBlock){
						s.drawImage(currentShelf.collectObject(i + offsetBlock).getSprite().getIcon(), blockPlacements[i].x + selectImage.getImage().getWidth()/2
								- currentShelf.collectObject(i + offsetBlock).getSprite().getIcon().getWidth()/2,
								blockPlacements[i].y + selectImage.getImage().getHeight()/2
								- currentShelf.collectObject(i + offsetBlock).getSprite().getIcon().getHeight()/2, null);
					}
				}
				s.dispose();
				imageShelfSnap = currentShelf;
			}
			g.drawImage(imageShelf, x, y, null);

			if(!textBoxContents.equals(textBoxContentsOld)){
				contentsImage = crop(fontSet.assemblePhrase(textBoxContents, 0));
				textBoxContentsOld = textBoxContents;
			}
			g.drawImage(contentsImage, (int) (textBox.getX() + 4), (int) (textBox.getY() + 4), null);
			
			if(textSelected && blinkerI < blinkerInterval)
				g.drawImage(blinkerImage.getImage(), (int) (contentsImage.getWidth() + textBox.getX() + 4), (int) (textBox.getY() + 4), null);
			
			g.drawImage(selectImage.getImage(), blockPlacements[selectedBlock].x + x, blockPlacements[selectedBlock].y + y, null);
			
			if(Global.showCollisionMaps){
				g.setColor(Color.BLUE);
				g.drawRect((int)textBox.getX(), (int)textBox.getY(), (int)textBox.getWidth(), (int)textBox.getHeight());
			}
		}
	}
	
	private BufferedImage crop(BufferedImage image){
		if(image.getWidth() > textBox.getWidth() - 12)
			return image.getSubimage((int) (image.getWidth() - textBox.getWidth() + 12), 0, (int) (textBox.getWidth() - 12), image.getHeight());
		return image;
	}
}
