package me.megamilkaru.futurestory.screens;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import me.megamilkaru.futurestory.FutureStory;
import me.megamilkaru.futurestory.entities.Asteroid;
import me.megamilkaru.futurestory.entities.Bullet;
import me.megamilkaru.futurestory.entities.Explosion;
import me.megamilkaru.futurestory.tools.CollisionRect;

public class MainGameScreen implements Screen
{
	public static final float SPEED = 300;
	
	public static final float SHIP_ANIMATION_SPEED = 0.1f;
	public static final int SHIP_WIDTH_PIXEL = 60;
	public static final int SHIP_HEIGHT_PIXEL = 72;
	public static final int SHIP_WIDTH = SHIP_WIDTH_PIXEL;
	public static final int SHIP_HEIGHT = SHIP_HEIGHT_PIXEL;
	public static final float SHOOT_WAIT_TIME = 0.3f;
	
	public static final float MIN_ASTEROID_SPAWN_TIME = 0.0f;
	public static final float MAX_ASTEROID_SPAWN_TIME = 0.2f;
	
	//in the tutorial, they use a "roll timer" for the tilting animation.
	//your ship uses a thruster, so theres no need.
	
	Animation<TextureRegion>[] shipStates;
	Texture hitBox;
	Texture leftThruster;
	Texture rightThruster;
	
	float x;
	float y;
	int shipState;
	float stateTime;
	float shootTimer;
	float asteroidSpawnTimer;
	
	Random random;
	
	FutureStory game;
	
	ArrayList<Bullet> bullets;
	ArrayList<Asteroid> asteroids;
	ArrayList<Explosion> explosions;
	
	Texture blank;
	
	BitmapFont scoreFont;
	
	CollisionRect playerRect;
	
	float health = 1; //0, dead. 1, full
	
	int score;
	
	public MainGameScreen (FutureStory game)
	{
		this.game = game;
		y = 40;
		x = FutureStory.WIDTH/2 - SHIP_WIDTH/2;
		bullets = new ArrayList<Bullet>();
		asteroids = new ArrayList<Asteroid>();
		explosions = new ArrayList<Explosion>();
		scoreFont = new BitmapFont(Gdx.files.internal("fonts/pressStart2P.fnt"));//internal means getting it from "assets"
		
		playerRect = new CollisionRect(0, 0, SHIP_WIDTH, SHIP_HEIGHT);
		
		blank = new Texture("player/Blank.png");
		
		score = 0;
		
		random = new Random();
		asteroidSpawnTimer = random.nextFloat() * (MAX_ASTEROID_SPAWN_TIME - MIN_ASTEROID_SPAWN_TIME) +  MIN_ASTEROID_SPAWN_TIME;
		
		shootTimer = 0;
		
		shipState = 2;
		shipStates = new Animation[4];
		
		TextureRegion[][] shipSpriteSheet = TextureRegion.split(new Texture("player/playerShip.png"), SHIP_WIDTH_PIXEL, SHIP_HEIGHT_PIXEL);
		
		shipStates[shipState] = new Animation(SHIP_ANIMATION_SPEED, shipSpriteSheet[0]);
		
		hitBox = new Texture("player/playerHitBox.png");
		leftThruster = new Texture ("player/leftThruster.png");
		rightThruster = new Texture("player/rightThruster.png");
		
	}
	
	@Override
	public void show() //screen first appears... "create"
	{
		
		
	}

