package fr.fbb.divisr.screens.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
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

	private SpriteBatch batch;
	private Viewport viewport;

	private List<Column> columns;
	private long lastSpawn;
	private boolean running = true;
	private Texture lifeOn;
	private Texture lifeOff;
	private Game currentGame;
	private BitmapFont numbersFont;
	private BitmapFont scoreFont;
	private ShapeRenderer sr;

	public GameScreen(final Divisr game, int cols, Game.Difficulty diff)
	{
		this.game = game;

		viewport = new ExtendViewport(1080, 1920, new OrthographicCamera());
		viewport.apply(true);
		batch = new SpriteBatch();

		currentGame = new Game(cols, diff);
		sr = new ShapeRenderer();

		// Fonts
		numbersFont = game.assetManager.get("fonts/numbers.ttf", BitmapFont.class);
		scoreFont = game.assetManager.get("fonts/score.ttf", BitmapFont.class);

		// Textures
		lifeOn = new Texture(Gdx.files.internal("life-on.png"));
		lifeOff = new Texture(Gdx.files.internal("life-off.png"));

		// Columns
		columns = new ArrayList<Column>();
		for (int i = 0; i < cols; ++i)
		{
			Column col = new Column(200, currentGame);
			col.position.x = i * viewport.getWorldWidth() / cols;
			col.position.y = 315;
			col.position.width = viewport.getWorldWidth() / cols;
			col.position.height = viewport.getWorldHeight() - col.position.y;
			columns.add(col);
		}

		spawnNumber();
	}

	@Override
	public void show()
	{
	}

	@Override
	public void draw()
	{
		Gdx.gl.glClearColor(0.10f, 0.14f, 0.49f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		viewport.getCamera().update();

		sr.setProjectionMatrix(viewport.getCamera().combined);
		sr.begin(ShapeRenderer.ShapeType.Filled);

		// Background
		sr.setColor(new Color(0x283593FF));
		sr.rect(0, 0, viewport.getWorldWidth(), 300);
		sr.setColor(new Color(0x181F58FF));
		sr.rect(0, 300, viewport.getWorldWidth(), 10);

		// Lanes
		for (Column col : columns)
		{
			sr.setColor(new Color(0x2B3997FF));
			sr.rect(col.position.x + 10, col.position.y, col.position.width - 20, 200);
			sr.setColor(new Color(0x303F9FFF));
			sr.rect(col.position.x + 10, col.position.y + 200, col.position.width - 20, col.position.height - 200);
		}

		sr.end();

		batch.setProjectionMatrix(viewport.getCamera().combined);
		batch.begin();

		// Columns
		for (Column column : columns)
		{
			column.draw(batch);
		}

		// Incoming values
		int i = 0;
		for (Integer value : currentGame.incomingValues)
		{
			if (i == 0)
			{
				numbersFont.draw(batch, Integer.toString(value), viewport.getWorldWidth() / 2 - 20, 180);
			}
			else
			{
				numbersFont.draw(batch, Integer.toString(value), (currentGame.incomingValues.size() - i) * 120 - 60, 180);
			}
			++i;
		}

		// Lives
		int lifeSpacing = (int)(viewport.getWorldWidth() * 0.015);
		int center = (int)(viewport.getWorldWidth() * 0.815);
		int size = currentGame.livesMax * lifeOn.getWidth() + (currentGame.livesMax - 1) * lifeSpacing;
		int leftBound = center - size / 2;
		for (int life = 0; life < currentGame.livesMax; ++life)
		{
			boolean on = life < currentGame.lives;
			batch.draw(on ? lifeOn : lifeOff, leftBound, 150);
			leftBound += lifeOn.getWidth() + lifeSpacing;
		}

		// Score
		scoreFont.draw(batch, "score", viewport.getWorldWidth() - 350, 120);
		scoreFont.draw(batch, Integer.toString(currentGame.score), viewport.getWorldWidth() - 100, 120);

		batch.end();
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
			viewport.getCamera().unproject(touchPos);

			// Pause button
			if (touchPos.x < 200 && touchPos.y < 200)
			{
				pauseGame();
				return;
			}

			// New bullet
			int value = currentGame.popValue();
			int index = (int)(touchPos.x / (viewport.getWorldWidth() / columns.size()));
			columns.get(index).addBullet(new Bullet(value, numbersFont));
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
		columns.get(index).addNumber(new Number(currentGame.fallingNumber(), numbersFont, new Color(0f, 1f, 0f, 1f)));

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
