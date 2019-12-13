package cs328.uidaho.tww.actors;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class Person extends BaseActor {
	
	public Person(float x, float y, Stage s) {
		super(x, y, s);
		
		this.loadTexture("people.png");
	}
	
}
