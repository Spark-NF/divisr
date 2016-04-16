package fr.fbb.divisr;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Number extends GameObject
{
	private Color color;
	private int content;
	private BitmapFont font;

	public Number(int content, BitmapFont font, Color color)
	{
		this.content = content;
		this.font = font;
		this.color = color;
	}

	@Override
	public void draw(SpriteBatch sb)
	{
		font.draw(sb, Integer.toString(content), position.x, position.y);
	}

	@Override
	public void update(float delta)
	{
		position.y -= 200 * delta;
	}
}