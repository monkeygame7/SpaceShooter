import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Random;


public class HomingMissile extends BasicBullet
{
    private Enemy target;
    private int direction;
    private BufferedImage image;
    private AffineTransform rotation;

    public HomingMissile( int x, int y, Enemy e, BufferedImage i )
    {
        super( x, y );
        width = 15;
        height = 15;
        damage = 20;
        speed = 7;
        target = e;
        direction = 270;
        scoreValue = 3;
        image = i;
        rotation = new AffineTransform();
        rotation.rotate( Math.toRadians( direction ), x + (width/2), y + (height/2) );
        rotation.translate( x, y );
    }

    public void move()
    {
        if ( target != null && !target.isDead() )
        {
            int tempDirection = ((int)Math.toDegrees( Math.atan2( (target.getY()+(target.getHeight())) - y, (target.getX()+(Math.random()*target.getWidth())) - x ) )
                + 360) % 360;
            if ( tempDirection > direction )
            {
                direction = (direction + 2) % 360;
                rotation.rotate( Math.toRadians(2), x + (width/2), y + (height/2) );
            }
            else if ( tempDirection < direction )
            {
                direction = ((direction - 2) + 360) % 360;
                rotation.rotate( Math.toRadians(-2), x + (image.getWidth()/2), y + (image.getHeight()/2) );
            }
            if ( y < target.getY() || direction > 357 || direction < 182 )
                target = null;
        }
        double xdiff = Trig.cos( direction ) * speed;
        double ydiff = Trig.sin( direction ) * speed;
        rotation.translate( xdiff, ydiff );
        rotation.rotate( Math.toRadians(direction), x + (image.getWidth()/2), y + (image.getHeight()/2) );

        x += xdiff;
        y += ydiff;
    }

    public void draw( Graphics2D g )
    {
        //g.drawImage( image, rotation, null );
        g.setColor( c );
        g.fillOval( x, y, width, height );
    }
}
