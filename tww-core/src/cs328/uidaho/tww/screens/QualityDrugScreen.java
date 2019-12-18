package cs328.uidaho.tww.screens;

import cs328.uidaho.tww.actors.BaseActor;
import cs328.uidaho.tww.actors.person.player.Player;

public class QualityDrugScreen extends BaseScreen {
	
	Player player;
	
	@Override
	public void initialize() {
		//Zoom in by 3x
		this.mainStage.getCamera().viewportWidth  /= 3f;
		this.mainStage.getCamera().viewportHeight /= 3f;
		
		BaseActor floor = new BaseActor(0f, 0f, this.mainStage);
		floor.loadTexture("locations/clove_haven/quality_drug_interior.png");
		
		player = new Player(this.mainStage);
	}

	@Override
	public void update(float dt) {
		
	}

}
