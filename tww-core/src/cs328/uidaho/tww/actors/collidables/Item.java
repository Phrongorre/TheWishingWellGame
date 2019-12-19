package cs328.uidaho.tww.actors.collidables;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;

import cs328.uidaho.tww.GameMetaData;
import cs328.uidaho.tww.actors.collidables.person.Player;
import cs328.uidaho.tww.actors.util.IInteractable;

public class Item extends Collidable implements IInteractable {
	
	private static int ID = 0;
	
	private int id;
	private String name;
	private boolean interactable;
	
	private Animation<TextureRegion> worldAnimation;
	private Animation<TextureRegion> inventoryAnimation;
	
	public Item(String name, float x, float y, Stage s) {
		super(x, y, s);
		
		this.id = Item.ID++;
		this.name = name;
		
		this.worldAnimation = this.animator.loadTexture("items/" + name + "_world.png");
		this.inventoryAnimation = this.animator.loadTexture("items/" + name + "_inventory.png");
		
		this.setAnimation(this.worldAnimation);
		
		this.interactable = true;
		this.setPhysicalCollisions(false);
	}
	
	public Item(Item other) {
		super(other.getX(), other.getY(), other.getStage());
		this.id = Item.ID++;
		this.name = other.name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public int id() {
		return this.id;
	}
	
	@Override
	public void interact(Player interactingPlayer) {
		if (GameMetaData.debugMode()) {
			System.out.print(this.id);
			System.out.println(": " + this.name);
		}
		this.setAnimation(this.inventoryAnimation);
		interactingPlayer.getInventory().addItem(this);
	}

	@Override
	public boolean isInteractable() {
		return this.interactable;
	}

	@Override
	public void setInteractable(boolean interactable) {
		this.interactable = interactable;
	}	
}
