package cs328.uidaho.tww.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import cs328.uidaho.tww.actors.BaseActor;
import cs328.uidaho.tww.actors.person.Player;
import cs328.uidaho.tww.actors.person.npc.NPC;
import cs328.uidaho.tww.gui.DialogueBox;

public class IntroScreen extends BaseScreen {

	Player player;
	DialogueBox dialogueBox;
	
	@Override
	public void initialize() {
		BaseActor.setWorldBounds(800f, 600f);
		
		//Zoom in by 4x
		this.mainStage.getCamera().viewportHeight /= 4f;
		this.mainStage.getCamera().viewportWidth  /= 4f;
		
		dialogueBox = new DialogueBox(0f, 0f, this.uiStage);
		dialogueBox.setVisible(false);
		
		this.uiTable.add().expandY();
		this.uiTable.row();
		this.uiTable.add(dialogueBox);
		
		
		this.player = new Player(0f, 0f, this.mainStage);
		this.player.setWireframesVisible(true);
		this.player.centerAtPosition(100f, 75f);
		this.player.alignCamera();
		
		NPC npc1 = new NPC(40f, 75f, this.mainStage);
		npc1.setWireframesVisible(true);
		npc1.addBlurb("What's up?");
	}

	@Override
	public void update(float dt) {
		for (BaseActor npcActor : BaseActor.getList(this.mainStage, NPC.class.getName())) {
			this.player.preventOverlap(npcActor);
			if (player.interactsWith(npcActor) && Gdx.input.isKeyJustPressed(Keys.E)) {
				dialogueBox.setVisible(true);
				dialogueBox.setText(((NPC)npcActor).getNextBlurb());
				dialogueBox.clearActions();
				dialogueBox.addAction(Actions.delay(3f, Actions.run(
					() -> {
						dialogueBox.setVisible(false);
					}
				)));
			 }
		 }
	}

}
