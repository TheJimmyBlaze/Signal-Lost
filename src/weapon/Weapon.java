package weapon;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;

import particleEffects.Particle;
import main.Global;
import main.Main;
import sprite.Sprite;
import sprite.SpriteLibrary;

public class Weapon {
	
	protected String name;
	protected Boolean playerOwned = false;
	
	protected Sprite right;
	protected Sprite left;
	
	protected Sprite flashRight;
	protected Sprite flashLeft;
	protected Point flashPoint = null;
	
	protected Point offset;
	protected double muzzleCorrection;
	protected int ejectionOffset;

	protected boolean automatic;
	protected int fireRate;
	protected int roundsPerShot;
	protected double damage;
	protected double pierce;

	protected double accuracy;
	protected double stability;
	protected final double CONE_OF_FIRE_BASE = 0.15;
	protected double coneOfFire;
	protected double armatureAngle;
	protected Point muzzle;
	protected Point crossHair;
	
	protected int magazineSize;
	protected double reloadSpeed;
	
	protected double currentAccuracy = 0d;
	protected double verticleRecoil = 0d;
	
	protected boolean firing = false;
	protected int fireOffset = 0;
	
	protected Color bulletTrailColor = new Color(204, 204, 96, 40);
	protected int trailThickness = 1;
	protected String flashName;
	protected String casingName;
	protected Bullet bulletType;
	protected int bulletsPerCasing = 1;
	protected int bulletsFiredThisCasing = 0;
	
	protected WeaponReceiver reciever;
	protected WeaponBarrel barrel;
	protected WeaponMod stock;
	protected WeaponMod grip;
	protected WeaponMod mag;
	protected WeaponMod sight;
	protected WeaponMod extension;
	
	protected Point coneTop;
	protected Point coneCenter;
	protected Point coneBottom;
	protected boolean disabled = false;

	public void setOwned(){playerOwned = true;}
	public boolean isOwned(){return playerOwned;}
	public boolean isDisabled(){return disabled;}
	public void disable(boolean val){disabled = val;}
	
	public Point getOffset(){return offset;}
	public double getMuzzleCorrection(){return muzzleCorrection;}
	public int getEjectionOffset(){return ejectionOffset;}
	
	public double getStability(){return stability;}
	public int getAccuracy(){return (int)currentAccuracy;}
	public int getRealAccuracy(){return (int)accuracy;}
	public double getVerticleRecoil(){return verticleRecoil;}
	
	public Sprite getFlashRight(){return flashRight;}
	public Sprite getFlashLeft(){return flashLeft;}
	
	public int isAuto(){return fireRate;}
	
	public BufferedImage getRight(){return right.getImage();}
	public BufferedImage getLeft(){return left.getImage();}
	
	public void fire(){firing = true;}
	public void stopFiring(){firing = false;}
	
	public Weapon(WeaponReceiver recieverPass, WeaponBarrel barrelPass, WeaponMod stockPass, WeaponMod gripPass, WeaponMod magPass, WeaponMod sightPass, WeaponMod extensionPass){
		
		reciever = recieverPass; barrel = barrelPass; stock = stockPass;
		grip = gripPass; mag = magPass; sight = sightPass; extension = extensionPass;
		
		fireRate = 20 - (reciever.getFireRate() + barrel.getFireRate() + stock.getFireRate() + grip.getFireRate() + mag.getFireRate() + sight.getFireRate() + extension.getFireRate());
		roundsPerShot = reciever.getPerShot() + barrel.getPerShot() + stock.getPerShot() + grip.getPerShot() + mag.getPerShot() + sight.getPerShot() + extension.getPerShot();
		damage = reciever.getDamage() + barrel.getDamage() + stock.getDamage() + grip.getDamage() + mag.getDamage() + sight.getDamage() + extension.getDamage();
		
		accuracy = 100 - (reciever.getAccuracy() + barrel.getAccuracy() + stock.getAccuracy() + grip.getAccuracy() + mag.getAccuracy() + sight.getAccuracy() + extension.getAccuracy());
		stability = 1 - (reciever.getStability() + barrel.getStability() + stock.getStability() + grip.getStability() + mag.getStability() + sight.getStability() + extension.getStability())/200;
		
		magazineSize = reciever.getMagazineSize() + barrel.getMagazineSize() + stock.getMagazineSize() + grip.getMagazineSize() + mag.getMagazineSize() + sight.getMagazineSize() + extension.getMagazineSize();
		reloadSpeed = 4 - (reciever.getReloadSpeed() + barrel.getReloadSpeed() + stock.getReloadSpeed() + grip.getReloadSpeed() + mag.getReloadSpeed() + sight.getReloadSpeed() + extension.getReloadSpeed());

		automatic = reciever.getAutomatic();
		bulletType = reciever.getBulletType();
		casingName = reciever.getCasingName();
		flashName = reciever.getFlashName();
		
		int extensionLength = 0;
		if(extension != null)
			extensionLength = extension.getSprite().getImage().getWidth();
		muzzleCorrection = barrel.getMuzzleCorrection();
		ejectionOffset = -reciever.getEjectionOffset() - barrel.getSprite().getImage().getWidth() - extensionLength;
		
		flashName = "StandardFlash";
		flashRight = SpriteLibrary.collectSpriteName(flashName).clone();
		flashLeft = SpriteLibrary.collectSpriteName(flashName).clone().flip();
		flashRight.makeRotateImage(0, flashRight.getImage().getHeight()/2, true);
		flashLeft.makeRotateImage(flashLeft.getImage().getWidth(), flashLeft.getImage().getHeight()/2, false);
	}
	
