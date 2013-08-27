import java.awt.Rectangle;


public class HealthPack
{
    private int x, y, width, height, value, speed;

    public HealthPack( int x, int y, int w, int h, int v )
    {
        this.x = x;
        this.y = y;
        width = w;
        height = h;
        value = v;
        speed = 3;
    }

    public void move()
    {
        y += speed;
    }

    public Rectangle getBounds()
    {
        return new Rectangle ( x, y, width, height );
    }

    public void setX( int x )
    {
        this.x = x;
    }

    public int getX()
    {
        return x;
    }

    public void setY( int y )
    {
        this.y = y;
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

    public int getValue()
    {
        return value;
    }
}
