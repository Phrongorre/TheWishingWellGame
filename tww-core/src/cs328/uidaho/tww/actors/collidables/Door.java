package cs328.uidaho.tww.actors.collidables;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import cs328.uidaho.tww.BaseGame;
import cs328.uidaho.tww.GameMetaData;
import cs328.uidaho.tww.actors.collidables.person.Player;
import cs328.uidaho.tww.actors.util.IInteractable;
import cs328.uidaho.tww.screens.BaseScreen;

public class Door extends Collidable implements IInteractable {
	
	private boolean interactable;
	private Class<? extends BaseScreen> screenClass;
	private float spawnX;
	private float spawnY;
	private boolean locked;
	private LockBox lock;
	
	public Door(float x, float y, float spawnX, float spawnY, Class<? extends BaseScreen> targetScreenClass, Stage s) {
		super(x, y, s);
		
		this.setCollisionShape(COLLISION_SQUARE);
		this.setCollisionSize(15f, 5f);
		this.setCollisionLocation(0f, 2.5f);
		
		this.interactable = true;
		this.screenClass = targetScreenClass;
		this.spawnX = spawnX;
		this.spawnY = spawnY;
		this.locked = false;
		this.lock = new LockBox(this.getStage());
		this.lock.setVisible(false);
		this.lock.setPhysicalCollisions(false);
		this.lock.setConsumesKeys(true);
		this.lock.setUnlockAction(Actions.run(
			() -> {
				this.setLocked(false);
				this.lock.clearKeys();
			}
		));
		
		this.setPhysicalCollisions(false);
	}
	
	public Door(float x, float y, Stage s) { this(x, y, 0f, 0f, null, s); }
	
	public void lockWithKey(String keyItemName) {
		this.setLocked(true);
		this.lock.addKey(keyItemName);
	}
	
	@Override
	public void setInteractable(boolean interactable) {
		this.interactable = interactable;
	}

	@Override
	public boolean isInteractable() {
		return this.interactable;
	}
	
	public void setLocked(boolean state) {
		this.locked = state;
		this.lock.clearKeys();
	}
	
	public void setSpawnLocation(float x, float y) {
		this.spawnX = x;
		this.spawnY = y;
	}
	
	public void setTargetScreen(Class<? extends BaseScreen> screenClass) {
		this.screenClass = screenClass;
	}

	@Override
	public void interact(Player interactingPlayer) {
		if (!this.locked || this.lock.attemptRewards(interactingPlayer.getInventory()) != null) {
			if (this.screenClass != null) {
				try {
					GameMetaData.setSpawnLocation(this.spawnX, this.spawnY);
					BaseScreen screen = GameMetaData.getScreen(this.screenClass);
					GameMetaData.setInventoryStage(screen.getUiStage());
					BaseGame.setActiveScreen(screen);
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

}
