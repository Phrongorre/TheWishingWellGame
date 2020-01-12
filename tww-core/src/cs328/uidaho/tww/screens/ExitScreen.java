package cs328.uidaho.tww.screens;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import cs328.uidaho.tww.BaseGame;
import cs328.uidaho.tww.GameMetaData;
import cs328.uidaho.tww.actors.BaseActor;

public class ExitScreen extends BaseScreen {

	@Override
	public void initialize() {
		BaseActor endSplash = new BaseActor(0f, 0f, "gui/end_splash.png", this.mainStage);
		endSplash.addAction(Actions.delay(5f, Actions.run(
			()->{
				GameMetaData.reset();
				BaseGame.setActiveScreen(new MenuScreen());
			}
		)));
	}

	@Override
	public void update(float dt) { }

}
