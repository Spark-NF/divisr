package fr.fbb.divisr.screens.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
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

	private List<Column> columns;
	private long lastSpawn;
	private boolean running = true;
	private Texture lifeOn;
	private Texture lifeOff;
	private Game currentGame;

	public GameScreen(final Divisr game, int cols, Game.Difficulty diff)
	{
		this.game = game;
		currentGame = new Game(cols, diff);

		// Textures
		lifeOn = new Texture(Gdx.files.internal("life-on.png"));
		lifeOff = new Texture(Gdx.files.internal("life-off.png"));

		// Columns
		columns = new ArrayList<Column>();
		for (int i = 0; i < cols; ++i)
		{
			Column col = new Column(200, currentGame);
			col.position.x = i * game.viewport.getWorldWidth() / cols;
			col.position.y = 315;
			col.position.width = game.viewport.getWorldWidth() / cols;
			col.position.height = game.viewport.getWorldHeight() - col.position.y;
			columns.add(col);
		}

		spawnNumber();
	}

	@Override
	public void draw()
	{
		Gdx.gl.glClearColor(0.10f, 0.14f, 0.49f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		ShapeRenderer sr = new ShapeRenderer();
		sr.setProjectionMatrix(game.camera.combined);
		sr.begin(ShapeRenderer.ShapeType.Filled);

		// Background
		sr.setColor(new Color(0x283593FF));
		sr.rect(0, 0, game.viewport.getWorldWidth(), 300);
		sr.setColor(new Color(0x181F58FF));
		sr.rect(0, 300, game.viewport.getWorldWidth(), 10);

		// Lanes
		for (Column col : columns)
		{
			sr.setColor(new Color(0x2B3997FF));
			sr.rect(col.position.x + 10, col.position.y, col.position.width - 20, 200);
			sr.setColor(new Color(0x303F9FFF));
			sr.rect(col.position.x + 10, col.position.y + 200, col.position.width - 20, col.position.height - 200);
		}

		sr.end();
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
			if (i == 0)
			{
				game.fontNumbers.draw(game.batch, Integer.toString(value), game.viewport.getWorldWidth() / 2 - 20, 180);
			}
			else
			{
				game.fontNumbers.draw(game.batch, Integer.toString(value), (currentGame.incomingValues.size() - i) * 120 - 60, 180);
			}
			++i;
		}

		// Lives
		int lifeSpacing = (int)(game.viewport.getWorldWidth() * 0.015);
		int center = (int)(game.viewport.getWorldWidth() * 0.815);
		int size = currentGame.livesMax * lifeOn.getWidth() + (currentGame.livesMax - 1) * lifeSpacing;
		int leftBound = center - size / 2;
		for (int life = 0; life < currentGame.livesMax; ++life)
		{
			boolean on = life < currentGame.lives;
			game.batch.draw(on ? lifeOn : lifeOff, leftBound, 150);
			leftBound += lifeOn.getWidth() + lifeSpacing;
		}

		// Score
		game.fontScore.draw(game.batch, "score", game.viewport.getWorldWidth() - 350, 120);
		game.fontScore.draw(game.batch, Integer.toString(currentGame.score), game.viewport.getWorldWidth() - 100, 120);

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
			game.camera.unproject(touchPos);

			// Pause button
			if (touchPos.x < 200 && touchPos.y < 200)
			{
				pauseGame();
				return;
			}

			// New bullet
			int value = currentGame.popValue();
			int index = (int)(touchPos.x / (game.viewport.getWorldWidth() / columns.size()));
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
		columns.get(index).addNumber(new Number(currentGame.fallingNumber(), game.fontNumbers, new Color(0f, 1f, 0f, 1f)));

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
		game.viewport.update(width, height);
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
