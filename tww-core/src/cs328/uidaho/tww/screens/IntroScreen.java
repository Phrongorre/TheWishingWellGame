package cs328.uidaho.tww.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import cs328.uidaho.tww.actors.BaseActor;
import cs328.uidaho.tww.actors.Car;
import cs328.uidaho.tww.actors.person.Person;
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
		
		NPC npc = new NPC(40f, 75f, this.mainStage);
		npc.setWireframesVisible(true);
		npc.addBlurb("What's up?");
		
		npc = new NPC(30f, 65f, this.mainStage);
		npc.setWireframesVisible(true);
		npc.addBlurb("How are you today?");
		npc.addBlurb("Wha?");
		
		Car carBlack = new Car(80f, 30f, this.mainStage);
		carBlack.setWireframesVisible(true);
	}

	@Override
	public void update(float dt) {
		for (BaseActor npcActor : BaseActor.getList(this.mainStage, NPC.class.getName())) {
			this.player.preventOverlap(npcActor);
			
			//Set Z-index in stage based on relatives y positions
			if (this.player.getY() < npcActor.getY() && this.player.getZIndex() < npcActor.getZIndex()) {
				this.player.setZIndex(npcActor.getZIndex()+1);
			}
			else if (this.player.getY() >= npcActor.getY() && this.player.getZIndex() > npcActor.getZIndex()) {
				npcActor.setZIndex(this.player.getZIndex()+1);
			}
			
			if (this.player.interactsWith(npcActor) && Gdx.input.isKeyJustPressed(Keys.E)) {
				this.dialogueBox.setText(((NPC)npcActor).getNextBlurb());
				this.dialogueBox.clearActions();
				this.dialogueBox.setVisible(true);
				this.dialogueBox.addAction(Actions.delay(3f, Actions.run(
					() -> {
						this.dialogueBox.setVisible(false);
					}
				)));
			 }
		}
		
		for (BaseActor carActor : BaseActor.getList(this.mainStage, Car.class.getName())) {
			this.player.preventOverlap(carActor);
			
			//Set Z-index in stage based on relatives y positions
			if (this.player.getY() < (carActor.getY()+carActor.getHeight()/4f) && this.player.getZIndex() < carActor.getZIndex()) {
				this.player.setZIndex(carActor.getZIndex()+1);
			}
			else if (this.player.getY() >= (carActor.getY()+carActor.getHeight()/4f) && this.player.getZIndex() > carActor.getZIndex()) {
				carActor.setZIndex(this.player.getZIndex()+1);
			}
		}
	}

}
