package cs328.uidaho.tww.actors.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import cs328.uidaho.tww.actors.BaseActor2;

public class Animator
{
	
	public static final int FACING_RIGHT = 0;
	public static final int FACING_LEFT  = 1;
	public static final int MOTION_IDLE  = 0;
	public static final int MOTION_WALK  = 1;
	private int facing;
	private int motion;
	
	private BaseActor2 baseActor2;
	private Array<Animation<TextureRegion>> anims;
	private Array<AnimationLR> animlrs;
	private int activeAnimIndex;
	private float stateTime;
	private boolean animationPaused;
	
	public Animator(BaseActor2 ba2)
	{
		this.baseActor2 = ba2;
		this.anims = new Array<Animation<TextureRegion>>();
		this.activeAnimIndex = -1;
		this.stateTime = 0f;
		this.animationPaused = false;
		this.animlrs = new Array<AnimationLR>();
	}
	
	public void setFacing(int facingcode)
	{
		this.facing = facingcode;
	}
	
	public boolean isFacing(int facingcode)
	{
		return this.facing == facingcode;
	}
	
	public void setMotion(int motioncode)
	{
		this.motion = motioncode;
	}
	
	public boolean isMotion(int motioncode)
	{
		return this.motion == motioncode;
	}
	
	//Set the correct animation based on the value of facing state
	private void setAnimationWithFacing(Animation<TextureRegion> leftAnim, Animation<TextureRegion> rightAnim)
	{
		switch (this.facing)
		{
		default:
		case FACING_LEFT:
			this.setAnimation(leftAnim);
			break;
		case FACING_RIGHT:
			this.setAnimation(rightAnim);
			break;
		}
	}
	
	//Use motionState and facingState to update current animation
	public void updateAnimation()
	{
		this.setAnimationWithFacing(this.animlrs.get(this.motion).left(), this.animlrs.get(this.motion).right());
	}
	
	public void setAnimationLR(int index, Animation<TextureRegion> lanim, Animation<TextureRegion> ranim)
	{
		if (index >= this.animlrs.size) { this.animlrs.setSize(index+1); }
		this.animlrs.set(index, new AnimationLR(lanim, ranim));
	}
	
	public void setAnimation(Animation<TextureRegion> animation)
	{
		if (!this.anims.contains(animation, false))
		{
			this.anims.add(animation);
		}
		this.activeAnimIndex = this.anims.indexOf(animation, false);
		
		TextureRegion tr = this.anims.get(this.activeAnimIndex).getKeyFrame(0f);
		float w = tr.getRegionWidth();
		float h = tr.getRegionHeight();
		this.baseActor2.setSize(w, h);
		this.baseActor2.setOrigin(w/2, h/2);
		
		//Since size was just set, set boundary rectangle
		if (!this.baseActor2.hasBoundaryPolygon())
		{
			this.baseActor2.setBoundaryRectangle();
		}
	}
	
	public boolean hasAnimation()
	{
		return (this.activeAnimIndex >= 0);
	}
	
	public void restartAnimation()
	{
		this.stateTime = 0f;
	}
	
	public void setAnimationPaused(boolean pause)
	{
		this.animationPaused = pause;
	}
	
	public void update(float dt)
	{
		if (!this.animationPaused)
		{
			this.stateTime += dt;
		}
	}
	
	public TextureRegion getKeyFrame()
	{
		if (this.activeAnimIndex >= 0) {
			return this.anims.get(this.activeAnimIndex).getKeyFrame(this.stateTime);
		}
		return null;
	}
	
