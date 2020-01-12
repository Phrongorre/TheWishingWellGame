package cs328.uidaho.tww.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputEvent.Type;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import cs328.uidaho.tww.BaseGame;
import cs328.uidaho.tww.actors.BaseActor;

public class MenuScreen extends BaseScreen {
	
	@Override
	public void initialize() {
		//Zoom in by 3x
		this.mainStage.getCamera().viewportWidth  /= 3f;
		this.mainStage.getCamera().viewportHeight /= 3f;
		(new BaseActor(0f, 0f, "gui/menu_splash.png", this.mainStage)).centerAtPosition(400f, 300f);
		
		//Add Start Button
		TextButton startButton = new TextButton("Start", BaseGame.textButtonStyle);
		startButton.addListener(
			(Event e) -> {
				if (!(e instanceof InputEvent) ||
					!((InputEvent)e).getType().equals(Type.touchDown)) {
					return false;
				}
				
				BaseGame.setActiveScreen(new ControlsScreen());
				return false;
			}
		);
		
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
		
		this.uiTable.pad(10f);
		this.uiTable.add().colspan(2).expand(1, 1);
		this.uiTable.row();
		this.uiTable.add(startButton);
		this.uiTable.add(quitButton);
	}
	
	@Override
	public void update(float dt) { }
	
}
