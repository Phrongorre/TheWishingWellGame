package cs328.uidaho.tww.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputEvent.Type;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;

import cs328.uidaho.tww.BaseGame;
import cs328.uidaho.tww.actors.BaseActor;
import cs328.uidaho.tww.actors.collidables.Building;
import cs328.uidaho.tww.actors.collidables.Car;
import cs328.uidaho.tww.actors.collidables.Collidable;
import cs328.uidaho.tww.actors.collidables.Door;
import cs328.uidaho.tww.actors.collidables.Item;
import cs328.uidaho.tww.actors.collidables.person.Player;
import cs328.uidaho.tww.actors.collidables.person.npc.ActionPrompt;
import cs328.uidaho.tww.actors.collidables.person.npc.Blurb;
import cs328.uidaho.tww.actors.collidables.person.npc.Discussion;
import cs328.uidaho.tww.actors.collidables.person.npc.GatedPrompt;
import cs328.uidaho.tww.actors.collidables.person.npc.NPC;
import cs328.uidaho.tww.actors.collidables.person.npc.Prompt;
import cs328.uidaho.tww.actors.collidables.person.npc.PromptHolder;
import cs328.uidaho.tww.actors.collidables.person.npc.Response;
import cs328.uidaho.tww.actors.util.IInteractable;
import cs328.uidaho.tww.gui.DialogueBox;
import cs328.uidaho.tww.screens.rooms.BooksShopInterior;
import cs328.uidaho.tww.screens.rooms.BrickhausCafeInterior;
import cs328.uidaho.tww.screens.rooms.MartinsGroceryInterior;
import cs328.uidaho.tww.screens.rooms.QualityDrugInterior;

public class CloveHavenScreen extends BaseScreen {

	Player player;
	DialogueBox dialogueBox;
	final PromptHolder promptHolder = new PromptHolder();
	
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
			400f, 63f, //Location
			200f, 16f, //Size
			  0f,  0f, //Offsets
			455f, 63f,  60f,  5f, MartinsGroceryInterior.class, //Door info
			"clove_haven/martins_grocery", this.mainStage
		);
		new Building(
			255f, 57f, //Location
			100f, 12f, //Size
			  3f,  6f, //Offsets
			340f, 63f, 163f,  5f, BrickhausCafeInterior.class, //Door info
			"clove_haven/brickhaus_cafe", this.mainStage
		);
		(new Building(
			200f, 63f, //Location
			 45f, 12f, //Size
			  4f,  0f, //Offsets
			220f, 63f,  55f,  5f, BooksShopInterior.class, //Door info
			"clove_haven/books_shop", this.mainStage
		)).close();
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
		
		new Item("coin", 10f, 70f, this.mainStage);
		new Collidable(10f, 70f, "locations/clove_haven/bush.png", this.mainStage);
		
		player = new Player(this.mainStage, this.uiStage);
		BaseActor.setWorldBounds(
			ground.getWidth(),
			ground.getHeight() + player.getHeight() - player.getCollisionHeight()/2f
		);
		
		//Initialize NPCs
		(new NPC(90f, 60f, this.mainStage)).setInteractable(false);
		
		Discussion disc = new Discussion();
		Response busy = new Response(
			"Not now, I'm busy.",
			new Blurb("Oh, okay...", new Blurb("Please come back if you change your mind."))
		);
		
		disc.addPrompt((new GatedPrompt("Hello!", "cola")).addLockedResponse(
			"Hello. What rumors have you heard about the wishing well?",
			new Blurb("Oh that crumbling heap of stones just out of town?",
				new Blurb("Yeah, I've heard young children go missing from there.",
					new Blurb("Some folks say it's spirits that take them...",
						(new Prompt("But personally it seems more like the Books Shop creep.")).addResponse(
							"The \"Books Shop creep?\"",
							(new Prompt("Yeah. I don't have proof, but he really wigs me out.")).addResponse(
								"When will the Books Shop be open again?",
								new Blurb("Oh that old shop is never open.",
									new Blurb("He keeps it locked up all day long.",
										new Blurb("But it just so happens I found a key that might unlock it.",
											new Blurb("Tell you what, you get me a soda, and I will give it to you!",
												(new Prompt("All of this talking has made me thirsty...")).addResponse(
													"Sure thing!",
													(new Prompt("Great, thanks!")).addResponse(
														"Do you know where I could find some?",
														new Blurb("They're usually in vending machines.",
															new Blurb("Martin's Grocery is probably your best bet.")
														)
													).addResponse(
														"Take care.",
														new Blurb("Please hurry back, I'm so thirsty...")
													)
												).addResponse(busy)
											)
										)
									)
								)
							)
						)
					)
				)
			)
		).addUnlockedResponse(
			"I have that soda you asked for.",
			new Blurb("Oh great! Thank you!",
				new Blurb("As promised, here's the key I found.",
					new Blurb("Good luck.")
				)
			)
		).setUnlockAction(Actions.run(
			()->{
				Item key = new Item("key", 0f, 0f, this.mainStage);
				key.interact(player);
			}
		)));
		
		(new NPC(105f, 57f, this.mainStage)).setDiscussion(disc);
		
		new Car(1, "locations/clove_haven/car_black.png", this.mainStage);
		new Car(2, "locations/clove_haven/car_red.png",   this.mainStage);
		new Car(5, "locations/clove_haven/car_white.png", this.mainStage);
	}
	
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
				
				for (Response response : prompt.responses()) {
					TextButton responseButton = new TextButton(response.response(), BaseGame.textButtonStyle);
					final Prompt fp = response.follow();
					responseButton.addListener(
						(Event e) -> {
							if (!(e instanceof InputEvent) ||
								!((InputEvent)e).getType().equals(Type.touchDown))
							{ return false; }
							
							if (GatedPrompt.class.isInstance(prompt)) ((GatedPrompt)prompt).unlockAction(player);
							else if (ActionPrompt.class.isInstance(prompt)) ((ActionPrompt)prompt).activateAction(player);
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
			}
			
			for (BaseActor doorActor : BaseActor.getList(mainStage, Door.class.getName())) {
				Door door = (Door)doorActor;
				
				if (player.interactsWith(door) && door.isInteractable() && Gdx.input.isKeyJustPressed(Keys.E)) {
					door.interact(player);
					return; //Don't interact with anything else
				}
			}
			
			for (BaseActor itemActor : BaseActor.getList(this.mainStage, Item.class.getName())) {
				Item item = (Item)itemActor;
				
				if (player.interactsWith(item) && item.isInteractable() && Gdx.input.isKeyJustPressed(Keys.E)) {
					item.interact(player);
					return; //Don't interact with anything else
				}
			}
			
			for (BaseActor npcActor : BaseActor.getList(this.mainStage, NPC.class.getName())) {
				NPC npc = (NPC)npcActor;
				
				if (player.interactsWith(npc) && npc.isInteractable() && Gdx.input.isKeyJustPressed(Keys.E)) {
					npc.interact(player);
					dialogueBox.setVisible(true);
					player.setInteracting(true);
					promptHolder.setPrompt(npc.getNextPrompt());
					return;
				}
			}
			
			for (BaseActor interactor : BaseActor.getList(this.mainStage, IInteractable.class.getName())) {
				IInteractable interactable = (IInteractable)interactor;
				
				if (player.interactsWith(interactable) && interactable.isInteractable() && Gdx.input.isKeyJustPressed(Keys.E)) {
					interactable.interact(player);
					return;
				}
			}
			
		}
	}

}
