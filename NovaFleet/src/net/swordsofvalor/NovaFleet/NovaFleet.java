package net.swordsofvalor.NovaFleet;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.swordsofvalor.NovaFleet.Entity.EnemyShip;
import net.swordsofvalor.NovaFleet.Entity.PlayerShip;
import net.swordsofvalor.NovaFleet.Entity.Projectile;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class NovaFleet extends BasicGame {
	
	private Image playerImage;
	private Image enemyImage;
	private PlayerShip player;
	private EnemyShip enemy;
	private static GameContainer gc;
	
	private static HashSet<Projectile> projectiles = new HashSet<>();
	
	public NovaFleet(String gamename) {
		super(gamename);
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		playerImage = new Image("res/player.png");
		enemyImage = new Image("res/enemy.png");
		
		player = new PlayerShip(250, 200, 0, playerImage);
		enemy = new EnemyShip(400, 400, 0, enemyImage);
		
		enemy.setHealth(200);
		
		Thread shoot = new Thread(new ShootRunnable(player));
		shoot.start();
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		Input input = gc.getInput();
		
		if (player.rotation() > 360) {
			player.setRotation(0);
		} else if (player.rotation() < 0) {
			player.setRotation(360);
		}
		
		if (input.isKeyDown(Input.KEY_A)) {
			player.setRotation(player.rotation() - 0.28F);
		}
		if (input.isKeyDown(Input.KEY_D)) {
			player.setRotation(player.rotation() + 0.28F);
		}
		if (input.isKeyDown(Input.KEY_W)) {
			player.setSpeed(player.getSpeed() + 0.03F);
		}
		if (input.isKeyDown(Input.KEY_S)) {
			player.setSpeed(player.getSpeed() - 0.03F);
		}
		if (input.isKeyDown(Input.KEY_SPACE)) {
			
//			CannonProjectile projectile = new CannonProjectile(player, new Image("res/projectile.png"));
//			projectiles.add(projectile);
		}
		
		player.update();
		List<Projectile> toRemove = new ArrayList<Projectile>();
		for (Projectile projectile : projectiles) {
			if (projectile.x() + 2 > enemy.x() && projectile.y() > enemy.y()) {
				if (projectile.x() + 2 < enemy.x() + 32 && projectile.y() < enemy.y() + 32) {
					projectile.remove();
					enemy.damage(1);
					if (enemy.getHealth() <= 0) {
						enemy.remove();
					}
				}
			}
			
			if (projectile.isRemoved()) {
				toRemove.add(projectile);
			}
			projectile.update();
		}
		if (enemy.isRemoved()) {
			enemy.remove(false);
			enemy.setX(new Random().nextFloat() * 1070);
			enemy.setY(new Random().nextFloat() * 670);
			enemy.setHealth(200);
		}
		projectiles.removeAll(toRemove);
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		
		g.drawString("Press any of the WASD Keys",10,50);
		
		g.drawImage(player.getImage(), player.x(), player.y());
		playerImage.setRotation(player.rotation());
		
		if (enemy != null) {
			g.drawImage(enemy.getImage(), enemy.x(), enemy.y());
			enemyImage.setRotation(enemy.rotation());
		}
		
		g.drawString("Speed: " + player.getSpeed(), 900, 650);
		
		for (Projectile projectile : projectiles) {
			g.drawImage(projectile.getImage(), projectile.x(), projectile.y());
		}
	}
	
	public static void main(String[] args) {
		try {
			AppGameContainer appgc;
			appgc = new AppGameContainer(new NovaFleet("Simple Slick Game"));
			appgc.setDisplayMode(1100, 700, false);
			gc = appgc;
			appgc.start();
		} catch (SlickException ex) {
			Logger.getLogger(NovaFleet.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	public static GameContainer getGC() {
		return gc;
	}
}
