package fr.fbb.divisr.screens.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import fr.fbb.divisr.Divisr;
import fr.fbb.divisr.GameScene;
import fr.fbb.divisr.screens.Screen;

public class GameScreen implements Screen
{
	private final Divisr game;
	private OrthographicCamera camera;
	private boolean running = true;
	private GameScene scene;

	public GameScreen(final Divisr game)
	{
		this.game = game;

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 360, 640);

		scene = new GameScene(game, 5, GameScene.Difficulty.Medium);
	}

	@Override
	public void draw()
	{
		Gdx.gl.glClearColor(0.1f, 0.2f, 0.5f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		game.batch.setProjectionMatrix(camera.combined);

		game.batch.begin();
		scene.draw(game.batch);
		game.batch.end();
	}

	@Override
	public void update(float delta)
	{
		if (!running)
			return;

		// Touch and mouse input
		if (Gdx.input.isTouched())
		{
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);

			if (touchPos.x < 50 && touchPos.y < 50)
			{
				pauseGame();
				return;
			}
		}

		scene.update(delta);
	}

	private void pauseGame()
	{
		if (running)
		{
			game.addScreen(new PauseScreen(game));
		}
	}

	private void resumeGame()
	{
		if (!running)
		{
			game.popScreen();
		}
	}

	@Override
	public void dispose()
	{ }

	@Override
	public void show()
	{ }

	@Override
	public void resize(int width, int height)
	{ }

	@Override
	public void pause()
	{
		pauseGame();
	}

	@Override
	public void resume()
	{
		resumeGame();
	}

	@Override
	public void hide()
	{ }
}
