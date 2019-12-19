package cs328.uidaho.tww.actors.collidables.person.npc;

import com.badlogic.gdx.utils.Array;

public class Discussion {
	
	private Array<Prompt> prompts;
	private int pIndex;
	
	public Discussion() {
		this.prompts = new Array<Prompt>();
		this.pIndex = -1;
	}
	
	public boolean hasPrompts() {
		return this.prompts.size > 0;
	}
	
	public Discussion addPrompt(Prompt prompt) {
		if (prompt == null) { return this; }
		if (!this.hasPrompts()) this.pIndex = 0;
		this.prompts.add(prompt);
		return this;
	}
	
	public Prompt getPrompt(int pIndex) {
		Prompt prompt = null;
		if (this.hasPrompts()) {
			this.pIndex = (pIndex+1)%this.prompts.size;
			prompt = this.prompts.get(pIndex);
		}
		return prompt;
	}
	
	public Prompt getPrompt() {
		return this.getPrompt(this.pIndex);
	}
}
