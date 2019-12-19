package cs328.uidaho.tww.screens;

import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputEvent.Type;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import cs328.uidaho.tww.BaseGame;
import cs328.uidaho.tww.GameMetaData;
import cs328.uidaho.tww.actors.BaseActor;

public class ControlsScreen extends BaseScreen {

	@Override
	public void initialize() {
		//Zoom in by 2x
		this.mainStage.getCamera().viewportWidth  /= 2f;
		this.mainStage.getCamera().viewportHeight /= 2f;
		(new BaseActor("gui/controls_splash.png", this.mainStage)).centerAtPosition(400f, 300f);;
		
		//Add Start Button
		TextButton startButton = new TextButton("Click to Begin", BaseGame.textButtonStyle);
		startButton.addListener(
			(Event e) -> {
				if (!(e instanceof InputEvent) ||
					!((InputEvent)e).getType().equals(Type.touchDown)) {
					return false;
				}
				
				GameMetaData.setSpawnLocation(350f, 55f);
				BaseGame.setActiveScreen(GameMetaData.getScreen(CloveHavenScreen.class));
				return false;
			}
		);
		
		this.uiTable.pad(10f);
		this.uiTable.add().expand(1, 1);
		this.uiTable.row();
		this.uiTable.add(startButton);
	}
	
	@Override
	public void update(float dt) { }

}
