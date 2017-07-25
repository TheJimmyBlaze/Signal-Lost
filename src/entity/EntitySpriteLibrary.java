package entity;

import java.awt.Point;

import sprite.AnimatedSprite;
import sprite.SpriteFrame;
import sprite.SpriteLibrary;
import sprite.SpriteLoader;

public class EntitySpriteLibrary {
	
	private static void assembleWearableImage(String name, SpriteLibrary lib, int still, int forward, int backward, int y, boolean includesArm, boolean micro){
		lib.addSprite(name, 10, new Point(still, y), micro);
		lib.addAnimSprite(new AnimatedSprite(new SpriteFrame[]{
			new SpriteFrame(new SpriteLoader(name, 0, 0, new Point(forward, y), micro).getSprite(), 2),
			new SpriteFrame(new SpriteLoader(name, 0, 0, new Point(forward, 4 +y), micro).getSprite(), 2),
			new SpriteFrame(new SpriteLoader(name, 0, 0, new Point(forward, 8 +y), micro).getSprite(), 2),
			new SpriteFrame(new SpriteLoader(name, 0, 0, new Point(forward, y), micro).getSprite(), 2),
			new SpriteFrame(new SpriteLoader(name, 0, 0, new Point(forward, y), micro).getSprite(), 2),
			new SpriteFrame(new SpriteLoader(name, 0, 0, new Point(forward, 4 +y), micro).getSprite(), 2),
			new SpriteFrame(new SpriteLoader(name, 0, 0, new Point(forward, 8 +y), micro).getSprite(), 2),
			new SpriteFrame(new SpriteLoader(name, 0, 0, new Point(forward, y), micro).getSprite(), 2)},
			name + "A", 10, SpriteLibrary.getSpriteListSize()), 10);
		lib.addAnimSprite(new AnimatedSprite(new SpriteFrame[]{
				new SpriteFrame(new SpriteLoader(name, 0, 0, new Point(backward, y), micro).getSprite().flip(), 2),
				new SpriteFrame(new SpriteLoader(name, 0, 0, new Point(backward, 4 +y), micro).getSprite().flip(), 2),
				new SpriteFrame(new SpriteLoader(name, 0, 0, new Point(backward, 8 +y), micro).getSprite().flip(), 2),
				new SpriteFrame(new SpriteLoader(name, 0, 0, new Point(backward, y), micro).getSprite().flip(), 2),
				new SpriteFrame(new SpriteLoader(name, 0, 0, new Point(backward, y), micro).getSprite().flip(), 2),
				new SpriteFrame(new SpriteLoader(name, 0, 0, new Point(backward, 4 +y), micro).getSprite().flip(), 2),
				new SpriteFrame(new SpriteLoader(name, 0, 0, new Point(backward, 8 +y), micro).getSprite().flip(), 2),
				new SpriteFrame(new SpriteLoader(name, 0, 0, new Point(backward, y), micro).getSprite().flip(), 2)},
				name + "AF", 10, SpriteLibrary.getSpriteListSize()), 10);
		lib.addAnimSprite(new AnimatedSprite(new SpriteFrame[]{
				new SpriteFrame(new SpriteLoader(name, 0, 0, new Point(forward, y), micro).getSprite(), 2),
				new SpriteFrame(new SpriteLoader(name, 0, 0, new Point(forward, 8 +y), micro).getSprite(), 2),
				new SpriteFrame(new SpriteLoader(name, 0, 0, new Point(forward, 4 +y), micro).getSprite(), 2),
				new SpriteFrame(new SpriteLoader(name, 0, 0, new Point(forward, y), micro).getSprite(), 2),
				new SpriteFrame(new SpriteLoader(name, 0, 0, new Point(forward, y), micro).getSprite(), 2),
				new SpriteFrame(new SpriteLoader(name, 0, 0, new Point(forward, 8 +y), micro).getSprite(), 2),
				new SpriteFrame(new SpriteLoader(name, 0, 0, new Point(forward, 4 +y), micro).getSprite(), 2),
				new SpriteFrame(new SpriteLoader(name, 0, 0, new Point(forward, y), micro).getSprite(), 2)},
				name + "RA", 10, SpriteLibrary.getSpriteListSize()), 10);
		lib.addAnimSprite(new AnimatedSprite(new SpriteFrame[]{
				new SpriteFrame(new SpriteLoader(name, 0, 0, new Point(backward, y), micro).getSprite().flip(), 2),
				new SpriteFrame(new SpriteLoader(name, 0, 0, new Point(backward, 8 +y), micro).getSprite().flip(), 2),
				new SpriteFrame(new SpriteLoader(name, 0, 0, new Point(backward, 4 +y), micro).getSprite().flip(), 2),
				new SpriteFrame(new SpriteLoader(name, 0, 0, new Point(backward, y), micro).getSprite().flip(), 2),
				new SpriteFrame(new SpriteLoader(name, 0, 0, new Point(backward, y), micro).getSprite().flip(), 2),
				new SpriteFrame(new SpriteLoader(name, 0, 0, new Point(backward, 8 +y), micro).getSprite().flip(), 2),
				new SpriteFrame(new SpriteLoader(name, 0, 0, new Point(backward, 4 +y), micro).getSprite().flip(), 2),
				new SpriteFrame(new SpriteLoader(name, 0, 0, new Point(backward, y), micro).getSprite().flip(), 2)},
				 name + "RAF", 10, SpriteLibrary.getSpriteListSize()), 10);
		if(includesArm)
			lib.addSprite(name + "Arm", 3, new Point(0, y), micro);
	}
	
