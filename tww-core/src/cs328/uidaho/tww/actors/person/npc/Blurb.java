package cs328.uidaho.tww.actors.person.npc;

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
		this.setFollowPrompt(0, follow);
	}
	
	@Override
	public void setFollowPrompt(int index, Prompt follow) {
		if (index == 0) this.setFollowPrompt(follow);
		return;
	}
	
	@Override
	public Prompt addResponse(String response, Prompt follow) {
		return this;
	}
	
}
