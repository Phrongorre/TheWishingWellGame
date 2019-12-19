package cs328.uidaho.tww.actors;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;

import cs328.uidaho.tww.actors.person.player.Inventory;
import cs328.uidaho.tww.actors.person.player.Item;
import cs328.uidaho.tww.actors.scenes.Scene;
import cs328.uidaho.tww.actors.scenes.SceneSegment;

public class LockBox extends Collidable {
	
	private Array<Item> keys;
	private Array<Item> rewards;
	private Scene unlockScene;
	
	public LockBox(float x, float y, Stage s) {
		super(x, y, s);
		
		this.keys = new Array<Item>();
		this.rewards = new Array<Item>();
		this.unlockScene = new Scene();
	}
	
	public LockBox clearKeys() {
		this.keys.clear();
		return this;
	}
	
	public LockBox addKey(Item keyItem) {
		this.keys.add(keyItem);
		return this;
	}
	
	public LockBox clearRewards() {
		this.rewards.clear();
		return this;
	}
	
	public LockBox addReward(Item rewardItem) {
		this.rewards.add(rewardItem);
		return this;
	}
	
	public LockBox clearUnlockScene() {
		this.unlockScene.clear();
		return this;
	}
	
	public LockBox addUnlockSceneSegment(SceneSegment segment) {
		this.unlockScene.addSegment(segment);
		return this;
	}
	
	public boolean isUnlocked(Inventory inventory) {
		boolean match;
		for (Item key : keys.items) {
			match = false;
			for (Item item : inventory.contents()) {
				if (item.equals(key)) {
					match = true;
					break;
				}
			}
			if (!match) return false;
		}
		return true;
	}
	
	public Array<Item> attemptRewards(Inventory inventory) {
		if (this.isUnlocked(inventory)) {
			this.getStage().addActor(this.unlockScene);
			this.unlockScene.start();
			return this.rewards;
		}
		return null;
	}
	
}
