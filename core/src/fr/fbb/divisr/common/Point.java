package fr.fbb.divisr.common;

public class Point implements IPoint
{
    public float x;
    public float y;

    public Point()
    {
        this.x = 0;
        this.y = 0;
    }

    public Point(float x, float y)
    {
        this.x = x;
        this.y = y;
    }

    public Point(double x, double y)
    {
        this.x = (float) x;
        this.y = (float) y;
    }

    public Point(IPoint src)
    {
        this.x = (src == null) ? 0 : src.X();
        this.y = (src == null) ? 0 : src.Y();
    }

    @Override
    public float X()
    {
        return x;
    }

    @Override
    public float Y()
    {
        return y;
    }

    @Override
    public Point add(IPoint right)
    {
        if (right == null)
            return new Point(this);
        return new Point(X() + right.X(), Y() + right.Y());
    }

    @Override
    public Point add(float right)
    {
        return new Point(X() + right, Y() + right);
    }

    @Override
    public Point mul(IPoint right)
    {
        if (right == null)
            return new Point(this);
        return new Point(X() * right.X(), Y() * right.Y());
    }

    @Override
    public Point mul(float right)
    {
        return new Point(X() * right, Y() * right);
    }

    @Override
    public Point sub(IPoint right)
    {
        if (right == null)
            return new Point(this);
        return new Point(X() - right.X(), Y() - right.Y());
    }

    @Override
    public Point sub(float right)
    {
        return new Point(X() - right, Y() - right);
    }

    @Override
    public Point div(IPoint right)
    {
        if (right == null)
            return new Point(this);
        return new Point(X() / right.X(), Y() / right.Y());
    }

    @Override
    public Point div(float right)
    {
        return new Point(X() / right, Y() / right);
    }

    @Override
    public float distance(IPoint right)
    {
        if (right == null)
            return distance(new Point(0, 0));
        final IPoint delta = sub(right);
        final float sqDeltaX = (float) Math.pow(delta.X(), 2);
        final float sqDeltaY = (float) Math.pow(delta.Y(), 2);
        return (float) Math.sqrt(sqDeltaX + sqDeltaY);
    }

    @Override
    public IPoint delta(IPoint right)
    {
        if (right == null)
            return right;
        return new Point(right.X() - X(), right.Y() - Y());
    }

    public Point setLocation(IPoint src)
    {
        if (src == null)
            return setLocation(0, 0);
        else
            return setLocation(src.X(), src.Y());
    }

    public Point setLocation(float x, float y)
    {
        this.x = x;
        this.y = y;
        return this;
    }

    public final Point offset(float dx, float dy)
    {
        x += dx;
        y += dy;
        return this;
    }

    public final Point offset(IPoint delta)
    {
        if (delta == null)
            return this;
        return offset(delta.X(), delta.Y());
    }

    @Override
    public String toString()
    {
        return "Point(" + x + ", " + y + ")";
    }

    public final boolean equals(float x, float y)
    {
        return this.x == x && this.y == y;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Point point = (Point) o;

        if (x != point.x) return false;
        return y == point.y;

    }

    public static IPoint Zero()
    {
        return new Point(0, 0);
    }

    public static IPoint One()
    {
        return new Point(1, 1);
    }
}
