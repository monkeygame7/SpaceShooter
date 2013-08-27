import java.util.LinkedList;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Image;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;


public class Enemy implements Health
{
    protected int x, y, width, height, shootCounter, movement, health, paralyzeCounter,  duration,
        frequency, scoreValue;
    protected Random rand;
    protected LinkedList<Bullet> bullets;
    protected boolean isParalyzed, targetable;
    protected Color color;
    protected Image[] image;
    protected int animation;
    private int paralyzeAnimation;

    public Enemy( int x, int y, LinkedList<Bullet> b, Image[] i )
    {
        this.x = x;
        this.y = y;
        width = 50;
        height = 50;
        duration = 40;
        scoreValue = 5;
        rand = new Random();
        animation = rand.nextInt( duration );
        isParalyzed = false;
        frequency = 400;
        shootCounter = rand.nextInt( frequency ) + 10;
        movement = 0;
        bullets = b;
        health = 20;
        paralyzeCounter = 100;
        paralyzeAnimation = 0;
        color = Color.red;
        image = i;
        targetable = true;
    }

    public boolean canTarget()
    {
        return targetable;
    }

    public void paralyze()
    {
        isParalyzed = true;
        paralyzeCounter = 100;
    }

    public void paralyzeMove()
    {
        paralyzeCounter--;
        paralyzeAnimation = (paralyzeAnimation + 1) % 100;
        if ( (paralyzeAnimation / 5) % 2 == 1 )
            x -= 1;
        else
            x += 1;

        if( paralyzeCounter == 0 )
        {
            isParalyzed = false;
        }
    }

    public void move()
    {
        if ( isParalyzed )
            paralyzeMove();
        else
        {
            animation = (animation + 1) % duration;
            shootCounter--;
            if ( shootCounter == 0 )
                shoot();

            if ( movement < 130 )
            {
                if ( movement % 2 == 0 )
                    y++;
                movement++;
            }
            else if ( movement < 240 )
            {
                if ( movement % 2 == 0 )
                    x++;
                movement++;
            }
            else if ( movement < 370 )
            {
                if ( movement % 2 == 0 )
                    y--;
                movement++;
            }
            else if ( movement < 480 )
            {
                if ( movement % 2 == 0 )
                    x--;
                movement++;
            }
            else
            {
                movement = 0;
            }
        }
    }

    public void draw( Graphics2D g )
    {
        if ( image != null )
        {
            if ( animation < duration/2 )
                g.drawImage( image[0], x, y, null );
            else
            {
                g.drawImage( image[1], x, y, null );
            }
        }
        else
        {
            g.setColor( color );
            g.fill( getBounds() );
        }
    }

    public Rectangle getBounds()
    {
        return new Rectangle( x, y, width, height );
    }

    public void shoot()
    {
        bullets.add( new EnemyBullet( x + (width / 2), y + height ) );
        shootCounter = rand.nextInt( frequency ) + 50;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    public Color getColor()
    {
        return color;
    }

    public boolean isDead()
    {
        return health <= 0;
    }

    public int getValue()
    {
        return scoreValue;
    }

    public int getHealth()
    {
        return health;
    }

    public void increaseHealth( int k )
    {
        health = Math.min( health + k, 100 );
    }

    public void lowerHealth( int k )
    {
        health = Math.max( health - k, 0 );
    }
}
