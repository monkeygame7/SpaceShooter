import java.util.LinkedList;
import java.util.Random;
import java.awt.Rectangle;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;


public class BasicBullet extends Bullet
{

    public BasicBullet( int x, int y )
    {
        this.x = x;
        this.y = y;
        width = 5;
        height = 10;
        speed = 6;
        damage = 8;
        enemy = false;
        scoreValue = 1;
        c = Color.orange;
    }

    public void move()
    {
        y -= speed;
    }

    public void collideWith( Health h )
    {
            h.lowerHealth( damage );
            y = -100;
    }

    public void draw( Graphics2D g )
    {
        g.setColor( getColor() );
        g.fillOval( x, y, width, height );
        g.setColor( getColor().brighter() );
        g.drawArc( x, y, width, height, 45, 180 );
        g.setColor( getColor().darker() );
        g.drawArc( x, y, width, height, 225, 360 );
    }

    @Override
    public void explode( LinkedList<Explosion> list, ExplosionImages expImages )
    {
        list.add( new ParticleExplosion( x + (width/2), y, expImages, true, c ) );
    }
}
