package fr.fbb.divisr.screens.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import fr.fbb.divisr.Divisr;
import fr.fbb.divisr.objects.Title;
import fr.fbb.divisr.screens.StageScreen;

public class LoadingScreen extends StageScreen
{
	private float mask;
	private float timePassed;
	private final float animationPeriod = 0.6f; // sec
	private ShapeRenderer sr;
	private SpriteBatch batch;
	private boolean clicked;
	private boolean loading;

	public LoadingScreen(Divisr game)
	{
		super(game);
		mask = 0;
		timePassed = 0;
		clicked = false;
	}

	@Override
	public void buildStage()
	{
		loadScreenAssets(Divisr.assetManager);

		batch = new SpriteBatch();
		sr = new ShapeRenderer();
		final Title title = new Title();
		final float posX = this.getWidth() / 2;
		final float posY = this.getHeight() / 2;
		title.setPosition(posX, posY);
		addActor(title);
	}

	@Override
	public void update(float delta)
	{
		timePassed += delta;

		// Sinus waves on mask for glow effect.
		mask = Math.min(1.0f, timePassed / animationPeriod)
				+ 0.1f * (float) Math.sin(0.6f * timePassed * Math.PI) - 0.1f;
		// Revert mask if changing screen.
		if (clicked)
		{
			mask = 1 - mask;
		}

		// Loading logic
		if (timePassed >= 1.0f)
		{
			if (!loading)
			{
				loadAssets(Divisr.assetManager);
			}
			if (Divisr.assetManager.update())
			{
				if (clicked && timePassed >= 1.0f)
				{
					divisr.setScreen(new MainMenuScreen(divisr));
				}
				else if (Gdx.input.justTouched())
				{
					timePassed = 0;
					clicked = true;
				}
			}
		}
	}

	@Override
	public void act(float delta)
	{
		// Not called?
		super.act(delta);
	}

	@Override
	public void show()
	{
		super.show();
		getViewport().apply(true);

		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 0);
	}

	@Override
	public void draw()
	{
		super.draw();
	}

	@Override
	public void drawBackground()
	{
		Gdx.gl.glEnable(Gdx.gl.GL_BLEND);
		Gdx.gl.glBlendFunc(Gdx.gl.GL_SRC_ALPHA, Gdx.gl.GL_ONE_MINUS_SRC_ALPHA);
		sr.setProjectionMatrix(getViewport().getCamera().combined);
		sr.begin(ShapeRenderer.ShapeType.Filled);

		final float width = getViewport().getWorldWidth();
		final float height = getViewport().getWorldHeight();
		final Color color = new Color(0.18f, 0.1f, 0.26f, mask);
		sr.setColor(color);
		sr.rect(0, 0, width, height);

		sr.end();
		Gdx.gl.glDisable(Gdx.gl.GL_BLEND);
	}

	private void loadScreenAssets(AssetManager manager)
	{
		// Textures
		manager.load("logo.png", Texture.class);
		manager.finishLoading();
	}

	private void loadAssets(AssetManager manager)
	{
		loading = true;

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

		// Fonts
		FreetypeFontLoader.FreeTypeFontLoaderParameter titleParams = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
		titleParams.fontFileName = "fonts/cooper-black.ttf";
		titleParams.fontParameters.size = 100;
		manager.load("fonts/title.ttf", BitmapFont.class, titleParams);

		FreetypeFontLoader.FreeTypeFontLoaderParameter numbersParams = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
		numbersParams.fontFileName = "fonts/cooper-black.ttf";
		numbersParams.fontParameters.size = 100;
		manager.load("fonts/numbers.ttf", BitmapFont.class, numbersParams);

		FreetypeFontLoader.FreeTypeFontLoaderParameter scoreParams = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
		scoreParams.fontFileName = "fonts/cooper-black.ttf";
		scoreParams.fontParameters.size = 60;
		manager.load("fonts/score.ttf", BitmapFont.class, scoreParams);

		FreetypeFontLoader.FreeTypeFontLoaderParameter buttonsParams = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
		buttonsParams.fontFileName = "fonts/cooper-black.ttf";
		buttonsParams.fontParameters.size = 60;
		manager.load("fonts/buttons.ttf", BitmapFont.class, buttonsParams);

		manager.finishLoading();
	}

	@Override
	public void dispose()
	{
	}

	@Override
	public void resize(int width, int height)
	{
		getViewport().update(width, height);
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
