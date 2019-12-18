package cs328.uidaho.tww.actors.util;

import com.badlogic.gdx.math.Polygon;

import cs328.uidaho.tww.actors.person.player.Player;

public interface IInteractable {
	public void setInteractable(boolean interactable);
	public boolean isInteractable();
	public void interact(Player interactingPlayer);
	public Polygon getBoundaryPolygon();
	
}
