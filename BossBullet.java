import java.awt.Color;


public class BossBullet extends DirectionalBullet
{
    private int direction;

    public BossBullet( int x, int y, int d )
    {
        super( x, y, d );
        direction = (d / 10) * 10;
        width = 30;
        height = 30;
        damage = 15;
        speed = 7;
        c = Color.yellow.darker();
    }
}
