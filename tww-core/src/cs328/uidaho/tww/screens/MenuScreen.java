package cs328.uidaho.tww.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputEvent.Type;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import cs328.uidaho.tww.BaseGame;
import cs328.uidaho.tww.GameMetaData;

public class MenuScreen extends BaseScreen {
	
	@Override
	public void initialize() {
		//Add Start Button
		TextButton startButton = new TextButton("Start", BaseGame.textButtonStyle);
		startButton.addListener(
			(Event e) -> {
				if (!(e instanceof InputEvent) ||
					!((InputEvent)e).getType().equals(Type.touchDown)) {
					return false;
				}
				
				GameMetaData.setSpawnLocation(350f, 55f);
				BaseGame.setActiveScreen(new CloveHavenScreen());
				return false;
			}
		);
		this.uiTable.add(startButton);
		
		//Add Quit Button
		TextButton quitButton = new TextButton("Quit", BaseGame.textButtonStyle);
		quitButton.addListener(
			(Event e) -> {
				if (!(e instanceof InputEvent) ||
					!((InputEvent)e).getType().equals(Type.touchDown)) {
					return false;
				}
				
				Gdx.app.exit();
				return false;
			}
		);
		this.uiTable.add(quitButton);
	}
	
	@Override
	public void update(float dt) {
		
	}
	
}
