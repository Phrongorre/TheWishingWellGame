package cs328.uidaho.tww.actors;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;

import cs328.uidaho.tww.screens.rooms.Room;

public class Building extends Collidable {
	
	private Animation<TextureRegion> openAnimation;
	private Animation<TextureRegion> closedAnimation;
	private Door entranceDoor;
	
	public Building(
		float x, float y, float w, float d, float xOffset, float yOffset,
		float doorX, float doorY, float doorSpawnX, float doorSpawnY, Class <? extends Room> interiorRoomClass,
		String buildingName, Stage s
	) {
		super(x, y, s);
		
		this.openAnimation = this.loadTexture("locations/" + buildingName + "_open.png");
		this.closedAnimation = this.loadTexture("locations/" + buildingName + "_closed.png");
		this.entranceDoor = new Door(doorX, doorY, doorSpawnX, doorSpawnY, interiorRoomClass, s);
		this.open();
		
		this.setCollisionShape(Collidable.COLLISION_SQUARE);
		this.collisionXshear = -2f/3f;
		this.setCollisionSize(w, d);
		this.setCollisionLocation(xOffset, yOffset + d/2f);
		this.setZChangeY(yOffset + d, yOffset);
	}
	
	public void setInteriorRoom(Class <? extends Room> interiorRoomClass) {
		this.entranceDoor.setTargetScreen(interiorRoomClass);
	}
	
	public void setDoorLocation(float x, float y) {
		this.entranceDoor.setPosition(x, y);
	}
	
	public void open() {
		this.setAnimation(this.openAnimation);
		this.entranceDoor.setInteractable(true);
	}
	
	public void close() {
		this.setAnimation(this.closedAnimation);
		this.entranceDoor.setInteractable(false);
	}
	
}
