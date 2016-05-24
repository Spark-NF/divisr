package fr.fbb.divisr.objects;

import com.badlogic.gdx.scenes.scene2d.Actor;

public class Obstacle extends Actor
{
	public enum State
	{
		Alive,
		Dead,
		Sacrificed, // divided (:
	}

	protected State state;
	protected float timeDead; // sec

	public Obstacle()
	{
		state = State.Alive;
		timeDead = 0;
	}

	public State getState()
	{
		return state;
	}

	public void setState(State state)
	{
		this.state = state;
	}

	public float getTimeDead()
	{
		return timeDead;
	}

	@Override
	public void act(float delta)
	{
		if (state != State.Alive)
			timeDead += delta;
		super.act(delta);
	}

}
