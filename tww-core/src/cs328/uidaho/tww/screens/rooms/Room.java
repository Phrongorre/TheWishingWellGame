package cs328.uidaho.tww.screens.rooms;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputEvent.Type;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;

import cs328.uidaho.tww.BaseGame;
import cs328.uidaho.tww.actors.BaseActor;
import cs328.uidaho.tww.actors.collidables.Collidable;
import cs328.uidaho.tww.actors.collidables.Door;
import cs328.uidaho.tww.actors.collidables.Item;
import cs328.uidaho.tww.actors.collidables.person.Player;
import cs328.uidaho.tww.actors.collidables.person.npc.ActionPrompt;
import cs328.uidaho.tww.actors.collidables.person.npc.GatedPrompt;
import cs328.uidaho.tww.actors.collidables.person.npc.NPC;
import cs328.uidaho.tww.actors.collidables.person.npc.Prompt;
import cs328.uidaho.tww.actors.collidables.person.npc.PromptHolder;
import cs328.uidaho.tww.actors.collidables.person.npc.Response;
import cs328.uidaho.tww.actors.util.IInteractable;
import cs328.uidaho.tww.gui.DialogueBox;
import cs328.uidaho.tww.screens.BaseScreen;

public abstract class Room extends BaseScreen {
	
	protected Door exitDoor;
	protected Player player;
	protected DialogueBox dialogueBox;
	protected final PromptHolder promptHolder = new PromptHolder();
	
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
		
		this.exitDoor = new Door(0f, 0f, 0f, 0f, null, this.mainStage);
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
	
	protected void loadPlayer() {
		this.player = new Player(this.mainStage, this.uiStage);
	}

}