	@Override
	public void render(float delta) //the 'draw' function
	{	
		//delta time is the amount of real life seconds it takes for the game to do one tick.
		//its (the time the second previous frame finished) - (the time the previous frame finished)
		//fps = 1/deltatime.	deltatime = 1/60.
		
		//System.out.println("Delta time: " + Gdx.graphics.getDeltaTime() + ",  FPS: " + Gdx.graphics.getFramesPerSecond());
		
		//shooting code
		shootTimer += delta;
		if(Gdx.input.isKeyJustPressed(Keys.SHIFT_RIGHT) && shootTimer >= SHOOT_WAIT_TIME)
		{
			shootTimer = 0;
			bullets.add(new Bullet(x + SHIP_WIDTH/2 - Bullet.SIZE/2));
		}
		
		//update bullets
		ArrayList<Bullet> bulletsToRemove = new ArrayList<Bullet>();
		for (Bullet bullet : bullets)
		{
			bullet.update(delta); //first, update
			if(bullet.remove)
				bulletsToRemove.add(bullet); //second, list the ones that should be removed
		}
		
		//Asteroid spawn code
		asteroidSpawnTimer -= delta;
		if(asteroidSpawnTimer <= 0)
		{
			asteroidSpawnTimer = random.nextFloat() * (MAX_ASTEROID_SPAWN_TIME - MIN_ASTEROID_SPAWN_TIME) +  MIN_ASTEROID_SPAWN_TIME;
			for(int i = 0; i < random.nextInt(3); i++)
			{
				asteroids.add(new Asteroid(random.nextInt(Gdx.graphics.getWidth()) - Asteroid.WIDTH)); //create random, 0 <--> screenWidth
			}
		}
		
		//Update asteroids
		ArrayList<Asteroid> asteroidsToRemove = new ArrayList<Asteroid>();
		for(Asteroid asteroid : asteroids)
		{
			asteroid.update(delta);
			if(asteroid.remove)
				asteroidsToRemove.add(asteroid);
		}
		
		//Update explosions
		ArrayList<Explosion> explosionsToRemove = new ArrayList<Explosion>();
		for (Explosion explosion : explosions)
		{
			explosion.update(delta);
			if(explosion.remove)
				explosionsToRemove.add(explosion);
		}
		
		//movement code
		boolean rightThrust = false;
		boolean leftThrust = false;
		
		if(Gdx.input.isKeyPressed(Keys.A)) //left
		{
			x -= SPEED * Gdx.graphics.getDeltaTime();
			
			rightThrust = true;
		}
		if(Gdx.input.isKeyPressed(Keys.D)) //right
		{
			x += SPEED * Gdx.graphics.getDeltaTime();
			
			leftThrust = true;
		}
		
		//After player moves, update collision Rect
		playerRect.move(x, y);
		
		//After all updates, check for collisions
		for (Bullet bullet : bullets)
		{
			for (Asteroid asteroid : asteroids)
			{
				if(bullet.getCollisionRect().collidesWith(asteroid.getCollisionRect()))
				{
					bulletsToRemove.add(bullet);
					asteroidsToRemove.add(asteroid);
					explosions.add(new Explosion(asteroid.getX(), asteroid.getY()));
					score += 100;
				}
			}
		}
		
		
		
		for(Asteroid asteroid : asteroids)
		{
			if(asteroid.getCollisionRect().collidesWith(playerRect))
			{
				explosions.add(new Explosion(asteroid.getX(), asteroid.getY()));
				asteroidsToRemove.add(asteroid);
				health -= 0.25;
				if(health <= 0)
					game.setScreen(new GameOverScreen(game, score));
			}
		}
		
		//Remove everything you must
		asteroids.removeAll(asteroidsToRemove);
		asteroidsToRemove.clear();
		
		bullets.removeAll(bulletsToRemove);
		bulletsToRemove.clear();
		
		explosions.removeAll(explosionsToRemove);
		explosionsToRemove.clear();
		
				
		//draw code
		stateTime += delta; //<--- this is statetime. the actual total seconds elapsed
		Gdx.gl.glClearColor(0, 0, 0, 1); //gl is 'opengl'.
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		game.batch.begin();
		
		if(rightThrust)
		{
			if(x < 0)
				x = 0;
			else
				game.batch.draw(rightThruster, x, y);
		}
		if(leftThrust)
		{
			if(x + SHIP_WIDTH > Gdx.graphics.getWidth())
				x = Gdx.graphics.getWidth() - SHIP_WIDTH;
			else
				game.batch.draw(leftThruster, x, y);
		}
		for(Bullet bullet : bullets)
		{
			bullet.render(game.batch);
		}
		
		for(Asteroid asteroid : asteroids)
		{
			asteroid.render(game.batch);
		}
		
		for(Explosion explosion : explosions)
		{
			explosion.render(game.batch);
		}
		
		if(health > 0.6f)
			game.batch.setColor(Color.GREEN);
		else if(health > 0.3f)
			game.batch.setColor(Color.ORANGE);
		else
			game.batch.setColor(Color.RED);
		
		game.batch.draw(blank, 0, 0, Gdx.graphics.getWidth() * health, 5);
		
		game.batch.setColor(Color.WHITE); //default shader;
		
		game.batch.draw(shipStates[shipState].getKeyFrame(stateTime, true), x, y, SHIP_WIDTH, SHIP_HEIGHT);
		
		game.batch.draw(hitBox, x, y);
		
		GlyphLayout scoreLayout = new GlyphLayout(scoreFont, "" + score);
		scoreFont.draw(game.batch, scoreLayout, Gdx.graphics.getWidth() / 2 - scoreLayout.width / 2, Gdx.graphics.getHeight() - scoreLayout.height - 10);
		
		game.batch.end();
		
	}

	@Override
	public void resize(int width, int height) 
	{
		
	}

	@Override
	public void pause() 
	{
		
		
	}

	@Override
	public void resume() 
	{
		
	}

	@Override
	public void hide() 
	{
		
	}

	@Override
	public void dispose() //something you call when you wanna get rid of a screen
	{
		
	}
	
}
