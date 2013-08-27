import java.util.LinkedList;
import java.awt.Image;
import java.awt.Color;
import java.util.ArrayList;


public class HomingCircleEnemy extends CircleEnemy
{
    private PlayerShip player;

    public HomingCircleEnemy( int x, int y, int r, LinkedList<Bullet> b, PlayerShip p, boolean l, Image[] i )
    {
        this( x, y, r, b, p, l, l? 160:590, i );
    }

    public HomingCircleEnemy( int x, int y, int r, LinkedList<Bullet> b, PlayerShip p, boolean l, int cX, Image[] i )
    {
        super( x, y, r, b, l, cX, i );
        health = 150;
        frequency = 150;
        scoreValue = 25;
        player = p;
        color = Color.magenta;
    }

    public void shoot()
    {
        if ( x > 0 && x < 800 )
        {
            int direction = (int) Math.toDegrees( Math.atan2( (player.getX() - x) ,(y  - player.getY()) ) );
            bullets.add( new DirectionalBullet( x + (width / 2), y + height, direction ) );
            shootCounter = rand.nextInt( frequency ) + 50;
        }
    }
}
