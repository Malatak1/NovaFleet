package net.swordsofvalor.NovaFleet;

import net.swordsofvalor.NovaFleet.Entity.PlayerShip;

public class ShootRunnable implements Runnable {
	
	private PlayerShip player;
	
	public ShootRunnable(PlayerShip player) {
		this.player = player;
	}
	
	@Override
	public void run() {
		System.out.println("Running");
		try {
			Thread.sleep(100);
			player.getSpeed();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
