import java.awt.Color;
import java.awt.Graphics2D;


public class EnemyBullet2 extends EnemyBullet
{
    public EnemyBullet2( int x, int y )
    {
        super( x, y );
        width = 12;
        height = 12;
        speed = 6;
        c = Color.green.brighter().brighter();
    }

    public void draw( Graphics2D g )
    {
        g.setColor( c );
        g.drawOval( x, y, width, height );
        g.setColor( c );
        g.drawOval( x + (width/4), y + (height/4), width/2, height/2 );
    }
}
