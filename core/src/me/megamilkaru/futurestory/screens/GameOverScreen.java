package me.megamilkaru.futurestory.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.Align;

import me.megamilkaru.futurestory.FutureStory;

public class GameOverScreen implements Screen 
{
	private static final int BANNER_WIDTH = 350;
	private static final int BANNER_HEIGHT = 200;
	
	FutureStory game;
	
	int score, highscore;
	
	Texture gameOverBanner;
	BitmapFont scoreFont;
	
	
	public GameOverScreen (FutureStory game, int score)
	{
		this.game = game;
		this.score = score;
		
		//Get highscore from this save file
		Preferences prefs = Gdx.app.getPreferences("futurestory");
		this.highscore = prefs.getInteger("highscore", 0); //search for highscore, get it's integer value. set to 0 otherwise.
		
		//Check if score beats highscore
		if(score > highscore)
		{
			prefs.putInteger("highscore", score);
			prefs.flush(); //flush == save to file.
		}
		
		//Load textures and fonts
		gameOverBanner = new Texture("menu/GameOver.png");
		scoreFont = new BitmapFont(Gdx.files.internal("fonts/pressStart2P.fnt"));
	}
	
	@Override
	public void show() 
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void render(float delta) 
	{
		Gdx.gl.glClearColor(0, 0, 0, 1); //gl is 'opengl'.
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		game.batch.begin();
		
		game.batch.draw(gameOverBanner, Gdx.graphics.getWidth() / 2 - BANNER_WIDTH / 2, Gdx.graphics.getHeight() - BANNER_HEIGHT - 15, BANNER_WIDTH, BANNER_HEIGHT);
		
		GlyphLayout scoreLayout = new GlyphLayout(scoreFont, "Score : \n" + score, Color.WHITE, 0, Align.left, false);
		GlyphLayout highscoreLayout = new GlyphLayout(scoreFont, "Highscore : \n" + highscore, Color.WHITE, 0, Align.left, false);
		scoreFont.draw(game.batch, scoreLayout, Gdx.graphics.getWidth() / 2 - scoreLayout.width / 2, Gdx.graphics.getHeight() - BANNER_HEIGHT - 15 * 2);
		scoreFont.draw(game.batch, highscoreLayout, Gdx.graphics.getWidth() / 2 - highscoreLayout.width / 2, Gdx.graphics.getHeight() - BANNER_HEIGHT - - highscoreLayout.height - 15 * 2);
		
		GlyphLayout tryAgainLayout = new GlyphLayout(scoreFont, "Try Again"); //glyph is top to bottom.
		GlyphLayout mainMenuLayout = new GlyphLayout(scoreFont, "Main Menu"); //everything else is bottom to top.
		
		float tryAgainX = Gdx.graphics.getWidth() / 2 - tryAgainLayout.width / 2;
		float tryAgainY = Gdx.graphics.getHeight() / 2 - tryAgainLayout.height / 2;
		float mainMenuX = Gdx.graphics.getWidth() / 2 - mainMenuLayout.width / 2;
		float mainMenuY = Gdx.graphics.getHeight() / 2 - mainMenuLayout.height / 2 - tryAgainLayout.height - 30;
		
		float touchX = Gdx.input.getX(), touchY = Gdx.graphics.getHeight() - Gdx.input.getY();
		
		//If try again and main menu is pressed
		if(Gdx.input.isTouched())
		{
			//Try again
			if(touchX > tryAgainX && touchX < tryAgainX + tryAgainLayout.width && touchY > tryAgainY - tryAgainLayout.height && touchY < tryAgainY)
			{
				this.dispose();
				game.batch.end();
				game.setScreen(new MainGameScreen(game));
				return;
			}
			
			//Main menu
			if(touchX > mainMenuX && touchX < mainMenuX + mainMenuLayout.width && touchY > mainMenuY - mainMenuLayout.height && touchY < mainMenuY)
			{
				this.dispose();
				game.batch.end();
				game.setScreen(new MainMenuScreen(game));
				return;
			}
		}
		
		//Draw buttons
		scoreFont.draw(game.batch, tryAgainLayout, tryAgainX, tryAgainY);
		scoreFont.draw(game.batch, mainMenuLayout, mainMenuX, mainMenuY);
		
		game.batch.end();
	}

	@Override
	public void resize(int width, int height) 
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() 
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() 
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() 
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() 
	{
		// TODO Auto-generated method stub

	}

}
