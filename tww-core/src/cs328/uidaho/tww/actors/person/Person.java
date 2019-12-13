package cs328.uidaho.tww.actors.person;

import com.badlogic.gdx.scenes.scene2d.Stage;

import cs328.uidaho.tww.actors.BaseActor;

public class Person extends BaseActor {
	
	public Person(float x, float y, Stage s) {
		super(x, y, s);
		
		this.loadTexture("people/people.png");
	}
	
}
