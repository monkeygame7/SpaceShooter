import java.awt.Graphics2D;
import java.awt.Color;


public class StunBullet extends BasicBullet
{
    private int animation;

    public StunBullet( int x, int y )
    {
        super( x, y );
        width = 5;
        height = 20;
        speed = 5;
        damage = 15;
        animation = 0;
        c = Color.cyan;
    }

    public void collideWith( Health h )
    {
            super.collideWith( h );
            ((Enemy)h).paralyze();
    }

    public void move()
    {
        super.move();
        animation = ( animation + 1 ) % 100;
    }

    public void draw( Graphics2D g )
    {
        g.setColor( getColor() );
        if ( (animation / 10) % 2 == 1 )
        {
            g.drawLine( x, y, x + width, y + (height / 4) );
            g.drawLine( x + width, y + (height / 4), x, y + (2 * height / 4) );
            g.drawLine( x, y + (2 * height / 4), x + width, y + (3 * height / 4) );
            g.drawLine( x + width, y + (3 * height / 4), x, y + height );
        }
        else
        {
            g.drawLine( x + width, y, x, y + (height / 4) );
            g.drawLine( x, y + (height / 4), x + width, y + (2 * height / 4) );
            g.drawLine( x + width, y + ( 2 * height / 4), x, y + (3 * height / 4) );
            g.drawLine( x, y + (3 * height / 4), x + width, y + height );
        }
    }
}
