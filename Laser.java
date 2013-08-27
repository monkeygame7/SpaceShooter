import java.awt.BasicStroke;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Image;
import java.awt.Color;
import java.awt.Graphics2D;


public class Laser extends BasicBullet
{
    private PlayerShip player;
    private boolean activating, shooting, done;
    private Image arg;
    private int animation, modifier;

    public Laser( int x, int y, int m, PlayerShip p )
    {
        super(x + 35, y);
        player = p;
        height = 0;
        width = 0;
        damage = 4;
        speed = 10;
        animation = 0;
        modifier = m;
        activating = true;
        shooting = true;
        done = false;
        c = Color.cyan;
        try
        {
            arg = ImageIO.read( getClass().getResource( "images/lasermouth.png" ) );
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }
    }

    public void collideWith( Health h )
    {
            h.lowerHealth( damage );
            Enemy e = (Enemy) h;
            if ( shooting )
            {
                y = e.getY() + e.getHeight() + 1;
                height = player.getY() - (e.getY() + e.getHeight()) - 1;
            }
            else
            {
                height = (y + height) - (e.getY() + e.getHeight()) - 5;
                y = e.getY() + e.getHeight() + 1;
            }
    }

    public void activate()
    {
        activating = true;
        animation++;

        if ( animation > 30 )
        {
            animation = 30;
            activating = false;
        }

        width = animation;
        if ( animation % 2 == 0 )
        {
            x -= x - (player.getX() + modifier + 35) + (animation/2);
            x--;
        }
    }

    public void draw( Graphics2D g )
    {
        if ( shooting )
            g.drawImage( arg, player.getX() - 15 + modifier, player.getY() - 30, null );
        g.setColor( c.darker() );
        g.fill( getBounds() );
        g.setColor( c );
        g.fillRect( x + (width/5), y, (3*width)/5, height );
        if ( height > 30 )
        {
            g.fillOval( x - 10, y - 20, width + 20, 40 );
            g.setColor( c.darker() );
            g.setStroke( new BasicStroke( width/5 ) );
            g.drawArc( x - 10, y - 20, width + 20, 40, -60, 300);
            g.setStroke( new BasicStroke() );
            g.setColor( c );
        }
        if ( shooting )
            g.fillOval( x - 5, y+height - 10, width + 10, 20 );
    }

    public void move()
    {
        if ( activating )
            activate();
        if ( !done )
        {
            shooting = player.isShooting();
        }
        y -= speed;

        if ( shooting )
        {
            height = player.getY() - y;
            if (!activating)
                x = player.getX() + modifier + 20;
        }
        if ( !shooting || (player.gun1() != 5  && player.gun2() != 5) )
        {
            done = true;
            activating = false;
            shooting = false;
        }
    }
}