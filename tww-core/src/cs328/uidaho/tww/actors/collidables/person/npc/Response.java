package cs328.uidaho.tww.actors.collidables.person.npc;

public class Response {
	
	private String response;
	private Prompt follow;
	
	public Response(String response, Prompt follow) {
		this.response = response;
		this.follow = follow;
	}
	
	public Response setResponse(String response) {
		this.response = response;
		return this;
	}
	
	public String response() {
		return this.response;
	}
	
	public Response setFollowPrompt(Prompt follow) {
		this.follow = follow;
		return this;
	}
	
	public Prompt follow() {
		return this.follow;
	}
	
}
