package fr.fbb.divisr.objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
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

    public Column(float velocity, Viewport viewport/*, Game divisr*/)
    {
        this.numbers = new LinkedList<Number>();
        this.bullets = new LinkedList<Bullet>();
        this.velocity = velocity;
        this.viewport = viewport;
        //this.divisr = divisr;
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
        // Bullet hit
	    Number topNumber = numbers.peek();
		Bullet topBullet = bullets.peek();
		if (topNumber != null && topBullet != null)
		{
			Rectangle numberRectangle = new Rectangle(1, topNumber.getY(), topNumber.getWidth(), topNumber.getHeight());
			Rectangle bulletRectangle = new Rectangle(1, topBullet.getY(), topBullet.getWidth(), topBullet.getHeight());

			if (numberRectangle .overlaps(bulletRectangle))
			{
				bullets.remove();
				numbers.remove();
				removeActor(topNumber);
				removeActor(topBullet);

				/*if (topNumber.divisible(topBullet))
				{
					divisr.goodGuess();
				}
				else
				{
					divisr.loseLife();
				}*/
			}
		}

        // Remove off screen numbers
        Iterator<Number> it = numbers.iterator();
        while (it.hasNext())
        {
            Number number = it.next();
            if (number.getY() + number.getHeight() < getY())
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
