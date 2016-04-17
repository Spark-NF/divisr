package fr.fbb.divisr.menu;

import fr.fbb.divisr.common.TouchInputEvent;
import fr.fbb.divisr.menu.components.Button;
import fr.fbb.divisr.menu.components.Component;
import fr.fbb.divisr.menu.components.Label;

import java.util.ArrayList;
import java.util.List;

public class MenuState implements IState
{
    private final List<Component> _components;
    private final MenuState _previousMenuState;
    private final float _baseMask;
    private final boolean _absorbTouch;

    public MenuState(MenuState previousMenuState, float baseMask, boolean absorbTouch)
    {
        _previousMenuState = previousMenuState;
        _baseMask = baseMask;
        _absorbTouch = absorbTouch;
        _components = new ArrayList<Component>();
    }

    public void init()
    {
        for (Component component : _components)
            component.init();
    }

    public MenuState getPrevious()
    {
        return _previousMenuState;
    }

    @Override
    public float getBaseMask()
    {
        return _baseMask;
    }

    @Override
    public List<? extends Component> getComponents()
    {
        return _components;
    }

    public void add(Label label)
    {
        _components.add(label);
    }

    public void add(Button button)
    {
        _components.add(button);
    }

    public boolean inputEvent(TouchInputEvent event)
    {
        for (Component component : _components)
            if (component.inputEvent(event))
                return true;
        return _absorbTouch;
    }
}
