package weapon;

import java.awt.Point;

import sprite.Sprite;

public class WeaponReceiver extends WeaponMod{
	
	protected Point stockOffset;
	protected Point sightOffset;
	protected int ejectionOffset;
	
	protected boolean automatic;
	protected Bullet bulletType;
	protected String casingName;
	protected String flashName;
	
	public Point getStockOffset(){return stockOffset;}
	public Point getSightOffset(){return sightOffset;}
	public int getEjectionOffset(){return ejectionOffset;}
	
	public boolean getAutomatic(){return automatic;}
	public Bullet getBulletType(){return bulletType;}
	public String getCasingName(){return casingName;}
	public String getFlashName(){return flashName;}

	public WeaponReceiver(String namePass, Sprite imagePass,
			Bullet bulletPass, String casingNamePass, String flashNamePass,
			int rarityPass, boolean automaticPass, int fireRatePass,
			int roundsPerShotPass, double damagePass,
			double accuracyPass, double stabilityPass,
			int magazineSizePass, double reloadSpeedPass,
			Point stockOffsetPass, Point sightOffsetPass, int ejectionOffsetPass) {
		super(namePass, imagePass, rarityPass, fireRatePass,
				roundsPerShotPass, damagePass, accuracyPass, stabilityPass,
				magazineSizePass, reloadSpeedPass);
		
		stockOffset = stockOffsetPass;
		sightOffset = sightOffsetPass;
		ejectionOffset = ejectionOffsetPass;
		
		automatic = automaticPass;
		bulletType = bulletPass;
		casingName = casingNamePass;
		flashName = flashNamePass;
	}
	
	public WeaponReceiver clone(int rarityPass){return new WeaponReceiver(name, image, 
			bulletType, casingName, flashName, rarityPass,
			automatic, fireRate, roundsPerShot, damage,
			accuracy, stability,
			magazineSize, reloadSpeed,
			stockOffset, sightOffset, ejectionOffset);}
}
