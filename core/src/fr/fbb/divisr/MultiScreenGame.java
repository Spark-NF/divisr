package fr.fbb.divisr;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import fr.fbb.divisr.screens.Screen;

import java.util.Stack;

public abstract class MultiScreenGame implements ApplicationListener
{
	private Stack<Screen> screens;

	public MultiScreenGame()
	{
		screens = new Stack<Screen>();
	}

	@Override
	public void dispose()
	{
		for (Screen screen : screens)
		{
			screen.hide();
			screen.dispose();
		}
		screens.clear();
	}

	@Override
	public void pause()
	{
		for (int i = 0; i < screens.size(); ++i)
		{
			screens.get(i).pause();
		}
	}

	@Override
	public void resume()
	{
		for (int i = 0; i < screens.size(); ++i)
		{
			screens.get(i).resume();
		}
	}

	@Override
	public void render()
	{
		for (Screen screen : screens)
		{
			screen.draw();
		}
		screens.peek().update(Gdx.graphics.getDeltaTime());
	}

	@Override
	public void resize(int width, int height)
	{
		for (Screen screen : screens)
		{
			screen.resize(width, height);
		}
	}

	public void setScreen(Screen s)
	{
		for (Screen screen : screens)
		{
			screen.hide();
			screen.dispose();
		}

		screens.clear();
		screens.add(s);

		s.show();
		s.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}

	public void addScreen(final Screen s)
	{
		screens.add(s);

		s.show();
		s.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}

	public void popScreen()
	{
		Screen s = screens.pop();

		s.hide();
	}
}