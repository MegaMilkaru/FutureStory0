package me.megamilkaru.futurestory.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

import me.megamilkaru.futurestory.FutureStory;

public class MainMenuScreen implements Screen 
{
	//create constants. its a good practice!
	private static final int EXIT_BUTTON_WIDTH = 300;
	private static final int EXIT_BUTTON_HEIGHT = 100;
	private static final int START_BUTTON_WIDTH = 330;
	private static final int START_BUTTON_HEIGHT = 100;
	private static final int EXIT_BUTTON_Y = 100;
	private static final int START_BUTTON_Y = 400;
	
	FutureStory game;
	
	Texture exitButtonActive;
	Texture exitButtonInactive;
	Texture startButtonActive;
	Texture startButtonInactive;
	
	
	public MainMenuScreen (FutureStory game)
	{
		this.game = game;
		//the pathing automatically starts at the "assets" folder given to you
		exitButtonActive = new Texture("menu/mStophover.png");
		exitButtonInactive = new Texture("menu/mStop.png");
		startButtonActive = new Texture("menu/mStarthover.png");
		startButtonInactive = new Texture("menu/mStart.png");
		
	}

	@Override
	public void show() 
	{
		
	}

	@Override
	public void render(float delta) 
	{
		Gdx.gl.glClearColor(0.01f, 0.075f, 0.24f, 1); //they are decimals. percentages from 0 to 1.
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		game.batch.begin();
		
		int x = FutureStory.WIDTH/2 - EXIT_BUTTON_WIDTH / 2;
		if(Gdx.input.getX() < x + EXIT_BUTTON_WIDTH && Gdx.input.getX() > x && FutureStory.HEIGHT - Gdx.input.getY() < EXIT_BUTTON_Y + EXIT_BUTTON_HEIGHT && FutureStory.HEIGHT - Gdx.input.getY() > EXIT_BUTTON_Y) 
		{
			game.batch.draw(exitButtonActive, x, EXIT_BUTTON_Y, EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
			if(Gdx.input.isTouched()) //if mouse presses. 
			{
				Gdx.app.exit();
			}
		}
		else
		{
			game.batch.draw(exitButtonInactive, x, EXIT_BUTTON_Y, EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
		}
		
		x = FutureStory.WIDTH/2 - START_BUTTON_WIDTH / 2;
		if(Gdx.input.getX() < x + START_BUTTON_WIDTH && Gdx.input.getX() > x && FutureStory.HEIGHT - Gdx.input.getY() < START_BUTTON_Y + START_BUTTON_HEIGHT && FutureStory.HEIGHT - Gdx.input.getY() > START_BUTTON_Y) 
		{
			game.batch.draw(startButtonActive, x, START_BUTTON_Y, START_BUTTON_WIDTH, START_BUTTON_HEIGHT);
			if(Gdx.input.isTouched()) //if mouse presses. 
			{
				this.dispose();
				game.setScreen(new MainGameScreen(game)); //its as easy as that... changing screens.
			}
		}
		else
		{
			game.batch.draw(startButtonInactive, x, START_BUTTON_Y, START_BUTTON_WIDTH, START_BUTTON_HEIGHT);
		}
		
		game.batch.end();
	}

	@Override
	public void resize(int width, int height) 
	{
		
	}

	@Override
	public void pause() {}

	@Override
	public void resume() {}

	@Override
	public void hide() {}

	@Override
	public void dispose() 
	{
		
	}
}
