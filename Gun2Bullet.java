import java.awt.Graphics2D;
import java.awt.Color;


public class Gun2Bullet extends BasicBullet
{
    private int side;

    public Gun2Bullet( int x, int y, int s )
    {
        super( x, y );
        width = 10;
        height = 25;
        damage = 10;
        side = s;
        speed = 9;
        c = Color.yellow;
    }

    public void draw( Graphics2D g )
    {
        int[] xs = { x, x, x + width, x + width };
        int[] ys = { y + height, y, y + (height / 2) + 5, y + height };
        int[] ys1 = { y + height, y + (height / 2) + 5, y, y + height };
        g.setColor( getColor() );
        g.fillPolygon( xs, side == -1? ys:ys1, 4 );
    }
}
