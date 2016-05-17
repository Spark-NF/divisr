package fr.fbb.divisr.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;

public class Number extends Actor
{
	private int value;
	private BitmapFont font;
	private Color color;
	private final static Texture texture;

	static
	{
		texture = new Texture(Gdx.files.internal("square-red.png"));
	}

	public Number(int value, BitmapFont font, Color color)
	{
		this.value = value;
		this.font = font;
		this.color = color;

        this.setWidth(64);
        this.setHeight(64);
	}

	@Override
	public void act(float delta)
	{
		setY(getY() - 200 * delta);
		super.act(delta);
	}

	public boolean divisible(Bullet bullet)
	{
		return value % bullet.value == 0;
	}

	@Override
	public void draw(Batch batch, float parentAlpha)
	{
		// Background
		float x = getX() - texture.getWidth() / 2;
		float y = getY();
		batch.draw(texture, x, y);

		// Text
		x = getParent().getWidth() / 2;
		y = getY() + (texture.getHeight() + 90) / 2; // font height
		font.draw(batch, Integer.toString(value), x, y, 0, Align.center, false);

		super.draw(batch, parentAlpha);
	}
}