	public Animation<TextureRegion> loadAnimationFromFiles(String[] fileNames, float frameDuration, boolean loop,
			TextureFilter minFilter, TextureFilter magFilter)
	{
		int fileCount = fileNames.length;
		Array<TextureRegion> textureArray = new Array<TextureRegion>();
		
		for (int n=0; n < fileCount; n++)
		{
			String fileName = fileNames[n];
			Texture texture = new Texture(Gdx.files.internal(fileName));
			texture.setFilter(minFilter, magFilter);
			textureArray.add(new TextureRegion(texture));
		}
		
		Animation<TextureRegion> anim = new Animation<TextureRegion>(frameDuration, textureArray);
		
		if (loop)
		{
			anim.setPlayMode(Animation.PlayMode.LOOP);
		}
		else
		{
			anim.setPlayMode(Animation.PlayMode.NORMAL);
		}
		
		//If no active animation, set this as active animation
		if (this.activeAnimIndex < 0)
		{
			this.setAnimation(anim);
		}
		//Otherwise, just add it to anims
		else
		{
			this.anims.add(anim);
		}
		
		return anim;
	}
	
	public Animation<TextureRegion> loadAnimationFromFiles(String[] fileNames, float frameDuration, boolean loop)
	{
		return this.loadAnimationFromFiles(fileNames, frameDuration, loop, TextureFilter.Linear, TextureFilter.Linear);
	}
	
	public Animation<TextureRegion> loadAnimationFromSheet(String fileName, int rows, int cols, float frameDuration, boolean loop,
			TextureFilter minFilter, TextureFilter magFilter)
	{
		Texture texture = new Texture(Gdx.files.internal(fileName));
		texture.setFilter(minFilter, magFilter);
		int frameWidth = texture.getWidth() / cols;
		int frameHeight = texture.getHeight() / rows;
		
		TextureRegion[][] temp = TextureRegion.split(texture, frameWidth, frameHeight);
		Array<TextureRegion> textureArray = new Array<TextureRegion>();
		
		for (int r=0; r < rows; r++)
		{
			for (int c=0; c < cols; c++)
			{
				textureArray.add(temp[r][c]);
			}
		}
		
		Animation<TextureRegion> anim = new Animation<TextureRegion>(frameDuration, textureArray);
		
		if (loop)
		{
			anim.setPlayMode(Animation.PlayMode.LOOP);
		}
		else
		{
			anim.setPlayMode(Animation.PlayMode.NORMAL);
		}
		
		//If no active animation, set this as active animation
		if (this.activeAnimIndex < 0)
		{
			this.setAnimation(anim);
		}
		//Otherwise, just add it to anims
		else
		{
			this.anims.add(anim);
		}
		
		return anim;
	}
	
	public Animation<TextureRegion> loadAnimationFromSheet(String fileName, int rows, int cols, float frameDuration, boolean loop)
	{
		return loadAnimationFromSheet(fileName, rows, cols, frameDuration, loop, TextureFilter.Linear, TextureFilter.Linear);
	}
	
	public Animation<TextureRegion> loadTexture(String fileName, TextureFilter minFilter, TextureFilter magFilter)
	{
		String[] fileNames = new String[1];
		fileNames[0] = fileName;
		return loadAnimationFromFiles(fileNames, 1f, true, minFilter, magFilter);
	}
	
	public Animation<TextureRegion> loadTexture(String fileName)
	{
		return this.loadTexture(fileName, TextureFilter.Linear, TextureFilter.Linear);
	}
	
	public Animation<TextureRegion> loadTextureFromSegment(String filename, int u, int v, int w, int h, TextureFilter minFilter, TextureFilter magFilter)
	{
		Texture texture = new Texture(Gdx.files.internal(filename));
		texture.setFilter(minFilter, magFilter);
		TextureRegion frame = new TextureRegion(texture, u, v, w, h);
		
		Array<TextureRegion> textureArray = new Array<TextureRegion>();
		textureArray.add(frame);
		
		Animation<TextureRegion> anim = new Animation<TextureRegion>(1f, textureArray);
		anim.setPlayMode(Animation.PlayMode.LOOP);
		
		//If no active animation, set this as active animation
		if (this.activeAnimIndex < 0)
		{
			this.setAnimation(anim);
		}
		//Otherwise, just add it to anims
		else
		{
			this.anims.add(anim);
		}
		
		return anim;
	}
	
	public boolean isAnimationFinished()
	{
		return this.anims.get(this.activeAnimIndex).isAnimationFinished(this.stateTime);
	}
	
	public void setOpacity(float opacity)
	{
		this.baseActor2.getColor().a = opacity;
	}
}
