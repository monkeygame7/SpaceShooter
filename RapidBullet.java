import java.awt.Graphics2D;


public class RapidBullet extends BasicBullet
{

    public RapidBullet( int x, int y )
    {
        super( x, y );
        height = 25;
        width = 5;
        damage = 9;
        speed = 9;
    }

    public void draw( Graphics2D g )
    {
        int[] xs = { x, x + (width / 4), x + ( 3 * width / 4), x + width };
        int[] ys = { y + height, y, y, y + height };
        g.setColor( getColor() );
        g.fillPolygon( xs, ys, 4 );
        g.setColor( getColor().brighter() );
        g.drawLine( xs[0], ys[0], xs[1], ys[1] );
        g.drawLine( xs[1], ys[1], xs[2], ys[2] );
        g.setColor( getColor().darker() );
        g.drawLine( xs[2], ys[2], xs[3], ys[3] );
        g.drawLine( xs[3], ys[3], xs[0], ys[0] );
    }
}
