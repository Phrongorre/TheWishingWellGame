package cs328.uidaho.tww.actors.person;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;

import cs328.uidaho.tww.actors.Collidable;

public class Person extends Collidable {
	
	public Person(float x, float y, Stage s) {
		super(x, y, s);
	}
	
	@Override
	public Animation<TextureRegion> loadTexture(String fileName) {
		Animation<TextureRegion> anim = super.loadTexture(fileName);
		
		this.setCollisionSize(this.getWidth()*1.5f, this.getWidth()*0.75f);
		
		return anim;
	}
	
}
