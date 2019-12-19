package cs328.uidaho.tww.actors.person.player;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;

import cs328.uidaho.tww.actors.BaseActor;

public class Inventory extends BaseActor {
	
	private Array<Item> contents;
	private int maxSize;
	private final float slotWidth = 48f;
	private final float slotHeight = 48f;
	private final int slotsWide = 3;
	private final float padding = 9f;
	
	public Inventory(Stage s) {
		super(0f, 0f, s);
		
		this.contents = new Array<Item>();
		this.maxSize = 12;
		
		this.loadTexture("gui/inventory.png");
		this.setPosition(s.getCamera().viewportWidth, s.getCamera().viewportHeight, Align.topRight);
	}
	
	public boolean addItem(Item item) {
		if (this.contents.size >= this.maxSize) return false;
		
		item.remove();
		this.getStage().addActor(item);
		
		int i = this.contents.size;
		this.contents.add(item);
		item.setPosition(
			(i%this.slotsWide)*this.slotWidth+this.padding+this.getX(Align.topLeft),
			(i/this.slotsWide)*this.slotHeight-this.padding+this.getY(Align.topLeft),
			Align.topLeft
		);
		
		return true;
	}
	
	public Item removeItem(String name) {
		if (this.contents.size == 0) return null;
		
		for (Item item : this.contents.items) {
			if (item.getName() == name) {
				this.contents.removeValue(item, true);
				item.remove();
				return item;
			}
		}
		
		return null;
	}
	
	public boolean contains(Item item) {
		for (Item inventoryItem : this.contents) {
			if (item == inventoryItem) return true;
		}
		return false;
	}
	
	public Array<Item> contents() {
		return this.contents;
	}
	
}
