package net.swordsofvalor.NovaFleet.Entity;

public abstract class Entity {
	
	private float x;
	private float y;
	private float a;
	private boolean remove;
	
	public Entity(float x, float y, float a) {
		this.x = x;
		this.y = y;
		this.a = a;
		remove = false;
	}
	
	public float x() {
		return x;
	}
	public float y() {
		return y;
	}
	public float rotation() {
		return a;
	}
	public void setX(float x) {
		this.x = x;
	}
	public void setY(float y) {
		this.y= y;
	}
	public void setRotation(float a) {
		this.a = a;
	}
	public boolean isRemoved() {
		return remove;
	}
	public void remove() {
		remove = true;
	}
	public void remove(boolean removalState) {
		remove = removalState;
	}
	
}
