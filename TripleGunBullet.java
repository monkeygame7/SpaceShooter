import java.awt.Color;


public class TripleGunBullet extends BasicBullet
{
    private int direction;

    public TripleGunBullet( int x, int y, int d, int da )
    {
        super( x, y );
        direction = d;
        width = 20;
        height = 20;
        damage = da;
        speed = 13;
        c = Color.green;
    }

    public void move()
    {
        if ( direction == -1 )
        {
            x -= speed / 2;
            y -= speed;
        }
        else if ( direction == 0)
        {
            y -= speed;
        }
        else if ( direction == 1 )
        {
            x += speed / 2;
            y -= speed;
        }
    }

}
