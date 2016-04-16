package fr.fbb.divisr.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class Column extends GameObject
{
	private Color color;
	private Queue<Number> numbers;
	private Queue<Bullet> bullets;
	private Game game;
	private float velocity;

	public Column(Color color, float velocity, Game game)
	{
		this.color = color;
		this.numbers = new LinkedList<Number>();
		this.bullets = new LinkedList<Bullet>();
		this.velocity = velocity;
		this.game = game;
	}

	public void addNumber(Number number)
	{
		number.position.x = this.position.x;
		number.position.y = this.position.y + this.position.height;

		numbers.add(number);
	}

	public void addBullet(Bullet bullet)
	{
		bullet.position.x = this.position.x;
		bullet.position.y = this.position.y;

		bullets.add(bullet);
	}

	@Override
	public void draw(SpriteBatch sb)
	{
		for (Number number : numbers)
		{
			number.draw(sb);
		}
		for (Bullet bullet : bullets)
		{
			bullet.draw(sb);
		}
	}

	@Override
	public void update(float delta)
	{
		// Bullet hit
		Bullet topBullet = bullets.peek();
		Number topNumber = numbers.peek();
		if (topNumber != null && topBullet != null && topNumber.position.overlaps(topBullet.position))
		{
			bullets.remove();
			numbers.remove();

			if (topNumber.divisible(topBullet))
			{
				game.goodGuess();
			}
			else
			{
				game.loseLife();
			}
		}

		// Numbers
		Iterator<Number> it = numbers.iterator();
		while (it.hasNext())
		{
			Number number = it.next();
			number.update(delta);

			// Remove off screen numbers
			if (number.position.y  + 64 < 0) {
				it.remove();

				game.loseLife();
			}
		}

		// Bullets
		Iterator<Bullet> itB = bullets.iterator();
		while (itB.hasNext())
		{
			Bullet bullet = itB.next();
			bullet.update(delta);

			// Remove off screen bullets
			if (bullet.position.y > 640) {
				itB.remove();
			}
		}
	}
}