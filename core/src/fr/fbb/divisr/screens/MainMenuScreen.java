package fr.fbb.divisr.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import fr.fbb.divisr.Divisr;
import fr.fbb.divisr.screens.game.GameScreen;

public class MainMenuScreen implements Screen
{
	private final Divisr game;
	private OrthographicCamera camera;

	public MainMenuScreen(final Divisr game)
	{
		this.game = game;

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 360, 640);
	}

	@Override
	public void draw()
	{
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		game.batch.setProjectionMatrix(camera.combined);

		game.batch.begin();
		game.font.draw(game.batch, "Welcome to Divisr", 100, 150);
		game.font.draw(game.batch, "Tap anywhere to begin", 100, 100);
		game.batch.end();
	}

	@Override
	public void update(float delta)
	{
		if (Gdx.input.isTouched())
		{
			game.setScreen(new GameScreen(game));
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