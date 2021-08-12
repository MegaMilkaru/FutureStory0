package me.megamilkaru.futurestory.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import me.megamilkaru.futurestory.tools.CollisionRect;

public class Asteroid 
{
	public static final int SPEED = 400;
	public static final int DEFAULT_Y = 40;
	public static final int WIDTH = 16;
	public static final int HEIGHT = 16;
	private static Texture texture; //"static so every bullet uses the same texture instead of recreating itself"
	
	float x, y;
	CollisionRect rect;
	public boolean remove = false;
	
	public Asteroid(float x) //custom made
	{
		this.x = x;
		this.y = Gdx.graphics.getHeight();

		this.rect = new CollisionRect(x, y, WIDTH, HEIGHT);
		
		if (texture == null)
			texture = new Texture("player/Asteroid.png");
	}
	
	public void update (float deltaTime) //custome made.
	{
		y -= SPEED * deltaTime; //REMEMBER THIS ONE LINE!!!
		if (y < -HEIGHT)
			remove = true;

		rect.move(x, y);
	}
	
	public void render (SpriteBatch batch) //custom made.
	{
		batch.draw(texture, x, y, WIDTH, HEIGHT);
	}
	
	public CollisionRect getCollisionRect ()
	{
		return rect;
	}
	
	public float getX()
	{
		return x;
	}
	
	public float getY()
	{
		return y;
	}
}
