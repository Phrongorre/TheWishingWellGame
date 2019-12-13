package cs328.uidaho.tww.actors.person.npc;

public class Blurb {
	
	private String content;
	
	public Blurb(String text) {
		this.content = text;
	}
	
	public void setContent(String text) {
		this.content = text;
	}
	
	public String content() {
		return this.content;
	}
	
}
