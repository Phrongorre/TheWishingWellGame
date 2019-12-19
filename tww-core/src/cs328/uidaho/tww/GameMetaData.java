package cs328.uidaho.tww;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;

import cs328.uidaho.tww.actors.person.player.Inventory;
import cs328.uidaho.tww.actors.person.player.Item;
import cs328.uidaho.tww.screens.BaseScreen;

public class GameMetaData {
	
	private static float spawnX;
	private static float spawnY;
	private static Inventory inventory;
	private static Array<BaseScreen> gameScreens;
	
	public static void initialize() {
		GameMetaData.spawnX = 0f;
		GameMetaData.spawnY = 0f;
		GameMetaData.inventory = null;
		GameMetaData.gameScreens = new Array<BaseScreen>();
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
	
	public static void registerScreen(BaseScreen screen) {
		if (!GameMetaData.gameScreens.contains(screen, false)) {
			GameMetaData.gameScreens.add(screen);
		}
	}
	
	//Attempt to get registered instance of screenClass
	//If instance is not registered, register a new instance and return that
	public static BaseScreen getScreen(Class<? extends BaseScreen> screenClass) {
		for (int s=0; s < GameMetaData.gameScreens.size; s++) {
			try {
				if (screenClass.isInstance(GameMetaData.gameScreens.get(s))) {
					return screenClass.cast(GameMetaData.gameScreens.get(s));
				}
			}
			catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		
		try {
			BaseScreen screenInstance = screenClass.newInstance();
			GameMetaData.registerScreen(screenClass.cast(screenInstance));
			return screenClass.cast(screenInstance);
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
