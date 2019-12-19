package cs328.uidaho.tww.screens.rooms;

import cs328.uidaho.tww.actors.BaseActor;
import cs328.uidaho.tww.screens.CloveHavenScreen;

public class QualityDrugInterior extends Room {
	
	@Override
	public void initialize() {
		BaseActor floor = new BaseActor(0f, 0f, this.mainStage);
		floor.loadTexture("locations/clove_haven/quality_drug_interior.png");
		
		super.initialize();
		this.loadPlayer();
		
		this.exitDoor.setPosition(165f, 0f);
		this.exitDoor.setSpawnLocation(165f, 57f);
		this.exitDoor.setTargetScreen(CloveHavenScreen.class);
	}

	@Override
	public void update(float dt) {
		super.update(dt);
	}

}
