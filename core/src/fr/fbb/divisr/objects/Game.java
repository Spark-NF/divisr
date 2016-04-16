package fr.fbb.divisr.objects;

import com.badlogic.gdx.math.MathUtils;

import java.util.LinkedList;
import java.util.Queue;

public class Game
{
	public enum Difficulty
	{
		Easy,
		Medium,
		Hard
	}

	public Difficulty difficulty;
	public Queue<Integer> incomingValues;
	public int columns;
	public int score;
	public int lives;
	public int livesMax;

	public Game(int columns, Difficulty difficulty)
	{
		this.columns = columns;
		this.difficulty = difficulty;
		score = 0;

		// Initial lives
		livesMax = (difficulty == Difficulty.Easy ? 5 : (difficulty == Difficulty.Medium ? 3 : 1));
		lives = livesMax;

		// Initial incoming values
		incomingValues = new LinkedList<Integer>();
		incomingValues.add(1);
		incomingValues.add(2);
		incomingValues.add(3);
		incomingValues.add(4);
	}

	public int popValue()
	{
		incomingValues.add(MathUtils.random(1, 9));
		return incomingValues.remove();
	}

	public void loseLife()
	{
		lives--;
	}

	public void goodGuess()
	{
		score++;
	}
}
