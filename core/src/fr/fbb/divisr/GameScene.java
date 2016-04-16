package fr.fbb.divisr;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.TimeUtils;
import fr.fbb.divisr.objects.Column;
import fr.fbb.divisr.objects.Number;

import java.util.ArrayList;
import java.util.List;

public class GameScene
{
	public enum Difficulty
	{
		Easy,
		Medium,
		Hard
	}

	private final Divisr game;
	private OrthographicCamera camera;
	private Difficulty difficulty;
	private List<Column> columns;
	private long lastSpawn;

	public GameScene(final Divisr game, int cols, Difficulty diff)
	{
		this.game = game;
		difficulty = diff;

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 360, 640);

		// Columns
		columns = new ArrayList<Column>();
		for (int i = 0; i < cols; ++i)
		{
			Column col = new Column(new Color(i / cols, 0f, 0f, 1f), 200);
			col.position.x = i * 360 / cols;
			col.position.y = 640;
			col.position.width = 360 / cols;
			col.position.height = 640;
			columns.add(col);
		}

		spawnNumber();
	}

	public void draw(SpriteBatch sb)
	{
		for (Column column : columns)
		{
			column.draw(sb);
		}
	}

	public void update(float delta)
	{
		// Touch and mouse input
		if (Gdx.input.isTouched())
		{
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);

			//bucket.x = touchPos.x - 64 / 2;
		}

		// Update columns
		for (Column column : columns)
		{
			column.update(delta);
		}

		// Spawn new numbers from time to time
		if (TimeUtils.nanoTime() - lastSpawn > 1000000000)
		{
			spawnNumber();
		}
	}

	public void spawnNumber()
	{
		int index = MathUtils.random(0, columns.size() - 1);
		columns.get(index).addNumber(new Number(MathUtils.random(1, 9), game.font, new Color(0f, 1f, 0f, 1f)));

		lastSpawn = TimeUtils.nanoTime();
	}
}
