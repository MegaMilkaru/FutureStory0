package me.megamilkaru.futurestory.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import me.megamilkaru.futurestory.FutureStory;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		//config helps you do shit like FPS... full screen...
		config.foregroundFPS = 60;
		config.fullscreen = false;
		config.width = FutureStory.WIDTH;	//"Since it's static, you can access it from the class. you dont need object" - HollowBit
		config.height = FutureStory.HEIGHT;
		config.resizable = false;
		
		new LwjglApplication(new FutureStory(), config);
	}
}
