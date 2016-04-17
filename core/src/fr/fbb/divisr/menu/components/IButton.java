package fr.fbb.divisr.menu.components;

import fr.fbb.divisr.common.IPoint;

public interface IButton
{
    enum State
    {
        Released,
        Pressed,
        Activated,
    }
    enum LogoStyle
    {
        Play,
        Pause,
        Stop,
        Stats,
        Settings,
    }
    boolean isVisible();
    IPoint getPosition();
    State getState();
    LogoStyle getLogoStyle();
    float getSize();
}
