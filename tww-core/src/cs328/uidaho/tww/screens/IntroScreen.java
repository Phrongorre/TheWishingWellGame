package cs328.uidaho.tww.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputEvent.Type;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import cs328.uidaho.tww.BaseGame;
import cs328.uidaho.tww.actors.BaseActor;
import cs328.uidaho.tww.actors.Building;
import cs328.uidaho.tww.actors.Car;
import cs328.uidaho.tww.actors.Collidable;
import cs328.uidaho.tww.actors.person.npc.Blurb;
import cs328.uidaho.tww.actors.person.npc.NPC;
import cs328.uidaho.tww.actors.person.npc.Prompt;
import cs328.uidaho.tww.actors.person.npc.Prompt.Response;
import cs328.uidaho.tww.actors.person.player.Player;
import cs328.uidaho.tww.gui.DialogueBox;

public class IntroScreen extends BaseScreen {

	Player player;
	DialogueBox dialogueBox;
	boolean showWireframes = false;
	boolean interacting;
	
	@Override
	public void initialize() {
		//Zoom in by 3x
		this.mainStage.getCamera().viewportWidth  /= 3f;
		this.mainStage.getCamera().viewportHeight /= 3f;
		
		dialogueBox = new DialogueBox(0f, 0f, this.uiStage);
		dialogueBox.setVisible(false);
		
		this.uiTable.add().colspan(3).expandY();
		this.uiTable.row();
		this.uiTable.add().expandX();
		this.uiTable.add(dialogueBox);
		this.uiTable.add().expandX();
		
		BaseActor sky = new BaseActor(0f, 0f, this.mainStage);
		sky.loadTexture("locations/clove_haven/sky.png");
		
		BaseActor mountains = new BaseActor(0f, 0f, this.mainStage);
		mountains.loadTexture("locations/clove_haven/mountains.png");
		
		BaseActor ground = new BaseActor(0f, 0f, this.mainStage);
		ground.loadTexture("locations/clove_haven/ground.png");
		
		Building qualityDrug = new Building(200f, 63f, 92f, 16f, -2f/3f, "locations/clove_haven/quality_drug_open.png", this.mainStage);
		qualityDrug.loadTexture("locations/clove_haven/quality_drug_open.png");	
		
		Collidable tree = new Collidable(100f, 60f, this.mainStage);
		tree.loadTexture("locations/clove_haven/tree.png");
		tree.setCollisionSize(tree.getWidth()/2f, tree.getWidth()/4f);
		tree.setCollisionLocation(0f, 8f);
		
		player = new Player(0f, 0f, this.mainStage);
		player.centerAtPosition(100f, 75f);
		BaseActor.setWorldBounds(
			ground.getWidth(),
			ground.getHeight() + player.getHeight() - player.getCollisionHeight()/2f
		);
		interacting = false;
		
		(new NPC(40f, 75f, this.mainStage)).addPrompt(new Blurb("What's up?"));
		
		(new NPC(30f, 65f, this.mainStage)).addPrompt(
			(new Prompt("How are you today?")).addResponse(
				"I'm peachy!", (new Prompt("Wha?")).addResponse(
					"I mean, I'm fine.", new Blurb("Oh, okay then.")
				)
			).addResponse(
				"I'm great!", new Blurb("That's good to hear!")
			)
		);
		
		new Car(172f, 34f, this.mainStage);
	}

	final PromptHolder promptHolder = new PromptHolder();
	@Override
	public void update(float dt) {
		if (player.isInteracting()) {
			if (promptHolder.hasChanged()) {
				Prompt prompt = promptHolder.prompt();
				if (prompt == null) {
					player.setInteracting(false);
					dialogueBox.setVisible(false);
					return;
				}
				dialogueBox.reset();
				dialogueBox.setText(prompt.prompt());
				
				for (int r=0; r < prompt.responseCount(); r++) {
					TextButton responseButton = new TextButton(prompt.response(r), BaseGame.textButtonStyle);
					final Prompt fp = prompt.followResponse(r);
					responseButton.addListener(
						(Event e) -> {
							if (!(e instanceof InputEvent) ||
								!((InputEvent)e).getType().equals(Type.touchDown))
							{ return false; }
							
							promptHolder.setPrompt(fp);
							
							return false;
						}
					);
					
					dialogueBox.addTextButton(responseButton);
				}
			}
		}
		else {
			for (BaseActor collidableActor : BaseActor.getList(this.mainStage, Collidable.class.getName())) {
				Collidable collidable = (Collidable)collidableActor;
				
				if (collidable != player) {
					player.preventOverlap(collidable);
					player.adjustZIndex(collidable);				
				}
				
				if (showWireframes) collidable.setWireframesVisible(true);
			}
			
			for (BaseActor npcActor : BaseActor.getList(this.mainStage, NPC.class.getName())) {
				NPC npc = (NPC)npcActor;
				
				if (player.interactsWith(npc) && Gdx.input.isKeyJustPressed(Keys.E)) {
					dialogueBox.setVisible(true);
					player.setInteracting(true);
					promptHolder.setPrompt(npc.getNextPrompt());
					return;
				}
			}
		}
	}
	
	private class PromptHolder {
		
		private Prompt prompt;
		private boolean changed;
		
		public PromptHolder() {
			this.prompt = null;
			this.changed = false;
		}
		
		public void setPrompt(Prompt prompt) {
			if (this.prompt != prompt) {
				this.prompt = prompt;
				this.changed = true;
			}
		}
		
		public Prompt prompt() {
			this.changed = false;
			return this.prompt;
		}
		
		public boolean hasChanged() {
			return this.changed;
		}
		
	}

}
