package cs328.uidaho.tww.actors.collidables.person.npc;

import com.badlogic.gdx.utils.Array;

public class Prompt {
	
	protected String prompt;
	protected Array<Response> responses;
	
	public Prompt(String prompt) {
		this.prompt = prompt;
		this.responses = new Array<Response>();
	}
	
	public Prompt(String prompt, Response response) {
		this(prompt);
		this.responses.add(response);
	}
	
	public Prompt setPrompt(String prompt) {
		this.prompt = prompt;
		return this;
	}
	
	public String prompt() {
		return this.prompt;
	}
	
	public Prompt addResponse(String response, Prompt follow) {
		this.responses.add(new Response(response, follow));
		return this;
	}
	
	public Prompt addResponse(Response response) {
		this.responses.add(response);
		return this;
	}
	
	public Array<Response> responses() {
		return this.responses;
	}
	
	public Array<Prompt> followPrompts() {
		Array<Prompt> prompts = new Array<Prompt>();
		for (Response response : this.responses) {
			if (response != null) {
				prompts.add(response.follow());
			}
		}
		return prompts;
	}
	
	public String toString() {
		String str = this.prompt + "\n";
		for (Response response : this.responses) {
			if (response != null) {
				str += "\n" + response.response();
			}
		}
		return str;
	}
	
}
