package cs328.uidaho.tww.actors;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Intersector.MinimumTranslationVector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;

import cs328.uidaho.tww.actors.util.Animator;

public class BaseActor2 extends Group
{
	
	//Static fields
	private static Rectangle worldBounds;
	private static float gravity = 980f;
	//Animation fields
	protected Animator animator;
	//Physics fields
	private Vector2 velocityVec;
	private Vector2 accelerationVec;
	private float maxSpeed;
	private float drag;
	private boolean affectedByGravity;
	private boolean affectedByDrag;
	//Collisions fields
	private Polygon boundaryPolygon;
	
	/*** Constructor ***/
	
	public BaseActor2(float x, float y, Stage s)
	{
		//call constructor from Actor class
		super();
		//perform additional initialization tasks
		this.setPosition(x, y);
		s.addActor(this);
		
		//Animation field initialization
		this.animator = new Animator(this);
		//Physics field initialization
		this.velocityVec = new Vector2();
		this.accelerationVec = new Vector2();
		this.maxSpeed = 1000f;
		this.drag = 0f;
		this.affectedByGravity = false;
		this.affectedByDrag = true;
	}
	
	@Override
	public void act(float dt)
	{
		super.act(dt);
		this.animator.update(dt);
	}
	
	/*** Animation methods ***/
	
	@Override
	public void draw(Batch batch, float parentAlpha)
	{
		//apply color tint effect
		Color c = this.getColor();
		batch.setColor(c);
		
		if (this.animator != null && this.animator.hasAnimation() && this.isVisible())
		{
			batch.draw(
				this.animator.getKeyFrame(),
				this.getX(),
				this.getY(),
				this.getOriginX(),
				this.getOriginY(),
				this.getWidth(),
				this.getHeight(),
				this.getScaleX(),
				this.getScaleY(),
				this.getRotation()
			);
		}
		
		super.draw(batch, parentAlpha);
	}
	
	public Animator getAnimator()
	{
		return this.animator;
	}
	
	/*** Physics methods ***/
	
	public boolean isMoving()
	{
		return (this.velocityVec.len() > 0f);
	}
	
	public void setMaxSpeed(float s)
	{
		this.maxSpeed = s;
	}
	
	public void setVelocity(float x, float y)
	{
		this.velocityVec.set(x, y);
	}
	
	public void setVelocityX(float x)
	{
		this.velocityVec.x = x;
	}
	
	public void setVelocityY(float y)
	{
		this.velocityVec.y = y;
	}
	
	public Vector2 getVelocity()
	{
		return this.velocityVec;
	}
	
	public void setAcceleration(float x, float y)
	{
		this.accelerationVec.set(x, y);
	}
	
	//Sets deceleration rate due to total drag forces when moving
	//Tip: objects with larger surface area usually have larger drag
	public void setDrag(float d)
	{
		this.drag = d;
	}
	
	public boolean isAffectedByDrag()
	{
		return this.affectedByDrag;
	}
	
	public void setAffectedByDrag(boolean d)
	{
		this.affectedByDrag = d;
	}
	
	public void addVelocity(float x, float y)
	{
		this.velocityVec.add(x, y);
	}
	
	public void addAcceleration(float x, float y)
	{
		this.accelerationVec.add(x, y);
	}
	
	public void addDrag(float d)
	{
		this.drag += d;
	}
	
	public void applyPhysics(float dt)
	{
		//apply acceleration due to gravity if applicable
		if (this.affectedByGravity) { this.accelerationVec.set(this.accelerationVec.add(0f, -BaseActor2.gravity)); }
		//apply acceleration
		this.velocityVec.add(this.accelerationVec.x * dt, this.accelerationVec.y * dt);
		float speed = this.velocityVec.len();
		//apply drag when moving if applicable
		if (this.affectedByDrag && speed > 0f) { speed -= this.drag; }
		//keep speed within set bounds
		speed = MathUtils.clamp(speed, 0f, this.maxSpeed);
		//update velocity
		this.velocityVec.setLength(speed);
		//apply velocity
		this.moveBy(this.velocityVec.x * dt, this.velocityVec.y * dt);
		//reset acceleration
		this.accelerationVec.set(0f, 0f);
	}
	
	public static void setGravity(float g)
	{
		BaseActor2.gravity = g;
	}
	
	public void setAffectedByGravity(boolean g)
	{
		this.affectedByGravity = g;
	}
	
	public boolean isAffectedByGravity()
	{
		return this.affectedByGravity;
	}
	
	public void centerAtPosition(float x, float y)
	{
		this.setPosition(x - this.getWidth()/2, y - this.getHeight()/2);
	}
	
	public void centerAtActor(BaseActor2 other)
	{
		this.centerAtPosition(other.getX() + other.getWidth()/2, other.getY() + other.getHeight()/2);
	}
	
	/*** Collisions methods ***/
	
	
	@Override
	protected void sizeChanged()
	{
		this.setBoundaryRectangle();
	}
	
	public void setBoundaryRectangle()
	{
		float w = this.getWidth();
		float h = this.getHeight();
		float[] vertices = {0f, 0f, w, 0f, w, h, 0f, h};
		this.boundaryPolygon = new Polygon(vertices);
	}
	