	private static void assembleAnimatedWearableImage(String name, SpriteLibrary lib){
		lib.addSprite(name + "", 10, new Point(8, 0));
		lib.addSprite(name + "J", 10, new Point(16, 0));
		lib.addAnimSprite(new AnimatedSprite(new SpriteFrame[]{
			new SpriteFrame(new SpriteLoader(name + "W1", 0, 0).getSprite(), 2),
			new SpriteFrame(new SpriteLoader(name + "W2", 0, 0, new Point(0, 4)).getSprite(), 2),
			new SpriteFrame(new SpriteLoader(name + "W3", 0, 0, new Point(4, 8)).getSprite(), 2),
			new SpriteFrame(new SpriteLoader(name + "W4", 0, 0, new Point(16, 0)).getSprite(), 2),
			new SpriteFrame(new SpriteLoader(name + "W5", 0, 0, new Point(16, 0)).getSprite(), 2),
			new SpriteFrame(new SpriteLoader(name + "W6", 0, 0, new Point(16, 4)).getSprite(), 2),
			new SpriteFrame(new SpriteLoader(name + "W7", 0, 0, new Point(12, 8)).getSprite(), 2),
			new SpriteFrame(new SpriteLoader(name + "W8", 0, 0, new Point(16, 0)).getSprite(), 2)},
			name + "A", 10, SpriteLibrary.getSpriteListSize()), 10);
		lib.addAnimSprite(new AnimatedSprite(new SpriteFrame[]{
				new SpriteFrame(new SpriteLoader(name + "W1", 0, 0, new Point(12, 0)).getSprite().flip(), 2),
				new SpriteFrame(new SpriteLoader(name + "W2", 0, 0, new Point(4, 4)).getSprite().flip(), 2),
				new SpriteFrame(new SpriteLoader(name + "W3", 0, 0, new Point(4, 8)).getSprite().flip(), 2),
				new SpriteFrame(new SpriteLoader(name + "W4", 0, 0, new Point(12, 0)).getSprite().flip(), 2),
				new SpriteFrame(new SpriteLoader(name + "W5", 0, 0, new Point(12, 0)).getSprite().flip(), 2),
				new SpriteFrame(new SpriteLoader(name + "W6", 0, 0, new Point(12, 4)).getSprite().flip(), 2),
				new SpriteFrame(new SpriteLoader(name + "W7", 0, 0, new Point(12, 8)).getSprite().flip(), 2),
				new SpriteFrame(new SpriteLoader(name + "W8", 0, 0, new Point(12, 0)).getSprite().flip(), 2)},
				name + "AF", 10, SpriteLibrary.getSpriteListSize()), 10);
		lib.addAnimSprite(new AnimatedSprite(new SpriteFrame[]{
				new SpriteFrame(new SpriteLoader(name + "W8", 0, 0, new Point(16, 0)).getSprite(), 2),
				new SpriteFrame(new SpriteLoader(name + "W7", 0, 0, new Point(12, 8)).getSprite(), 2),
				new SpriteFrame(new SpriteLoader(name + "W6", 0, 0, new Point(16, 4)).getSprite(), 2),
				new SpriteFrame(new SpriteLoader(name + "W5", 0, 0, new Point(16, 0)).getSprite(), 2),
				new SpriteFrame(new SpriteLoader(name + "W4", 0, 0, new Point(16, 0)).getSprite(), 2),
				new SpriteFrame(new SpriteLoader(name + "W3", 0, 0, new Point(4, 8)).getSprite(), 2),
				new SpriteFrame(new SpriteLoader(name + "W2", 0, 0, new Point(0, 4)).getSprite(), 2),
				new SpriteFrame(new SpriteLoader(name + "W1", 0, 0).getSprite(), 2)},
				name + "RA", 10, SpriteLibrary.getSpriteListSize()), 10);
		lib.addAnimSprite(new AnimatedSprite(new SpriteFrame[]{
				new SpriteFrame(new SpriteLoader(name + "W8", 0, 0, new Point(12, 0)).getSprite().flip(), 2),
				new SpriteFrame(new SpriteLoader(name + "W7", 0, 0, new Point(12, 8)).getSprite().flip(), 2),
				new SpriteFrame(new SpriteLoader(name + "W6", 0, 0, new Point(12, 4)).getSprite().flip(), 2),
				new SpriteFrame(new SpriteLoader(name + "W5", 0, 0, new Point(12, 0)).getSprite().flip(), 2),
				new SpriteFrame(new SpriteLoader(name + "W4", 0, 0, new Point(12, 0)).getSprite().flip(), 2),
				new SpriteFrame(new SpriteLoader(name + "W3", 0, 0, new Point(4, 8)).getSprite().flip(), 2),
				new SpriteFrame(new SpriteLoader(name + "W2", 0, 0, new Point(4, 4)).getSprite().flip(), 2),
				new SpriteFrame(new SpriteLoader(name + "W1", 0, 0, new Point(12, 0)).getSprite().flip(), 2)},
				name + "RAF", 10, SpriteLibrary.getSpriteListSize()), 10);
	}
	
