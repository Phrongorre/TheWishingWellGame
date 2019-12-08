package cs328.uidaho.tww.screens;

import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputEvent.Type;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import cs328.uidaho.tww.BaseGame;

public class MenuScreen extends BaseScreen {
	
	@Override
	public void initialize() {
		TextButton startButton = new TextButton("Start", BaseGame.textButtonStyle);
		startButton.addListener(
			(Event e) -> {
				if (!(e instanceof InputEvent) ||
					!((InputEvent)e).getType().equals(Type.touchDown)) {
					return false;
				}
				
				BaseGame.setActiveScreen(new IntroScreen());
				return false;
			}
		);
	}
	
	@Override
	public void update(float dt) { }
	
	@Override
	public boolean keyDown(int keycode) { return false; }
	
}
