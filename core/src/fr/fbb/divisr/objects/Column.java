package fr.fbb.divisr.objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class Column extends Group
{
    private Queue<Number> numbers;
    private Queue<Bullet> bullets;
    //private Game divisr;
    private float velocity;
    private final Viewport viewport;
    private ShapeRenderer sr;

    public Column(float velocity, Viewport viewport/*, Game divisr*/)
    {
        this.numbers = new LinkedList<Number>();
        this.bullets = new LinkedList<Bullet>();
        this.velocity = velocity;
        this.viewport = viewport;
        //this.divisr = divisr;
        sr = new ShapeRenderer();
    }

    public void add(Number number)
    {
        final float posX = this.getX() + this.getWidth() / 2;
        final float posY = this.getY() + this.getHeight();
        number.setPosition(posX, posY);

        numbers.add(number);
        addActor(number);
    }

    public void add(Bullet bullet)
    {
        final float posX = this.getX() + this.getWidth() / 2;
        final float posY = this.getY() + bullet.getHeight();
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
        // Bullet hit
        // TODO: collisions
		/*
		Bullet topBullet = bullets.peek();
		Number topNumber = numbers.peek();
		if (topNumber != null && topBullet != null && topNumber.position.overlaps(topBullet.position))
		{
			bullets.remove();
			numbers.remove();

			if (topNumber.divisible(topBullet))
			{
				divisr.goodGuess();
			}
			else
			{
				divisr.loseLife();
			}
		}
		*/

        // Remove off screen numbers
        Iterator<Number> it = numbers.iterator();
        while (it.hasNext())
        {
            Number number = it.next();
            if (number.getY() - number.getHeight() < getY())
            {
                removeActor(number);
                it.remove();
                // TODO: check from parent
                //divisr.loseLife();
            }
        }

        // Remove off screen bullets
        Iterator<Bullet> itB = bullets.iterator();
        while (itB.hasNext())
        {
            Bullet bullet = itB.next();
            if (bullet.getY() > getY() + getHeight())
            {
                removeActor(bullet);
                itB.remove();
            }
        }
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha)
    {
        super.draw(batch, parentAlpha);
    }
}
