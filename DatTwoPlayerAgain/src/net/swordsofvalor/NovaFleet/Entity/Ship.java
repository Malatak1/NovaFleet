package net.swordsofvalor.NovaFleet.Entity;

import net.swordsofvalor.NovaFleet.NovaFleet;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;

public abstract class Ship extends Damageable {
	
	private long lastShotFired = 0;
	private Image image;
	private float speed = 0;
	private boolean left = true;
	
	public Ship(int x, int y, int a, Image image) {
		super(x, y, a);
		this.image = image;
	}

	public Image getImage() {
		return image;
	}
	
	public float getSpeed() {
		return speed;
	}
	
	public void setSpeed(float speed) {
		this.speed = speed;
	}
	
	public void move() {
		float a = this.rotation();
		this.setX(this.x() + ((float) Math.sin(Math.toRadians(a))) * speed);
		this.setY(this.y() + ((float) Math.cos(Math.toRadians(a))) * speed);
	}
	
	public void update(int delta) {
		GameContainer gc = NovaFleet.getGC();
		move();
		if (speed > 0) {
			speed -= 0.001 * delta;
		}
		if (speed < 0) {
			speed += 0.001 * delta;
		}
		if (speed > 0.8 * delta) {
			speed = 0.8F * delta;
		}
		if (speed < -0.8 * delta) {
			speed = -0.8F * delta;
		}
		if (this.x() < 0) {
			this.setX(0);
		}
		if (this.y() < 0) {
			this.setY(0);
		}
		if (this.x() > gc.getWidth() - 32) {
			this.setX(gc.getWidth() - 32);
		}
		if (this.y() > gc.getHeight() - 32) {
			this.setY(gc.getHeight() - 32);
		}
	}
	
	public void shootProjectile(int delta) {
		if (left) {
			if (lastShotFired + 50 < System.currentTimeMillis()) {
//				float x = this.x() + 16;
//				float y = this.y() + 16;
				float a = this.rotation();
				float x = this.x() + 16 + ((float) Math.sin(Math.toRadians(a - 90))) * 10;
				float y = this.y() + 16 + ((float) Math.cos(Math.toRadians(a - 90))) * 10;
				CannonProjectile projectile = new CannonProjectile(x, y, a < 0 ? a + 360 : a, NovaFleet.getCannonImg(), this);				projectile.setSpeed(speed);
				NovaFleet.addProjectile(projectile);
				lastShotFired = System.currentTimeMillis();
				left = false;
			}
		} else {
			if (lastShotFired + 50 < System.currentTimeMillis()) {
//				float x = this.x() + 16;
//				float y = this.y() + 16;
				float a = this.rotation();
				float x = this.x() + 16 + ((float) Math.sin(Math.toRadians(a + 90))) * 10;
				float y = this.y() + 16 + ((float) Math.cos(Math.toRadians(a + 90))) * 10;
				CannonProjectile projectile = new CannonProjectile(x, y, a < 0 ? a + 360 : a, NovaFleet.getCannonImg(), this);
				projectile.setSpeed(speed);
				NovaFleet.addProjectile(projectile);
				lastShotFired = System.currentTimeMillis();
				left = true;
			}
		}
	}
}