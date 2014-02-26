package net.swordsofvalor.NovaFleet.Entity;

import net.swordsofvalor.NovaFleet.NovaFleet;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;

public class CannonProjectile extends Projectile {
	
	private Ship shooter;
	
	public CannonProjectile(float x, float y, float a, Image image, Ship shooter) {
		super(x, y, a, image);
		this.shooter = shooter;
	}
	
	public CannonProjectile(Ship shooter, Image image) {
		this(shooter.x() + 16, shooter.y() + 16, shooter.rotation(), image, shooter);
	}

	@Override
	public int getDamage() {
		return 5;
	}

	@Override
	public Ship getShooter() {
		return shooter;
	}
	
	@Override
	public void update() {
		GameContainer gc = NovaFleet.getGC();
		this.setSpeed(4F);
		move();
		if (this.x() < 0) {
			this.remove();
		}
		if (this.y() < 0) {
			this.remove();
		}
		if (this.x() > gc.getWidth()) {
			this.remove();
		}
		if (this.y() > gc.getHeight()) {
			this.remove();
		}
	}

}
