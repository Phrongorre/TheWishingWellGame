package cs328.uidaho.tww.screens.rooms;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

import cs328.uidaho.tww.actors.BaseActor;
import cs328.uidaho.tww.actors.Door;
import cs328.uidaho.tww.actors.person.player.Player;
import cs328.uidaho.tww.screens.BaseScreen;

public abstract class Room extends BaseScreen {
	
	protected Door exitDoor;
	protected Player player;
	
	@Override
	public void initialize() {
		//Zoom in by 3x
		this.mainStage.getCamera().viewportWidth  /= 3f;
		this.mainStage.getCamera().viewportHeight /= 3f;
		
		this.exitDoor = new Door(0f, 0f, 0f, 0f, null, this.mainStage);
	}

	@Override
	public void update(float dt) {
		for (BaseActor doorActor : BaseActor.getList(mainStage, Door.class.getName())) {
			Door door = (Door)doorActor;
			
			if (player.interactsWith(door) && Gdx.input.isKeyJustPressed(Keys.E)) {
				door.interact();
				return; //Don't interact with any NPC's
			}
		}
	}
	
	protected void loadPlayer() {
		this.player = new Player(this.mainStage);
	}

}
