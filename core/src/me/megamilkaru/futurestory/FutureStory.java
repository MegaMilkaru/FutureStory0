package me.megamilkaru.futurestory;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import me.megamilkaru.futurestory.screens.GameOverScreen;
import me.megamilkaru.futurestory.screens.MainGameScreen;
import me.megamilkaru.futurestory.screens.MainMenuScreen;

public class FutureStory extends Game //ApplicationAdapter <--CHANGE THIS!!!!
{
	public static final int WIDTH = 480;
	public static final int HEIGHT = 720;
	
	public SpriteBatch batch;
	
	@Override
	public void create() 
	{
		batch = new SpriteBatch();
		this.setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render() 
	{
		super.render();
	}
}
