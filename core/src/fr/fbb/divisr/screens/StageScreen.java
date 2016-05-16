package fr.fbb.divisr.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import fr.fbb.divisr.Divisr;

public abstract class StageScreen extends Stage implements Screen
{
    protected Divisr divisr;

    public StageScreen(final Divisr divisr)
    {
        super(new ExtendViewport(1080, 1920, new OrthographicCamera()));

        this.divisr = divisr;
    }

    public abstract void buildStage();

    @Override
    public void draw()
    {
        // FIXME: clear hides lane background.
        /*
        if (!isOverlay())
        {
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        }
        */

        super.draw();
    }

    @Override
    public void update(float delta)
    {
        super.act(delta);
    }

    @Override
    public void show()
    {
        buildStage();

        Gdx.gl.glClearColor(0.3f, 0, 0.2f, 1);
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void resize(int width, int height)
    {
        getViewport().update(width, height, true);
    }

    @Override
    public void hide()
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
	public boolean isOverlay()
	{
		return false;
	}
}
