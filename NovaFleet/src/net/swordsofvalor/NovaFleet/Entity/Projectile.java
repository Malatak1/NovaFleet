package net.swordsofvalor.NovaFleet.Entity;

import org.newdawn.slick.Image;

public abstract class Projectile extends Entity {
	
	private Image image;
	private float speed = 0;
	
	public Projectile(float x, float y, float a, Image image) {
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
		this.setX(this.x() + ((float)Math.cos(Math.toRadians(a))) * speed);
		this.setY(this.y() + ((float)Math.sin(Math.toRadians(a))) * speed);
	}
	
	public abstract int getDamage();
	public abstract Ship getShooter();
	public abstract void update();
	
}
