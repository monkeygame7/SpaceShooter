import java.util.Random;
import java.awt.Graphics2D;
import java.awt.Color;


public class Star
{
    int x, y, speed;
    Color color;
    Random rand;

    public Star( int x, int y, int s, Random r )
    {
        this.x = x;
        this.y = y;
        rand = r;
        speed = s;
        int i = rand.nextInt( 100 );
        if ( i < 5 )
            color = Color.red.darker();
        else if ( i < 20 )
            color = Color.orange.darker();
        else if ( i < 50 )
            color = Color.yellow.darker();
        else
            color = Color.white.darker();

    }

    public void move( boolean b )
    {
        y += b? 20:speed;
        if ( y > 600 )
        {
            y = 0 - rand.nextInt( 20 );
            x = rand.nextInt( 800 );

            int i = rand.nextInt( 100 );
            if ( i < 5 )
                color = Color.red.darker();
            else if ( i < 20 )
                color = Color.orange.darker();
            else if ( i < 50 )
                color = Color.yellow.darker();
            else
                color = Color.white.darker();
        }
    }

    public void draw( Graphics2D g, boolean b, boolean p )
    {
        g.setColor( color );

        if ( b )
            g.drawLine( x, y, x, y - (p? 35:(rand.nextInt(50) + 5) ) );
        else
        {
            g.drawLine( x, y, x, y - (speed==1? 2:4) );
            if ( speed == 2 )
                g.drawLine( x + 1, y, x + 1, y - (speed==1? 2:4) );
        }
    }
}
