package weapon;

public class BulletLibrary {

	public static Bullet nine;
	public static Bullet fiveFive;
	public static Bullet sevenSix;
	
	public BulletLibrary(){
		nine = new Bullet(6, 50, 0, 6, 12);
		fiveFive = new Bullet(16, 80, 1, 8, 16);
		sevenSix = new Bullet(26, 120, 2, 12, 24);
	}
}
