package cs328.uidaho.tww.actors.util;

import com.badlogic.gdx.math.Polygon;

public interface IInteractable {
	public void setInteractable(boolean interactable);
	public boolean isInteractable();
	public void interact();
	public Polygon getBoundaryPolygon();
	
}
