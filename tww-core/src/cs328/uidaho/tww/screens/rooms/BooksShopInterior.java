package cs328.uidaho.tww.screens.rooms;

import cs328.uidaho.tww.actors.BaseActor;
import cs328.uidaho.tww.actors.collidables.person.npc.Blurb;
import cs328.uidaho.tww.actors.collidables.person.npc.NPC;
import cs328.uidaho.tww.screens.CloveHavenScreen;

public class BooksShopInterior extends Room {

	@Override
	public void initialize() {
		BaseActor floor = new BaseActor(0f, 0f, this.mainStage);
		floor.loadTexture("locations/clove_haven/quality_drug_interior.png");
		
		super.initialize();
		this.loadPlayer();
		
		this.exitDoor.setPosition(57f, 0f);
		this.exitDoor.setSpawnLocation(218f, 57f);
		this.exitDoor.setTargetScreen(CloveHavenScreen.class);
		
		(new NPC(50f, 30f, this.mainStage)).addPrompt(
			new Blurb("So you finally found me.",
				new Blurb("I hope it was worth the effort.",
					new Blurb("After all...",
						new Blurb("There's only so much to do in this town."))
				)
			)
		);
	}

	@Override
	public void update(float dt) {
		super.update(dt);
	}

}
