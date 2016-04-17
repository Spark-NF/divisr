package fr.fbb.divisr;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader.FreeTypeFontLoaderParameter;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import fr.fbb.divisr.screens.MainMenuScreen;

public class Divisr extends MultiScreenGame
{
	public AssetManager assetManager;

	public void create()
	{
		// Load assets
		assetManager = new AssetManager();
		loadAssets(assetManager);
		assetManager.finishLoading();

		this.setScreen(new MainMenuScreen(this));
	}

	private void loadAssets(AssetManager manager)
	{
		// Load UI skin
		manager.load("skin/uiskin.atlas", TextureAtlas.class);
		manager.load("skin/uiskin.json", Skin.class, new SkinLoader.SkinParameter("skin/uiskin.atlas"));

		// Init free-type font loader
		FileHandleResolver resolver = new InternalFileHandleResolver();
		manager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
		manager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));

		FreeTypeFontLoaderParameter numbersParams = new FreeTypeFontLoaderParameter();
		numbersParams.fontFileName = "fonts/cooper-black.ttf";
		numbersParams.fontParameters.size = 100;
		manager.load("fonts/numbers.ttf", BitmapFont.class, numbersParams);

		FreeTypeFontLoaderParameter scoreParams = new FreeTypeFontLoaderParameter();
		scoreParams.fontFileName = "fonts/cooper-black.ttf";
		scoreParams.fontParameters.size = 60;
		manager.load("fonts/score.ttf", BitmapFont.class, scoreParams);

		FreeTypeFontLoaderParameter titleParams = new FreeTypeFontLoaderParameter();
		titleParams.fontFileName = "fonts/cooper-black.ttf";
		titleParams.fontParameters.size = 100;
		manager.load("fonts/title.ttf", BitmapFont.class, titleParams);

		FreeTypeFontLoaderParameter buttonsParams = new FreeTypeFontLoaderParameter();
		buttonsParams.fontFileName = "fonts/cooper-black.ttf";
		buttonsParams.fontParameters.size = 60;
		manager.load("fonts/buttons.ttf", BitmapFont.class, buttonsParams);
	}

	public void dispose()
	{
		assetManager.dispose();
	}
}
