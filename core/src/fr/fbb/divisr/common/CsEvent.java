package fr.fbb.divisr.common;

import java.util.ArrayList;
import java.util.List;

public class CsEvent<T>
{
    public List<CsListener<T>> _listeners;

    public CsEvent()
    {
        _listeners = new ArrayList<CsListener<T>>();
    }

    public void register(CsListener<T> listener)
    {
        _listeners.add(listener);
    }

    public void unregister(CsListener<T> listener)
    {
        _listeners.remove(listener);
    }

    public void invoke(T arg)
    {
        for (CsListener<T> listener : _listeners)
            listener.onAction(arg);
    }

    private interface CsListener<T>
    {
        void onAction(T arg);
    }
}
