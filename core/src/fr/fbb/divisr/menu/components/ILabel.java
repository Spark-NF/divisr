package fr.fbb.divisr.menu.components;

import fr.fbb.divisr.common.IPoint;

public interface ILabel
{
    enum TextAlign
    {
        Left,
        Right,
        Center,
    }
    boolean isVisible();
    IPoint getPosition();
    TextAlign getTextAlign();
    float getTextSize();
    String getText();
}
