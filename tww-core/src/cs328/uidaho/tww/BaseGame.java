package cs328.uidaho.tww;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

import cs328.uidaho.tww.screens.BaseScreen;

public abstract class BaseGame extends Game {
	
	private static BaseGame game;
	public static LabelStyle labelStyle;
	public static TextButtonStyle textButtonStyle;
	
	public BaseGame() { BaseGame.game = this; }
	
	@Override
	public void create() {
		//prepare for multiple classes/stages to receive discrete input
		InputMultiplexer im = new InputMultiplexer();
		Gdx.input.setInputProcessor(im);
		
		BaseGame.labelStyle = new LabelStyle();
		//HIERO-GENERATED METHOD
		//this.labelStyle.font = new BitmapFont(Gdx.files.internal("cooper.fnt"));
		//FREETYPEFONTGENERATOR METHOD
		FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("gui/OpenSans.ttf"));
		FreeTypeFontParameter fontParameters = new FreeTypeFontParameter();
		fontParameters.size = 32;
		fontParameters.color = Color.WHITE;
		fontParameters.borderWidth = 2;
		fontParameters.borderColor = Color.BLACK;
		fontParameters.borderStraight = true;
		fontParameters.minFilter = TextureFilter.Linear;
		fontParameters.magFilter = TextureFilter.Linear;
		BitmapFont customFont = fontGenerator.generateFont(fontParameters);
		BaseGame.labelStyle.font = customFont;
		
		textButtonStyle = new TextButtonStyle();
		Texture buttonTex = new Texture(Gdx.files.internal("gui/button.png"));
		NinePatch buttonPatch = new NinePatch(buttonTex, 12, 12, 12, 12);
		textButtonStyle.up = new NinePatchDrawable(buttonPatch);
		textButtonStyle.font = customFont;
		textButtonStyle.fontColor = Color.GRAY;
	}
	
	public static void setActiveScreen(BaseScreen s) {
		BaseGame.game.setScreen(s);
	}

}
