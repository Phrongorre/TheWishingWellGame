package cs328.uidaho.tww.actors.scenes;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;

import cs328.uidaho.tww.actors.BaseActor;

public class SetAnimationAction extends Action
{
	
	protected Animation<TextureRegion> animationToDisplay;
	
	public SetAnimationAction(Animation<TextureRegion> a)
	{
		this.animationToDisplay = a;
	}
	
	@Override
	public boolean act(float dt)
	{
		BaseActor ba = (BaseActor)this.target;
		ba.getAnimator().setAnimation(this.animationToDisplay);
		return true;
	}
	
}
