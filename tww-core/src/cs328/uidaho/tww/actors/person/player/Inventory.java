package cs328.uidaho.tww.actors.person.player;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;

import cs328.uidaho.tww.actors.BaseActor;

public class Inventory extends BaseActor {
	
	private Array<Item> contents;
	private int maxSize;
	private float slotWidth;
	private float slotHeight;
	private int slotsAcross;
	
	public Inventory(Table uiTable) {
		super(0f, 0f, uiTable.getStage());
		
		this.loadTexture("gui/inventory.png");
		uiTable.add(this);		
		
		this.contents = new Array<Item>();
		this.maxSize = 12;
		
		this.slotWidth = 16f;
		this.slotHeight = 16f;
		this.slotsAcross = 3;
	}
	
	public boolean addItem(Item item) {
		if (this.contents.size >= this.maxSize) return false;
		
		this.contents.add(item);
		item.setPosition(
			this.getX() + this.slotWidth  * (this.contents.size % this.slotsAcross),
			this.getY() - this.slotHeight * (this.contents.size / this.slotsAcross)
		);
		this.addActor(item);
		
		return true;
	}
	
	public Item removeItem(String name) {
		if (this.contents.size == 0) return null;
		
		for (Item item : this.contents.items) {
			if (item.getName() == name) {
				this.contents.removeValue(item, true);
				return item;
			}
		}
		
		return null;
	}
	
}
