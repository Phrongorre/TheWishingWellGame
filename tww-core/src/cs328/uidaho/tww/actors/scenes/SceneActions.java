package cs328.uidaho.tww.actors.scenes;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Align;

import cs328.uidaho.tww.actors.BaseActor;

public class SceneActions extends Actions
{
	
	public static Action setText(String s)
	{
		return new SetTextAction(s);
	}
	
	public static Action pause()
	{
		return Actions.forever(Actions.delay(1f));
	}
	
	public static Action moveToScreenLeft(float duration)
	{
		return Actions.moveToAligned(
			0f,
			0f,
			Align.bottomLeft,
			duration
		);
	}
	
	public static Action moveToScreenRight(float duration)
	{
		return Actions.moveToAligned(
			BaseActor.getWorldBounds().width,
			0f,
			Align.bottomRight,
			duration
		);
	}
	
	public static Action moveToScreenCenter(float duration)
	{
		return Actions.moveToAligned(
			BaseActor.getWorldBounds().width / 2f,
			0f,
			Align.bottom,
			duration
		);
	}
	
	public static Action moveToOutsideLeft(float duration)
	{
		return Actions.moveToAligned(
			0f,
			0f,
			Align.bottomRight,
			duration
		);
	}
	
	public static Action moveToOutsideRight(float duration)
	{
		return Actions.moveToAligned(
			BaseActor.getWorldBounds().width,
			0f,
			Align.bottomLeft,
			duration
		);
	}
	
	public static Action setAnimation(Animation<TextureRegion> a)
	{
		return new SetAnimationAction(a);
	}
	
	public static Action typewriter(String s)
	{
		return new TypewriterAction(s);
	}
	
}
