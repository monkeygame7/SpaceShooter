import java.awt.Color;


public class DirectionalBullet extends EnemyBullet
{
    protected int direction;
    protected int xSpeed;
    protected int ySpeed;

    public DirectionalBullet( int x, int y, int d )
    {
        super( x, y );
        direction = (d / 25) * 25;
        width = 5;
        height = 15;
        damage = 6;
        speed = 6;
        xSpeed = (int)( speed * Trig.sin( direction ) );
        ySpeed = (int)( speed * -Trig.cos( direction ) );
        c = Color.orange.darker();
    }

    public void move()
    {
        x += xSpeed;
        y += ySpeed;
    }
}
