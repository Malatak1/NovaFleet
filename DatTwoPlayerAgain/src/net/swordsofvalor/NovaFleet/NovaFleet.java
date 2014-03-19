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
import net.swordsofvalor.NovaFleet.Entity.Ship;

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
	private Image fireImage;
	private Image goodhealthp;
	private Image goodhealthp2;
	private Image medhealthp;
	private Image medhealthp2;
	private Image badhealthp;
	private Image badhealthp2;
	private Image badhealthp3;
	private static Image cannonImage;
	private static Image missileImage;
	private Image autoevade;
	private Image welcomeImage;
	private PlayerShip player;
	private EnemyShip enemy;
	private static GameContainer gc;
	private Image Greenwin;
	private Image Redwin;
	public int p1score = 0;
	public int p2score = 0;
	public int escallow = 0;
	public int missilered = 0;
	public int missilegreen = 0;
	public int missileammored = 1;
	public int missileammogreen = 1;
	public int showinfo = 0;
	public int autoimagegreen = 0;
	public int autoimagered = 0;
	public int keypress = 0;
	public int firegreen = 0;
	public int firered = 0;
	public int startblue = 0;
	public int startpurple = 0;
	public int playerfiring = 0;
	public int enemyfiring = 0;
	
	public static HashSet<Projectile> projectiles = new HashSet<>();
	
	private double prot;
	
	public NovaFleet(String gamename) {
		super(gamename);
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		playerImage = new Image("res/player.png");
		enemyImage = new Image("res/enemy.png");
		cannonImage = new Image("res/projectile.png");
		Greenwin = new Image("res/Greenwin.png");
		Redwin = new Image("res/Redwin.png");
		missileImage = new Image("res/missile.png");
		autoevade = new Image ("res/autoevade.png");
		fireImage = new Image ("res/fire.png");
		welcomeImage = new Image ("res/welcome.png");
		goodhealthp = new Image ("res/goodhealthp.png");
		medhealthp = new Image ("res/medhealthp.png");
		badhealthp = new Image ("res/badhealthp.png");
		goodhealthp2 = new Image ("res/goodhealthp2.png");
		medhealthp2 = new Image ("res/medhealthp2.png");
		badhealthp2 = new Image ("res/badhealthp2.png");
		badhealthp3 = new Image ("res/badhealthp3.png");
		
		player = new PlayerShip(250, 200, 180, playerImage);
		enemy = new EnemyShip(830, 200, 0, enemyImage);
		
		enemy.setHealth(100);
		player.setHealth(100);
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
		playerInput(player, input, delta);
		playerInput(enemy, input, delta);
		
		
		enemy.update(delta);
		player.update(delta);
		List<Projectile> toRemove = new ArrayList<Projectile>();
		for (Projectile projectile : projectiles) {
			if (projectile.getShooter().equals(player)) {
				if (projectile.x() + 2 > enemy.x() && projectile.y() > enemy.y()) {
					if (projectile.x() + 2 < enemy.x() + 32 && projectile.y() < enemy.y() + 32 && missilegreen == 0) {
						projectile.remove();
						enemy.damage(1);
						if (enemy.getHealth() < 76 && keypress == 0){
							autoimagered = 1;
							
						}
						if (enemy.getHealth() <= 0) {
							enemy.remove();
							missileammogreen = 1;
							missileammored = 1;
						}
					}
					if (projectile.x() + 2 < enemy.x() + 32 && projectile.y() < enemy.y() + 32 && missilegreen == 1) {
						projectile.remove();
						enemy.damage(20);
						missileammogreen = 0;
						missilegreen = 0;
						if (enemy.getHealth() <= 0) {
							enemy.remove();
							missileammogreen = 1;
							missileammored = 1;
						}
					}
				}
			} else {
				if (projectile.x() + 2 > player.x() && projectile.y() > player.y()) {
					if (projectile.x() + 2 < player.x() + 32 && projectile.y() < player.y() + 32 && missilered == 0) {
						projectile.remove();
						player.damage(1);
						if (player.getHealth() < 76 && player.getSpeed() < .001F){
							autoimagegreen = 1;
							if(playerfiring == 1){
							player.setRotation(enemy.rotation() + 180);
							}
						}
						if (player.getHealth() <= 0) {
							player.remove();
							missileammored = 1;
							missileammogreen = 1;
						}
					}
				}
				if (projectile.x() + 2 > player.x() && projectile.y() > player.y()) {
				if (projectile.x() + 2 < player.x() + 32 && projectile.y() < player.y() + 32 && missilered == 1) {
					projectile.remove();
					player.damage(20);
					missileammored = 0;
					missilered = 0;
					if (enemy.getHealth() <= 0) {
						player.remove();
						missileammored = 1;
						missileammogreen = 1;
					}
				}
				}
			}
			
			if (projectile.isRemoved()) {
				toRemove.add(projectile);
			}
			projectile.update(delta);
		}
		if (enemy.isRemoved() && escallow == 0) {
			enemy.remove(false);
			enemy.setX(new Random().nextFloat() * 1070);
			enemy.setY(new Random().nextFloat() * 670);
			enemy.setHealth(100);
			p1score = p1score + 5;
			p2score = p2score - 1;
		}
		if (player.isRemoved() && escallow == 0) {
			player.remove(false);
			player.setX(new Random().nextFloat() * 1070);
			player.setY(new Random().nextFloat() * 670);
			player.setHealth(100);
			p2score = p2score + 5;
			p1score = p1score - 1;
		}
		projectiles.removeAll(toRemove);
		
		
		
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		
		g.drawImage(welcomeImage, 450, 200);
		
		if(startblue == 1 && startpurple == 1){
			welcomeImage.setAlpha(0);
		}
		
		if(startblue == 0){
		g.drawString("Press the 'W' key to start!", 100, 200);
		}
		if(startpurple == 0){
		g.drawString("Press the 'UP' key to start!", 740, 200);
		}
		
		if(startblue == 1){
		g.drawString("Player Blue: " + p1score, 10, 40);
		g.drawImage(player.getImage(), player.x(), player.y());
		g.drawString(" " + player.getHealth(), player.x(), player.y());
		if(player.getHealth() > 80){
			g.drawImage(goodhealthp, player.x() - 15, player.y() - 12);
		}
		if(player.getHealth() > 66 && player.getHealth() < 80){
			g.drawImage(goodhealthp2, player.x() - 15, player.y() - 12);
		}
		if(player.getHealth() < 66 && player.getHealth() > 55){
			g.drawImage(medhealthp, player.x() - 15, player.y() - 12);
		}
		if(player.getHealth() > 40 && player.getHealth() < 55){
			g.drawImage(medhealthp2, player.x() - 15, player.y() - 12);
		}
		if(player.getHealth() < 40 && player.getHealth() > 25){
			g.drawImage(badhealthp, player.x() - 15, player.y() - 12);
		}
		if(player.getHealth() > 10 && player.getHealth() < 25){
			g.drawImage(badhealthp2, player.x() - 15, player.y() - 12);
		}
		if(player.getHealth() < 10){
			g.drawImage(badhealthp3, player.x() - 15, player.y() - 12);
		}
		}
		
		if(startpurple == 1){
		g.drawString("Player Purple: " + p2score, 930, 40);
		g.drawImage(enemy.getImage(),enemy.x(), enemy.y());
		g.drawString(" " + enemy.getHealth(), enemy.x(), enemy.y());
		if(enemy.getHealth() > 80){
			g.drawImage(goodhealthp, enemy.x() - 15, enemy.y() - 12);
		}
		if(enemy.getHealth() > 66 && enemy.getHealth() < 80){
			g.drawImage(goodhealthp2, enemy.x() - 15, enemy.y() - 12);
		}
		if(enemy.getHealth() < 66 && enemy.getHealth() > 55){
			g.drawImage(medhealthp, enemy.x() - 15, enemy.y() - 12);
		}
		if(enemy.getHealth() > 40 && enemy.getHealth() < 55){
			g.drawImage(medhealthp2, enemy.x() - 15, enemy.y() - 12);
		}
		if(enemy.getHealth() < 40 && enemy.getHealth() > 25){
			g.drawImage(badhealthp, enemy.x() - 15, enemy.y() - 12);
		}
		if(enemy.getHealth() > 10 && enemy.getHealth() < 25){
			g.drawImage(badhealthp2, enemy.x() - 15, enemy.y() - 12);
		}
		if(enemy.getHealth() < 10){
			g.drawImage(badhealthp3, enemy.x() - 15, enemy.y() - 12);
		}
		}
		
		if(showinfo == 1){
		g.drawString("Rotation: " + player.rotation(), 900, 675);
		g.drawString("ERotate: " + enemy.rotation(), 900, 550);
		g.drawString("PRotate: " + prot, 900, 570);
		g.drawString("Speed: " + player.getSpeed(), 900, 650);
		g.drawString("Projectiles: " + projectiles.size(), 900, 625);
		g.drawString("Keypress: " + keypress, 900, 525);
		}
		
		if (firegreen == 1){
			
		}
		
		if (autoimagegreen == 1){
			g.drawImage(autoevade, player.x() -55, player.y() -70);
		}
		if (autoimagered == 1){
			g.drawImage(autoevade, enemy.x() -55, enemy.y() -70);
		}
		playerImage.setRotation((player.rotation() * -1) + 180);
		enemyImage.setRotation((enemy.rotation() * -1) + 180);
		
		if (p1score>= 15){
			g.drawImage(Greenwin, 420, 200);
			g.drawString("Press ESC to quit.", 465, 280);
			escallow = 1;
		}
		
		if (p2score>= 15){
			g.drawImage(Redwin, 420, 200);
			g.drawString("Press ESC to quit.", 465, 280);
			escallow = 1;
		}
		
		
		for (Projectile projectile : projectiles) {
			g.drawImage(projectile.getImage(), projectile.x(), projectile.y());
		}
		
		
			
		if(missilered == 1){
			g.drawImage(missileImage, player.x()-10, player.y()-10);
		}
		if(missilegreen == 1){
			g.drawImage(missileImage, enemy.x()-10, enemy.y()-10);
		}
	}
	

	public static void main(String[] args) {
		try {
			AppGameContainer appgc;
			appgc = new AppGameContainer(new NovaFleet("NovaFleet"));
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
	public void playerInput(Ship Player, Input input, int delta) {
		//player
		int a = Input.KEY_A;
		int d = Input.KEY_D;
		int w = Input.KEY_W;
		int s = Input.KEY_S;
		int c = Input.KEY_C;
		int shift = Input.KEY_LSHIFT;
		int esc = Input.KEY_ESCAPE;
		int q = Input.KEY_Q;

		int i = Input.KEY_F3;
		
//enemy
		int left = Input.KEY_LEFT;
		int right = Input.KEY_RIGHT;
		int up = Input.KEY_UP;
		int down = Input.KEY_DOWN;
		int rshift = Input.KEY_RSHIFT;
		int slash = Input.KEY_SLASH;	
		int enter = Input.KEY_RALT;
		
		//player
		if (input.isKeyDown(a)) {
			if (input.isKeyDown(shift)) {
				player.setRotation(player.rotation() + 0.05F * delta);
			}
			else if(missilered == 1){
				player.setRotation(player.rotation() + 0.05F * delta);
			}
			else {
				player.setRotation(player.rotation() + 0.15F * delta);
			 }
		}
		else{
			keypress = 0;
		}
		if (input.isKeyDown(d)) {
			if (input.isKeyDown(shift)) {
				player.setRotation(player.rotation() - 0.05F * delta);
			}
			else if(missilered == 1){
				player.setRotation(player.rotation() - 0.05F * delta);
			}
			else {
				player.setRotation(player.rotation() - 0.15F * delta);
			}
		}
		if (input.isKeyDown(w) && missilered == 0) {
			keypress = 1;
			startblue = 1;
			autoimagegreen = 0;
			firegreen = 1;
			player.setSpeed(player.getSpeed() + 0.001F * delta);
		}
		if (input.isKeyDown(s) && missilered == 0) {
			keypress = 1;
			autoimagegreen = 0;
			 
				player.setSpeed(player.getSpeed() - 0.001F * delta);
			
		}
		if (input.isKeyDown(c) && escallow == 0) {
			autoimagegreen = 0;
			player.shootProjectile(delta);
		}
		if (input.isKeyDown(w) && missilered == 1) {
			autoimagegreen = 0;
				player.setSpeed(player.getSpeed() + 0.0009F * delta);
		}
		//enemy
		if (input.isKeyDown(left)) {
			if (input.isKeyDown(enter)) {
				enemy.setRotation(enemy.rotation() + 0.05F * delta);
			}
			else if(missilegreen == 1){
				enemy.setRotation(enemy.rotation() + 0.05F * delta);
			}
			else {
				enemy.setRotation(enemy.rotation() + 0.15F * delta);
			 }
		}
		if (input.isKeyDown(right)) {
			if (input.isKeyDown(enter)) {
				enemy.setRotation(enemy.rotation() - 0.05F * delta);
			}
			else if(missilegreen == 1){
				enemy.setRotation(enemy.rotation() - 0.05F * delta);
			}
			else {
				enemy.setRotation(enemy.rotation() - 0.15F * delta);
			}
		}
		if (input.isKeyDown(up) && missilegreen == 0) {
			keypress = 1;
			autoimagered = 0;
			startpurple = 1;
			
			  
				enemy.setSpeed(enemy.getSpeed() + 0.001F * delta);
			
		}
		if (input.isKeyDown(down) && missilegreen == 0) {
			keypress = 1;
			autoimagered = 0;
			 
				enemy.setSpeed(enemy.getSpeed() - 0.001F * delta);
		}
		if (input.isKeyDown(rshift) && escallow == 0) {
			enemy.shootProjectile(delta);
		}
		if (input.isKeyDown(up) && missilegreen == 1) {
			autoimagered = 0;
			enemy.setSpeed(enemy.getSpeed() + 0.0009F * delta);
		}
		if (input.isKeyDown(esc) && escallow == 1) {
			System.exit(0);
		}
		
		if (input.isKeyDown(q) &&  missileammogreen == 1) {
			missilegreen = 1;
		}
		if (input.isKeyDown(slash) &&  missileammored == 1) {
			missilered = 1;
		}
		if (input.isKeyDown(i)){
			showinfo = 1;
			
		}
		else{
			showinfo = 0;
		}
	
		
	}
}
