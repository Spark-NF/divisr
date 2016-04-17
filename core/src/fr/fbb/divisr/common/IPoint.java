package fr.fbb.divisr.common;

public interface IPoint
{
    float X();
    float Y();
    IPoint add(IPoint right);
    IPoint add(float right);
    IPoint mul(IPoint right);
    IPoint mul(float right);
    IPoint sub(IPoint right);
    IPoint sub(float right);
    IPoint div(IPoint right);
    IPoint div(float right);
    float distance(IPoint right);
    IPoint delta(IPoint right);
}
