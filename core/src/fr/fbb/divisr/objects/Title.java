package fr.fbb.divisr.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import fr.fbb.divisr.Divisr;

public class Title extends Actor
{
	private final Texture texture;

	public Title()
	{
		// Assets
		texture = Divisr.assetManager.get("logo.png", Texture.class);

		this.setWidth(texture.getWidth());
		this.setHeight(texture.getHeight());
	}

	@Override
	public void act(float delta)
	{
		setY(getY() + 200 * delta);
		super.act(delta);
	}

	@Override
	public void draw(Batch batch, float parentAlpha)
	{
		// Logo
		float x = getX() - texture.getWidth() / 2;
		float y = getY() - 0.1f * getParent().getHeight();
		batch.draw(texture, x, y);

		super.draw(batch, parentAlpha);
	}
}
