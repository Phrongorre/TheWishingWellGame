package cs328.uidaho.tww.actors.person;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Player extends Person {
	
	public Player(float x, float y, Stage s) {
		super(x, y, s);
		
		this.setMaxSpeed(200f);
		this.setAcceleration(4000f);
		this.setDeceleration(4000f);
	}
	
	@Override
	public void act(float dt) {
		if (Gdx.input.isKeyPressed(Keys.LEFT)) {
			this.accelerateAtAngle(180f);
		}
		if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
			this.accelerateAtAngle(0f);
		}
		if (Gdx.input.isKeyPressed(Keys.UP)) {
			this.accelerateAtAngle(90f);
		}
		if (Gdx.input.isKeyPressed(Keys.DOWN)) {
			this.accelerateAtAngle(270f);
		}
		
		this.applyPhysics(dt);
		this.boundToWorld();
	}
	
}
