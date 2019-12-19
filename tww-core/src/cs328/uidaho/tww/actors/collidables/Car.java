package cs328.uidaho.tww.actors.collidables;

import java.util.Random;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class Car extends Collidable {

	public Car(float x, float y, Stage s) {
		super(x, y, s);
		
		Random random = new Random();
		int r = random.nextInt(3);
		switch (r) {
		case 0:
			this.animator.loadTexture("locations/clove_haven/car_black.png");
			break;
		case 1:
			this.animator.loadTexture("locations/clove_haven/car_white.png");
			break;
		case 2:
			this.animator.loadTexture("locations/clove_haven/car_red.png");
			break;
		};
		
		this.setCollisionSize(this.getWidth(), this.getHeight()/2f);
		this.setCollisionLocation(0f, this.collisionHeight/2f);
	}
	
	public Car(int parkingSpotIndex, String textureFileName, Stage s) {
		super(114f + 58f*parkingSpotIndex, 33f, s);
		this.animator.loadTexture(textureFileName);
		this.setCollisionSize(this.getWidth(), this.getHeight()/2f);
		this.setCollisionLocation(0f, this.collisionHeight/2f);
	}
	
}
