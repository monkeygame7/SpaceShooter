import java.awt.Point;
import java.awt.Color;
import java.awt.Graphics2D;


public class ParticleExplosion extends Explosion
{
    private Particle[] particles;
    private Color color;

    public ParticleExplosion( int x, int y, ExplosionImages i, boolean goingUp, Color c )
    {
        this.x = x;
        this.y = y + (goingUp? -5:5);
        animation = 0;
        length = 5;
        particles = new Particle[10];
        color = c;
        for( int k = 0; k < particles.length; k++ )
        {
            int d = (int)( Math.random() * 45 );
            d += (k%2 == 0)? 15:135;
            if ( !goingUp )
                d += 180;
            particles[k] = new Particle( x, y, d );
        }
    }

    public void step()
    {
        animation++;
        for ( int k = 0; k < particles.length; k++ )
            particles[k].step();
    }

    public void draw( Graphics2D g )
    {
        g.setColor( color );
        for ( int k = 0; k < particles.length; k++ )
        {
            particles[k].draw( g );
        }
    }

}
