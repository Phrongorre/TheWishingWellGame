package cs328.uidaho.tww.actors.collidables.person.npc;

public class Blurb extends Prompt {
	
	public Blurb(String prompt, Prompt follow) {
		super(prompt);
		super.addResponse("Next", follow);
	}
	
	public Blurb(String prompt) {
		super(prompt);
		super.addResponse("Next", null);
	}
	
	public void setFollowPrompt(Prompt follow) {
		this.responses().get(0).setFollowPrompt(follow);
	}
	
	@Override
	public Prompt addResponse(String response, Prompt follow) {
		return this;
	}
	
}
