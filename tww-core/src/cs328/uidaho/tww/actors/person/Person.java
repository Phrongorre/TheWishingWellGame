package cs328.uidaho.tww.actors.person;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.scenes.scene2d.Stage;

import cs328.uidaho.tww.actors.BaseActor;

public class Person extends BaseActor {
	
	protected boolean wireframesVisible;
	
	public Person(float x, float y, Stage s) {
		super(x, y, s);
		
		this.wireframesVisible = false;
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		
		if (this.boundaryPolygon != null && this.wireframesVisible) {
			Polygon bpoly = this.getBoundaryPolygon();
			batch.draw(
				this.collisionWireframes.get(1),
				bpoly.getX(),
				bpoly.getY(),
				bpoly.getOriginX(),
				bpoly.getOriginY(),
				this.getWidth()*1.5f,
				this.getWidth()*0.75f,
				bpoly.getScaleX(),
				bpoly.getScaleY(),
				bpoly.getRotation()
			);
		}
	}
	
	@Override
	public Polygon getBoundaryPolygon() {
		this.boundaryPolygon.setPosition(this.getX()-(this.getWidth()*1.5f-this.getWidth())/2f, this.getY()-(this.getWidth()*0.75f)/2f);
		this.boundaryPolygon.setOrigin(this.getOriginX(), this.getOriginY());
		this.boundaryPolygon.setRotation(this.getRotation());
		this.boundaryPolygon.setScale(this.getScaleX(), this.getScaleY());
		return this.boundaryPolygon;
	}
	
	public void setWireframesVisible(boolean visible) {
		this.wireframesVisible = visible;
	}
}
