package fr.fbb.divisr;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import fr.fbb.divisr.canvas.Canvas;
import fr.fbb.divisr.common.IPoint;
import fr.fbb.divisr.common.Point;
import fr.fbb.divisr.common.TouchInputEvent;
import fr.fbb.divisr.menu.MenuScene;
import fr.fbb.divisr.menu.MenuState;
import fr.fbb.divisr.menu.components.Button;
import fr.fbb.divisr.menu.components.IButton;
import fr.fbb.divisr.menu.components.Label;
import fr.fbb.divisr.screens.MainMenuScreen;
import fr.fbb.divisr.screens.game.GameScreen;

public class Divisr extends MultiScreenGame
{
    public SpriteBatch batch;
    public Viewport viewport;
    public OrthographicCamera camera;

    // TODO move this to a proper location
    public BitmapFont fontNumbers;
    public BitmapFont fontScore;
    public BitmapFont fontMenuTitle;
    public BitmapFont fontMenuText;

    // Game components.
    private boolean _initialized;
    private GameScreen _gameScreen;

    // Menu components.
    private MenuScene _menuScene;
    private Canvas _menuCanvas;
    private MenuState _homeState;
    private MenuState _gameEndState;
    private MenuState _pauseState;
    private MenuState _playingState;
    private MenuState _statsState;
    private MenuState _settingsState;

    @Override
    public void create()
    {
        batch = new SpriteBatch();

        // Load fonts
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/cooper-black.ttf"));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = 100;
        fontNumbers = generator.generateFont(parameter);
        parameter.size = 60;
        fontScore = generator.generateFont(parameter);
        parameter.size = 100;
        fontMenuTitle = generator.generateFont(parameter);
        parameter.size = 60;
        fontMenuText = generator.generateFont(parameter);
        generator.dispose();

        this.setScreen(new MainMenuScreen(this));

        camera = new OrthographicCamera();
        viewport = new StretchViewport(1080, 1920, camera);
        viewport.apply(true);

        //_batch = new SpriteBatch();
        //_img = new Texture("badlogic.jpg");

        // Menu states
        _homeState = new MenuState(null, 0.6f, true);
        _gameEndState = new MenuState(_homeState, 0.6f, true);
        _pauseState = new MenuState(_homeState, 0.6f, true);
        _playingState = new MenuState(_pauseState, 0, false);
        _statsState = new MenuState(_homeState, 0.8f, true);
        _settingsState = new MenuState(_homeState, 0.8f, true);

        // Objects
        _menuCanvas = new Canvas(100);
        IPoint viewSize = new Point(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        _menuCanvas.setView(viewSize);
        _menuScene = new MenuScene();
        //_gameScreen = new GameScreen(null, 4, Game.Difficulty.Easy);

        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.input.setInputProcessor(new InputAdapter()
        {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button)
            {
                IPoint screenPosition = new Point(screenX, screenY);
                return InputEvent(screenPosition, TouchInputEvent.Type.TouchDown, pointer);
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button)
            {
                IPoint screenPosition = new Point(screenX, screenY);
                return InputEvent(screenPosition, TouchInputEvent.Type.TouchUp, pointer);
            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer)
            {
                IPoint screenPosition = new Point(screenX, screenY);
                return InputEvent(screenPosition, TouchInputEvent.Type.TouchDrag, pointer);
            }

            @Override
            public boolean mouseMoved(int screenX, int screenY)
            {
                IPoint screenPosition = new Point(screenX, screenY);
                return InputEvent(screenPosition, TouchInputEvent.Type.MouseMove, 0);
            }

            private boolean InputEvent(IPoint screenPosition, TouchInputEvent.Type type, int pointer)
            {
                IPoint menuPosition = _menuCanvas.getIndexPos(screenPosition);
                // TODO
                //IPoint gamePosition = _gameCanvas.getIndexPos(screenPosition);
                return !_menuScene.inputEvent(new TouchInputEvent(type, menuPosition, pointer));
                //|| _player.inputEvent(new TouchInputEvent(type, gamePosition, pointer));
            }

            @Override
            public boolean keyDown(int keycode)
            {
                if (keycode == Input.Keys.BACK)
                    return _menuScene.onBackPressed();
                return false;
            }


        });

        _initialized = false;
    }

    private void init()
    {
        populateStates();
        _menuScene.init(_homeState);
        _initialized = true;
    }

