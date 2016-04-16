package fr.fbb.divisr;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;

import java.util.List;

public class Column
{
	private Rectangle surface;
	private Color color;
	private List<Number> numbers;
	private float velocity;

	public Column(Rectangle surface, Color color, List<Number> numbers, float velocity)
	{
		this.surface = surface;
		this.color = color;
		this.numbers = numbers;
		this.velocity = velocity;
	}
}