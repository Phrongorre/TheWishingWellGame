package cs328.uidaho.tww.actors;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class Building extends Collidable {
	
	public Building(float x, float y, float w, float d, float xOffset, float yOffset, String fileName, Stage s) {
		super(x, y, s);
		
		this.loadTexture(fileName);
		
		this.setCollisionShape(Collidable.COLLISION_SQUARE);
		this.collisionXshear = -2f/3f;
		this.setCollisionSize(w, d);
		this.setCollisionLocation(xOffset, yOffset + d/2f);
		this.setZChangeY(yOffset + d, yOffset);
	}
	
}
