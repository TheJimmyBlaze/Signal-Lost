package sprite;

import java.awt.Point;
import java.util.ArrayList;

import entity.EntitySpriteLibrary;

public class SpriteLibrary {
	
	private static ArrayList<Sprite> spriteList = new ArrayList<Sprite>();
	
	public static int getSpriteCount(){return spriteList.size();}
	
	private static ArrayList<SpriteShelf> spriteShelves = new ArrayList<SpriteShelf>();
	
	public static int getSpriteListSize(){return spriteList.size();}
	
	public static SpriteShelf collectShelf(String name){
		for(int i = 0; i < spriteShelves.size(); i++){
			if(spriteShelves.get(i).getName().equals(name)){
				return spriteShelves.get(i);
			}
		}
		return null;
	}
	
	public static Sprite collectSpriteUID(int shelfIndex, int uid){
		for(int i = 0; i < spriteList.size(); i++){
			if(spriteList.get(i).getShelfIndex() == shelfIndex && spriteList.get(i).getUID() == uid){
				return spriteList.get(i);
			}
		}
		
		System.out.println("Unspecified Sprite Name Error: " + uid);
		return null;
	}
	
	public static Sprite collectSpriteName(String name){
		for(int i = 0; i < spriteList.size(); i++){
			if(spriteList.get(i).getName().equals(name)){
				return spriteList.get(i);
			}
		}
		
		System.out.println("Unspecified Sprite Name Error: " + name);
		return null;
	}
	
	public SpriteLibrary(){
		spriteShelves.add(new SpriteShelf("ruins", 0));
		spriteShelves.add(new SpriteShelf("wood", 1));
		spriteShelves.add(new SpriteShelf("decoration", 2));
		spriteShelves.add(new SpriteShelf("character", 3));
		spriteShelves.add(new SpriteShelf("store", 4));
		spriteShelves.add(new SpriteShelf("weapon", 5));
		spriteShelves.add(new SpriteShelf("plant", 6));
		spriteShelves.add(new SpriteShelf("ground", 7));
		spriteShelves.add(new SpriteShelf("building", 8));
		spriteShelves.add(new SpriteShelf("moss", 9));
		spriteShelves.add(new SpriteShelf("armour", 10));
		spriteShelves.add(new SpriteShelf("weaponMods", 11));
		buildLibrary();
	}
	
	private void buildLibrary(){
		buildRuins();
		buildWood();
		buildDecs();
		buildStore();
		buildWeapon();
		buildPlants();
		buildGround();
		buildBuilding();
		buildMoss();
		buildUIElements();
		buildWeaponMods();
		
		EntitySpriteLibrary.buildChars(this);
		EntitySpriteLibrary.buildArmour(this);
	}
	
	public void addSprite(String name, int shelfIndex){
		spriteList.add(new SpriteLoader(name, shelfIndex ,spriteShelves.get(shelfIndex).getSpriteCount()).getSprite());
		spriteShelves.get(shelfIndex).add(new SpriteLoader(name, shelfIndex, spriteShelves.get(shelfIndex).getSpriteCount()).getSprite(),
				spriteShelves.get(shelfIndex).getSpriteCount());
	}
	public void addSprite(String name, int shelfIndex, Point offset){
		spriteList.add(new SpriteLoader(name, shelfIndex, spriteShelves.get(shelfIndex).getSpriteCount(), offset).getSprite());
		spriteShelves.get(shelfIndex).add(new SpriteLoader(name, shelfIndex, spriteShelves.get(shelfIndex).getSpriteCount()).getSprite(), 
				spriteShelves.get(shelfIndex).getSpriteCount());
	}
	public void addSprite(String name, int shelfIndex, boolean micro){
		spriteList.add(new SpriteLoader(name, shelfIndex, spriteShelves.get(shelfIndex).getSpriteCount(), micro).getSprite());
		spriteShelves.get(shelfIndex).add(new SpriteLoader(name, shelfIndex, spriteShelves.get(shelfIndex).getSpriteCount(), 
				micro).getSprite(), spriteShelves.get(shelfIndex).getSpriteCount());
	}
	public void addSprite(String name, int shelfIndex, Point offset, boolean micro){
		spriteList.add(new SpriteLoader(name, shelfIndex, spriteShelves.get(shelfIndex).getSpriteCount(), offset, micro).getSprite());
		spriteShelves.get(shelfIndex).add(new SpriteLoader(name, shelfIndex, spriteShelves.get(shelfIndex).getSpriteCount(), micro).getSprite(), 
				spriteShelves.get(shelfIndex).getSpriteCount());
	}
	
