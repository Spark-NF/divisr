package fr.fbb.divisr.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import fr.fbb.divisr.Divisr;
import fr.fbb.divisr.objects.Game;
import fr.fbb.divisr.screens.game.GameScreen;

public class MainMenuScreen implements Screen
{
	private final Divisr game;

	public MainMenuScreen(final Divisr game)
	{
		this.game = game;
	}

	@Override
	public void draw()
	{
		Gdx.gl.glClearColor(0.3f, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		game.batch.begin();
		game.fontMenuTitle.draw(game.batch, "Welcome to Divisr", 100, 1000);
		game.fontMenuText.draw(game.batch, "Tap anywhere to begin", 100, 800);
		game.batch.end();
	}

	@Override
	public void update(float delta)
	{
		if (Gdx.input.isTouched())
		{
			game.setScreen(new GameScreen(game, 4, Game.Difficulty.Medium));
			dispose();
		}
	}

	@Override
	public void show()
	{ }

	@Override
	public void resize(int width, int height)
	{ }

	@Override
	public void pause()
	{ }

	@Override
	public void resume()
	{ }

	@Override
	public void hide()
	{ }

	@Override
	public void dispose()
	{ }
}