import java.util.LinkedList;
import java.awt.Image;
import java.util.ArrayList;


public class SidewaysEnemy extends Enemy
{
    private int speed;

    public SidewaysEnemy( boolean goingRight, int x, int y, LinkedList<Bullet> b, Image[] i )
    {
        super( x, y, b, i );
        width = 50;
        height = 50;
        health = 100;
        frequency = 50;
        scoreValue = 20;
        speed = goingRight? 3:-3;
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
        bullets.add( new EnemyBullet2( x + (width / 2), y + height ) );
        shootCounter = rand.nextInt( frequency ) + 50;
    }
}
