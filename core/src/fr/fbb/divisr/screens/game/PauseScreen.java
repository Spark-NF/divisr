package fr.fbb.divisr.screens.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import fr.fbb.divisr.Divisr;
import fr.fbb.divisr.screens.Screen;

public class PauseScreen implements Screen
{
	private final Divisr game;

	public PauseScreen(final Divisr game)
	{
		this.game = game;
	}

	@Override
	public void draw()
	{
		// Dark background
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		ShapeRenderer sr = new ShapeRenderer();
		sr.setProjectionMatrix(game.camera.combined);
		sr.begin(ShapeRenderer.ShapeType.Filled);
		sr.setColor(new Color(0, 0, 0, 0.5f));
		sr.rect(0, 0, game.viewport.getWorldWidth(), game.viewport.getWorldHeight());
		sr.end();
		Gdx.gl.glDisable(GL20.GL_BLEND);

		game.batch.begin();
		game.fontMenuTitle.draw(game.batch, "Gamed paused", 100, 1000);
		game.fontMenuText.draw(game.batch, "Tap anywhere to resume", 100, 800);
		game.batch.end();
	}

	@Override
	public void update(float delta)
	{
		// Resume by touch
		if (Gdx.input.justTouched())
		{
			game.popScreen();
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
