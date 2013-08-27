import java.util.LinkedList;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Color;
import java.util.ArrayList;


public class ZigZagEnemy extends Enemy
{
    private int speed, minY;
    private boolean right, down;
    private PlayerShip player;

    public ZigZagEnemy( int x, int y, LinkedList<Bullet> b, PlayerShip p, Image[] i )
    {
        this( x, y, 0, b, p, i );
    }

    public ZigZagEnemy( int x, int y, int m, LinkedList<Bullet> b, PlayerShip p, Image[] i )
    {
        super( x, y, b, i );
        minY = m;
        width = 100;
        height = 50;
        this.y = Math.max( y, minY );
        health = 80;
        speed = 3;
        scoreValue = 13;
        int k = (int)( Math.random() * 2 );
        right = k==0? true:false;
        k = (int)( Math.random() * 2 );
        down = k==0? true:false;
        player = p;
        animation = rand.nextInt( 40 );
        frequency = 250;
        color = Color.orange.darker().darker();
    }

    public void move()
    {
        if ( isParalyzed )
            paralyzeMove();
        else
        {
            animation = (animation + 1) % 40;

            shootCounter--;
            if ( shootCounter == 0 )
                shoot();
            if ( x + width > 800 || x < 0 )
                right = !right;
            if ( y < minY || y + height > 400 )
                down = !down;

            x += right? speed:-speed;
            y += down? speed:-speed;
        }
    }

    public void shoot()
    {
        int direction = (int) Math.toDegrees( Math.atan2( (player.getX() - x) ,(y  - player.getY()) ) );
        bullets.add( new DirectionalBullet( x + (width / 2), y + height, direction ) );
        shootCounter = rand.nextInt( frequency ) + 50;
    }

    public void draw( Graphics2D g )
    {
        g.drawImage( image[animation/10], x, y, null );
    }
}
