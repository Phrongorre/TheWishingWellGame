package cs328.uidaho.tww.actors.collidables.person.npc;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

import cs328.uidaho.tww.GameMetaData;

public class LockPrompt extends Prompt {
	
	private String unlockItemName;
	private Action unlockAction;
	private Array<Response> lockedResponses;
	private Array<Response> unlockedResponses;
	
	public LockPrompt(String prompt, String unlockItemName) {
		super(prompt);
		
		this.unlockItemName = unlockItemName;
		this.unlockAction = null;
		this.lockedResponses = new Array<Response>();
		this.unlockedResponses = new Array<Response>();
	}
	
	@Override
	public Prompt setPrompt(String prompt) {
		this.prompt = prompt;
		return this;
	}
	
	@Override
	public String prompt() {
		return this.prompt;
	}
	
	@Override
	public LockPrompt addResponse(String response, Prompt follow) {
		this.addLockedResponse(response, follow);
		this.addUnlockedResponse(response, follow);
		return this;
	}
	
	public LockPrompt addLockedResponse(String response, Prompt follow) {
		this.lockedResponses.add(new Response(response, follow));
		return this;
	}
	
	public LockPrompt addUnlockedResponse(String response, Prompt follow) {
		this.unlockedResponses.add(new Response(response, follow));
		return this;
	}
	
	@Override
	public LockPrompt addResponse(Response response) {
		this.addLockedResponse(response.response(), response.follow());
		this.addUnlockedResponse(response.response(), response.follow());
		return this;
	}
	
	@Override
	public Array<Response> responses() {
		if (GameMetaData.getInventory().contains(this.unlockItemName)) {
			return this.unlockedResponses;
		}
		else {
			return this.lockedResponses;
		}
	}
	
	@Override
	public Array<Prompt> followPrompts() {
		Array<Prompt> prompts = new Array<Prompt>();
		for (Response response : this.responses()) {
			if (response != null) {
				prompts.add(response.follow());
			}
		}
		return prompts;
	}
	
	@Override
	public String toString() {
		String str = this.prompt + "\n";
		for (Response response : this.responses()) {
			if (response != null) {
				str += "\n" + response.response();
			}
		}
		return str;
	}
	
	public void setUnlockAction(Action a) {
		this.unlockAction = a;
	}
	
	public void unlockAction(Actor a) {
		if (GameMetaData.getInventory().contains(this.unlockItemName)) {
			GameMetaData.getInventory().removeItem(this.unlockItemName);
			if (this.unlockAction != null) {
				a.clearActions();
				a.addAction(this.unlockAction);
			}
		}
	}

	public LockPrompt addLockedResponse(Response response) {
		this.lockedResponses.add(response);
		return this;
	}
	
}
