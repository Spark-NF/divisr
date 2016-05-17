package fr.fbb.divisr.screens.menu;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import fr.fbb.divisr.Divisr;
import fr.fbb.divisr.screens.StageScreen;

public class LoadingScreen extends StageScreen
{
	public LoadingScreen(Divisr game)
	{
		super(game);

		loadAssets(Divisr.assetManager);
	}

	@Override
	public void buildStage()
	{
	}

	@Override
	public void update(float delta)
	{
		if (Divisr.assetManager.update())
		{
			divisr.setScreen(new MainMenuScreen(divisr));
		}
	}

	@Override
	public void draw()
	{
		super.draw();
	}

	private void loadAssets(AssetManager manager)
	{
		// Load UI skin
		manager.load("skin/uiskin.atlas", TextureAtlas.class);
		manager.load("skin/uiskin.json", Skin.class, new SkinLoader.SkinParameter("skin/uiskin.atlas"));

		// Textures
		manager.load("life-on.png", Texture.class);
		manager.load("life-off.png", Texture.class);
		manager.load("square-red.png", Texture.class);
		manager.load("circle-blue.png", Texture.class);

		// Init free-type font loader
		FileHandleResolver resolver = new InternalFileHandleResolver();
		manager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
		manager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));

		FreetypeFontLoader.FreeTypeFontLoaderParameter numbersParams = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
		numbersParams.fontFileName = "fonts/cooper-black.ttf";
		numbersParams.fontParameters.size = 100;
		manager.load("fonts/numbers.ttf", BitmapFont.class, numbersParams);

		FreetypeFontLoader.FreeTypeFontLoaderParameter scoreParams = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
		scoreParams.fontFileName = "fonts/cooper-black.ttf";
		scoreParams.fontParameters.size = 60;
		manager.load("fonts/score.ttf", BitmapFont.class, scoreParams);

		FreetypeFontLoader.FreeTypeFontLoaderParameter titleParams = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
		titleParams.fontFileName = "fonts/cooper-black.ttf";
		titleParams.fontParameters.size = 100;
		manager.load("fonts/title.ttf", BitmapFont.class, titleParams);

		FreetypeFontLoader.FreeTypeFontLoaderParameter buttonsParams = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
		buttonsParams.fontFileName = "fonts/cooper-black.ttf";
		buttonsParams.fontParameters.size = 60;
		manager.load("fonts/buttons.ttf", BitmapFont.class, buttonsParams);

		manager.finishLoading();
	}
}