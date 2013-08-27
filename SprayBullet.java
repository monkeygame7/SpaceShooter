import java.awt.Point;
import java.awt.Color;


public class SprayBullet extends BasicBullet
{
    protected int direction;
    protected int movement;
    protected int radius;
    protected Point center;

    public SprayBullet( int x, int y, int d )
    {
        super( x, y );
        width = 8;
        height = 8;
        speed = 7;
        damage = 10;
        direction = d;
        radius = Math.abs( direction )==2? 150:75;
        movement = 90 / speed;
        c = Color.green.darker();
        center = new Point( x, y - radius );
    }

    public void move()
    {
        movement += (direction<0? -1:1);

        if ( movement > (180/speed) || movement < 0 )
            direction = 0;
        if ( direction == 0 )
            y -= speed;
        else
        {
            x = (int)( center.x + ( radius * Trig.cos( movement * (speed) ) ) );
            y = (int)( center.y + ( radius * Trig.sin( movement * (speed) ) ) );
        }
    }
}
