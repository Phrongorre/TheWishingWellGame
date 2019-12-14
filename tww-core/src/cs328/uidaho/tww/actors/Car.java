package cs328.uidaho.tww.actors;

import java.util.Random;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Car extends BaseActor {

	public Car(float x, float y, Stage s) {
		super(x, y, s);
		
		Random random = new Random();
		int r = random.nextInt(3);
		switch (r) {
		case 0:
			this.loadTexture("locations/clove_haven/car_black.png");
			break;
		case 1:
			this.loadTexture("locations/clove_haven/car_white.png");
			break;
		case 2:
			this.loadTexture("locations/clove_haven/car_red.png");
			break;
		};
		
		this.setBoundaryPolygon(this.getWidth(), this.getHeight()/2f, 8);
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
				this.getWidth(),
				this.getHeight()/2f,
				bpoly.getScaleX(),
				bpoly.getScaleY(),
				bpoly.getRotation()
			);
		}
	}
	
	@Override
	public Polygon getBoundaryPolygon() {
		this.boundaryPolygon.setPosition(this.getX(), this.getY());
		this.boundaryPolygon.setOrigin(this.getOriginX(), this.getOriginY());
		this.boundaryPolygon.setRotation(this.getRotation());
		this.boundaryPolygon.setScale(this.getScaleX(), this.getScaleY());
		return this.boundaryPolygon;
	}

}
