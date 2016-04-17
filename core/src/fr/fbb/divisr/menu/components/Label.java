package fr.fbb.divisr.menu.components;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import fr.fbb.divisr.canvas.ICanvas;
import fr.fbb.divisr.common.IPoint;
import fr.fbb.divisr.common.Point;
import fr.fbb.divisr.common.TouchInputEvent;

public class Label extends Component implements ILabel
{
    private BitmapFont _font;
    private String _text;
    private float _textSize;
    private TextAlign _textAlign;

    public Label(Point position, float textSize, String text)
    {
        super(position);
        _font = new BitmapFont();
        _text = text;
        _textSize = textSize;
        _textAlign = TextAlign.Center;
    }

    @Override
    public TextAlign getTextAlign()
    {
        return _textAlign;
    }

    @Override
    public float getTextSize()
    {
        return _textSize;
    }

    @Override
    public String getText()
    {
        return _text;
    }

    public void setText(String text)
    {
        _text = text;
    }

    public void setTextSize(float textSize)
    {
        _textSize = textSize;
    }

    public void setTextAlign(TextAlign textAlign)
    {
        _textAlign = textAlign;
    }

    @Override
    public boolean inputEvent(TouchInputEvent event)
    {
        return false;
    }

    @Override
    public boolean draw(ICanvas canvas)
    {
        if (!isVisible())
            return false;
        final IPoint pos = getPosition();
        // TODO
        //_textPaint.setTextSize(getTextSize());
        switch (getTextAlign())
        {
            case Left:
                //_textPaint.setTextAlign(Paint.Align.LEFT);
                break;
            case Right:
                //_textPaint.setTextAlign(Paint.Align.RIGHT);
                break;
            case Center:
                //_textPaint.setTextAlign(Paint.Align.CENTER);
                break;
        }
        canvas.text(Color.WHITE, getText(), pos, _textSize);
        return true;
    }
}
