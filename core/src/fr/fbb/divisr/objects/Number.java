package fr.fbb.divisr.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Align;
import fr.fbb.divisr.Divisr;

public class Number extends Obstacle
{
	private int value;
	private BitmapFont font;
	private Color color;
	private final Texture texture;

	public Number(int value, BitmapFont font, Color color)
	{
		this.value = value;
		this.font = font;
		this.color = color;

		// Assets
		texture = Divisr.assetManager.get("square-red.png", Texture.class);

		this.setWidth(texture.getWidth());
		this.setHeight(texture.getHeight());
	}

	@Override
	public void act(float delta)
	{
		if (state != State.Dead)
			setY(getY() - 200 * delta);
		super.act(delta);
	}

	public boolean divisible(Bullet bullet)
	{
		return divisible(bullet.value);
	}

	public boolean divisible(int value)
	{
		return this.value % value == 0;
	}

	@Override
	public void draw(Batch batch, float parentAlpha)
	{
		// Background
		float x = getX() - texture.getWidth() / 2;
		float y = getY();
		if (state == State.Dead)
			batch.setColor(new Color(0.2f, 0.2f, 0.2f, 1.0f));
		else if (state == State.Sacrificed)
			batch.setColor(new Color(0.8f, 0.8f, 0.8f, 1.0f));
		else
			batch.setColor(Color.WHITE);
		batch.draw(texture, x, y);

		// Text
		x = getParent().getWidth() / 2;
		y = getY() + (texture.getHeight() + 90) / 2; // font height
		font.draw(batch, Integer.toString(value), x, y, 0, Align.center, false);

		// Reset color.
		batch.setColor(Color.WHITE);

		super.draw(batch, parentAlpha);
	}
}
