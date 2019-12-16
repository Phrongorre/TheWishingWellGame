package cs328.uidaho.tww.actors.person.npc;

import com.badlogic.gdx.utils.Array;

public class Prompt {
	
	private String prompt;
	private Array<Response> responses;
	
	public Prompt(String prompt) {
		this.prompt = prompt;
		this.responses = new Array<Response>();
	}
	
	public void copy(Prompt other) {
		this.prompt = other.prompt;
		this.responses.clear();
		for (Response response : other.responses) {
			this.responses.add(response);
		}
	}
	
	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}
	
	public String prompt() {
		return this.prompt;
	}
	
	public int addResponse(String response, Prompt follow) {
		this.responses.add(new Response(response, follow));
		return this.responses.size-1;
	}
	
	public String response(int index) {
		Response response = this.responses.get(index);
		if (response != null) return response.response;
		return null;
	}
	
	public Response[] responses() {
		return this.responses();
	}
	
	public Prompt[] followPrompts() {
		Prompt[] prompts = new Prompt[this.responses.size];
		for (int r = 0; r < this.responses.size; r++) {
			if (this.responses.get(r) != null) {
				prompts[r] = this.responses.get(r).follow;
			}
			else {
				prompts[r] = null;
			}
		}
		return prompts;
	}
	
	public String toString() {
		String str = this.prompt + "\n";
		for (Response response : this.responses) {
			if (response != null) {
				str += "\n" + response.response;
			}
		}
		return str;
	}
	
	public int responseCount() {
		return this.responses.size;
	}
	
	public void setFollowPrompt(int index, Prompt follow) {
		Response response = this.responses.get(index);
		if (response != null) response.follow = follow;
	}
	
	public Prompt followResponse(int index) {
		Response response = this.responses.get(index);
		if (response != null) return response.follow;
		return null;
	}
	
	public class Response {
		
		public String response;
		public Prompt follow;
		
		public Response(String response, Prompt follow) {
			this.response = response;
			this.follow = follow;
		}
		
	}
	
}
