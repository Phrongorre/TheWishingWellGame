package cs328.uidaho.tww.actors.scenes;

import com.badlogic.gdx.scenes.scene2d.Action;

import cs328.uidaho.tww.actors.DialogueBox;

public class SetTextAction extends Action
{
	
	protected String textToDisplay;
	
	public SetTextAction(String t)
	{
		this.textToDisplay = t;
	}
	
	@Override
	public boolean act(float dt)
	{
		DialogueBox db = (DialogueBox)this.target;
		db.setText(this.textToDisplay);
		return true;
	}
	
}
