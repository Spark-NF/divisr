package fr.fbb.divisr.screens.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import fr.fbb.divisr.Divisr;
import fr.fbb.divisr.objects.Bullet;
import fr.fbb.divisr.objects.Column;
import fr.fbb.divisr.objects.Game;
import fr.fbb.divisr.objects.Number;
import fr.fbb.divisr.screens.Screen;

import java.util.ArrayList;
import java.util.List;

public class GameScreen implements Screen
{
	private final Divisr game;
	private Viewport viewport;
	private OrthographicCamera camera;

	private List<Column> columns;
	private long lastSpawn;
	private boolean running = true;
	private Game currentGame;

	public GameScreen(final Divisr game, int cols, Game.Difficulty diff)
	{
		this.game = game;
		currentGame = new Game(cols, diff);

		camera = new OrthographicCamera();
		viewport = new StretchViewport(1080, 1920, camera);
		viewport.apply();
		camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);

		// Columns
		columns = new ArrayList<Column>();
		for (int i = 0; i < cols; ++i)
		{
			Column col = new Column(200, currentGame);
			col.position.x = i * viewport.getWorldWidth() / cols;
			col.position.y = 200;
			col.position.width = viewport.getWorldWidth() / cols;
			col.position.height = viewport.getWorldHeight() - 200;
			columns.add(col);
		}

		spawnNumber();
	}

	@Override
	public void draw()
	{
		Gdx.gl.glClearColor(0.1f, 0.2f, 0.5f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		game.batch.setProjectionMatrix(camera.combined);

		game.batch.begin();

		// Columns
		for (Column column : columns)
		{
			column.draw(game.batch);
		}

		// Incoming values
		int i = 0;
		for (Integer value : currentGame.incomingValues)
		{
			game.fontNumbers.draw(game.batch, Integer.toString(value), (currentGame.incomingValues.size() - 1 - i) * 60 + 20, 100);
			++i;
		}

		// Lives
		for (int life = 0; life < currentGame.livesMax; ++life)
		{
			if (life < currentGame.lives)
			{
				game.fontScore.draw(game.batch, "<3", viewport.getWorldWidth() - life * 80 - 80, 80);
			}
			else
			{
				game.fontScore.draw(game.batch, "--", viewport.getWorldWidth() - life * 80 - 80, 80);
			}
		}

		game.fontScore.draw(game.batch, Integer.toString(currentGame.score), viewport.getWorldWidth() - 50, viewport.getWorldHeight() - 10);

		game.batch.end();
	}

	@Override
	public void update(float delta)
	{
		if (!running)
			return;

		// Touch and mouse input
		if (Gdx.input.justTouched())
		{
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);

			// Pause button
			if (touchPos.x < 200 && touchPos.y < 200)
			{
				pauseGame();
				return;
			}

			// New bullet
			int value = currentGame.popValue();
			int index = (int)(touchPos.x / (viewport.getWorldWidth() / columns.size()));
			columns.get(index).addBullet(new Bullet(value, game.fontNumbers));
		}

		// Update columns
		for (Column column : columns)
		{
			column.update(delta);
		}

		// Game over on no life
		if (currentGame.lives == 0)
		{
			running = false;
			game.addScreen(new GameOverScreen(game, currentGame));
			return;
		}

		// Spawn new numbers from time to time
		if (TimeUtils.nanoTime() - lastSpawn > 1000000000)
		{
			spawnNumber();
		}
	}

	private void spawnNumber()
	{
		int index = MathUtils.random(0, columns.size() - 1);
		columns.get(index).addNumber(new Number(MathUtils.random(1, 9), game.fontNumbers, new Color(0f, 1f, 0f, 1f)));

		lastSpawn = TimeUtils.nanoTime();
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
	{
		viewport.update(width, height);
	}

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
