package net.swordsofvalor.NovaFleet.Entity;

import net.swordsofvalor.NovaFleet.NovaFleet;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;

public abstract class Ship extends Damageable {
	
	private Image image;
	private float speed = 0;
	
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
		this.setX(Math.round(this.x() + ((float) Math.cos(Math.toRadians(a))) * speed));
		this.setY(Math.round(this.y() + ((float) Math.sin(Math.toRadians(a))) * speed));
	}
	
	public void update() {
		GameContainer gc = NovaFleet.getGC();
		move();
		if (speed > 0) {
			speed -= 0.02;
		}
		if (speed < 0) {
			speed += 0.02;
		}
		if (speed > 2) {
			speed = 2;
		}
		if (this.x() < 0) {
			this.setX(0);
		}
		if (this.y() < 0) {
			this.setY(0);
		}
		if (this.x() > gc.getWidth()) {
			this.setX(gc.getWidth());
		}
		if (this.y() > gc.getHeight()) {
			this.setY(gc.getHeight());
		}
	}
}