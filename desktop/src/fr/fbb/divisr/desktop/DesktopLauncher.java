package fr.fbb.divisr.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import fr.fbb.divisr.Divisr;

public class DesktopLauncher
{
	public static void main (String[] arg)
	{
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Divisr";
		config.width = 360;
		config.height = 640;
		config.addIcon("desktop/icon.png", Files.FileType.Internal);
		new LwjglApplication(new Divisr(), config);
	}
}
