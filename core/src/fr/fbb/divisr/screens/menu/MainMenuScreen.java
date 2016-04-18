package fr.fbb.divisr.screens.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import fr.fbb.divisr.Divisr;
import fr.fbb.divisr.screens.MenuScreen;

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
		btnPlay.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.addScreen(new LevelMenuScreen(game));
			}
		});
		btnExit.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.exit();
			}
		});
	}
}