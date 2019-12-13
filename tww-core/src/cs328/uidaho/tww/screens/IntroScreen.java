package cs328.uidaho.tww.screens;

import cs328.uidaho.tww.actors.BaseActor;
import cs328.uidaho.tww.actors.person.Player;
import cs328.uidaho.tww.actors.person.npc.NPC;
import cs328.uidaho.tww.gui.DialogueBox;

public class IntroScreen extends BaseScreen {

	Player player;
	
	@Override
	public void initialize() {
		BaseActor.setWorldBounds(800f, 600f);
		
		this.player = new Player(0f, 0f, this.mainStage);
		this.player.centerAtPosition(400f, 300f);
		
		NPC npc1 = new NPC(0f, 0f, this.mainStage);
		npc1.addBlurb("What's up?");
		
		DialogueBox db = new DialogueBox(0f, 0f, this.mainStage);
		db.setText(npc1.getNextBlurb());
		this.uiTable.add(db);
		
		//Zoom in by 4x
//		this.mainStage.getCamera().viewportHeight /= 4f;
//		this.mainStage.getCamera().viewportWidth  /= 4f;
	}

	@Override
	public void update(float dt) {
		 for (BaseActor npcActor : BaseActor.getList(this.mainStage, NPC.class.getName())) {
			 this.player.preventOverlap(npcActor);
		 }
	}

}
