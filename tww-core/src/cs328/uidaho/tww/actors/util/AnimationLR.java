package cs328.uidaho.tww.actors.util;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AnimationLR
{
	
	private Animation<TextureRegion> leftAnim;
	private Animation<TextureRegion> rightAnim;
	
	public AnimationLR()
	{
		this.leftAnim  = null;
		this.rightAnim = null;
	}
	
	public AnimationLR(Animation<TextureRegion> lanim, Animation<TextureRegion> ranim)
	{
		this.leftAnim  = lanim;
		this.rightAnim = ranim;
	}
	
	public Animation<TextureRegion> left()
	{
		return this.leftAnim;
	}
	
	public Animation<TextureRegion> right()
	{
		return this.rightAnim;
	}
	
}
