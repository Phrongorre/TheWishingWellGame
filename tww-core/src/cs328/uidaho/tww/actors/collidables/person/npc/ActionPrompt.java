package cs328.uidaho.tww.actors.collidables.person.npc;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ActionPrompt extends Prompt {
	
	protected Action promptAction;
	
	public ActionPrompt(String prompt) {
		super(prompt);
		
		this.promptAction = null;
	}
	
	public ActionPrompt setPromptAction(Action a) {
		this.promptAction = a;
		return this;
	}
	
	public void activateAction(Actor a) {
		if (this.promptAction != null) {
			a.clearActions();
			a.addAction(this.promptAction);
		}
	}

}
