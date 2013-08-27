import java.util.LinkedList;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Rectangle;


public abstract class Bullet
{
    protected int x, y, width, height, speed, damage;
    protected double scoreValue;
    protected Color c;
    protected boolean enemy;

    public double getValue()
    {
        return scoreValue;
    }

    public Rectangle getBounds()
    {
        return new Rectangle( x, y, width, height );
    }

    public boolean enemy()
    {
        return enemy;
    }

    public Color getColor()
    {
        return c;
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

    public abstract void move();

    public abstract void collideWith( Health h );

    public abstract void draw( Graphics2D g );

    public abstract void explode( LinkedList<Explosion> list, ExplosionImages expImages );
}
