package fr.fbb.divisr;

import com.badlogic.gdx.assets.AssetManager;
import fr.fbb.divisr.screens.menu.LoadingScreen;

public class Divisr extends MultiScreenGame
{
	public static AssetManager assetManager;

	public void create()
	{
		assetManager = new AssetManager();
		this.setScreen(new LoadingScreen(this));
	}

	public void dispose()
	{
		assetManager.dispose();
	}
}
