package fr.fbb.divisr.screens.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.TimeUtils;
import fr.fbb.divisr.Divisr;
import fr.fbb.divisr.objects.Bullet;
import fr.fbb.divisr.objects.Column;
import fr.fbb.divisr.objects.Game;
import fr.fbb.divisr.objects.Number;
import fr.fbb.divisr.screens.StageScreen;

public class GameScreen extends StageScreen
{
	private long lastSpawn;
	private boolean running = true;
	private Game game;
	private BitmapFont numbersFont;
	private ShapeRenderer sr;

	public GameScreen(final Divisr divisr, Game game)
	{
		super(divisr);

		this.game = game;
		addActor(game);
	}

	@Override
	public void buildStage()
	{
		sr = new ShapeRenderer();

		// Game
		game.viewport = getViewport();

		// Assets
		numbersFont = Divisr.assetManager.get("fonts/numbers.ttf", BitmapFont.class);

		// Create columns
		final int columnNum = game.columnNum;
		final Column[] columns = new Column[columnNum];
		for (int i = 0; i < columnNum; i++)
		{
			Column col = new Column(200, getViewport());
			col.setPosition(i * getViewport().getWorldWidth() / columnNum, 315);
			col.setWidth(getViewport().getWorldWidth() / columnNum);
			col.setHeight(getViewport().getWorldHeight() - col.getY());
			columns[i] = col;
			game.addActor(col);
		}
		game.columns = columns;

		//spawnNumber();
	}

	@Override
	public void show()
	{
		super.show();
		getViewport().apply(true);

		Gdx.gl.glClearColor(0.10f, 0.14f, 0.49f, 1f);
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
			touchPos = getViewport().getCamera().unproject(touchPos);

			// Pause button
			if (touchPos.x < 200 && touchPos.y < 200)
			{
				pauseGame();
				return;
			}

			// New bullet
			int index = (int)(touchPos.x / (getViewport().getWorldWidth() / game.columnNum));
			int value = game.popValue();
			final Bullet bullet = new Bullet(value, numbersFont);
			game.spawnBullet(index, bullet);
		}

		act(delta);
	}

	@Override
	public void act(float delta)
	{
		// Game over on no life
		if (game.lives == 0)
		{
			running = false;
			divisr.addScreen(new GameOverScreen(divisr, game));
			return;
		}

		// Spawn new numbers from time to time
		if (TimeUtils.nanoTime() - lastSpawn > 1000000000)
		{
			final Color color = new Color(0f, 1f, 0f, 1f);
			final Number number = new Number(game.fallingNumber(), numbersFont, color);
			lastSpawn = TimeUtils.nanoTime();
			game.spawnNumber(number);
		}

		super.act(delta);
	}

	@Override
	public void drawBackground()
	{
		sr.setProjectionMatrix(getViewport().getCamera().combined);
		sr.begin(ShapeRenderer.ShapeType.Filled);

		// Background
		sr.setColor(new Color(0x283593FF));
		sr.rect(0, 0, getViewport().getWorldWidth(), 300);
		sr.setColor(new Color(0x181F58FF));
		sr.rect(0, 300, getViewport().getWorldWidth(), 10);

		// Lanes
		for (Column col : game.columns)
		{
			sr.setColor(new Color(0x2B3997FF));
			sr.rect(col.getX() + 10, col.getY(), col.getWidth() - 20, 200);
			sr.setColor(new Color(0x303F9FFF));
			sr.rect(col.getX() + 10, col.getY() + 200, col.getWidth() - 20, col.getHeight() - 200);
		}

		sr.end();
	}

    private void pauseGame()
	{
		if (running)
		{
			divisr.addScreen(new PauseScreen(divisr));
		}
	}

	private void resumeGame()
	{
		if (!running)
		{
			divisr.popScreen();
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
