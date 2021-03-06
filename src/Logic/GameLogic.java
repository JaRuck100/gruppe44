package Logic;

import java.awt.geom.Point2D;

import Main.Houston;
import Main.Map;
import Main.Movable;
import Main.Player;
import Main.Quest;
import Main.Story;

/** enthaelt die Logic vom Spiel */
public class GameLogic {

	private Houston houston;
	private Player player;
	private Map map;
	private EnemyLogic enemyLogic;
	private PlayerLogic playerLogic;
	private MagicLogic magicLogic;
	private ItemLogic itemLogic;
	private Story story;
	private Quest quest;

	private Point2D topLeft, topRight, bottomLeft, bottomRight;
	double tlX, tlY; // Temporaere Character Ecke
	private long delta;
	private double dX, dY;

	/** Wert einer Kachel */
	public int value;
	public int npcv;

	/**
	 * Initialisiert die anderen Logiken und die Eckpunkte des Charakters
	 * @param houston
	 */
	public GameLogic(Houston houston) {
		this.houston	= houston;
		this.player		= houston.player;
		this.map		= houston.map;
		this.story		= houston.story;
		this.quest 		= houston.quest;

		this.playerLogic	= houston.playerLogic		= new PlayerLogic(houston);
		this.enemyLogic		= houston.enemyLogic		= new EnemyLogic(houston);
		this.magicLogic		= houston.magicLogic		= new MagicLogic(houston);
		this.itemLogic		= houston.itemLogic			= new ItemLogic(houston);

		// 4 Punkte fuer die 4 Ecken des Character
		topLeft			= new Point2D.Double();
		topRight		= new Point2D.Double();
		bottomLeft		= new Point2D.Double();
		bottomRight		= new Point2D.Double();
	}

	/**
	 * Startet ein neues Spiel
	 * @param levelNumber
	 * @param mapNumber
	 */
	public void setupNewGame(int levelNumber, int mapNumber) {
		player.resetPlayerStats(100, 100, 120, 100, 3, 1, 0);
		houston.inventory.clear();

		changeLevel(levelNumber, mapNumber);
	}

	// Springt zum angegebenen Level.
	public void changeLevel(int levelNumber, int mapNumber) {
		map.renewMap(levelNumber, mapNumber);
		story.renewStory(mapNumber);
		quest.renewQuest(mapNumber);

		playerLogic.onLevelChange();
		enemyLogic.onLevelChange();
		magicLogic.onLevelChange();
		itemLogic.onLevelChange();
	}

	/**
	 * Berechnet z.B. alle Bewegungen und Kollisionen im Spiel
	 * @param delta
	 */
	public void doGameUpdates(long delta) {
		this.delta = delta;
		if (houston.multiPlayer.ready)
			houston.multiPlayer.doGameUpdates();

		playerLogic.doGameUpdates();
		enemyLogic.doGameUpdates();
		itemLogic.doGameUpdates();
		magicLogic.doGameUpdates();
	}

	// Regelt den Wechsel zur naechsten Karte
	private void nextMap() {
		if(enemyLogic.bossIsAlive){
			return;
		}
		player.stop();

		if (map.getMapNumber() < (map.getCountOfMapsByLevel() - 1)) {
			// Naechste Karte
			changeLevel(map.getLevelNumber(), (map.getMapNumber() + 1));
		} else {
			if (map.getLevelNumber() < (map.getCountOfLevel() - 1)) {
				// Naechstes Level
				changeLevel((map.getLevelNumber() + 1), 0);
			} else {
				// Spiel gewonnen
				if (houston.multiPlayer.isOver == false)
					houston.multiPlayer.exitGame(Houston.WON);
				else
					houston.changeAppearance(true, false, Houston.WON);
			}
		}
	}

