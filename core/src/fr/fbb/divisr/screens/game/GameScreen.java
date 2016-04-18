package fr.fbb.divisr.screens.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.TimeUtils;
import fr.fbb.divisr.Divisr;
import fr.fbb.divisr.objects.Bullet;
import fr.fbb.divisr.objects.Column;
import fr.fbb.divisr.objects.Game;
import fr.fbb.divisr.objects.Number;
import fr.fbb.divisr.screens.MenuScreen;

import java.util.ArrayList;
import java.util.List;

public class GameScreen extends MenuScreen
{
	private long lastSpawn;
	private boolean running = true;
	private Texture lifeOn;
	private Texture lifeOff;
	private Game currentGame;
	private BitmapFont numbersFont;
	private BitmapFont scoreFont;
	private ShapeRenderer sr;
	private SpriteBatch batch;

	public GameScreen(final Divisr game, Game currentGame)
	{
		super(game);

		batch = new SpriteBatch();
		this.currentGame = currentGame;
	}

	@Override
	public void buildStage()
	{
		sr = new ShapeRenderer();

		// Fonts
		numbersFont = game.assetManager.get("fonts/numbers.ttf", BitmapFont.class);
		scoreFont = game.assetManager.get("fonts/score.ttf", BitmapFont.class);

		// Textures
		lifeOn = new Texture(Gdx.files.internal("life-on.png"));
		lifeOff = new Texture(Gdx.files.internal("life-off.png"));

		// Game
		ArrayList<Column> columns = new ArrayList<Column>();
		final int columnNum = currentGame.columnNum;
		for (int i = 0; i < columnNum; ++i)
		{
			Column col = new Column(200, currentGame);
			col.position.x = i * getViewport().getWorldWidth() / columnNum;
			col.position.y = 315;
			col.position.width = getViewport().getWorldWidth() / columnNum;
			col.position.height = getViewport().getWorldHeight() - col.position.y;
			columns.add(col);
		}
		currentGame.columns = columns;

		spawnNumber();
	}

	@Override
	public void show()
	{
		super.show();
		getViewport().apply(true);

		Gdx.gl.glClearColor(0.10f, 0.14f, 0.49f, 1f);
	}

	@Override
	public void draw()
	{
		super.draw();

		sr.setProjectionMatrix(getViewport().getCamera().combined);
		sr.begin(ShapeRenderer.ShapeType.Filled);

		// Background
		sr.setColor(new Color(0x283593FF));
		sr.rect(0, 0, getViewport().getWorldWidth(), 300);
		sr.setColor(new Color(0x181F58FF));
		sr.rect(0, 300, getViewport().getWorldWidth(), 10);

		// Lanes
		for (Column col : currentGame.columns)
		{
			sr.setColor(new Color(0x2B3997FF));
			sr.rect(col.position.x + 10, col.position.y, col.position.width - 20, 200);
			sr.setColor(new Color(0x303F9FFF));
			sr.rect(col.position.x + 10, col.position.y + 200, col.position.width - 20, col.position.height - 200);
		}

		sr.end();

		batch.setProjectionMatrix(getViewport().getCamera().combined);
		batch.begin();

		// Columns
		for (Column column : currentGame.columns)
		{
			column.draw(batch);
		}

		// Incoming values
		int i = 0;
		for (Integer value : currentGame.incomingValues)
		{
			if (i == 0)
			{
				numbersFont.draw(batch, Integer.toString(value), getViewport().getWorldWidth() / 2 - 20, 180);
			}
			else
			{
				numbersFont.draw(batch, Integer.toString(value), (currentGame.incomingValues.size() - i) * 120 - 60, 180);
			}
			++i;
		}

		// Lives
		int lifeSpacing = (int)(getViewport().getWorldWidth() * 0.015);
		int center = (int)(getViewport().getWorldWidth() * 0.815);
		int size = currentGame.livesMax * lifeOn.getWidth() + (currentGame.livesMax - 1) * lifeSpacing;
		int leftBound = center - size / 2;
		for (int life = 0; life < currentGame.livesMax; ++life)
		{
			boolean on = life < currentGame.lives;
			batch.draw(on ? lifeOn : lifeOff, leftBound, 150);
			leftBound += lifeOn.getWidth() + lifeSpacing;
		}

		// Score
		scoreFont.draw(batch, "score", getViewport().getWorldWidth() - 350, 120);
		scoreFont.draw(batch, Integer.toString(currentGame.score), getViewport().getWorldWidth() - 100, 120);

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
			getViewport().getCamera().unproject(touchPos);

			// Pause button
			if (touchPos.x < 200 && touchPos.y < 200)
			{
				pauseGame();
				return;
			}

			// New bullet
			int value = currentGame.popValue();
			int index = (int)(touchPos.x / (getViewport().getWorldWidth() / currentGame.columns.size()));
			currentGame.columns.get(index).addBullet(new Bullet(value, numbersFont));
		}

		// Update columnNum
		for (Column column : currentGame.columns)
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
		int index = MathUtils.random(0, currentGame.columns.size() - 1);
		currentGame.columns.get(index).addNumber(new Number(currentGame.fallingNumber(), numbersFont, new Color(0f, 1f, 0f, 1f)));

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
		getViewport().update(width, height);
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
