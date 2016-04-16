package fr.fbb.divisr.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import fr.fbb.divisr.Divisr;

public class HtmlLauncher extends GwtApplication
{
	@Override
	public GwtApplicationConfiguration getConfig()
	{
		return new GwtApplicationConfiguration(360, 640);
	}

	@Override
	public ApplicationListener createApplicationListener()
	{
		return new Divisr();
	}
}