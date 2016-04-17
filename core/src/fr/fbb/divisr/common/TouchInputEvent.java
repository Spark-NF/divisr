package fr.fbb.divisr.common;

public class TouchInputEvent
{
    public enum Type
    {
        TouchDown,
        TouchUp,
        TouchDrag,
        MouseMove
    }

    public final Type type;
    public final IPoint position;
    public final int pointer;

    public TouchInputEvent(Type type, IPoint position, int pointer)
    {
        this.type = type;
        this.position = position;
        this.pointer = pointer;
    }
}
