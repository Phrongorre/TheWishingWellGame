package cs328.uidaho.tww.actors.person.npc;

public class PromptHolder {
	
	private Prompt prompt;
	private boolean changed;
	
	public PromptHolder() {
		this.prompt = null;
		this.changed = false;
	}
	
	public void setPrompt(Prompt prompt) {
		if (this.prompt != prompt) {
			this.prompt = prompt;
			this.changed = true;
		}
	}
	
	public Prompt prompt() {
		this.changed = false;
		return this.prompt;
	}
	
	public boolean hasChanged() {
		return this.changed;
	}
	
}
