package net.swordsofvalor.NovaFleet.Entity;

import net.swordsofvalor.NovaFleet.NovaFleet;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;

public class CannonProjectile extends Projectile {
	
	private Ship shooter;
	
	public CannonProjectile(float x, float y, float a, Image image, Ship shooter) {
		super(x, y, a < 0 ? a + 360 : a, image);
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
	public void update(int delta) {
		GameContainer gc = NovaFleet.getGC();
		if (getSpeed() == 0) {
			setSpeed(0.2F);
		}
		this.setSpeed(getSpeed() + 0.005F * delta);
		move(delta);
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
