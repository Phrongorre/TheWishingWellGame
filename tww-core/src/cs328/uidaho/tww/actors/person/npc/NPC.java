package cs328.uidaho.tww.actors.person.npc;

import java.util.Random;

import com.badlogic.gdx.scenes.scene2d.Stage;

import cs328.uidaho.tww.actors.person.Person;
import cs328.uidaho.tww.actors.util.IInteractable;

public class NPC extends Person implements IInteractable {
	
	private Discussion discussion;
	private boolean interactable;
	
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
		
		this.interactable = true;
	}
	
	public Discussion addPrompt(Prompt prompt) {
		return this.discussion.addPrompt(prompt);
	}
	
	public Prompt getNextPrompt() {
		return this.discussion.getPrompt();
	}

	@Override
	public boolean isInteractable() {
		return this.interactable;
	}
	
	@Override
	public void interact() { }

	@Override
	public void setInteractable(boolean interactable) {
		this.interactable = interactable;
	}
	
}
