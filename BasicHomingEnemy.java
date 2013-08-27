import java.util.LinkedList;
import java.awt.Image;
import java.awt.Color;


public class BasicHomingEnemy extends Enemy
{
    private int speed;
    private PlayerShip player;

    public BasicHomingEnemy( boolean goingRight, int x, int y, LinkedList<Bullet> b, PlayerShip p, Image[] i )
    {
        super( x, y, b, i );
        frequency = 250;
        health = 90;
        scoreValue = 20;
        player = p;
        speed = goingRight? 4:-4;
        color = Color.green.brighter();
    }

    public void move()
    {
        if ( isParalyzed )
            paralyzeMove();
        else
        {
            shootCounter--;
            if ( shootCounter == 0 )
                shoot();

            x += speed;
            if ( x < 10 || x + width > 790 )
                speed *= -1;
        }
    }

    public void shoot()
    {
        int direction = (int) Math.toDegrees( Math.atan2( (player.getX() - x) ,(y  - player.getY()) ) );
        bullets.add( new DirectionalBullet( x + (width / 2), y + height, direction ) );
        shootCounter = rand.nextInt( frequency ) + 50;
    }
}
