package cs328.uidaho.tww.actors.collidables.person;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.scenes.scene2d.Stage;

import cs328.uidaho.tww.GameMetaData;
import cs328.uidaho.tww.actors.util.IInteractable;
import cs328.uidaho.tww.gui.Inventory;

public class Player extends Person {
	
	private Polygon interactionPolygon;
	private boolean interacting;
	private Inventory inventory;
	
	public Player(Stage s, Stage uiStage) {
		super(GameMetaData.getSpawnX(), GameMetaData.getSpawnY(), s);
		
		this.setMaxSpeed(80f);
		this.setAcceleration(4000f);
		this.setDeceleration(4000f);
		
		this.loadTexture("people/person_awm0.png");
		
		this.setInteractionPolygon(8);
		
		if (GameMetaData.getInventory() == null) {
			GameMetaData.setInventory(new Inventory(uiStage));
		}
		else {
			//GameMetaData.setInventoryStage(uiStage);
		}
		this.inventory = GameMetaData.getInventory();
		this.interacting = false;
	}
	
	@Override
	public void act(float dt) {
		if (!this.interacting) {
			if (Gdx.input.isKeyPressed(Keys.LEFT)) {
				this.accelerateAtAngle(180f);
			}
			if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
				this.accelerateAtAngle(0f);
			}
			if (Gdx.input.isKeyPressed(Keys.UP)) {
				this.accelerateAtAngle(90f);
			}
			if (Gdx.input.isKeyPressed(Keys.DOWN)) {
				this.accelerateAtAngle(270f);
			}
			
			this.applyPhysics(dt);
			this.boundToWorld();
			
			this.alignCamera();
		}
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		
		if (this.interactionPolygon != null && GameMetaData.wireframesVisible()) {
			Polygon ipoly = this.getInteractionPolygon();
			batch.draw(
				this.collisionWireframes.get(1),
				ipoly.getX(),
				ipoly.getY(),
				ipoly.getOriginX(),
				ipoly.getOriginY(),
				this.getWidth()*4f,
				this.getWidth()*2f,
				ipoly.getScaleX(),
				ipoly.getScaleY(),
				ipoly.getRotation()
			);
		}
	}
	
	public void setInteracting(boolean interacting) {
		this.interacting = interacting;
	}
	
	public boolean isInteracting() {
		return this.interacting;
	}
	
	public void setInteractionRectangle() {
		this.setInteractionRectangle(this.getWidth()*4f, this.getWidth()*2f);
	}
	
	public void setInteractionRectangle(float w, float h) {
		float[] vertices = {0f, 0f, w, 0f, w, h, 0f, h};
		this.interactionPolygon = new Polygon(vertices);
	}
	
	public void setInteractionPolygon(int numSides) {
		this.setInteractionPolygon(this.getWidth()*6f, this.getWidth()*3f, numSides);
	}
	
	public void setInteractionPolygon(float w, float h, int numSides) {
		float[] vertices = new float[2*numSides];
		
		for (int i=0; i < numSides; i++) {
			float angle = i * MathUtils.PI2 / numSides;
			//x-coordinate
			vertices[2*i] = w/2 * MathUtils.cos(angle) + w/2;
			//y-coordinate
			vertices[2*i+1] = h/2 * MathUtils.sin(angle) + h/2;
		}
		
		this.interactionPolygon = new Polygon(vertices);
	}
	
	public Polygon getInteractionPolygon() {
		this.interactionPolygon.setPosition(this.getX()-this.getWidth()*1.5f,  this.getY()-this.getWidth());
		this.interactionPolygon.setOrigin(this.getOriginX(), this.getOriginY());
		this.interactionPolygon.setRotation(this.getRotation());
		this.interactionPolygon.setScale(this.getScaleX(), this.getScaleY());
		return this.interactionPolygon;
	}
	
	public boolean interactsWith(IInteractable other) {
		Polygon poly1 = this.getInteractionPolygon();
		Polygon poly2 = other.getBoundaryPolygon();
		
		//initial test to improve performance (MUCH more efficient collision detection algorithm)
		if (!poly1.getBoundingRectangle().overlaps(poly2.getBoundingRectangle())) {
			return false;
		}
		
		return Intersector.overlapConvexPolygons(poly1, poly2);
	}
	
	public Inventory getInventory() {
		return this.inventory;
	}
	
}
