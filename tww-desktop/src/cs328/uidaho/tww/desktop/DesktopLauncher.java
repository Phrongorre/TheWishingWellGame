package cs328.uidaho.tww.desktop;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

import cs328.uidaho.tww.WishingWellGame;

public class DesktopLauncher
{
	
	public static void main (String[] arg)
	{
		Game wishingWellGame;
		
		wishingWellGame = new WishingWellGame();
		
		new LwjglApplication(wishingWellGame, "The Wishing Well v0.0.1", 800, 600);
	}
	
}
