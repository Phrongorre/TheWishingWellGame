package cs328.uidaho.tww.gui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;

import cs328.uidaho.tww.BaseGame;
import cs328.uidaho.tww.actors.BaseActor;

public class DialogueBox extends BaseActor {

	private Label dialogueLabel;
	private Table dialogueTable;
	private float padding = 16f;
	
	public DialogueBox(float x, float y, Stage s) {
		super(x, y, s);
		
		this.dialogueTable = new Table();
		
		this.loadTexture("gui/dialog-translucent.png");
		this.dialogueLabel = new Label(" ", BaseGame.labelStyle);
		this.dialogueLabel.setWrap(true);
		this.alignTopLeft();
		this.dialogueTable.add(this.dialogueLabel).align(Align.topLeft);
		this.dialogueTable.pad(this.padding);
		this.dialogueTable.align(Align.topLeft);
		this.dialogueTable.setSize(this.getWidth(), this.getHeight());
		
		this.addActor(this.dialogueTable);
	}
	
	public DialogueBox(Stage s) { this(0f, 0f, s); }
	
	public void reset() {
		this.dialogueTable.clear();
		this.dialogueTable.add(this.dialogueLabel).align(Align.topLeft);
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
	
	public void addTextButton(TextButton textButton) {
		this.dialogueTable.row();
		this.dialogueTable.add(textButton).align(Align.center);
	}
	
}