	public Point tick(double armatureAnglePass, Point muzzlePass, Point crossHairPass, Point casingEjection){
		flashPoint = null;
		
		muzzle = muzzlePass;
		crossHair = crossHairPass;
		armatureAngle = armatureAnglePass;
		
		createCones(armatureAngle);
		
		verticleRecoil *= stability;
		if(verticleRecoil < 1)
			verticleRecoil = 0;
		if(firing && fireOffset == 0 && !disabled){
			Point destination = new Point((int)(coneCenter.x + Math.random() * (Main.gameCursor.getSprite().getAccuracy()*2) - Main.gameCursor.getSprite().getAccuracy()),
											(int)(coneCenter.y + Math.random() * (Main.gameCursor.getSprite().getAccuracy()*2) - Main.gameCursor.getSprite().getAccuracy()));
			Bullet bullet = bulletType.clone(muzzle, destination);
			Main.levelHandler.addBullet(bullet);
			
			bulletsFiredThisCasing ++;
			if(bulletsFiredThisCasing == bulletsPerCasing){
				Particle shellCasing = new Particle(0, 0, casingName, SpriteLibrary.collectSpriteName(casingName)
						, casingEjection, -16 + (int)(Math.random() * 8), (int)(Math.random() * 8)-4, 64, 20, true, 1, 1);
				Main.levelHandler.addParticle(shellCasing);
				bulletsFiredThisCasing = 0;
			}
			
			verticleRecoil += bulletType.getRecoil();
			flashPoint = muzzle;
			fireOffset = fireRate;
			if(playerOwned)
				Main.activeCamera.Shake(bulletType.getShake());
			if(!automatic)
				firing = false;
		}
		
		if(fireOffset != 0)
			fireOffset --;
		
		currentAccuracy = verticleRecoil*2 + accuracy;
		coneOfFire = CONE_OF_FIRE_BASE * (currentAccuracy/100);
		
		return coneCenter;
	}
	
	public Point createCones(double angel){
		double adjacentLength = Math.sqrt(Math.pow(crossHair.x - muzzle.x, 2) + Math.pow(crossHair.y - muzzle.y, 2));
		
		double cofAdjust = coneOfFire;
		if(crossHair.x > muzzle.x)
			cofAdjust = -cofAdjust;
		
		double hypotenuseLength = adjacentLength / Math.cos(coneOfFire);
        coneTop = new Point((int)(muzzle.x + hypotenuseLength * Math.cos(cofAdjust + angel)),
        				(int)(muzzle.y + hypotenuseLength * Math.sin(cofAdjust + angel)));
        coneBottom = new Point((int)(muzzle.x + hypotenuseLength * Math.cos(-cofAdjust + angel)),
        				(int)(muzzle.y + hypotenuseLength * Math.sin(-cofAdjust + angel)));
        coneCenter = new Point((int)((coneTop.x + coneBottom.x) /2), (int)((coneTop.y + coneBottom.y)/2));
        
        if(playerOwned){
	        Main.gameCursor.setTargetIndicator(coneCenter);
	        Main.gameCursor.getSprite().setAccuracy((int)Math.sqrt(Math.pow(coneTop.x - coneCenter.x, 2) + Math.pow(coneTop.y - coneCenter.y, 2)));
        }
        
        return coneCenter;
	}
	
	public void assemble(){}
	
	public void draw(Graphics g, boolean face){
		
		if(Global.showWeaponCones){
			g.setColor(Color.WHITE);
			g.drawLine(muzzle.x + Main.activeCamera.getOffset().x, muzzle.y + Main.activeCamera.getOffset().y, 
					coneCenter.x + Main.activeCamera.getOffset().x, coneCenter.y + Main.activeCamera.getOffset().y);
	        
			g.setColor(Color.MAGENTA);
			g.drawLine(muzzle.x + Main.activeCamera.getOffset().x, muzzle.y + Main.activeCamera.getOffset().y, 
					coneTop.x + Main.activeCamera.getOffset().x, coneTop.y + Main.activeCamera.getOffset().y);
			g.drawLine(muzzle.x + Main.activeCamera.getOffset().x, muzzle.y + Main.activeCamera.getOffset().y, 
					coneBottom.x + Main.activeCamera.getOffset().x, coneBottom.y + Main.activeCamera.getOffset().y);
		}
		
		if(flashPoint != null){
			if(face)
				g.drawImage(flashRight.getImage(), flashPoint.x + Main.activeCamera.getOffset().x - flashRight.getImage().getWidth()/2, flashPoint.y + Main.activeCamera.getOffset().y - flashRight.getImage().getHeight()/2, null);
			else
				g.drawImage(flashLeft.getImage(), flashPoint.x + Main.activeCamera.getOffset().x - flashLeft.getImage().getWidth()/2, flashPoint.y + Main.activeCamera.getOffset().y - flashLeft.getImage().getHeight()/2, null);
		}
	}
}
