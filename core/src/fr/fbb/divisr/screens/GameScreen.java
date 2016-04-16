package fr.fbb.divisr.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import fr.fbb.divisr.Divisr;

import java.util.Iterator;

public class GameScreen implements Screen
{
	private final Divisr game;
	private OrthographicCamera camera;
	private Texture bucketImage;
	private Texture dropImage;
	private Rectangle bucket;
	private Array<Rectangle> raindrops;
	private long lastDropTime;

	public GameScreen(final Divisr gam)
	{
		this.game = gam;

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 360, 640);

		bucketImage = new Texture("badlogic.jpg");
		dropImage = new Texture("badlogic.jpg");

		bucket = new Rectangle();
		bucket.x = 360 / 2 - 64 / 2;
		bucket.y = 20;
		bucket.width = 64;
		bucket.height = 64;

		raindrops = new Array<Rectangle>();
		spawnRaindrop();
	}

	private void draw()
	{
		camera.update();
		game.batch.setProjectionMatrix(camera.combined);
		game.batch.begin();

		game.batch.draw(bucketImage, bucket.x, bucket.y);
		for(Rectangle raindrop: raindrops) {
			game.batch.draw(dropImage, raindrop.x, raindrop.y);
		}

		game.batch.end();
	}

	private void update(float delta)
	{
		if (Gdx.input.isTouched())
		{
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			bucket.x = touchPos.x - 64 / 2;
		}

		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) bucket.x -= 200 * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) bucket.x += 200 * Gdx.graphics.getDeltaTime();

		if(bucket.x < 0) bucket.x = 0;
		if(bucket.x > 360 - 64) bucket.x = 360 - 64;

		Iterator<Rectangle> iter = raindrops.iterator();
		while (iter.hasNext()) {
			Rectangle raindrop = iter.next();
			raindrop.y -= 200 * Gdx.graphics.getDeltaTime();
			if(raindrop.y + 64 < 0 || raindrop.overlaps(bucket)) iter.remove();
		}

		if(TimeUtils.nanoTime() - lastDropTime > 1000000000) spawnRaindrop();
	}

	private void spawnRaindrop()
	{
		Rectangle raindrop = new Rectangle();
		raindrop.x = MathUtils.random(0, 360 - 64);
		raindrop.y = 640;
		raindrop.width = 64;
		raindrop.height = 64;
		raindrops.add(raindrop);
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
	public void render(float delta)
	{
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		draw();
		update(delta);
	}

	@Override
	public void resize(int width, int height)
	{

	}

	@Override
	public void pause()
	{

	}

	@Override
	public void resume()
	{

	}

	@Override
	public void hide()
	{

	}
}
