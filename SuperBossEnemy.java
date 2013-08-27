import java.util.LinkedList;
import java.awt.Image;
import java.awt.Color;


public class SuperBossEnemy extends BossEnemy
{
    private int shootCounter2;

    public SuperBossEnemy( int x, int y, int hl, LinkedList<Bullet> b, PlayerShip p, Image[] i )
    {
        super( x, y, hl, b, p, i );
        width = 250;
        height = 100;
        scoreValue = 50;
        shootCounter2 = rand.nextInt( 50 ) + 50;
        color = Color.green.darker().darker();
    }

    public void move()
    {
        if ( isParalyzed )
            paralyzeMove();
        else
        {
            animation = (animation + 1) % duration;
            shootCounter2--;
            if ( shootCounter2 == 0 )
                shoot2();
            shootCounter--;
            if ( shootCounter == 0 )
                shoot();
            if ( x < 10 || x + width > 790 )
                speed *= -1;
            x += speed;
        }
    }

    public void shoot2()
    {
        bullets.add( new EnemyBullet( x + (width / 2), y + height ) );
        shootCounter2 = rand.nextInt( 50 ) + 50;
    }
}
