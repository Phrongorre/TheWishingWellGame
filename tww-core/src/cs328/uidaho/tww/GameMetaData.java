package cs328.uidaho.tww;

import com.badlogic.gdx.scenes.scene2d.Stage;

import cs328.uidaho.tww.actors.person.player.Inventory;
import cs328.uidaho.tww.actors.person.player.Item;

public class GameMetaData {
	
	private static float spawnX;
	private static float spawnY;
	private static Inventory inventory;
	
	public static void initialize() {
		GameMetaData.spawnX = 0f;
		GameMetaData.spawnY = 0f;
		GameMetaData.inventory = null;
	}
	
	public static void setSpawnLocation(float x, float y) {
		GameMetaData.spawnX = x;
		GameMetaData.spawnY = y;
	}
	
	public static float getSpawnX() {
		return GameMetaData.spawnX;
	}
	
	public static float getSpawnY() {
		return GameMetaData.spawnY;
	}
	
	public static void setInventory(Inventory inventory) {
		GameMetaData.inventory = inventory;
	}
	
	public static Inventory getInventory() {
		return GameMetaData.inventory;
	}

	public static void setInventoryStage(Stage uiStage) {
		GameMetaData.inventory.remove();
		uiStage.addActor(GameMetaData.inventory);
		for (Item item : GameMetaData.inventory.contents()) {
			item.remove();
			uiStage.addActor(item);
		}
	}
	
}
