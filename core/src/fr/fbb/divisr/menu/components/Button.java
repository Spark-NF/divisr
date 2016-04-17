package fr.fbb.divisr.menu.components;

import com.badlogic.gdx.graphics.Color;
import fr.fbb.divisr.canvas.ICanvas;
import fr.fbb.divisr.common.IPoint;
import fr.fbb.divisr.common.Point;
import fr.fbb.divisr.common.TouchInputEvent;

public class Button extends Component implements IButton
{
    public interface IAction
    {
        void OnActivated(IButton button);
    }

    private State _state;
    private LogoStyle _logoStyle;
    private float _size;
    private IAction _action;

    public Button(Point position, float size, LogoStyle logoStyle, IAction action)
    {
        super(position);
        _size = size;
        _logoStyle = logoStyle;
        _action = action;
    }

    @Override
    public void init()
    {
        _state = State.Released;
    }

    @Override
    public LogoStyle getLogoStyle()
    {
        return _logoStyle;
    }

    @Override
    public float getSize()
    {
        return _size;
    }

    @Override
    public State getState()
    {
        return _state;
    }

    @Override
    public boolean inputEvent(TouchInputEvent event)
    {
        _state = (event.position.distance(getPosition()) < _size) ? State.Pressed : State.Released;
        if (_state == State.Pressed && event.type == TouchInputEvent.Type.TouchUp)
        {
            _state = State.Activated;
            _action.OnActivated(this);
        }
        return (_state == State.Pressed);
    }

    @Override
    public boolean draw(ICanvas canvas)
    {
        if (!isVisible())
            return false;
        final IPoint p = getPosition();
        float s = getSize();
        Color color = Color.WHITE;
        if (getState() == IButton.State.Pressed)
            s *= 1.1f;
        canvas.disk(new Color(0.2f, 0.2f, 0.2f, 0.4f), p, s);
        canvas.circle(Color.WHITE, p, s);
        if (getState() == IButton.State.Pressed)
            s *= 1.3f;
        switch (getLogoStyle())
        {
            case Play:
                canvas.line(color, p, new Point(0.4f * s, 0.4f * s), new Point(0.4f * s, 0));
                canvas.line(color, p, new Point(0.4f * s, -0.4f * s), new Point(0.4f * s, 0));
                canvas.line(color, p, new Point(0.4f * s, 0.4f * s), new Point(-0.4f * s, 0.4f * s));
                break;
            case Pause:
                canvas.erect(color, p, new Point(0.4f * s, 0.4f * s), new Point(-0.2f * s, 0.4f * s));
                canvas.erect(color, p, new Point(-0.2f * s, 0.4f * s), new Point(0.4f * s, 0.4f * s));
                break;
            case Stop:
                canvas.erect(color, p, new Point(0.4f * s, 0.4f * s), new Point(0.4f * s, 0.4f * s));
                break;
            case Stats:
                canvas.circle(color, new Point(p.X() - 0.4f * s, p.Y() - 0.3f * s), 0.1f * s);
                canvas.erect(color, p, new Point(0.2f * s, 0.4f * s), new Point(0.5f * s, -0.2f * s));
                canvas.erect(color, p, new Point(0.5f * s, 0.1f * s), new Point(0.5f * s, 0.1f * s));
                canvas.erect(color, p, new Point(0.5f * s, -0.2f * s), new Point(0.5f * s, 0.4f * s));
                break;
            case Settings:
                canvas.circle(color, p, 0.16f * s);
                boolean lvl = false;
                final int it = 12;
                final double radCst = 2.0f * Math.PI / it;
                for (int i = 0; i < it; i++, lvl = !lvl)
                {
                    IPoint p0 = circlePos(p, lvl ? 0.5f * s : 0.3f * s, i * radCst);
                    IPoint p1 = circlePos(p, lvl ? 0.3f * s : 0.5f * s, i * radCst);
                    IPoint p2 = circlePos(p, lvl ? 0.3f * s : 0.5f * s, (i + 1) * radCst);
                    canvas.line(color, p0, p1);
                    canvas.line(color, p1, p2);
                }
                break;
        }
        return true;
    }

    private IPoint circlePos(IPoint center, float radius, double angle)
    {
        double dx = radius * Math.cos(angle);
        double dy = radius * Math.sin(angle);
        return center.add(new Point(dx, dy));
    }
}
