package cs328.uidaho.tww;

public class GameMetaData {
	
	private static float spawnX;
	private static float spawnY;
	
	public static void initialize() {
		GameMetaData.spawnX = 0f;
		GameMetaData.spawnY = 0f;
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
	
}
