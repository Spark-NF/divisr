package fr.fbb.divisr.objects;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Bullet extends GameObject
{
	public int value;
	private BitmapFont font;

	public Bullet(int value, BitmapFont font)
	{
		this.value = value;
		this.font = font;

		position.width = 64;
		position.height = 64;
	}

	@Override
	public void draw(SpriteBatch sb)
	{
		font.draw(sb, Integer.toString(value), position.x, position.y);
	}

	@Override
	public void update(float delta)
	{
		position.y += 200 * delta;
	}
}