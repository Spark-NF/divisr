package fr.fbb.divisr.objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Group;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Column extends Group
{
	private List<Number> numbers;
	private List<Bullet> bullets;
	private Game game;

	public Column(Game game)
	{
		this.numbers = new LinkedList<Number>();
		this.bullets = new LinkedList<Bullet>();
		this.game = game;
	}

	public void add(Number number)
	{
		// Falling numbers start at the center top of the column
		final float posX = this.getWidth() / 2;
		final float posY = this.getHeight();
		number.setPosition(posX, posY);

		numbers.add(number);
		addActor(number);
	}

	public void add(Bullet bullet)
	{
		// Bullets start at the center bottom of the column
		final float posX = this.getWidth() / 2;
		final float posY = 0;
		bullet.setPosition(posX, posY);

		bullets.add(bullet);
		addActor(bullet);
	}

	public int numbersCount()
	{
		return numbers.size();
	}

	public int bulletsCount()
	{
		return bullets.size();
	}

	@Override
	public void act(float delta)
	{
		checkCollisions();

		// Remove off screen or dead numbers
		Iterator<Number> itN = numbers.iterator();
		while (itN.hasNext())
		{
			Number number = itN.next();
			if (number.getState() != Obstacle.State.Alive && number.timeDead > 0.8f)
			{
				removeActor(number);
				itN.remove();
			}
			else if (number.getY() + number.getHeight() < getY())
			{
				removeActor(number);
				itN.remove();
				game.loseLife();
			}
		}

		// Remove off screen or dead bullets
		Iterator<Bullet> itB = bullets.iterator();
		while (itB.hasNext())
		{
			Bullet bullet = itB.next();
			if (bullet.getState() != Obstacle.State.Alive && bullet.timeDead > 0.8f)
			{
				removeActor(bullet);
				itB.remove();
			}
			else if (bullet.getY() > getY() + getHeight())
			{
				removeActor(bullet);
				itB.remove();
				game.loseLife();
			}
		}
		super.act(delta);
	}

	private void checkCollisions()
	{
		for (Bullet bullet : bullets)
		{
			if (bullet.state == Obstacle.State.Alive)
			{
				for (Number number : numbers)
				{
					if (number.state == Obstacle.State.Alive)
					{
						checkCollisions(bullet, number);
					}
				}
			}
		}
	}

	private void checkCollisions(Bullet bullet, Number number)
	{
		if (number == null || bullet == null)
		{
			return;
		}

		Rectangle numberRectangle = new Rectangle(1, number.getY(), number.getWidth(), number.getHeight());
		Rectangle bulletRectangle = new Rectangle(1, bullet.getY(), bullet.getWidth(), bullet.getHeight());

		if (numberRectangle.overlaps(bulletRectangle))
		{
			if (number.divisible(bullet))
			{
				number.setState(Obstacle.State.Sacrificed);
				bullet.setState(Obstacle.State.Sacrificed);
				game.goodGuess();
			}
			else
			{
				number.setState(Obstacle.State.Dead);
				bullet.setState(Obstacle.State.Dead);
				game.loseLife();
			}
		}
	}

	@Override
	public void draw(Batch batch, float parentAlpha)
	{
		super.draw(batch, parentAlpha);
	}
}
