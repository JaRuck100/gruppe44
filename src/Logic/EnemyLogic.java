package Logic;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import Main.Enemy;
import Main.Houston;
import Main.Map;

/** enthaelt die Logic der Gegner */
public class EnemyLogic {

	private Houston houston;
	private Map map;

	/** Gegnerarray, enthaelt alle Gegner der aktuellen Karte */
	public ArrayList<Enemy> enemies;
	private Enemy enemy;

	/** gibt an, ob der Bossgegner noch lebt */
	public boolean bossIsAlive;
	BufferedImage textures = null;

	/**
	 * erstellt das Gegnerarray
	 * @param houston
	 */
	public EnemyLogic(Houston houston) {
		this.houston = houston;
		this.map = houston.map;
		enemies = new ArrayList<Enemy>();
	}

	/** liest die Gegner aus der Karte und erstellt diese */
	public void onLevelChange() {
		enemies.clear();
		for(int i = 0; i<6; i++){
			for (Point2D singleEnemyPosition : map.multiSearch(map.enemyArray, 30+i)) {
				if(i<3)
					enemies.add(new Enemy(setTextures( map.getLevelNumber() * 3 + i), singleEnemyPosition, 0, map.getLevelNumber() * 3 + i));
				else
					enemies.add(new Enemy(setTextures(map.getLevelNumber() * 3 + i-3), singleEnemyPosition, 3, map.getLevelNumber() * 3 + i-3));
			}
		}
	}

	/**
	 * entfernt getoetete Gegner;
	 * bewegt die Gegner;
	 * prueft ob Spieler und Gegner sich ueberschneiden und fuegt dem Spieler ggf Schaden zu
	 */
	public void doGameUpdates() {
		for (int i = enemies.size() - 1; i >= 0; i--) {
			enemy = enemies.get(i);

			//Prueft, ob es ein Bossgegner ist
			if((enemy.enemyType == 2) || (enemy.enemyType == 5) || (enemy.enemyType == 8)){
				bossIsAlive = true;
			}
			// Filtert die zu loeschenden Enemies raus
			if (enemy.remove) {
				if(bossIsAlive){
					bossIsAlive = false;
				}
				enemies.remove(i);
				continue;
			}
			houston.gameLogic.controlCharacterMovement(enemy);

			// Prueft, ob der Player sich mit einem Gegner Ueberschneidet
			if (houston.player.getBounds().intersects(enemy.getBounds())) {
				houston.player.decreaseHealth(2);
			}
		}
	}

	private BufferedImage setTextures(int enemyType) {
		try {
			if ((enemyType >= 0) && (enemyType < 3)) {
				textures = ImageIO.read(new File("./res/img/characters/analysis.png"));
			}
			if ((enemyType >= 3) && (enemyType < 6)) {
				textures = ImageIO.read(new File("./res/img/characters/french_m1.png"));
			}
			if ((enemyType >= 6) && (enemyType < 9)) {
				textures = ImageIO.read(new File("./res/img/characters/informatik.png"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return textures;
	}
}
