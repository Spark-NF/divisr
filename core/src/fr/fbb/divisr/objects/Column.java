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
	private float velocity;

	public Column(Color color, float velocity)
	{
		this.color = color;
		this.numbers = new LinkedList<Number>();
		this.velocity = velocity;
	}

	public void addNumber(Number number)
	{
		number.position.x = this.position.x;
		number.position.y = this.position.y;

		numbers.add(number);
	}

	@Override
	public void draw(SpriteBatch sb)
	{
		for (Number number : numbers)
		{
			number.draw(sb);
		}
	}

	@Override
	public void update(float delta)
	{
		Iterator<Number> it = numbers.iterator();
		while (it.hasNext())
		{
			Number number = it.next();
			number.update(delta);

			// Remove off screen numbers (game over)
			if (number.position.y  + 64 < 0) {
				it.remove();
			}
		}
	}
}