import java.awt.Graphics2D;
import java.awt.Point;


public class Particle
{
    private Point point;
    private int direction, speed;

    public Particle( int x, int y, int d )
    {
        point = new Point( x, y );
        direction = d;
        speed = (int)( Math.random() * 5 ) + 3;
    }

    public void step()
    {
        point.x += speed * Trig.cos( direction );
        point.y += speed * Trig.sin( direction );
    }

    public void draw( Graphics2D g )
    {
        g.drawLine( point.x, point.y, (int)(point.x + speed * Trig.cos( direction )), (int)(point.y + speed * Trig.sin( direction ) ) );
    }

    public Point getPoint()
    {
        return point;
    }
}
