package cs328.uidaho.tww.actors.person.player;

import com.badlogic.gdx.utils.Array;

public class Inventory {
	
	private Array<Item> contents;
	private int maxSize;
	
	public Inventory() {
		this.contents = new Array<Item>();
		this.maxSize = 12;
	}
	
	public boolean addItem(Item item) {
		if (this.contents.size >= this.maxSize) return false;
		
		this.contents.add(item);
		
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
