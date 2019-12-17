package cs328.uidaho.tww.actors.util;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;

import cs328.uidaho.tww.actors.BaseActor;

public class Collider extends BaseActor {
	
	public static final int COLLISION_SQUARE = 0;
	public static final int COLLISION_ROUND  = 1;
	
	private Polygon boundaryPolygon;
	private Array<TextureRegion> collisionWireframes;
	private boolean wireframesVisible;
	
	private float collisionWidth;
	private float collisionHeight;
	private float collisionX;
	private float collisionY;
	private float collisionXshear;
	private int   collisionShape;
	
	public Collider(float x, float y, Stage s) {
		super(x, y, s);
	}
	
}
