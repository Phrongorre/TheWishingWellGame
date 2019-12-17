package cs328.uidaho.tww.actors.person.player;

import com.badlogic.gdx.scenes.scene2d.Stage;

import cs328.uidaho.tww.actors.Collidable;
import cs328.uidaho.tww.actors.util.IInteractable;

public class Item extends Collidable implements IInteractable {
	
	private static int ID = 0;
	
	private int id;
	private String name;
	private boolean interactable;
	
	public Item(String name, float x, float y, Stage s) {
		super(x, y, s);
		
		this.id = Item.ID++;
		this.name = name;
		
		this.loadTexture("items/" + name + "_world.png");
		
		this.interactable = true;
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
	public void interact() {
		System.out.print(this.id);
		System.out.println(": " + this.name);
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