	public void setBoundaryPolygon(int numSides)
	{
		float w = this.getWidth();
		float h = this.getHeight();
		float[] vertices = new float[2*numSides];
		
		for (int i=0; i < numSides; i++)
		{
			float angle = i * MathUtils.PI2 / numSides;
			//x-coordinate
			vertices[2*i] = w/2 * MathUtils.cos(angle) + w/2;
			//y-coordinate
			vertices[2*i+1] = h/2 * MathUtils.sin(angle) + h/2;
		}
		
		this.boundaryPolygon = new Polygon(vertices);
	}
	
	public Polygon getBoundaryPolygon()
	{
		this.boundaryPolygon.setPosition(this.getX(),  this.getY());
		this.boundaryPolygon.setOrigin(this.getOriginX(), this.getOriginY());
		this.boundaryPolygon.setRotation(this.getRotation());
		this.boundaryPolygon.setScale(this.getScaleX(), this.getScaleY());
		return this.boundaryPolygon;
	}
	
	public boolean hasBoundaryPolygon()
	{
		return (this.boundaryPolygon != null);
	}
	
	public boolean overlaps(BaseActor2 other)
	{
		Polygon poly1 = this.getBoundaryPolygon();
		Polygon poly2 = other.getBoundaryPolygon();
		
		//initial test to improve performance (MUCH more efficient collision detection algorithm)
		if (!poly1.getBoundingRectangle().overlaps(poly2.getBoundingRectangle()))
		{
			return false;
		}
		
		return Intersector.overlapConvexPolygons(poly1, poly2);
	}
	
	public Vector2 preventOverlap(BaseActor2 other)
	{
		Polygon poly1 = this.getBoundaryPolygon();
		Polygon poly2 = other.getBoundaryPolygon();
		
		//initial test to improve performance
		if (!poly1.getBoundingRectangle().overlaps(poly2.getBoundingRectangle()))
		{
			return null;
		}
		
		MinimumTranslationVector mtv = new MinimumTranslationVector();
		boolean polygonOverlap = Intersector.overlapConvexPolygons(poly1, poly2, mtv);
		
		if (!polygonOverlap)
		{
			return null;
		}
		
		this.moveBy(mtv.normal.x * mtv.depth, mtv.normal.y * mtv.depth);
		return mtv.normal;
	}
	
	/*** World boundary methods ***/
	
	public static void setWorldBounds(float width, float height)
	{
		BaseActor2.worldBounds = new Rectangle(0f, 0f, width, height);
	}
	
	public static void setWorldBounds(BaseActor2 ba)
	{
		BaseActor2.setWorldBounds(ba.getWidth(), ba.getHeight());
	}
	
	public void boundToWorld()
	{
		//check left edge
		if (this.getX() < 0f)
		{
			this.setX(0f);
		}
		//check right edge
		if (this.getX() + this.getWidth() > BaseActor2.worldBounds.width)
		{
			this.setX(BaseActor2.worldBounds.width - this.getWidth());
		}
		//check bottom edge
		if (this.getY() < 0f)
		{
			this.setY(0f);
		}
		//check top edge
		if (this.getY() + this.getHeight() > BaseActor2.worldBounds.height)
		{
			this.setY(BaseActor2.worldBounds.height - this.getHeight());
		}
	}
	
	public void wrapAroundWorld()
	{
		if (this.getX() + this.getWidth() < 0f)
		{
			this.setX(BaseActor2.worldBounds.width);
		}
		if (this.getX() > BaseActor2.worldBounds.width)
		{
			this.setX(-this.getWidth());
		}
		if (this.getY() + this.getHeight() < 0f)
		{
			this.setY(BaseActor2.worldBounds.height);
		}
		if (this.getY() > BaseActor2.worldBounds.height)
		{
			this.setY(-this.getHeight());
		}
	}
	
	/*** Camera methods ***/
	
	public void alignCamera()
	{
		Camera cam = this.getStage().getCamera();
		
		//center camera on actor
		cam.position.set(this.getX() + this.getOriginX(), this.getY() + this.getOriginY(), 0f);
		
		//bound camera to layout
		cam.position.x = MathUtils.clamp(
				cam.position.x,
				cam.viewportWidth/2,
				worldBounds.width - cam.viewportWidth/2
				);
		cam.position.y = MathUtils.clamp(
				cam.position.y,
				cam.viewportHeight/2,
				worldBounds.height - cam.viewportHeight/2
				);
		cam.update();
	}
	
	/*** Asset management methods ***/
	
	public static ArrayList<BaseActor2> getList(Stage stage, String className)
	{
		ArrayList<BaseActor2> list = new ArrayList<BaseActor2>();
		
		@SuppressWarnings("rawtypes")
		Class theClass = null;
		
		try
		{
			theClass = Class.forName(className);
		}
		catch (Exception error)
		{
			error.printStackTrace();
			System.out.println("Use '" + className + ".class.getName()' for more reliable results");
		}
		
		for (Actor a : stage.getActors())
		{
			if (theClass.isInstance(a))
			{
				list.add((BaseActor2)a);
			}
			
		}
		
		return list;
	}
	
	public static int count(Stage stage, String className)
	{
		return BaseActor2.getList(stage, className).size();
	}
	
}
