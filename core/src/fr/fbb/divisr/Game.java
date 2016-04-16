package fr.fbb.divisr;

import java.util.List;

public class Game
{
	public enum Difficulty
	{
		Easy,
		Medium,
		Hard
	}

	private Difficulty difficulty;
	private List<Column> columns;

	public Game(int cols, Difficulty diff)
	{
		difficulty = diff;

		// Columns
		/*for (int i = 0; i < cols; ++i)
		{
			columns.add(new Column());
		}*/
	}

	public void draw()
	{

	}

	public void update(float delta)
	{

	}
}
