package folderol;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class ItemLogic {

	private Houston houston;
	public ArrayList<Item> items;
	private Item item;
	private int itemType;
	public ArrayList<BufferedImage> texture;

	public ItemLogic(Houston houston) {
		this.houston = houston;

		items = new ArrayList<Item>();
		texture = new ArrayList<BufferedImage>();
		initializeTextures();
	}

	private void initializeTextures() {
		try {
			texture.add(0, ImageIO.read(new File("./res/img/tiles/health.png")));
			texture.add(1, ImageIO.read(new File("./res/img/tiles/mana.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void onLevelChange() {
		items.clear();

		itemType = 0;
		for (Point2D singleItemPosition : houston.map.multiSearch(90 + itemType)) {
			items.add(new Item(this, singleItemPosition, itemType));
		}
		itemType = 1;
		for (Point2D singleItemPosition : houston.map.multiSearch(90 + itemType)) {
			items.add(new Item(this, singleItemPosition, itemType));
		}
	}

	public void doGameUpdates() {
		for (int i = items.size() - 1; i >= 0; i--) {
			item = items.get(i);

			// Filtert die zu löschenden Magics raus
			if (item.remove) {
				items.remove(i);
				continue;
			}
			// Prüft, ob das Item sich mit dem Player überschneidet
			if (item.bounds.intersects(houston.player.bounds)) {
				item.remove = true;
				pickUpItem(item.itemType);
			}
		}

	}

	private void pickUpItem(int itemType) {
		switch (itemType) {
		case 0:
			houston.inventory.addHealthPack();
			break;
		case 1:
			houston.inventory.addManaPotion();
			break;
		}
	}

}