package fr.fbb.divisr;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fr.fbb.divisr.screens.MainMenuScreen;

public class Divisr extends MultiScreenGame
{
	public SpriteBatch batch;
	public BitmapFont font;

	public void create()
	{
		batch = new SpriteBatch();

		font = new BitmapFont();

		this.setScreen(new MainMenuScreen(this));
	}

	public void render()
	{
		super.render();
	}

	public void dispose()
	{
		batch.dispose();
		font.dispose();
	}
}