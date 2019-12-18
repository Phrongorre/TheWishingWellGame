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
import cs328.uidaho.tww.actors.Door;
import cs328.uidaho.tww.actors.person.npc.Blurb;
import cs328.uidaho.tww.actors.person.npc.NPC;
import cs328.uidaho.tww.actors.person.npc.Prompt;
import cs328.uidaho.tww.actors.person.player.Item;
import cs328.uidaho.tww.actors.person.player.Player;
import cs328.uidaho.tww.gui.DialogueBox;
import cs328.uidaho.tww.screens.rooms.BooksShopInterior;
import cs328.uidaho.tww.screens.rooms.BrickhausCafeInterior;
import cs328.uidaho.tww.screens.rooms.QualityDrugInterior;

public class CloveHavenScreen extends BaseScreen {

	Player player;
	DialogueBox dialogueBox;
	boolean showWireframes = true;
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
		
		new BaseActor("locations/clove_haven/sky.png", this.mainStage);
		new BaseActor("locations/clove_haven/mountains.png", this.mainStage);
		BaseActor ground = new BaseActor("locations/clove_haven/ground.png", this.mainStage);
		new BaseActor(300f, 140f, "locations/clove_haven/clouds.png", this.mainStage);
		
		new Collidable(382f, 65f, "locations/clove_haven/bench.png", this.mainStage);
		new Collidable(357f, 67f, "locations/clove_haven/bush.png", this.mainStage);
		
		new Building(
			255f, 57f, //Location
			100f, 12f, //Size
			  3f,  6f, //Offsets
			340f, 63f, 163f,  5f, BrickhausCafeInterior.class, //Door info
			"clove_haven/brickhaus_cafe", this.mainStage
		);
		new Building(
			200f, 63f, //Location
			 45f, 12f, //Size
			  4f,  0f, //Offsets
			220f, 63f,  55f,  5f, BooksShopInterior.class, //Door info
			"clove_haven/books_shop", this.mainStage
		);
		new Building(
			 80f, 63f, //Location
			 90f, 16f, //Size
			  3f,  0f, //Offsets
			167f, 63f, 163f,  5f, QualityDrugInterior.class, //Door info
			"clove_haven/quality_drug", this.mainStage
		);
		
		Collidable tree = new Collidable(20f, 65f, this.mainStage);
		tree.loadTexture("locations/clove_haven/tree.png");
		tree.setCollisionSize(tree.getWidth()/2f, tree.getWidth()/4f);
		tree.setCollisionLocation(0f, 8f);
		
		new Item("key", 400f, 80f, this.mainStage);
		
		player = new Player(this.mainStage);
		BaseActor.setWorldBounds(
			ground.getWidth(),
			ground.getHeight() + player.getHeight() - player.getCollisionHeight()/2f
		);
		
		//Initialize NPCs
		(new NPC(105f, 60f, "people/person_lwm0.png", this.mainStage)).addPrompt(
			new Blurb("'Sup?")
		);
		
		(new NPC(120f, 57f, "people/person_adm0.png", this.mainStage)).addPrompt(
			(new Prompt("How are you today?")).addResponse(
				"I'm peachy!",
				(new Prompt("Wha?")).addResponse(
					"I mean, I'm fine.",
					new Blurb("Oh, okay then.")
				)
			).addResponse(
				"I'm great!",
				new Blurb("That's good to hear!")
			)
		);
		
		new Car(1, "locations/clove_haven/car_black.png", this.mainStage);
		new Car(2, "locations/clove_haven/car_red.png",   this.mainStage);
		new Car(5, "locations/clove_haven/car_white.png", this.mainStage);
	}

	final PromptHolder promptHolder = new PromptHolder();
	@Override
	public void update(float dt) {
		if (player.isInteracting()) {
			
			//If prompt has changed, build a new prompt DialogueBox setup
			if (promptHolder.hasChanged()) {
				Prompt prompt = promptHolder.prompt();
				//If out of prompts, then interaction is finished
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
			
			for (BaseActor doorActor : BaseActor.getList(mainStage, Door.class.getName())) {
				Door door = (Door)doorActor;
				
				if (player.interactsWith(door) && Gdx.input.isKeyJustPressed(Keys.E)) {
					door.interact(player);
					return; //Don't interact with anything else
				}
			}
			
			for (BaseActor itemActor : BaseActor.getList(this.mainStage, Item.class.getName())) {
				Item item = (Item)itemActor;
				
				if (player.interactsWith(item) && Gdx.input.isKeyJustPressed(Keys.E)) {
					item.interact(player);
					return; //Don't interact with anything else
				}
			}
			
			for (BaseActor npcActor : BaseActor.getList(this.mainStage, NPC.class.getName())) {
				NPC npc = (NPC)npcActor;
				
				if (player.interactsWith(npc) && Gdx.input.isKeyJustPressed(Keys.E)) {
					npc.interact(player);
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
