package me.megamilkaru.futurestory.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Explosion 
{
	public static final float FRAME_LENGTH = 0.2f; //how long it takes to finish the animation
	public static final int OFFSET = 32; //
	public static final int SIZE = 64;
	
	private static Animation<TextureRegion> anim = null;
	float x,y;
	float stateTime;
	
	public boolean remove = false;
	
	public Explosion (float x, float y)
	{
		this.x = x - OFFSET;
		this.y = y - OFFSET;
		stateTime = 0;
		
		if (anim == null) //		 frame duration, array of the image aniamtion
				anim = new Animation<TextureRegion>(FRAME_LENGTH, TextureRegion.split(new Texture("player/Explosion.png"), SIZE, SIZE)[0]);
	}
	
	public void update (float deltaTime)
	{
		stateTime += deltaTime;
		if(anim.isAnimationFinished(stateTime))
			remove = true;
	}
	
	public void render (SpriteBatch batch)
	{
		batch.draw(anim.getKeyFrame(stateTime), x, y);
	}
}
