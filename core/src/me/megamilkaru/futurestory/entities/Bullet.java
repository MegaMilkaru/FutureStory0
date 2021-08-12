package me.megamilkaru.futurestory.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import me.megamilkaru.futurestory.tools.CollisionRect;

public class Bullet 
{
	public static final int SPEED = 500;
	public static final int DEFAULT_Y = 40;
	public static final int SIZE = 20;
	private static Texture texture; //"static so every bullet uses the same texture instead of recreating itself"
	
	float x, y;
	CollisionRect rect;
	public boolean remove = false;
	
	public Bullet(float x) //custom made
	{
		this.x = x;
		this.y = DEFAULT_Y;
		
		this.rect = new CollisionRect(x, y, SIZE, SIZE);
		
		if (texture == null)
			texture = new Texture("player/Bullet.png");
	}
	
	public void update (float deltaTime) //custome made.
	{
		y += SPEED * deltaTime; //REMEMBER THIS ONE LINE!!!
		if (y > Gdx.graphics.getHeight())
			remove = true;
		
		rect.move(x, y);
	}
	
	public void render (SpriteBatch batch) //custom made.
	{
		batch.draw(texture, x, y, SIZE, SIZE);
	}
	
	public CollisionRect getCollisionRect()
	{
		return rect;
	}
}
