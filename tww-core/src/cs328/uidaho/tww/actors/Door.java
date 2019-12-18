package cs328.uidaho.tww.actors;

import com.badlogic.gdx.scenes.scene2d.Stage;

import cs328.uidaho.tww.BaseGame;
import cs328.uidaho.tww.GameMetaData;
import cs328.uidaho.tww.actors.util.IInteractable;
import cs328.uidaho.tww.screens.BaseScreen;

public class Door extends Collidable implements IInteractable {
	
	private boolean interactable;
	private Class<? extends BaseScreen> screenClass;
	private float spawnX;
	private float spawnY;
	
	public Door(float x, float y, float spawnX, float spawnY, Class<? extends BaseScreen> targetScreenClass, Stage s) {
		super(x, y, s);
		
		this.setCollisionShape(COLLISION_SQUARE);
		this.setCollisionSize(15f, 5f);
		this.setCollisionLocation(0f, 2.5f);
		
		this.interactable = true;
		this.screenClass = targetScreenClass;
		this.spawnX = spawnX;
		this.spawnY = spawnY;
	}
	
	public Door(float x, float y, Stage s) { this(x, y, 0f, 0f, null, s); }

	@Override
	public void setInteractable(boolean interactable) {
		this.interactable = interactable;
	}

	@Override
	public boolean isInteractable() {
		return this.interactable;
	}
	
	public void setSpawnLocation(float x, float y) {
		this.spawnX = x;
		this.spawnY = y;
	}
	
	public void setTargetScreen(Class<? extends BaseScreen> screenClass) {
		this.screenClass = screenClass;
	}

	@Override
	public void interact() {
		if (this.screenClass != null) {
			try
			{
				GameMetaData.setSpawnLocation(this.spawnX, this.spawnY);
				BaseGame.setActiveScreen(this.screenClass.newInstance());
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

}
