package cs328.uidaho.tww.screens.rooms;

import cs328.uidaho.tww.actors.BaseActor;
import cs328.uidaho.tww.actors.person.player.Item;
import cs328.uidaho.tww.screens.CloveHavenScreen;

public class MartinsGroceryInterior extends Room {
	
	@Override
	public void initialize() {
		BaseActor floor = new BaseActor(0f, 0f, this.mainStage);
		floor.loadTexture("locations/clove_haven/quality_drug_interior.png");
		
		super.initialize();
		this.loadPlayer();
		
		this.exitDoor.setPosition(62f, 0f);
		this.exitDoor.setSpawnLocation(453f, 60f);
		this.exitDoor.setTargetScreen(CloveHavenScreen.class);
		
		new Item("cola", 30f, 25f, this.mainStage);
	}

	@Override
	public void update(float dt) {
		super.update(dt);
	}
	
}