	public void addAnimSprite(Sprite sprite, int shelfIndex){
		spriteList.add(sprite);
		spriteShelves.get(shelfIndex).add(sprite, spriteShelves.get(shelfIndex).getSpriteCount());
	}
	
	private void buildRuins(){
		addSprite("ruins/Stone-Block", 0);
		addSprite("ruins/Stone-BlockFancy", 0);
		addSprite("ruins/Stone-BlockBricks", 0);
		addSprite("ruins/Stone-BlockPillar", 0);
		addSprite("ruins/Stone-BlockUpArrow", 0);
		addSprite("ruins/Stone-BlockDownArrow", 0);
		
		addSprite("ruins/Stone-Slab", 0);
		addSprite("ruins/Stone-SlabFancy", 0);
		addSprite("ruins/Stone-SlabBricks", 0);
		addSprite("ruins/Stone-SlabPillar", 0);
		
		addSprite("ruins/Stone-Split", 0);
		addSprite("ruins/Stone-SplitFancy", 0);
		
		addSprite("ruins/Stone-Piece", 0);
		addSprite("ruins/Stone-PieceFancy", 0);
		
		addSprite("ruins/Stone-Slice", 0);
		addSprite("ruins/Stone-SliceBricks", 0);
		
		addSprite("ruins/Stone-LargeBlock", 0);
	}
	
	private void buildWood(){
		addSprite("wood/Wood-Planks", 1);
	}
	
	private void buildDecs(){
		addSprite("decoration/RedBanner", 2);
		addSprite("decoration/Crate", 2);
		addSprite("decoration/Barrier", 2);
		addAnimSprite(new AnimatedSprite(new SpriteFrame[]{
				new SpriteFrame(new SpriteLoader("decoration/Ribbon1", 0, 0).getSprite(), 2),
				new SpriteFrame(new SpriteLoader("decoration/Ribbon2", 0, 0).getSprite(), 3),
				new SpriteFrame(new SpriteLoader("decoration/Ribbon3", 0, 0).getSprite(), 2),
				new SpriteFrame(new SpriteLoader("decoration/Ribbon4", 0, 0).getSprite(), 3)},
				"null/Ribbon", 2, 3), 2);
	}
	
	private void buildStore(){
		addSprite("store/LocBoard", 4);
	}
	
	private void buildWeapon(){
		addSprite("weapons/StandardFlash", 5, true);
		addSprite("weapons/LaserFlash", 5, true);
		addSprite("weapons/9mmShellCasing", 5, true);
		addSprite("weapons/556ShellCasing", 5, true);
		addSprite("weapons/762ShellCasing", 5, true);
		addSprite("weapons/LaserCasing", 5, true);
		addSprite("weapons/Beretta", 5, true);
		addSprite("weapons/BerettaS", 5, true);
		addSprite("weapons/M37", 5, true);
		addSprite("weapons/Mac", 5, true);
		addSprite("weapons/AK47", 5, true);
		addSprite("weapons/GoldenAK", 5, true);
		addSprite("weapons/JungleM37", 5, true);
		addSprite("weapons/Flatline", 5, true);
		addSprite("weapons/BeamAR", 5, true);
	}
	
	private void buildWeaponMods(){
		addSprite("weaponMods/BarMag", 11, true);
		addSprite("weaponMods/KarMag", 11, true);
		addSprite("weaponMods/SlipMag", 11, true);
		addSprite("weaponMods/ClipMag", 11, true);
		addSprite("weaponMods/RipMag", 11, true);
		addSprite("weaponMods/TzarMag", 11, true);
		addSprite("weaponMods/StrilykIronSight", 11, true);
		addSprite("weaponMods/StrilykBarrel", 11, true);
		addSprite("weaponMods/StrilykStubBarrel", 11, true);
		addSprite("weaponMods/StrilykPrecisionBarrel", 11, true);
		addSprite("weaponMods/StrilykBearReceiver", 11, true);
		addSprite("weaponMods/StrilykStagReceiver", 11, true);
		addSprite("weaponMods/StrilykWolfReceiver", 11, true);
		addSprite("weaponMods/StrilykBarrelCap", 11, true);
		addSprite("weaponMods/MaerSuppressor", 11, true);
		addSprite("weaponMods/SpetSuppressor", 11, true);
		addSprite("weaponMods/MuzzleBreak", 11, true);
		addSprite("weaponMods/PortedCompensator", 11, true);
		addSprite("weaponMods/WoodenGrip", 11, true);
		addSprite("weaponMods/WoodenErgoGrip", 11, true);
		addSprite("weaponMods/WoodenComfortGrip", 11, true);
		addSprite("weaponMods/WoodenStock", 11, true);
		addSprite("weaponMods/LightWoodenStock", 11, true);
		addSprite("weaponMods/WoodenMarksmanStock", 11, true);
	}
	
