package cs328.uidaho.tww.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public abstract class BaseScreen implements Screen, InputProcessor {
	
	protected Stage mainStage;
	protected Stage uiStage;
	protected Table uiTable;
	
	public BaseScreen() {
		this.mainStage = new Stage();
		this.uiStage   = new Stage();
		this.uiTable   = new Table();
		
		this.uiTable.setFillParent(true);
		this.uiStage.addActor(this.uiTable);
		
		this.initialize();
	}

	public abstract void initialize();
	public abstract void update(float dt);
	
	@Override
	public void render(float dt) {
		this.uiStage.act(dt);
		this.mainStage.act(dt);
		
		this.update(dt);
		
		Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		this.mainStage.draw();
		this.uiStage.draw();
	}
	
	//methods required by Screen interface
	@Override
	public void resize(int width, int height) { }
	
	@Override
	public void pause()   { }
	
	@Override
	public void resume()  { }
	
	@Override
	public void dispose() { }
	
	@Override
	public void show() {
		InputMultiplexer im = (InputMultiplexer)Gdx.input.getInputProcessor();
		im.addProcessor(this);
		im.addProcessor(this.uiStage);
		im.addProcessor(this.mainStage);
	}
	
	@Override
	public void hide() {
		InputMultiplexer im = (InputMultiplexer)Gdx.input.getInputProcessor();
		im.removeProcessor(this);
		im.removeProcessor(this.uiStage);
		im.removeProcessor(this.mainStage);
	}
	
	@Override
	public boolean keyDown(int keycode) { return false; }

	@Override
	public boolean keyUp(int keycode) { return false; }

	@Override
	public boolean keyTyped(char character) { return false; }

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) { return false; }

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) { return false; }

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) { return false; }

	@Override
	public boolean mouseMoved(int screenX, int screenY) { return false; }

	@Override
	public boolean scrolled(int amount) { return false; }
	
}
