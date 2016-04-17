package fr.fbb.divisr.menu.components;

import fr.fbb.divisr.common.IPoint;

/**
 * Visitable superclass interface for all menu components.
 */
public interface IComponent extends IDrawable
{
    IPoint getPosition();
    boolean isVisible();
}