	/**
	 * Setzt die Tastendruecke um in die Bewegung des Charakter
	 * @param character
	 */
	public void controlCharacterMovement(Movable character) {
		dX = dY = 0;
		getCharacterCorners(character);

		// Bewegung nach Links
		if (character.getLeft() > 0 && character.getRight() == 0) {
			dX = character.getDX(-1, delta);
			if (isValidXMovement(topLeft, bottomLeft, dX, 1) == 1) {
				dX = 0;
				character.onWallHit();
			} else if (isValidXMovement(topLeft, bottomLeft, dX, 3) == 3) {
				dX = 0;
				npcv = 1;
				character.onWallHit();
			} else if (isValidXMovement(topLeft, bottomLeft, dX, 4) == 4){
				dX = 0;
				npcv = 2;
				character.onWallHit();
			}

			// Bewegung nach Rechts
		} else if (character.getRight() > 0 && character.getLeft() == 0) {
			dX = character.getDX(1, delta);
			if (isValidXMovement(topRight, bottomRight, dX, 1) == 1) {
				dX = 0;
				character.onWallHit();
			} else if (isValidXMovement(topRight, bottomRight, dX, 3) == 3) {
				dX = 0;
				npcv = 1;
				character.onWallHit();
			} else if (isValidXMovement(topRight, bottomRight, dX, 4) == 4) {
				dX = 0;
				npcv = 2;
				character.onWallHit();
			}
		}

		// Bewegung nach Oben
		if (character.getUp() > 0 && character.getDown() == 0) {
			dY = character.getDY(-1, delta);
			if (isValidYMovement(topLeft, topRight, dY, 1) == 1) {
				dY = 0;
				character.onWallHit();
			} else if (isValidYMovement(topLeft, topRight, dY, 3) == 3) {
				dY = 0;
				npcv = 1;
				character.onWallHit();
			} else if (isValidYMovement(topLeft, topRight, dY, 4) == 4) {
				dY = 0;
				npcv = 2;
				character.onWallHit();
			}

			// Bewegung nach Unten
		} else if (character.getDown() > 0 && character.getUp() == 0) {
			dY = character.getDY(1, delta);
			if (isValidYMovement(bottomLeft, bottomRight, dY, 1) == 1) {
				dY = 0;
				character.onWallHit();
			} else if (isValidYMovement(bottomLeft, bottomRight, dY, 3) == 3) {
				dY = 0;
				npcv = 1;
				character.onWallHit();
			} else if (isValidYMovement(bottomLeft, bottomRight, dY, 4) == 4) {
				dY = 0;
				npcv = 2;
				character.onWallHit();

			}
		}

		// Bewegt den Charakter falls notwendig
		if (dX != 0 || dY != 0)
			character.move(dX, dY);
	}

	/** Ermittelt, ob sich der Player auf einer Speziellen Kachel befindet, und leitet entsprechende Massnahmen ein */
	public void detectSpecialTiles() {
		if (player.isMoving()) {
			try {
				value = (map.mapArray[(int) Math.floor(player.getCenterPosition().getY())/32][(int) Math.floor((player.getCenterPosition().getX())/32)]);
			} catch (ArrayIndexOutOfBoundsException e) {
				System.out.println("ungueltige Position x:"+player.getX()+" y:"+player.getY()); die();
			}

			// entsprechende Massnahmen
			if (value == 7) {
				backToLastCheckpoint();
				player.decreaseHealth(25);
			} else if (value == 9) {
				nextMap();
			}
		}
	}

	// Ermittelt die Koordinaten der 4 Eckpunkte des Charakter
	private void getCharacterCorners(Movable character) {
		double tlX = character.getX();
		double tlY = character.getY();
		topLeft.setLocation(tlX, tlY);
		topRight.setLocation(tlX + character.getWidth(), tlY);
		bottomLeft.setLocation(tlX, tlY + character.getHeight());
		bottomRight.setLocation(tlX + character.getWidth(), tlY + character.getHeight());
	}

	// Ermittelt, ob die, durch horizontale Bewegung dX, errechnete neue Position (des Spielers) in einer Wand liegt oder nicht
	private int isValidXMovement(Point2D pointTop, Point2D pointBottom, double dX, int checkValue) {
		try {
			if ((map.mapArray[(int) Math.floor(pointTop.getY()/32)][(int) Math.floor((pointTop.getX()+dX)/32)] == checkValue))
				return checkValue;
			else if ((map.mapArray[(int) Math.floor(pointBottom.getY()/32)][(int) Math.floor((pointBottom.getX()+dX)/32)] == checkValue))
				return checkValue;
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("ungueltige x Bewegung pT:"+pointTop+" pB:"+pointBottom); die();
		}
		return 0;
	}

	// Ermittelt, ob die, durch vertikale Bewegung dY, errechnete neue Position (des Spielers) in einer Wand liegt oder nicht
	private int isValidYMovement(Point2D pointLeft, Point2D pointRight, double dY2, int checkValue) {
		try {
			if ((map.mapArray[(int) Math.floor(pointLeft.getY()+dY)/32][(int) Math.floor(pointLeft.getX()/32)] == checkValue))
				return checkValue;
			else if ((map.mapArray[(int) Math.floor(pointRight.getY()+dY)/32][(int) Math.floor(pointRight.getX()/32)] == checkValue))
				return checkValue;
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("ungueltige y Bewegung pL:"+pointLeft+" pR:"+pointRight); die();
		}
		return 0;
	}

	private void backToLastCheckpoint() {
		// Zurueck zur ersten Karte des aktuellen Level
		changeLevel(map.getLevelNumber(), 0);
		player.setArmor(100);
	}

	private void die() {
		houston.changeAppearance(true, false, Houston.STARTMENU);
	}

}
