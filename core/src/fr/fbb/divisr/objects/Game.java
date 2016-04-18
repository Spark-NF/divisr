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
	public LinkedList<Integer> incomingValues;
	private LinkedList<Integer> fallingNumbers;
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
		incomingValues.add(MathUtils.random(1, 5));
		incomingValues.add(MathUtils.random(1, 5));
		incomingValues.add(MathUtils.random(1, 5));
		incomingValues.add(MathUtils.random(1, 5));

		fallingNumbers = new LinkedList<Integer>();
	}

	public int popValue()
	{
		if (fallingNumbers.size() > 0)
		{
			fallingNumbers.remove();
		}

		incomingValues.add(MathUtils.random(1, 5));
		return incomingValues.remove();
	}

	public int fallingNumber()
	{
		int next;
		int see = 1; //incomingValues.size();

		// Total random
		if (fallingNumbers.size() >= incomingValues.size() /*|| (fallingNumbers.size() != 0 && MathUtils.random(1, see) <= fallingNumbers.size())*/)
		{
			next = MathUtils.random(1, 5) * MathUtils.random(1, 5);
		}

		// Multiple of one of the incoming numbers
		else
		{
			int index = Math.min(incomingValues.size() - 1, MathUtils.random(fallingNumbers.size(), fallingNumbers.size() + see - 1));
			next = MathUtils.random(1, 5) * incomingValues.get(index);
		}

		fallingNumbers.add(next);
		return next;
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
