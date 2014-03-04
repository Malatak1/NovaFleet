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
	private static Image cannonImage;
	private PlayerShip player;
	private EnemyShip enemy;
	private static GameContainer gc;
	
	public static HashSet<Projectile> projectiles = new HashSet<>();
	
	private static int AI = 0;
	private double prot;
	
	public NovaFleet(String gamename) {
		super(gamename);
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		playerImage = new Image("res/player.png");
		enemyImage = new Image("res/enemy.png");
		cannonImage = new Image("res/projectile.png");
		
		player = new PlayerShip(250, 200, 180, playerImage);
		enemy = new EnemyShip(400, 400, 0, enemyImage);
		
		enemy.setHealth(20);
		player.setHealth(20);
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		Input input = gc.getInput();
		
		if (player.rotation() > 360) {
			player.setRotation(0);
		} else if (player.rotation() < 0) {
			player.setRotation(360);
		}
		
		if (enemy.rotation() > 360) {
			enemy.setRotation(0);
		} else if (enemy.rotation() < 0) {
			enemy.setRotation(360);
		}
		
		/** Player */
		if (input.isKeyDown(Input.KEY_A)) {
			if (input.isKeyDown(Input.KEY_LSHIFT)) {
				player.setRotation(player.rotation() + 0.50F * delta);
			} else {
				player.setRotation(player.rotation() + 0.20F * delta);
			}
		}
		if (input.isKeyDown(Input.KEY_D)) {
			if (input.isKeyDown(Input.KEY_LSHIFT)) {
				player.setRotation(player.rotation() - 0.5F * delta);
			} else {
				player.setRotation(player.rotation() - 0.2F * delta);
			}
		}
		if (input.isKeyDown(Input.KEY_W)) {
			if (!input.isKeyDown(Input.KEY_LSHIFT)) {
				player.setSpeed(player.getSpeed() + 0.03F * delta);
			} else if (player.getSpeed() > 0.3F) {
				player.setSpeed(0.3F);
			} else {
				player.setSpeed(player.getSpeed() + 0.2F * delta);
			}
		}
		if (input.isKeyDown(Input.KEY_S)) {
			if (!input.isKeyDown(Input.KEY_LSHIFT)) {
				player.setSpeed(player.getSpeed() - 0.03F * delta);
			} else if (player.getSpeed() < -0.3F) {
				player.setSpeed(-0.3F * delta);
			} else {
				player.setSpeed(player.getSpeed() - 0.2F * delta);
			}
		}
		if (input.isKeyDown(Input.KEY_SPACE)) {
			player.shootProjectile(delta);
		}
		
		if (AI < 10_000) {
			if (AI < 500) {
				enemy.shootProjectile(delta);
			} else if (AI < 750) {
				
			} else if (AI < 1250) {
				enemy.shootProjectile(delta);
			} else if (AI < 1500) {

			} else if (AI < 2000) {
				enemy.shootProjectile(delta);
			}
		} else {
			AI = 0;
		}
		
		prot = Math.toDegrees(Math.atan2(player.x() - enemy.x(), player.y() - enemy.y()));
		if (prot < 0) {
			prot += 360;
		}
		enemy.setRotation((float) prot);
//		if (prot - enemy.rotation() < 0.02 || prot - enemy.rotation() > -0.02) {
//			enemy.setRotation((float) prot);
//		} else if (prot > enemy.rotation()) {
//			enemy.setRotation(enemy.rotation() - 0.01F);
//		} else if (prot < enemy.rotation()) {
//			enemy.setRotation(enemy.rotation() + 0.01F);
//		}
//		if (enemy.rotation() < 0) {
//			enemy.setRotation(enemy.rotation() + 180);
//		}
		
		AI++;
		
		enemy.update(delta);
		
		player.update(delta);
		List<Projectile> toRemove = new ArrayList<Projectile>();
		for (Projectile projectile : projectiles) {
			if (projectile.getShooter().equals(player)) {
				if (projectile.x() + 2 > enemy.x() && projectile.y() > enemy.y()) {
					if (projectile.x() + 2 < enemy.x() + 32 && projectile.y() < enemy.y() + 32) {
						projectile.remove();
						enemy.damage(1);
						if (enemy.getHealth() <= 0) {
							enemy.remove();
						}
					}
				}
			} else {
				if (projectile.x() + 2 > player.x() && projectile.y() > player.y()) {
					if (projectile.x() + 2 < player.x() + 32 && projectile.y() < player.y() + 32) {
						projectile.remove();
						player.damage(1);
						if (player.getHealth() <= 0) {
							player.remove();
						}
					}
				}
			}
			
			if (projectile.isRemoved()) {
				toRemove.add(projectile);
			}
			projectile.update(delta);
		}
		if (enemy.isRemoved()) {
			enemy.remove(false);
			enemy.setX(new Random().nextFloat() * 1070);
			enemy.setY(new Random().nextFloat() * 670);
			enemy.setHealth(20);
		}
		if (player.isRemoved()) {
			System.exit(0);
		}
		projectiles.removeAll(toRemove);
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		
		g.drawString("Press any of the WASD Keys",10,50);
		
		g.drawImage(player.getImage(), player.x(), player.y());
		
		g.drawString("Rotation: " + player.rotation(), 900, 675);
		g.drawString("ERotate: " + enemy.rotation(), 900, 550);
		g.drawString("PRotate: " + prot, 900, 570);
		playerImage.setRotation((player.rotation() * -1) + 180);
		
		if (enemy != null) {
			enemyImage.setRotation((enemy.rotation() * -1) + 180);
			g.drawImage(enemy.getImage(), enemy.x(), enemy.y());
		}
		
		g.drawString("Speed: " + player.getSpeed(), 900, 650);
		g.drawString("Projectiles: " + projectiles.size(), 900, 625);
		
		for (Projectile projectile : projectiles) {
			g.drawImage(projectile.getImage(), projectile.x(), projectile.y());
		}
		
		g.drawString(" " + player.getHealth(), player.x(), player.y());
		g.drawString(" " + enemy.getHealth(), enemy.x(), enemy.y());
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
	public static Image getCannonImg() {
		return cannonImage;
	}
	public static void addProjectile(Projectile projectile) {
		projectiles.add(projectile);
	}
}
