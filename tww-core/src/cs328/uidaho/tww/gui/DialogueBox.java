package cs328.uidaho.tww.gui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;

import cs328.uidaho.tww.BaseGame;
import cs328.uidaho.tww.actors.BaseActor;

public class DialogueBox extends BaseActor {

	private Label dialogueLabel;
	private Table buttonTable;
	private float padding = 16f;
	
	public DialogueBox(float x, float y, Stage s) {
		super(x, y, s);
		
		this.loadTexture("gui/dialog-translucent.png");
		this.dialogueLabel = new Label(" ", BaseGame.labelStyle);
		this.dialogueLabel.setWrap(true);
		this.alignTopLeft();
		this.dialogueLabel.setPosition(this.padding, this.padding);
		this.setDialogueSize(this.getWidth(), this.getHeight());
		this.addActor(this.dialogueLabel);
	}
	
	public DialogueBox(Stage s) { this(0f, 0f, s); }
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		
	}
	
	public void setDialogueSize(float width, float height) {
		this.setSize(width, height);
		this.dialogueLabel.setWidth(width - 2 * this.padding);
		this.dialogueLabel.setHeight(height - 2 * this.padding);
	}
	
	public void setText(String text) {
		this.dialogueLabel.setText(text);
	}
	
	public void setFontScale(float scale) {
		this.dialogueLabel.setFontScale(scale);
	}
	
	public void setFontColor(Color color) {
		this.dialogueLabel.setColor(color);
	}
	
	public void setBackgroundColor(Color color) {
		this.setColor(color);
	}
	
	public void alignTopLeft() {
		this.dialogueLabel.setAlignment(Align.topLeft);
	}
	
	public void alignCenter() {
		this.dialogueLabel.setAlignment(Align.center);
	}
	
}