	public static void buildChars(SpriteLibrary lib){
		lib.addSprite("character/Template", 3, new Point(8, 0));
		lib.addSprite("character/TemplateJ", 3, new Point(16, 0));
		lib.addAnimSprite(new AnimatedSprite(new SpriteFrame[]{
			new SpriteFrame(new SpriteLoader("character/TemplateW1", 0, 0).getSprite(), 2),
			new SpriteFrame(new SpriteLoader("character/TemplateW2", 0, 0, new Point(0, 4)).getSprite(), 2),
			new SpriteFrame(new SpriteLoader("character/TemplateW3", 0, 0, new Point(8, 8)).getSprite(), 2),
			new SpriteFrame(new SpriteLoader("character/TemplateW4", 0, 0, new Point(20, 0)).getSprite(), 2),
			new SpriteFrame(new SpriteLoader("character/TemplateW5", 0, 0, new Point(20, 0)).getSprite(), 2),
			new SpriteFrame(new SpriteLoader("character/TemplateW6", 0, 0, new Point(20, 4)).getSprite(), 2),
			new SpriteFrame(new SpriteLoader("character/TemplateW7", 0, 0, new Point(16, 8)).getSprite(), 2),
			new SpriteFrame(new SpriteLoader("character/TemplateW8", 0, 0, new Point(20, 0)).getSprite(), 2)},
			"null/TemplateA", 3, SpriteLibrary.getSpriteListSize()), 3);
		lib.addAnimSprite(new AnimatedSprite(new SpriteFrame[]{
				new SpriteFrame(new SpriteLoader("character/TemplateW1", 0, 0, new Point(12, 0)).getSprite().flip(), 2),
				new SpriteFrame(new SpriteLoader("character/TemplateW2", 0, 0, new Point(4, 4)).getSprite().flip(), 2),
				new SpriteFrame(new SpriteLoader("character/TemplateW3", 0, 0, new Point(8, 8)).getSprite().flip(), 2),
				new SpriteFrame(new SpriteLoader("character/TemplateW4", 0, 0, new Point(12, 0)).getSprite().flip(), 2),
				new SpriteFrame(new SpriteLoader("character/TemplateW5", 0, 0, new Point(12, 0)).getSprite().flip(), 2),
				new SpriteFrame(new SpriteLoader("character/TemplateW6", 0, 0, new Point(12, 4)).getSprite().flip(), 2),
				new SpriteFrame(new SpriteLoader("character/TemplateW7", 0, 0, new Point(12, 8)).getSprite().flip(), 2),
				new SpriteFrame(new SpriteLoader("character/TemplateW8", 0, 0, new Point(12, 0)).getSprite().flip(), 2)},
				"null/TemplateAF", 3, SpriteLibrary.getSpriteListSize()), 3);
		lib.addAnimSprite(new AnimatedSprite(new SpriteFrame[]{
				new SpriteFrame(new SpriteLoader("character/TemplateW8", 0, 0, new Point(20, 0)).getSprite(), 2),
				new SpriteFrame(new SpriteLoader("character/TemplateW7", 0, 0, new Point(16, 8)).getSprite(), 2),
				new SpriteFrame(new SpriteLoader("character/TemplateW6", 0, 0, new Point(20, 4)).getSprite(), 2),
				new SpriteFrame(new SpriteLoader("character/TemplateW5", 0, 0, new Point(20, 0)).getSprite(), 2),
				new SpriteFrame(new SpriteLoader("character/TemplateW4", 0, 0, new Point(20, 0)).getSprite(), 2),
				new SpriteFrame(new SpriteLoader("character/TemplateW3", 0, 0, new Point(8, 8)).getSprite(), 2),
				new SpriteFrame(new SpriteLoader("character/TemplateW2", 0, 0, new Point(0, 4)).getSprite(), 2),
				new SpriteFrame(new SpriteLoader("character/TemplateW1", 0, 0).getSprite(), 2)},
				"null/TemplateRA", 3, SpriteLibrary.getSpriteListSize()), 3);
		lib.addAnimSprite(new AnimatedSprite(new SpriteFrame[]{
				new SpriteFrame(new SpriteLoader("character/TemplateW8", 0, 0, new Point(12, 0)).getSprite().flip(), 2),
				new SpriteFrame(new SpriteLoader("character/TemplateW7", 0, 0, new Point(12, 8)).getSprite().flip(), 2),
				new SpriteFrame(new SpriteLoader("character/TemplateW6", 0, 0, new Point(12, 4)).getSprite().flip(), 2),
				new SpriteFrame(new SpriteLoader("character/TemplateW5", 0, 0, new Point(12, 0)).getSprite().flip(), 2),
				new SpriteFrame(new SpriteLoader("character/TemplateW4", 0, 0, new Point(12, 0)).getSprite().flip(), 2),
				new SpriteFrame(new SpriteLoader("character/TemplateW3", 0, 0, new Point(8, 8)).getSprite().flip(), 2),
				new SpriteFrame(new SpriteLoader("character/TemplateW2", 0, 0, new Point(4, 4)).getSprite().flip(), 2),
				new SpriteFrame(new SpriteLoader("character/TemplateW1", 0, 0, new Point(12, 0)).getSprite().flip(), 2)},
				"null/TemplateRAF", 3, SpriteLibrary.getSpriteListSize()), 3);
		lib.addSprite("character/TemplateArm", 3, new Point(0, 0));
		
		assembleWearableImage("hair/Quiff", lib, 80, 80, 64, 48, false, false);
		assembleWearableImage("hair/Slick", lib, 80, 80, 72, 60, false, false);
		
		assembleWearableImage("eyes/BlueEyes", lib, 40, 40, 12, 0, false, true);
		assembleWearableImage("eyes/GreenEyes", lib, 40, 40, 12, 0, false, true);
		assembleWearableImage("eyes/BrownEyes", lib, 40, 40, 12, 0, false, true);
	}
	
	public static void buildArmour(SpriteLibrary lib){
		
		assembleWearableImage("armour/BanditMask1", lib, 84, 84, 72, 76, false, false);
		assembleWearableImage("armour/HeadSet", lib, 88, 88, 92, 94, false, false);
		assembleWearableImage("armour/WarLordCrown", lib, 80, 80, 72, 64, false, false);
		
		assembleWearableImage("armour/PolymerChest", lib, 20, 16, 24, 0, true, false);
		assembleWearableImage("armour/WarLordChest", lib, 20, 16, 24, 0, true, false);
		assembleWearableImage("armour/BanditChest1", lib, 20, 16, 24, 0, true, false);
		
		assembleAnimatedWearableImage("armour/PolymerLeg", lib);
		assembleAnimatedWearableImage("armour/WarLordLeg", lib);
		assembleAnimatedWearableImage("armour/BanditLeg1", lib);
	}

}
