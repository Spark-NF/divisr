package fr.fbb.divisr.menu.components;

import fr.fbb.divisr.common.Point;
import fr.fbb.divisr.common.TouchInputEvent;

/**
 * Visitable superclass and model for all menu components.
 */
public abstract class Component implements IComponent
{
    private Point _position;
    private boolean _visible;

    protected Component(Point position)
    {
        _position = position;
        _visible = true;
    }

    public void init()
    {
    }

    @Override
    public boolean isVisible()
    {
        return _visible;
    }

    @Override
    public Point getPosition()
    {
        return _position;
    }

    public void setVisible(boolean visible)
    {
        _visible = visible;
    }

    public void setPosition(Point position)
    {
        _position = position;
    }

    public abstract boolean inputEvent(TouchInputEvent event);
}
