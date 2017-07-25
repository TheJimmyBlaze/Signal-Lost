package weapon;

import sprite.Sprite;

public class WeaponBarrel extends WeaponMod{
	
	protected double muzzleCorrection;
	public double getMuzzleCorrection(){return muzzleCorrection;}

	public WeaponBarrel(String namePass, Sprite imagePass,
			int rarityPass, int fireRatePass,
			int roundsPerShotPass, double damagePass,
			double accuracyPass, double stabilityPass,
			int magazineSizePass, double reloadSpeedPass,
			double muzzleCorrectionPass) {
		super(namePass, imagePass, rarityPass, fireRatePass,
				roundsPerShotPass, damagePass, accuracyPass, stabilityPass,
				magazineSizePass, reloadSpeedPass);
		muzzleCorrection = muzzleCorrectionPass;
	}
	
	public WeaponBarrel clone(int rarityPass){return new WeaponBarrel(name, image, rarityPass,
			fireRate, roundsPerShot, damage,
			accuracy, stability,
			magazineSize, reloadSpeed,
			muzzleCorrection);}
}
