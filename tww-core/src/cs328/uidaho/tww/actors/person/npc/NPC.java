package cs328.uidaho.tww.actors.person.npc;

import com.badlogic.gdx.scenes.scene2d.Stage;

import cs328.uidaho.tww.actors.person.Person;

public class NPC extends Person {
	
	private Discussion discussion;
	
	public NPC(float x, float y, Stage s) {
		super(x, y, s);
		
		this.discussion = new Discussion();
		this.discussion.addBlurb(new Blurb("Hello!"));
	}
	
	public int addBlurb(String content) {
		return this.discussion.addBlurb(new Blurb(content));
	}
	
}
