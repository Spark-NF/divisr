package fr.fbb.divisr.screens.menu;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import fr.fbb.divisr.Divisr;
import fr.fbb.divisr.objects.Game;
import fr.fbb.divisr.screens.StageScreen;
import fr.fbb.divisr.screens.game.GameScreen;

public class LevelMenuScreen extends StageScreen
{
	public LevelMenuScreen(Divisr game)
	{
		super(game);
	}

	@Override
	public void buildStage()
	{
		// Skin
		Skin skin = Divisr.assetManager.get("skin/uiskin.json", Skin.class);
		skin.get(TextButton.TextButtonStyle.class).font = Divisr.assetManager.get("fonts/buttons.ttf", BitmapFont.class);

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
		btnEasy.addListener(new ClickListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				divisr.setScreen(new GameScreen(divisr, new Game(3, Game.Difficulty.Easy)));
				return false;
			}
		});
		btnMedium.addListener(new ClickListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				divisr.setScreen(new GameScreen(divisr, new Game(4, Game.Difficulty.Medium)));
				return false;
			}
		});
		btnHard.addListener(new ClickListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				divisr.setScreen(new GameScreen(divisr, new Game(5, Game.Difficulty.Hard)));
				return false;
			}
		});
		btnBack.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				divisr.popScreen();
			}
		});
	}
}