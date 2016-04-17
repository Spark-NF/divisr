package fr.fbb.divisr.canvas;

import com.badlogic.gdx.graphics.Color;
import fr.fbb.divisr.common.IPoint;

public interface ICanvas
{

    IPoint getSceneSize();

    IPoint getScreenSize();

    /**
     * Draws a debug grid.
     */
    void debugGrid(int step);

    /**
     * Draws a line with the specified color.
     *
     * @param color Draw color
     * @param start Start point
     * @param end   End point
     */
    void line(Color color, IPoint start, IPoint end);

    /**
     * Draws a line with the specified color.
     *
     * @param color  Draw color
     * @param startX Start point X
     * @param startY Start point Y
     * @param endX   End point X
     * @param endY   End point Y
     */
    //void line(Color color, float startX, float startY, float endX, float endY);

    /**
     * Draws a line with the specified color.
     * Coordinates are calculated from the center point.
     *
     * @param color      Draw color
     * @param p          Center point
     * @param deltaStart Start point delta from center
     * @param deltaEnd   End point delta from center
     */
    void line(Color color, IPoint p, IPoint deltaStart, IPoint deltaEnd);

    /**
     * Draws a line with the specified color.
     * Coordinates are calculated from the center point.
     *
     * @param color Draw color
     * @param p     Center point
     * @param dsX   Start point delta X from center
     * @param dsY   Start point delta Y from center
     * @param deX   End point delta X from center
     * @param deY   End point delta Y from center
     */
    //void line(Color color, IPoint p, float dsX, float dsY, float deX, float deY);

    /**
     * Draws a line and its horizontal symmetry.
     * Coordinates are calculated from the center point.
     *
     * @param color Draw color
     * @param p     Center point
     * @param dsX   Start point delta X from center
     * @param dsY   Start point delta Y from center
     * @param deX   End point delta X from center
     * @param deY   End point delta Y from center
     */
    //void hsline(Color color, IPoint p, float dsX, float dsY, float deX, float deY);

    /**
     * Fills a rectangle with the specified color.
     *
     * @param color Draw color
     * @param start Start point X
     * @param end   End point X
     */
    void rect(Color color, IPoint start, IPoint end);

    /**
     * Draws an empty rectangle with the specified color.
     * Coordinates are calculated from the center point.
     *
     * @param color      Draw color
     * @param center     Center point
     * @param deltaStart Start point delta from center
     * @param deltaEnd   End point delta from center
     */
    void erect(Color color, IPoint center, IPoint deltaStart, IPoint deltaEnd);

    /**
     * Fills a rectangle with the specified color.
     *
     * @param color Draw color
     * @param start Start point X
     * @param end   End point X
     */
    void pane(Color color, IPoint start, IPoint end);

    /**
     * Draws an empty rectangle with the specified color.
     * Coordinates are calculated from the center point.
     *
     * @param color      Draw color
     * @param center     Center point
     * @param deltaStart Start point delta from center
     * @param deltaEnd   End point delta from center
     */
    void epane(Color color, IPoint center, IPoint deltaStart, IPoint deltaEnd);

    /**
     * Fills a rectangle and its horizontal symmetry.
     * Coordinates are calculated from the center point.
     *
     * @param color Draw color
     * @param p     Center point
     * @param dsX   Start point delta X from center
     * @param dsY   Start point delta Y from center
     * @param deX   End point delta X from center
     * @param deY   End point delta Y from center
     */
    //void hsrect(Color color, IPoint p, float dsX, float dsY, float deX, float deY);

    /**
     * Draws a circle outline with specified color.
     *
     * @param color Draw color
     * @param p     Center point
     * @param r     Circle radius
     */
    void circle(Color color, IPoint p, float r);

    /**
     * Fills a circle with specified color.
     *
     * @param color Draw color
     * @param p     Center point
     * @param r     Circle radius
     */
    void disk(Color color, IPoint p, float r);

    /**
     * Draws the specified text on canvas.
     * TODO: add options
     *
     * @param text Text to draw
     * @param pos  Grid position
     */
    void text(Color color, String text, IPoint pos, float scale);

    IPoint getScreenPos(IPoint indexPosition);

    IPoint getIndexPos(IPoint screenPos);

}
