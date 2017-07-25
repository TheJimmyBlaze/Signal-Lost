package window;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import sprite.Sprite;
import sprite.SpriteLibrary;
import main.CommandHandler;
import main.Main;
import main.Global;

public class ConsoleWindow extends Window{
	
	private Button close;
	private Button send;
	
	private Sprite blinkerImage;
	private int blinkerI = 0;
	
	private String textBoxContents = "";
	private String textBoxContentsOld = "old";
	private BufferedImage contentsImage;
	
	private ArrayList<BufferedImage> textBoxHistoryImage = new ArrayList<BufferedImage>();
	private ArrayList<String> commandHistory = new ArrayList<String>();
	
	private int historyIndex = -99;
	
	protected Rectangle textBox;

	public ConsoleWindow(String namePass) {
		super(namePass);
		
		x = Main.WIDTH - 8 - image.getSize()[0] - Main.frame.getInsets().right;
		y = Main.frame.getInsets().top + 8;
		
		handle = new Rectangle(x, y, image.getSize()[0], 7 * 4);
		moveComponants();
		
		blinkerImage = SpriteLibrary.collectSpriteName("Blinker");
		addHistoryFromText("Command Interface Initiated", 1);
	}
	
	public boolean click(Rectangle r){
		if(visible && new Rectangle(x, y, image.getImage().getWidth(), image.getImage().getHeight()).intersects(r)){
			super.click(r);
			
			if(close.click(r)){
				visible = false;
				release();
				WindowHandler.deactivateWindow(name);
			}
			if(send.click(r) && textBoxContents.length() > 0){
				addHistoryFromTextBox();
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
			close.tick(r);
			send.tick(r);
			
			blinkerI ++;
			if (blinkerI > 2*blinkerInterval)
				blinkerI = 0;
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
				addHistoryFromTextBox();
				textBoxContents = "";
			}
		}
		
		if(e.getKeyCode() == 38){
			if(historyIndex > 0){
				historyIndex--;
			}
			else if(historyIndex == -99){
				historyIndex = commandHistory.size()-1;
			}
			textBoxContents = commandHistory.get(historyIndex);
		}
		if(e.getKeyCode() == 40){
			if(historyIndex < commandHistory.size()-1 && historyIndex != -99){
				historyIndex++;
				textBoxContents = commandHistory.get(historyIndex);
			}
			else if(historyIndex == commandHistory.size()-1){
				historyIndex = -99;
				textBoxContents = "";
			}
		}
	}
	
	private void addHistoryFromTextBox(){
		commandHistory.add(textBoxContents);
		addHistoryFromText(textBoxContents + ":", 0);
		addHistoryFromText(CommandHandler.executeCommand(textBoxContents.split(" ")), 2);
		historyIndex = -99;
	}
	
	private void addHistoryFromText(String text, int c){
		if(!text.equals("")){
			String[] splitText = text.split(String.valueOf((char)13));
			
			for(String phrase : splitText){
				String[] phraseSplit = splitToFit(phrase);
				textBoxHistoryImage.add(fontSet.assemblePhrase(phraseSplit[0], c));
				addHistoryFromText(phraseSplit[1], c);
			}
		}
	}
	
	private String[] splitToFit(String text){
		if(fontSet.assemblePhrase(text, 0).getWidth() > textBox.getWidth() + 16){
			BufferedImage phrase = fontSet.assemblePhrase(text.charAt(0) + "", 0);
			int i = 1;
			while(phrase.getWidth() < textBox.getWidth() + 8){
				phrase = fontSet.assemblePhrase(text.substring(0, i), 0);
				i ++;
			}
			
			return new String[]{text.substring(0, i),text.substring(i, text.length())};
		}
		return new String[]{text, ""};
	}
	
	public void moveComponants(){
		super.moveComponants();
		textBox = new Rectangle(x + 3 * 4, y + image.getSize()[1] - 9 * 4, image.getSize()[0] - 14 * 4, 6 * 4);
		
		close = new Button("QuitButton", x + image.getSize()[0] - 12 * 4 - SpriteLibrary.collectSpriteName("QuitButton").getSize()[0], y + 2 * 4);
		send = new Button("SendButton", x + image.getSize()[0] - 3 * 4 - SpriteLibrary.collectSpriteName("SendButton").getSize()[0],
										y + image.getSize()[1] - 3 * 4 - SpriteLibrary.collectSpriteName("SendButton").getSize()[1]);
	}

	public void draw(Graphics g){
		if(visible){
			super.draw(g);
			
			close.draw(g);
			send.draw(g);
			
			if(!textBoxContents.equals(textBoxContentsOld)){
				contentsImage = crop(fontSet.assemblePhrase(textBoxContents, 0));
				textBoxContentsOld = textBoxContents;
			}
			g.drawImage(contentsImage, x + 16, y + 208, null);
			
			if(textSelected && blinkerI < blinkerInterval)
				g.drawImage(blinkerImage.getImage(), x + contentsImage.getWidth() + 18, y + 208, null);
			
			for(int i = 1; i <= 10; i++){
				if(textBoxHistoryImage.size() > i-1){
					g.drawImage(textBoxHistoryImage.get(textBoxHistoryImage.size() - i), x + 16, y + 193 - i*16, null);
				}
				else i = 10;
			}
			
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