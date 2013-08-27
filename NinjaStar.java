import java.awt.Graphics2D;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Image;
import java.awt.Color;


public class NinjaStar extends DirectionalBullet
{
    private Image[] images;
    private boolean animation;
    public NinjaStar( int x, int y, int d )
    {
        super( x, y, d );
        speed = 10;
        damage = 2;
        direction = d;
        width = 15;
        height = 15;
        animation = true;
        xSpeed = (int)( speed * Trig.sin( direction ) );
        ySpeed = (int)( speed * -Trig.cos( direction ) );
        c = Color.gray;
        images = new Image[2];
        try
        {
            images[0] = ImageIO.read( getClass().getResource( "images/ninjastar1.png" ) );
            images[1] = ImageIO.read( getClass().getResource( "images/ninjastar2.png" ) );
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }
    }

    public void move()
    {
        super.move();
        animation = !animation;
    }

    public void draw( Graphics2D g )
    {
        g.drawImage( images[animation? 0:1], x, y, null );
    }
}
