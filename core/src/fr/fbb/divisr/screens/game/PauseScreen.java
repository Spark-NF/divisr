package fr.fbb.divisr.screens.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import fr.fbb.divisr.Divisr;
import fr.fbb.divisr.screens.Screen;

public class PauseScreen implements Screen
{
	private final Divisr game;
	private OrthographicCamera camera;

	public PauseScreen(final Divisr game)
	{
		this.game = game;

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 360, 640);
	}

	@Override
	public void draw()
	{

	}

	@Override
	public void update(float delta)
	{
		// Resume by touch
		if (Gdx.input.isTouched())
		{
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);

			if (touchPos.x > 50)
				game.popScreen();
		}
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

	}

	@Override
	public void resume()
	{

	}

	@Override
	public void hide()
	{

	}

	@Override
	public void dispose()
	{

	}
}
