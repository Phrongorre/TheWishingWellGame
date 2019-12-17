package cs328.uidaho.tww.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputEvent.Type;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;

import cs328.uidaho.tww.BaseGame;
import cs328.uidaho.tww.actors.BaseActor;
import cs328.uidaho.tww.actors.Building;
import cs328.uidaho.tww.actors.Car;
import cs328.uidaho.tww.actors.Collidable;
import cs328.uidaho.tww.actors.person.npc.Blurb;
import cs328.uidaho.tww.actors.person.npc.NPC;
import cs328.uidaho.tww.actors.person.npc.Prompt;
import cs328.uidaho.tww.actors.person.player.Item;
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
		
		this.uiTable.add().expandY();
		this.uiTable.row();
		this.uiTable.add(dialogueBox).align(Align.center);
		
		BaseActor sky = new BaseActor(0f, 0f, this.mainStage);
		sky.loadTexture("locations/clove_haven/sky.png");
		
		BaseActor mountains = new BaseActor(0f, 0f, this.mainStage);
		mountains.loadTexture("locations/clove_haven/mountains.png");
		
		BaseActor ground = new BaseActor(0f, 0f, this.mainStage);
		ground.loadTexture("locations/clove_haven/ground.png");
		
		new Building(
			355f, 57f, //Location
			100f, 12f, //Size
			  3f,  6f, //Offsets
			"locations/clove_haven/brickhaus_cafe_open.png", this.mainStage
		);
		new Building(
			300f, 63f, //Location
			 45f, 12f, //Size
			  4f,  0f, //Offsets
			"locations/clove_haven/books_shop_open.png", this.mainStage
		);
		new Building(
			180f, 63f, //Location
			 90f, 16f, //Size
			  3f,  0f, //Offsets
			"locations/clove_haven/quality_drug_open.png", this.mainStage
		);
		
		Collidable tree = new Collidable(120f, 65f, this.mainStage);
		tree.loadTexture("locations/clove_haven/tree.png");
		tree.setCollisionSize(tree.getWidth()/2f, tree.getWidth()/4f);
		tree.setCollisionLocation(0f, 8f);
		
		new Item("key", 500f, 80f, this.mainStage);
		
		player = new Player(350f, 55f, this.mainStage);
		BaseActor.setWorldBounds(
			ground.getWidth(),
			ground.getHeight() + player.getHeight() - player.getCollisionHeight()/2f
		);
		interacting = false;
		
		//Initialize NPCs
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
		
		new Car(114f + 58f*1, 33f, this.mainStage);
		new Car(114f + 58f*2, 35f, this.mainStage);
		new Car(114f + 58f*5, 34f, this.mainStage);
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
			
			for (BaseActor itemActor : BaseActor.getList(this.mainStage, Item.class.getName())) {
				Item item = (Item)itemActor;
				
				if (player.interactsWith(item) && Gdx.input.isKeyJustPressed(Keys.E)) {
					item.interact();
					return; //Don't interact with any NPC's
				}
			}
			
			for (BaseActor npcActor : BaseActor.getList(this.mainStage, NPC.class.getName())) {
				NPC npc = (NPC)npcActor;
				
				if (player.interactsWith(npc) && Gdx.input.isKeyJustPressed(Keys.E)) {
					npc.interact();
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
