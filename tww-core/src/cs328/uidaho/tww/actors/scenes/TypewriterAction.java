package cs328.uidaho.tww.actors.scenes;

import cs328.uidaho.tww.actors.DialogueBox;

public class TypewriterAction extends SetTextAction
{
	
	private float elapsedTime;
	private float charactersPerSecond;
	
	public TypewriterAction(String t)
	{
		super(t);
		
		this.elapsedTime = 0f;
		this.charactersPerSecond = 30f;
	}
	
	@Override
	public boolean act(float dt)
	{
		this.elapsedTime += dt;
		int numberOfCharacters = (int)(this.elapsedTime * this.charactersPerSecond);
		
		if (numberOfCharacters > this.textToDisplay.length())
		{
			numberOfCharacters = this.textToDisplay.length();
		}
		
		String partialText = this.textToDisplay.substring(0, numberOfCharacters);
		DialogueBox db = (DialogueBox)this.target;
		db.setText(partialText);
		
		//action is complete when all characters have been displayed
		return (numberOfCharacters >= this.textToDisplay.length());
	}
	
}
