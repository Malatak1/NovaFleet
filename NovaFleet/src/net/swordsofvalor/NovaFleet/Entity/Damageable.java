package net.swordsofvalor.NovaFleet.Entity;

public abstract class Damageable extends Entity {
	
	private int health;
	
	public Damageable(int x, int y, int a) {
		super(x, y, a);
	}
	
	public int getHealth() {
		return health;
	}
	
	public void setHealth(int health) {
		this.health = health;
	}
	
	public void damage(int value) {
		this.health -= value;
	}
	
}