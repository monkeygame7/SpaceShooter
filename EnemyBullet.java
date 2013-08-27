import java.util.LinkedList;
import java.awt.Graphics2D;
import java.awt.Color;


public class EnemyBullet extends Bullet
{
    public EnemyBullet( int x, int y )
    {
        this.x = x;
        this.y = y;
        width = 4;
        height = 10;
        speed = 4;
        damage = 5;
        enemy = true;
        c = new Color( 220, 77, 223 );
    }

    public void move()
    {
        y += speed;
    }

    public void collideWith( Health h )
    {
            h.lowerHealth( damage );
            y = 1000;
    }

    @Override
    public void draw( Graphics2D g )
    {
        g.setColor( getColor() );
        g.fillOval( x, y, width, height );
        g.setColor( getColor().brighter() );
        g.drawArc( x, y, width, height, 45, 180 );
        g.setColor( getColor().darker() );
        g.drawArc( x, y, width, height, 225, 360 );    }

    @Override
    public void explode( LinkedList<Explosion> list, ExplosionImages expImages )
    {
        list.add( new ParticleExplosion( x + (width/2), y, expImages, false, c ) );
    }
}
