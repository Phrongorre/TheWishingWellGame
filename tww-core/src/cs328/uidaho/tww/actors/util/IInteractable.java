package cs328.uidaho.tww.actors.util;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.scenes.scene2d.Stage;

import cs328.uidaho.tww.actors.Collidable;

public interface IInteractable {
	public void setInteractable(boolean interactable);
	public boolean isInteractable();
	public void interact();
	public Polygon getBoundaryPolygon();
	
}
