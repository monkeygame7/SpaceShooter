import java.awt.image.BufferedImage;
import java.awt.geom.AffineTransform;
import java.awt.Graphics2D;
import java.awt.Color;


public class BeeBullet extends BasicBullet
{
    private int direction;

    public BeeBullet( int x, int y )
    {
        super( x, y );
        width = 5;
        height = 6;
        speed = (int) (Math.random() * 20) + 9;
        damage = 2;
        scoreValue = 0.5;
        direction = ((int)(Math.random() * 160) + 10);
        c = Color.yellow;
    }

    public void move()
    {
        int k = (int)( Math.random() * 100 );

        x += (k % 2) == 0? 1:-1 * ( speed * Math.cos( Math.toRadians( direction ) ) );
        y += (k % 4) == 0? 1:-1 * (Math.random() * speed);
    }

    public void draw( Graphics2D g )
    {
        g.setColor( c );
        g.fillOval( x, y, width, height );
        g.setColor( Color.black );
        g.drawLine( x, y + 1, x + width, y + 1);
        g.drawLine( x, y + 4, x + width, y + 4 );
    }
}
