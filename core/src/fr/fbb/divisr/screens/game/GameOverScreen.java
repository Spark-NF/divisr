package fr.fbb.divisr.screens.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import fr.fbb.divisr.Divisr;
import fr.fbb.divisr.objects.Game;
import fr.fbb.divisr.screens.menu.MainMenuScreen;
import fr.fbb.divisr.screens.MenuScreen;

public class GameOverScreen extends MenuScreen
{
	private Game lostGame;

	public GameOverScreen(final Divisr game, Game lostGame)
	{
		super(game);

		this.lostGame = lostGame;
	}

	@Override
	public void buildStage()
	{
		// Skin
		Skin skin = game.assetManager.get("skin/uiskin.json", Skin.class);
		skin.get(TextButton.TextButtonStyle.class).font = game.assetManager.get("fonts/buttons.ttf", BitmapFont.class);

		// Buttons
		TextButton btnResume = new TextButton("Try again", skin);
		btnResume.setSize(600, 200);
		btnResume.setPosition(getWidth() / 2, getHeight() / 2 + 240f, Align.center);
		addActor(btnResume);

		TextButton btnMainMenu = new TextButton("Main menu", skin);
		btnMainMenu.setSize(600, 200);
		btnMainMenu.setPosition(getWidth() / 2, getHeight() / 2, Align.center);
		addActor(btnMainMenu);

		TextButton btnExit = new TextButton("Exit", skin);
		btnExit.setSize(600, 200);
		btnExit.setPosition(getWidth() / 2, getHeight() / 2 - 240f, Align.center);
		addActor(btnExit);

		// Listeners
		btnResume.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new GameScreen(game, new Game(lostGame.columnNum, lostGame.difficulty)));
			}
		});
		btnMainMenu.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new MainMenuScreen(game));
			}
		});
		btnExit.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.exit();
			}
		});
	}

	@Override
	public void draw()
	{
		// Dark background
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		ShapeRenderer sr = new ShapeRenderer();
		sr.setProjectionMatrix(getViewport().getCamera().combined);
		sr.begin(ShapeRenderer.ShapeType.Filled);
		sr.setColor(new Color(0.5f, 0, 0, 0.5f));
		sr.rect(0, 0, getViewport().getWorldWidth(), getViewport().getWorldHeight());
		sr.end();
		Gdx.gl.glDisable(GL20.GL_BLEND);

		super.draw();
	}

	@Override
	public boolean isOverlay()
	{
		return true;
	}
}
