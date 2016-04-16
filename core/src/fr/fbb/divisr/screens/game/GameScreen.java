package fr.fbb.divisr.screens.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import fr.fbb.divisr.Divisr;
import fr.fbb.divisr.screens.Screen;

import java.util.Iterator;

public class GameScreen implements Screen
{
	private final Divisr game;
	private OrthographicCamera camera;
	private Texture bucketImage;
	private Texture dropImage;
	private Rectangle bucket;
	private Array<Rectangle> numbers;
	private long lastDropTime;
	private int lines = 5;
	private boolean running = true;

	public GameScreen(final Divisr game)
	{
		this.game = game;

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 360, 640);

		bucketImage = new Texture("badlogic.jpg");
		dropImage = new Texture("badlogic.jpg");

		bucket = new Rectangle();
		bucket.x = 360 / 2 - 64 / 2;
		bucket.y = 20;
		bucket.width = 64;
		bucket.height = 64;

		numbers = new Array<Rectangle>();
		spawnNumber();
	}

	@Override
	public void draw()
	{
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		game.batch.setProjectionMatrix(camera.combined);
		game.batch.begin();

		game.batch.draw(bucketImage, bucket.x, bucket.y);

		// Columns
		for (int i = 0; i < lines; ++i)
		{
			//game.batch.draw(dropImage, raindrop.x, raindrop.y);
		}

		// Numbers
		for (Rectangle number: numbers)
		{
			game.batch.draw(dropImage, number.x, number.y);
		}

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
				pause();
				return;
			}

			bucket.x = touchPos.x - 64 / 2;
		}

		// Keyboard input
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
			bucket.x -= 200 * delta;
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
			bucket.x += 200 * delta;

		// Out of bounds check
		if (bucket.x < 0)
			bucket.x = 0;
		if (bucket.x > 360 - 64)
			bucket.x = 360 - 64;

		// Update numbers
		Iterator<Rectangle> it = numbers.iterator();
		while (it.hasNext())
		{
			Rectangle number = it.next();
			number.y -= 200 * delta;

			// Number fell off the screen
			if (number.y + 64 < 0)
			{
				it.remove();
			}
		}

		// Spawn new numbers from time to time
		if (TimeUtils.nanoTime() - lastDropTime > 1000000000)
			spawnNumber();
	}

	private void spawnNumber()
	{
		Rectangle number = new Rectangle();
		number.x = MathUtils.random(0, 360 - 64);
		number.y = 640;
		number.width = 64;
		number.height = 64;
		numbers.add(number);

		lastDropTime = TimeUtils.nanoTime();
	}

	@Override
	public void dispose()
	{
		dropImage.dispose();
		bucketImage.dispose();
	}

	@Override
	public void show()
	{

	}

	@Override
	public void resize(int width, int height)
	{

	}

	@Override
	public void pause()
	{
		if (running)
		{
			game.addScreen(new PauseScreen(game));
		}
	}

	@Override
	public void resume()
	{
		if (!running)
		{
			game.popScreen();
		}
	}

	@Override
	public void hide()
	{

	}
}
