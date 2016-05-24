package fr.fbb.divisr.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Align;
import fr.fbb.divisr.Divisr;

public class Bullet extends Obstacle
{
	public int value;
	private BitmapFont font;
	private final Texture texture;

	public Bullet(int value, BitmapFont font)
	{
		this.value = value;
		this.font = font;

		// Assets
		texture = Divisr.assetManager.get("circle-blue.png", Texture.class);

		this.setWidth(texture.getWidth());
		this.setHeight(texture.getHeight());
	}

	@Override
	public void act(float delta)
	{
		if (state != State.Dead)
			setY(getY() + 400 * delta);
		super.act(delta);
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
