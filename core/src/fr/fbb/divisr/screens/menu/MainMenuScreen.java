package fr.fbb.divisr.screens.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import fr.fbb.divisr.Divisr;
import fr.fbb.divisr.screens.StageScreen;

public class MainMenuScreen extends StageScreen
{
	public MainMenuScreen(Divisr game)
	{
		super(game);
	}

	@Override
	public void buildStage()
	{
		// Skin
		Skin skin = divisr.assetManager.get("skin/uiskin.json", Skin.class);
		skin.get(TextButton.TextButtonStyle.class).font = divisr.assetManager.get("fonts/buttons.ttf", BitmapFont.class);

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
				divisr.addScreen(new LevelMenuScreen(divisr));
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