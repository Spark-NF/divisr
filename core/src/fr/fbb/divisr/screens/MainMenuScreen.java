package fr.fbb.divisr.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import fr.fbb.divisr.Divisr;

public class MainMenuScreen extends MenuScreen
{
	public MainMenuScreen(Divisr game)
	{
		super(game);
	}

	@Override
	public void buildStage()
	{
		// Skin
		Skin skin = game.assetManager.get("skin/uiskin.json", Skin.class);
		skin.get(TextButton.TextButtonStyle.class).font = game.assetManager.get("fonts/buttons.ttf", BitmapFont.class);

		// Buttons
		TextButton btnPlay = new TextButton("Play", skin);
		btnPlay.setSize(600, 200);
		btnPlay.setPosition(getWidth() / 2, getHeight() / 2 + 120f, Align.center);
		addActor(btnPlay);

		TextButton btnExit = new TextButton("Exit", skin);
		btnExit.setSize(600, 200);
		btnExit.setPosition(getWidth() / 2, getHeight() / 2 - 120f, Align.center);
		addActor(btnExit);

		// Listeners
		btnPlay.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				game.setScreen(new LevelMenuScreen(game));
				return false;
			}
		});
		btnExit.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				Gdx.app.exit();
				return false;
			}
		});
	}
}