package cs328.uidaho.tww.actors;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class Building extends Collidable {
	
	public Building(float x, float y, float w, float d, float xShear, String fileName, Stage s) {
		super(x, y, s);
		
		this.loadTexture(fileName);
		
		this.setCollisionShape(Collidable.COLLISION_SQUARE);
		this.collisionXshear = xShear;
		this.setCollisionSize(w, d);
		this.setCollisionLocation(0f, d/2f);
		this.setZChangeY(d, 0f);
	}
	
}
