package fr.fbb.divisr.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Number extends GameObject
{
	private int value;
	private BitmapFont font;
	private Color color;

	public Number(int value, BitmapFont font, Color color)
	{
		this.value = value;
		this.font = font;
		this.color = color;

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
		position.y -= 200 * delta;
	}

	public boolean divisible(Bullet bullet)
	{
		return value % bullet.value == 0;
	}
}