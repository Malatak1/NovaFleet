package net.swordsofvalor.NovaFleet.Entity;

import org.newdawn.slick.Image;

public class PlayerShip extends Ship {
	
	private boolean isShooting;
	
	public PlayerShip(int x, int y, int a, Image image) {
		super(x, y, a, image);
	}
	
	public boolean isShooting() {
		return isShooting;
	}
	
	public void setIsShooting(boolean isShooting) {
		this.isShooting = isShooting;
	}
	
}
