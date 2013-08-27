import java.awt.Graphics2D;
import java.awt.Color;


public class Nuke extends BasicBullet
{
    private boolean exploding;

    public Nuke( int x, int y )
    {
        super( x, y );
        speed = 1;
        damage = 5;
        exploding = false;
        width = 15;
        height = 40;
        scoreValue = 0.1;
        c = Color.darkGray;
    }

    public void move()
    {
        y -= speed/4 + 1;
        if ( speed != 0 )
        {
            speed++;
        }
        if ( exploding )
        {
            height += 2;
            width += 2;
            x--;
            y -= height % 3 - 1;
        }
        if ( width > 200 || height > 200 )
        {
            exploding = false;
            y = -1000;
        }
    }


    public void collideWith( Health h )
    {
        if ( !exploding )
        {
            width = 5;
            height = 5;
            c = Color.white;
            exploding = true;
        }
        speed = 0;
        h.lowerHealth( damage );
    }

    public void draw( Graphics2D g )
    {
        g.setColor( getColor() );
        if (exploding)
            g.fillOval( x, y, width, height );
        else
            g.fillRect( x, y, width, height );
    }
}
