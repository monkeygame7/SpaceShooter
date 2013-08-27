import java.util.LinkedList;
import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.Color;
import java.util.ArrayList;


public class BossEnemy extends Enemy
{
    private PlayerShip player;
    protected int speed, maxHealth;

    public BossEnemy( int x, int y, int hl, LinkedList<Bullet> b, PlayerShip p, Image[] i )
    {
        super( x, y, b, i );
        width = 200;
        height = 100;
        frequency = 250;
        duration = 50;
        player = p;
        shootCounter = rand.nextInt( frequency ) + 10;
        speed = 3;
        scoreValue = 30;
        health = hl;
        maxHealth = health;
        color = Color.red.darker().darker();
    }

    public void paralyze()
    {
        isParalyzed = true;
        paralyzeCounter = 10;
    }

    public void move()
    {
        if ( isParalyzed )
            paralyzeMove();
        else
        {
            animation = (animation + 1) % duration;
            shootCounter--;
            if ( shootCounter == 0 )
                shoot();
            if ( x < 10 || x + width > 790 )
                speed *= -1;
            x += speed;
        }
    }

    public void shoot()
    {
        int direction = (int) Math.toDegrees( Math.atan2( (player.getX() - x) ,(y  - player.getY()) ) );
        bullets.add( new BossBullet( x + (width / 2) - 5, y + height, direction ) );
        shootCounter = rand.nextInt( frequency ) + 50;
    }

    public void lowerHealth( int k )
    {
        super.lowerHealth( k/2 );
    }

    public void draw( Graphics2D g )
    {
        super.draw( g );
        double percentage = ((double)health/(double)maxHealth);
        g.setColor( Color.red );
        g.fillRect( x, y - 5, width, 2 );
        g.setColor( Color.green );
        g.fillRect( x, y - 5, (int)(percentage * width), 2 );
    }
}