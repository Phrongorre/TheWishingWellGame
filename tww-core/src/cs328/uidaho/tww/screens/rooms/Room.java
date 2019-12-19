package cs328.uidaho.tww.screens.rooms;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

import cs328.uidaho.tww.actors.BaseActor;
import cs328.uidaho.tww.actors.collidables.Collidable;
import cs328.uidaho.tww.actors.collidables.Door;
import cs328.uidaho.tww.actors.collidables.Item;
import cs328.uidaho.tww.actors.collidables.person.Player;
import cs328.uidaho.tww.actors.collidables.person.npc.NPC;
import cs328.uidaho.tww.actors.collidables.person.npc.PromptHolder;
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
		
		this.exitDoor = new Door(0f, 0f, 0f, 0f, null, this.mainStage);
	}

	@Override
	public void update(float dt) {
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
	
	protected void loadPlayer() {
		this.player = new Player(this.mainStage, this.uiStage);
	}

}
