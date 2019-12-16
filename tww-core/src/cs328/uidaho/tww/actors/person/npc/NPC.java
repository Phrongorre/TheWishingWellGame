package cs328.uidaho.tww.actors.person.npc;

import java.util.Random;

import com.badlogic.gdx.scenes.scene2d.Stage;

import cs328.uidaho.tww.actors.person.Person;

public class NPC extends Person {
	
	private Discussion discussion;
	
	public NPC(float x, float y, Stage s) {
		super(x, y, s);
		
		Random random = new Random();
		int r = random.nextInt(4);
		switch (r) {
		case 0:
			this.loadTexture("people/person_awm0.png");
			break;
		case 1:
			this.loadTexture("people/person_adm0.png");
			break;
		case 2:
			this.loadTexture("people/person_lwm0.png");
			break;
		case 3:
			this.loadTexture("people/person_ldm0.png");
			break;
		};
		
		this.discussion = new Discussion();
		int i = this.discussion.addBlurb(new Blurb("Hello!"));
		System.out.println(i);
	}
	
	public int addBlurb(String content) {
		return this.discussion.addBlurb(new Blurb(content));
	}
	
	public String getNextBlurb() {
		return this.discussion.getBlurb().content();
	}
	
}
