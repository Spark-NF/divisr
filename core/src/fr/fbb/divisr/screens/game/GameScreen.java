package fr.fbb.divisr.screens.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.TimeUtils;
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
		camera.setToOrtho(false, 360, 640);

		// Columns
		columns = new ArrayList<Column>();
		for (int i = 0; i < cols; ++i)
		{
			Column col = new Column(new Color(i / cols, 0f, 0f, 1f), 200, currentGame);
			col.position.x = i * 360 / cols;
			col.position.y = 0;
			col.position.width = 360 / cols;
			col.position.height = 640 - 50;
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
			game.font.draw(game.batch, Integer.toString(value), (currentGame.incomingValues.size() - 1 - i) * 30, 20);
			++i;
		}

		// Lives
		for (int life = 0; life < currentGame.livesMax; ++life)
		{
			if (life < currentGame.lives)
			{
				game.font.draw(game.batch, "<3", 360 - life * 30 - 20, 20);
			}
			else
			{
				game.font.draw(game.batch, "--", 360 - life * 30 - 20, 20);
			}
		}

		game.font.draw(game.batch, Integer.toString(currentGame.score), 360 - 20, 640);

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
			if (touchPos.x < 50 && touchPos.y < 50)
			{
				pauseGame();
				return;
			}

			// New bullet
			int value = currentGame.popValue();
			int index = (int)touchPos.x / (360 / columns.size());
			columns.get(index).addBullet(new Bullet(value, game.font));
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
		columns.get(index).addNumber(new Number(MathUtils.random(1, 9), game.font, new Color(0f, 1f, 0f, 1f)));

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
