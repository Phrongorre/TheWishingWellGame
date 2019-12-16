package cs328.uidaho.tww.actors.person.player;

import com.badlogic.gdx.scenes.scene2d.Stage;

import cs328.uidaho.tww.actors.Collidable;

public class Item extends Collidable {
	
	private static int ID = 0;
	
	private int id;
	private String name;
	
	public Item(String name, float x, float y, Stage s) {
		super(x, y, s);
		
		this.id = Item.ID++;
		this.name = name;
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
	
}
