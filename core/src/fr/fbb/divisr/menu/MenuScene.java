package fr.fbb.divisr.menu;

import com.badlogic.gdx.graphics.Color;
import fr.fbb.divisr.canvas.ICanvas;
import fr.fbb.divisr.common.Point;
import fr.fbb.divisr.common.TouchInputEvent;
import fr.fbb.divisr.menu.components.Component;

public class MenuScene
{
    public enum Blend
    {
        None,
        Single,
        SingleR, // Reverse single (after double)
        Double
    }

    private int _switchingTimer;
    private MenuState _activeMenuState;
    private MenuState _switchingTo;
    private float _blendMask; // 0 .. 1
    private Blend _blend;
    private final int _animationPeriod = 400;
    //public final CsEvent<MenuState> OnStateChanged = new CsEvent<MenuState>();

    public MenuScene()
    {
    }

    public void init(MenuState activeMenuState)
    {
        _switchingTo = null;
        _switchingTimer = 0;
        _activeMenuState = activeMenuState;
        _activeMenuState.init();
        _blendMask = 0;
        _blend = Blend.None;
        switchState(activeMenuState, Blend.Single);
    }

    public MenuState getActiveMenuState()
    {
        return _activeMenuState;
    }

    //@Override
    public float getOpacityMask()
    {
        return _blendMask;
    }

    public MenuState getSwitchingTo()
    {
        return _switchingTo;
    }

    public boolean onBackPressed()
    {
        final MenuState previousMenuState = _activeMenuState.getPrevious();
        if (previousMenuState == null)
            System.exit(0);
        else if (_activeMenuState.getBaseMask() == previousMenuState.getBaseMask())
            switchState(previousMenuState, Blend.Double);
        else
            switchState(previousMenuState, Blend.Single);
        return true;
    }

    public boolean inputEvent(TouchInputEvent event)
    {
        if (_activeMenuState == null)
            return false;
        return _activeMenuState.inputEvent(event);
    }

    /**
     * @param timePassed ms since the last update²
     */
    public void update(int timePassed)
    {
        final int period = _animationPeriod;
        // Decrement switching timer if needed.
        if (_switchingTimer > 0)
        {
            _switchingTimer -= timePassed;
            // Change state if timer is null.
            if (_switchingTimer <= 0)
            {
                if (_blend == Blend.Double)
                {
                    _switchingTimer = period;
                    _blend = Blend.SingleR;
                } else
                    _switchingTimer = 0;
                onStateSwitch(_switchingTo);
            }
        }
        // Set blend mask.
        final float oldBase = _activeMenuState.getBaseMask();
        final float newBase = _switchingTo.getBaseMask();
        final float dBase = newBase - oldBase;
        final float progress = (float) (period - _switchingTimer) / period;
        if (_switchingTimer == 0)
            _blendMask = newBase;
        else
        {
            if (_blend == Blend.None)
                _blendMask = newBase;
            else if (_blend == Blend.Single)
                _blendMask = oldBase + progress * dBase;
            else if (_blend == Blend.SingleR)
                _blendMask = newBase + (1 - progress) * (1 - newBase);
            else //(_blend == Blend.Double)
                _blendMask = oldBase + progress * (1 - oldBase);
        }
    }

    public void switchState(MenuState newMenuState, Blend blend)
    {
        _switchingTo = newMenuState;
        if (blend == Blend.None)
            onStateSwitch(newMenuState);
        else
        {
            _switchingTimer = _animationPeriod;
            _blend = blend;
        }
    }

    /**
     * Draws the game scene
     */
    public void draw(ICanvas canvas)
    {
        canvas.debugGrid(10);
        final Color color = new Color(0.0f, 0.0f, 0.0f, _blendMask);
        canvas.pane(color, new Point(0, 0), canvas.getSceneSize());
        for (Component obj : _activeMenuState.getComponents())
            if (obj.isVisible())
                obj.draw(canvas);
    }

    private void onStateSwitch(MenuState newMenuState)
    {
        if (newMenuState == _activeMenuState)
            return;
        MenuState previous = _activeMenuState;
        _activeMenuState = newMenuState;
        //OnStateChanged.invoke(newMenuState);
    }
}
