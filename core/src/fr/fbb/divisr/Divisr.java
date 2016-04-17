package fr.fbb.divisr;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import fr.fbb.divisr.screens.MainMenuScreen;

public class Divisr extends MultiScreenGame
{
	public SpriteBatch batch;

	// TODO move this to a proper location
	public BitmapFont fontNumbers;
	public BitmapFont fontScore;
	public BitmapFont fontMenuTitle;
	public BitmapFont fontMenuText;

	public void create()
	{
		batch = new SpriteBatch();

		// Load fonts
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/cooper-black.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 100;
		fontNumbers = generator.generateFont(parameter);
		parameter.size = 60;
		fontScore = generator.generateFont(parameter);
		parameter.size = 100;
		fontMenuTitle = generator.generateFont(parameter);
		parameter.size = 60;
		fontMenuText = generator.generateFont(parameter);
		generator.dispose();

		this.setScreen(new MainMenuScreen(this));
	}

	public void render()
	{
		super.render();
	}

	public void dispose()
	{
		batch.dispose();

		fontNumbers.dispose();
		fontScore.dispose();
		fontMenuTitle.dispose();
		fontMenuText.dispose();
	}
}