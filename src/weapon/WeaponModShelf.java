package weapon;

import java.util.ArrayList;

public class WeaponModShelf {
	
	protected String name;
	
	protected ArrayList<WeaponReceiver> receivers = new ArrayList<WeaponReceiver>();
	protected ArrayList<WeaponBarrel> barrels = new ArrayList<WeaponBarrel>();
	protected ArrayList<WeaponMod> stocks = new ArrayList<WeaponMod>();
	protected ArrayList<WeaponMod> grips = new ArrayList<WeaponMod>();
	protected ArrayList<WeaponMod> mags = new ArrayList<WeaponMod>();
	protected ArrayList<WeaponMod> sights = new ArrayList<WeaponMod>();
	protected ArrayList<WeaponMod> extensions = new ArrayList<WeaponMod>();
	
	public WeaponModShelf(String namePass){name = namePass;}
	
	public WeaponReceiver collectReceiver(int rarity){
		return receivers.get((int)(Math.random()*receivers.size())).clone(rarity);
	}
	public WeaponBarrel collectBarrel(int rarity){
		return barrels.get((int)(Math.random()*barrels.size())).clone(rarity);
	}
	public WeaponMod collectStock(int rarity){
		return stocks.get((int)(Math.random()*stocks.size())).clone(rarity);
	}
	public WeaponMod collectGrip(int rarity){
		return grips.get((int)(Math.random()*grips.size())).clone(rarity);
	}
	public WeaponMod collectMag(int rarity){
		return mags.get((int)(Math.random()*mags.size())).clone(rarity);
	}
	public WeaponMod collectSight(int rarity){
		return sights.get((int)(Math.random()*sights.size())).clone(rarity);
	}
	public WeaponMod collectExtension(int rarity){
		return extensions.get((int)(Math.random()*extensions.size())).clone(rarity);
	}
	
	public void addReceiver(WeaponReceiver mod){receivers.add(mod);}
	public void addBarrel(WeaponBarrel mod){barrels.add(mod);}
	public void addStock(WeaponMod mod){stocks.add(mod);}
	public void addGrip(WeaponMod mod){grips.add(mod);}
	public void addMag(WeaponMod mod){mags.add(mod);}
	public void addSight(WeaponMod mod){sights.add(mod);}
	public void addExtension(WeaponMod mod){extensions.add(mod);}
}