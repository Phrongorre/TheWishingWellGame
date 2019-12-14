package cs328.uidaho.tww.actors.person.npc;

import com.badlogic.gdx.utils.Array;

public class Discussion {
	
	private Array<Blurb> blurbs;
	private int bIndex;
	
	public Discussion() {
		this.blurbs = new Array<Blurb>();
		this.bIndex = -1;
	}
	
	public boolean hasBlurbs() {
		return this.blurbs.size > 0;
	}
	
	public int addBlurb(Blurb blurb) {
		if (!this.hasBlurbs()) this.bIndex = 0;
		this.blurbs.add(blurb);
		return this.blurbs.size-1;
	}
	
	public Blurb getBlurb(int bIndex) {
		Blurb blurb = null;
		if (this.hasBlurbs()) {
			this.bIndex = (bIndex+1)%this.blurbs.size;
			blurb = this.blurbs.get(bIndex);
		}
		return blurb;
	}
	
	public Blurb getBlurb() {
		return this.getBlurb(this.bIndex);
	}
}
