package fr.fbb.divisr.menu;

import fr.fbb.divisr.menu.components.IDrawable;

import java.util.List;

public interface IState
{
    float getBaseMask();
    List<? extends IDrawable> getComponents();
}
