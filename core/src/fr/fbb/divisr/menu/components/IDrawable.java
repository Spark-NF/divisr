package fr.fbb.divisr.menu.components;

import fr.fbb.divisr.canvas.ICanvas;

public interface IDrawable
{
    /**
     * Declares the Drawable interface prototype.
     * @param canvas    Canvas to draw the component on.
     * @return True if object has been drawn, false otherwise.
     */
    boolean draw(ICanvas canvas);
}
