package fr.fbb.divisr.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.viewport.Viewport;
import fr.fbb.divisr.Divisr;

import java.util.LinkedList;

public class Game extends Group
{
	public enum Difficulty
	{
		Easy,
		Medium,
		Hard
	}

	public LinkedList<Integer> incomingValues;
	//private LinkedList<Integer> fallingNumbers;

	public Difficulty difficulty;
	public int columnNum;
	public int score;
	public int lives;
	public int livesMax;
	public Column[] columns;
	private static Texture lifeOn;
	private static Texture lifeOff;
	public BitmapFont numbersFont;
	public BitmapFont scoreFont;
	public Viewport viewport;

	public Game(int columnNum, Difficulty difficulty)
	{
		this.columnNum = columnNum;
		this.difficulty = difficulty;
		score = 0;

		// Assets
		lifeOn = Divisr.assetManager.get("life-on.png", Texture.class);
		lifeOff = Divisr.assetManager.get("life-off.png", Texture.class);
		numbersFont = Divisr.assetManager.get("fonts/numbers.ttf", BitmapFont.class);
		scoreFont = Divisr.assetManager.get("fonts/score.ttf", BitmapFont.class);

		// Initial lives
		livesMax = (difficulty == Difficulty.Easy ? 5 : (difficulty == Difficulty.Medium ? 3 : 1));
		lives = livesMax;

		/*
		// Create columns
		columns = new ArrayList<Column>();
		for (int i = 0; i < columnNum; ++i)
		{
			Column col = new Column(200);
			col.setPosition(i * viewPort.getWorldWidth() / columnNum, 315);
			col.setWidth(viewPort.getWorldWidth() / columnNum);
			col.setHeight(getViewport().getWorldHeight() - col.getY());
			columns.add(col);
		}
		*/

		// Initial incoming values
		incomingValues = new LinkedList<Integer>();
		incomingValues.add(MathUtils.random(1, 5));
		incomingValues.add(MathUtils.random(1, 5));
		incomingValues.add(MathUtils.random(1, 5));
		incomingValues.add(MathUtils.random(1, 5));

		//fallingNumbers = new LinkedList<Integer>();
	}

	public void spawnBullet(int index, Bullet bullet)
	{
		columns[index].add(bullet);
	}

	public int popValue()
	{
		incomingValues.add(MathUtils.random(1, 5));
		return incomingValues.remove();
	}

	public int numbersCount()
	{
		int count = 0;
		for (Column column : columns)
		{
			count += column.numbersCount();
		}
		return count;
	}

	public int bulletsCount()
	{
		int count = 0;
		for (Column column : columns)
		{
			count += column.bulletsCount();
		}
		return count;
	}

	/*
	 * Logic for finding the next falling number value.
	 */
	public int fallingNumber()
	{
		int next;
		int see = 2; //incomingValues.size();

		// Total random
		if (numbersCount() >= bulletsCount() /*|| (fallingNumbers.size() != 0 && MathUtils.random(1, see) <= fallingNumbers.size())*/)
		{
			next = MathUtils.random(1, 5) * MathUtils.random(1, 5);
		}

		// Multiple of one of the incoming numbers
		else
		{
			int index = Math.min(bulletsCount() - 1, MathUtils.random(numbersCount(), numbersCount() + see - 1));
			next = MathUtils.random(1, 5) * incomingValues.get(index);
		}

		//fallingNumbers.add(next);
		return next;
	}

	public void spawnNumber(Number number)
	{
		int index = MathUtils.random(0, columnNum - 1);
		columns[index].add(number);
	}

	public void loseLife()
	{
		lives--;
	}

	public void goodGuess()
	{
		score++;
	}

	@Override
	public void act(float delta)
	{
		super.act(delta);
	}

	@Override
	public void draw(Batch batch, float parentAlpha)
	{
		// Incoming values
		int i = 0;
		for (Integer value : incomingValues)
		{
			if (i == 0)
			{
				numbersFont.draw(batch, Integer.toString(value), viewport.getWorldWidth() / 2 - 20, 180);
			}
			else
			{
				numbersFont.draw(batch, Integer.toString(value), (incomingValues.size() - i) * 120 - 60, 180);
			}
			++i;
		}

		// Lives
		int lifeSpacing = (int)(viewport.getWorldWidth() * 0.015);
		int center = (int)(viewport.getWorldWidth() * 0.815);
		int size = livesMax * lifeOn.getWidth() + (livesMax - 1) * lifeSpacing;
		int leftBound = center - size / 2;
		for (int life = 0; life < livesMax; ++life)
		{
			boolean on = life < lives;
			batch.draw(on ? lifeOn : lifeOff, leftBound, 150);
			leftBound += lifeOn.getWidth() + lifeSpacing;
		}

		// Score
		scoreFont.draw(batch, "score", viewport.getWorldWidth() - 350, 120);
		scoreFont.draw(batch, Integer.toString(score), viewport.getWorldWidth() - 100, 120);

		super.draw(batch, parentAlpha);
	}
}
