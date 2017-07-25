package weapon;

import sprite.Sprite;

public class WeaponMod {

	protected String name;
	protected Sprite image;
	protected int rarity;
	
	protected int fireRate;
	protected int roundsPerShot;
	protected double damage;
	
	protected double accuracy;
	protected double stability;
	
	protected int magazineSize;
	protected double reloadSpeed;
	
	public Sprite getSprite(){return image;}
	
	public String getName(){return name;}
	
	public int getFireRate(){return fireRate;}
	public int getPerShot(){return roundsPerShot;}
	public double getDamage(){return damage;}
	
	public double getAccuracy(){return accuracy;}
	public double getStability(){return stability;}
	
	public int getMagazineSize(){return magazineSize;}
	public double getReloadSpeed(){return reloadSpeed;}
	
	public WeaponMod(){}
	
	public WeaponMod(String namePass, Sprite imagePass, int rarityPass,
					int fireRatePass, int roundsPerShotPass, double damagePass,
					double accuracyPass, double stabilityPass,
					int magazineSizePass, double reloadSpeedPass){
		
		double rarityScale = 1 + rarity/10;
		
		name = namePass; image = imagePass; rarity = rarityPass;
		fireRate = (int)(fireRatePass*rarityScale); roundsPerShot = (int)(roundsPerShotPass*rarityScale); damage = damagePass*rarityScale;
		accuracy = accuracyPass*rarityScale; stability = stabilityPass*rarityScale;
		magazineSize = (int)(magazineSizePass*rarityScale); reloadSpeed = reloadSpeedPass*rarityScale;
	}
	
	public WeaponMod clone(int rarityPass){return new WeaponMod(name, image, rarityPass,
			fireRate, roundsPerShot, damage,
			accuracy, stability,
			magazineSize, reloadSpeed);}
}