	private void buildPlants(){
		addSprite("plants/FernTree", 6);
		addSprite("plants/TallGrass", 6);
		addSprite("plants/PointedBush", 6);
		addSprite("plants/RedFlower", 6);
		
		addSprite("plants/SmallVine", 6);
		addSprite("plants/Bambo", 6);
	}
	
	private void buildGround(){
		addSprite("ground/Dirt", 7);
		addSprite("ground/DirtTop", 7);
		addSprite("ground/DirtBottom", 7);
		addSprite("ground/DirtLeft", 7);
		addSprite("ground/DirtRight", 7);
		addSprite("ground/DirtTopLeft", 7);
		addSprite("ground/DirtTopRight", 7);
		addSprite("ground/DirtBottomLeft", 7);
		addSprite("ground/DirtBottomRight", 7);
		
		addSprite("ground/Grass", 7);
		addSprite("ground/GrassLeft", 7);
		addSprite("ground/GrassRight", 7);
		addSprite("ground/GrassTop", 7);
		addSprite("ground/GrassTopLeft", 7);
		addSprite("ground/GrassTopRight", 7);
		addSprite("ground/GrassFlowers", 7);
		
		addSprite("ground/LargeRock", 7);
		addSprite("ground/MediumRock", 7);
		addSprite("ground/SmallRock", 7);
	}

	private void buildBuilding(){
		addSprite("building/IronOne", 8);
		addSprite("building/IronTwo", 8);
		addSprite("building/WoodBoardOne", 8);
		addSprite("building/WoodBoardTwo", 8);
		addSprite("building/ElectricBoxOne", 8);
		addSprite("building/MetalWindow", 8);
		addSprite("building/Door", 8);
		addSprite("building/IronPitchLeft", 8);
		addSprite("building/IronPitchRight", 8);
		addSprite("building/IronPitch", 8);
		addSprite("building/WoodPitchLeft", 8);
		addSprite("building/WoodPitchRight", 8);
		addSprite("building/WoodPitch", 8);
		addSprite("building/WoodRoofOne", 8);
		addSprite("building/WoodRoofTwo", 8);
		addSprite("building/RoofTilesLeft", 8);
		addSprite("building/RoofTiles", 8);
		addSprite("building/RoofTilesRight", 8);
		addSprite("building/RoofPipe", 8);
	}
	
	private void buildMoss(){
		addSprite("moss/MossBottom", 9);
		addSprite("moss/MossVerticle1", 9);
		addSprite("moss/MossVerticle2", 9);
		addSprite("moss/MossTop", 9);
		addSprite("moss/MossCross1", 9);
		addSprite("moss/MossCross2", 9);
		addSprite("moss/MossCross3", 9);
		addSprite("moss/MossCross4", 9);
		addSprite("moss/MossPatch1", 9);
		addSprite("moss/MossPatch2", 9);
	}
	
	private void buildUIElements(){
		spriteList.add(new SpriteLoader("ui/ConsoleWindow", 0, 0).getSprite());
		spriteList.add(new SpriteLoader("ui/QuitButton", 0, 0).getSprite());
		spriteList.add(new SpriteLoader("ui/QuitButtonInv", 0, 0).getSprite());
		spriteList.add(new SpriteLoader("ui/SendButton", 0, 0).getSprite());
		spriteList.add(new SpriteLoader("ui/SendButtonInv", 0, 0).getSprite());
		
		spriteList.add(new SpriteLoader("ui/SelectorWindow", 0, 0).getSprite());
		spriteList.add(new SpriteLoader("ui/SelectorRightButton", 0, 0).getSprite());
		spriteList.add(new SpriteLoader("ui/SelectorRightButtonInv", 0, 0).getSprite());
		spriteList.add(new SpriteLoader("ui/SelectorLeftButton", 0, 0).getSprite());
		spriteList.add(new SpriteLoader("ui/SelectorLeftButtonInv", 0, 0).getSprite());
		spriteList.add(new SpriteLoader("ui/SelectorBlocking", 0, 0).getSprite());
		spriteList.add(new SpriteLoader("ui/SelectorBlockingInv", 0, 0).getSprite());
		spriteList.add(new SpriteLoader("ui/SelectorSelected", 0, 0).getSprite());
		
		spriteList.add(new SpriteLoader("ui/Blinker", 0, 0).getSprite());
	}
}
