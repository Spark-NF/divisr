package fr.fbb.divisr.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public abstract class GameObject
{
	public Rectangle position = new Rectangle();

	public abstract void draw(SpriteBatch sb);
	public abstract void update(float delta);
}
