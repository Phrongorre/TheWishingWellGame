package cs328.uidaho.tww.actors.collidables;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;

import cs328.uidaho.tww.actors.collidables.person.Player;
import cs328.uidaho.tww.actors.util.IInteractable;
import cs328.uidaho.tww.gui.Inventory;

public class LockBox extends Collidable implements IInteractable {
	
	private Array<String> keyItemNames;
	private Array<Item> rewards;
	private Action unlockAction;
	private boolean interactable;
	private boolean consumesKeys;
	
	public LockBox(float x, float y, String textureFileName, Stage s) {
		super(x, y, textureFileName, s);
		
		this.keyItemNames = new Array<String>();
		this.rewards = new Array<Item>();
		this.unlockAction = null;
		this.interactable = true;
		this.consumesKeys = true;
	}
	
	public LockBox clearKeys() {
		this.keyItemNames.clear();
		return this;
	}
	
	public LockBox addKey(String keyItemName) {
		this.keyItemNames.add(keyItemName);
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
	
	public void setUnlockAction(Action a) {
		this.unlockAction = a;
	}
	
	public boolean isUnlocked(Inventory inventory) {
		for (String keyItemName : this.keyItemNames) {
			if (!inventory.contains(keyItemName)) {
				return false;
			}
		}
		return true;
	}
	
	public Array<Item> attemptRewards(Inventory inventory) {
		if (this.isUnlocked(inventory)) {
			if (this.consumesKeys) {
				for (String keyItemName : this.keyItemNames) {
					inventory.removeItem(keyItemName);
				}
			}
			if (this.unlockAction != null) {
				this.clearActions();
				this.addAction(this.unlockAction);
			}
			return this.rewards;
		}
		return null;
	}

	@Override
	public void setInteractable(boolean interactable) {
		this.interactable = interactable;
	}

	@Override
	public boolean isInteractable() {
		return this.interactable;
	}

	@Override
	public void interact(Player interactingPlayer) {
		Inventory inventory = interactingPlayer.getInventory();
		Array<Item> rewardItems = this.attemptRewards(inventory);
		if (rewardItems != null) {
			for (Item rewardItem : rewardItems) {
				rewardItem.interact(interactingPlayer);
			}
		}
	}
	
}
