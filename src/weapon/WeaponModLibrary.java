package weapon;

import java.awt.Point;

import sprite.SpriteLibrary;

public class WeaponModLibrary {

	public static WeaponModShelf rifle = new WeaponModShelf("Rifle");
	
	public WeaponModLibrary(){
		buildRifleMods();
	}
	
	//Weapon characteristics: (int)fireRate(10-x), (int)roundsPerShot, damage, accuracy(60-x), stability(1-x), (int)magazineSize, reloadSpeed(4-x)
	private void buildRifleMods(){
		rifle.addReceiver(new WeaponReceiver("StrilykBearReceiver", SpriteLibrary.collectSpriteName("StrilykBearReceiver"), BulletLibrary.fiveFive, "556ShellCasing", "StandardFlash", 0,
											true, 15, 1, 50, 25, 12, 0, 0.5,
											new Point(0, 12), new Point(68, 0), 45));
		rifle.addReceiver(new WeaponReceiver("StrilykStagReceiver", SpriteLibrary.collectSpriteName("StrilykStagReceiver"), BulletLibrary.sevenSix, "762ShellCasing", "StandardFlash", 0,
											false, 2, 1, 100, 50, 6, 0, 0,
											new Point(0, 12), new Point(68, 0), 64));
		rifle.addReceiver(new WeaponReceiver("StrilykWolfReceiver", SpriteLibrary.collectSpriteName("StrilykWolfReceiver"), BulletLibrary.nine, "9mmShellCasing", "StandardFlash", 0,
											true, 16, 1, 30, 0, 18, 0, 1,
											new Point(0, 12), new Point(56, 0), 34));
		
		rifle.addBarrel(new WeaponBarrel("StrilykBarrel", SpriteLibrary.collectSpriteName("StrilykBarrel"), 0,
											1, 0, 8, 8, 8, 0, 0.2,
											0.09));
		rifle.addBarrel(new WeaponBarrel("StrilykStubBarrel", SpriteLibrary.collectSpriteName("StrilykStubBarrel"), 0,
											2, 0, 12, 2, 16, 0, 0.4,
											0.09));
		rifle.addBarrel(new WeaponBarrel("StrilykPrecisionBarrel", SpriteLibrary.collectSpriteName("StrilykPrecisionBarrel"), 0,
											0, 0, 16, 16, 4, 0, 0,
											0.09));
		
		rifle.addStock(new WeaponMod("WoodenStock", SpriteLibrary.collectSpriteName("WoodenStock"), 0,
											0, 0, 0, 8, 4, 0, 0));
		rifle.addStock(new WeaponMod("LightWoodenStock", SpriteLibrary.collectSpriteName("LightWoodenStock"), 0,
											0, 0, 0, 4, 8, 0, 0));
		rifle.addStock(new WeaponMod("WoodenMarksmanStock", SpriteLibrary.collectSpriteName("WoodenMarksmanStock"), 0,
											0, 0, 0, 12, 2, 0, 0));
		
		rifle.addGrip(new WeaponMod("WoodenGrip", SpriteLibrary.collectSpriteName("WoodenGrip"), 0,
											0, 0, 0, 4, 4, 0, 0));
		rifle.addGrip(new WeaponMod("WoodenErgoGrip", SpriteLibrary.collectSpriteName("WoodenErgoGrip"), 0,
											0, 0, 0, 8, 2, 0, 0));
		rifle.addGrip(new WeaponMod("WoodenComfortGrip", SpriteLibrary.collectSpriteName("WoodenComfortGrip"), 0,
											0, 0, 0, 0, 8, 0, 0));

		rifle.addMag(new WeaponMod("BarMag", SpriteLibrary.collectSpriteName("BarMag"), 0,
											0, 0, 0, 4, 8, 16, 2));
		rifle.addMag(new WeaponMod("SlipMag", SpriteLibrary.collectSpriteName("SlipMag"), 0,
											0, 0, 0, 2, 6, 18, 1.8));
		rifle.addMag(new WeaponMod("TzarMag", SpriteLibrary.collectSpriteName("TzarMag"), 0,
											0, 0, 0, 0, 12, 24, 1.25));
		rifle.addMag(new WeaponMod("ClipMag", SpriteLibrary.collectSpriteName("ClipMag"), 0,
											0, 0, 0, 0, 8, 28, 1));
		rifle.addMag(new WeaponMod("KarMag", SpriteLibrary.collectSpriteName("KarMag"), 0,
											0, 0, 0, 8, 0, 6, 0.6));
		rifle.addMag(new WeaponMod("RipMag", SpriteLibrary.collectSpriteName("RipMag"), 0,
											0, 0, 0, 6, 0, 8, 0.4));
		
		rifle.addSight(new WeaponMod("StrilykIronSight", SpriteLibrary.collectSpriteName("StrilykIronSight"), 0,
											0, 0, 0, 0, 0, 0, 0));

		rifle.addExtension(new WeaponMod("StrilykBarrelCap", SpriteLibrary.collectSpriteName("StrilykBarrelCap"), 0,
											0, 0, 8, 0, 8, 0, 0.3));
		rifle.addExtension(new WeaponMod("MaerSuppressor", SpriteLibrary.collectSpriteName("MaerSuppressor"), 0,
											0, 0, 0, 6, 2, 0, 0));
		rifle.addExtension(new WeaponMod("SpetSuppressor", SpriteLibrary.collectSpriteName("SpetSuppressor"), 0,
											0, 0, 0, 4, 4, 0, 0));
		rifle.addExtension(new WeaponMod("PortedCompensator", SpriteLibrary.collectSpriteName("PortedCompensator"), 0,
											0, 0, 8, 2, 2, 0, 0.1));
		rifle.addExtension(new WeaponMod("MuzzleBreak", SpriteLibrary.collectSpriteName("MuzzleBreak"), 0,
											0, 0, 12, 4, 0, 0, 0.1));
	}
}