    /**
     * Adds all UI components to the layers.
     */
    private void populateStates()
    {
        // Create button actions.
        Button.IAction noAction = new Button.IAction()
        {
            @Override
            public void OnActivated(IButton button)
            {
                // Do nothing.
            }
        };
        Button.IAction homeAction = new Button.IAction()
        {
            @Override
            public void OnActivated(IButton button)
            {
                _menuScene.switchState(_homeState, MenuScene.Blend.Double);
            }
        };
        Button.IAction statsAction = new Button.IAction()
        {
            @Override
            public void OnActivated(IButton button)
            {
                _menuScene.switchState(_statsState, MenuScene.Blend.Double);
            }
        };
        Button.IAction settingsAction = new Button.IAction()
        {
            @Override
            public void OnActivated(IButton button)
            {
                _menuScene.switchState(_settingsState, MenuScene.Blend.Double);
            }
        };
        Button.IAction playAction = new Button.IAction()
        {
            @Override
            public void OnActivated(IButton button)
            {
                _menuScene.switchState(_playingState, MenuScene.Blend.Single);
            }
        };
        Button.IAction pauseAction = new Button.IAction()
        {
            @Override
            public void OnActivated(IButton button)
            {
                _menuScene.switchState(_pauseState, MenuScene.Blend.Single);
            }
        };

        // Create helper variables.
        final float w = _menuCanvas.getSceneSize().X();
        final float h = _menuCanvas.getSceneSize().Y();
        final Point smallButtonPos = new Point(0.1f * w, 0.8f * h);
        final Point mLabelPos = new Point(0.5f * w, 0.6f * h);
        final Point mButtonPos = new Point(0.5f * w, 0.4f * h);
        final Point mbButtonPos = new Point(0.5f * w, 0.2f * h);
        final Point lButtonPos = new Point(0.2f * w, 0.2f * h);
        final Point rButtonPos = new Point(0.8f * w, 0.2f * h);
        final IButton.LogoStyle playLogo = IButton.LogoStyle.Play;
        final IButton.LogoStyle pauseLogo = IButton.LogoStyle.Pause;
        final IButton.LogoStyle stopLogo = IButton.LogoStyle.Stop;
        final IButton.LogoStyle statsLogo = IButton.LogoStyle.Stats;
        final IButton.LogoStyle settingsLogo = IButton.LogoStyle.Settings;

        // Populate layers.
        _homeState.add(new Label(mLabelPos, 0.008f * w, "LEARN"));
        _homeState.add(new Button(mButtonPos, 0.19f * w, playLogo, playAction));
        _homeState.add(new Button(lButtonPos, 0.10f * w, statsLogo, statsAction));
        _homeState.add(new Button(rButtonPos, 0.10f * w, settingsLogo, settingsAction));

        _gameEndState.add(new Label(mLabelPos, 0.16f * w, "game over"));
        _gameEndState.add(new Button(mButtonPos, 0.19f * w, playLogo, playAction));
        _gameEndState.add(new Button(mbButtonPos, 0.10f * w, stopLogo, homeAction));

        _pauseState.add(new Label(mLabelPos, 0.16f * w, "pause"));
        _pauseState.add(new Button(mButtonPos, 0.19f * w, playLogo, playAction));
        _pauseState.add(new Button(mbButtonPos, 0.10f * w, stopLogo, homeAction));

        _statsState.add(new Label(mLabelPos, 0.16f * w, "stats"));
        _statsState.add(new Button(smallButtonPos, 0.08f * w, stopLogo, homeAction));
        _statsState.add(new Label(mbButtonPos, 0.08f * w, "coming soon..."));

        _settingsState.add(new Label(mLabelPos, 0.16f * w, "settings"));
        _settingsState.add(new Button(smallButtonPos, 0.08f * w, stopLogo, homeAction));
        _settingsState.add(new Label(mbButtonPos, 0.08f * w, "coming soon..."));

        _playingState.add(new Button(smallButtonPos, 0.08f * w, pauseLogo, pauseAction));
    }

    @Override
    public void resize(int width, int height)
    {
        super.resize(width, height);
        IPoint viewSize = new Point(width, height);
        //_gameCanvas.setView(viewSize);
        _menuCanvas.setView(viewSize);
    }

    /**
     * The overridden render method.
     * Render and draw scenes.
     */
    @Override
    public void render()
    {
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (!_initialized)
            init();
        int timePassed = (int) (1000 * Gdx.graphics.getRawDeltaTime());
        _menuScene.update(timePassed);
        //_gameScene.update(timePassed);
        /*
        if (!_player.isAlive())
            return;
        _player.update(timePassed, _gameScene);
        */
        if (_menuScene.getActiveMenuState() == _playingState)
        {
            //_gameScreen.draw();
        }
        _menuScene.draw(_menuCanvas);
        //_batch.begin();
        //_batch.draw(_img, 0, 0);
        //_batch.end();

        super.render();
    }

    @Override
    public void dispose()
    {
        batch.dispose();

        fontNumbers.dispose();
        fontScore.dispose();
        fontMenuTitle.dispose();
        fontMenuText.dispose();
    }
}
