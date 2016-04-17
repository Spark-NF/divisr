package fr.fbb.divisr.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import fr.fbb.divisr.Divisr;
import fr.fbb.divisr.objects.Game;
import fr.fbb.divisr.screens.game.GameScreen;

public class LevelMenuScreen extends MenuScreen
{
	public LevelMenuScreen(Divisr game)
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
		TextButton btnEasy = new TextButton("Easy", skin);
		btnEasy.setSize(600, 200);
		btnEasy.setPosition(getWidth() / 2, getHeight() / 2 + 360f, Align.center);
		addActor(btnEasy);

		TextButton btnMedium = new TextButton("Medium", skin);
		btnMedium.setSize(600, 200);
		btnMedium.setPosition(getWidth() / 2, getHeight() / 2 + 120f, Align.center);
		addActor(btnMedium);

		TextButton btnHard = new TextButton("Hard", skin);
		btnHard.setSize(600, 200);
		btnHard.setPosition(getWidth() / 2, getHeight() / 2 - 120f, Align.center);
		addActor(btnHard);

		TextButton btnBack = new TextButton("Back", skin);
		btnBack.setSize(600, 200);
		btnBack.setPosition(getWidth() / 2, getHeight() / 2 - 360f, Align.center);
		addActor(btnBack);

		// Listeners
		btnEasy.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				game.setScreen(new GameScreen(game, 3, Game.Difficulty.Easy));
				return false;
			}
		});
		btnMedium.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				game.setScreen(new GameScreen(game, 4, Game.Difficulty.Medium));
				return false;
			}
		});
		btnHard.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				game.setScreen(new GameScreen(game, 5, Game.Difficulty.Hard));
				return false;
			}
		});
		btnBack.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				game.setScreen(new MainMenuScreen(game));
				return false;
			}
		});
	}
}