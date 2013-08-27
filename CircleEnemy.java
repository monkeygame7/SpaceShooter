import java.util.LinkedList;
import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Color;
import java.util.ArrayList;


public class CircleEnemy extends Enemy
{
    private int speed, radius;
    private int moveCount;
    private Point center;
    private boolean startSpinning;
    private boolean left;

    public CircleEnemy( int x, int y, int r, LinkedList<Bullet> b, boolean l, Image[] i )
    {
        this( x, y, r, b, l, l? 160:590, i );
    }

    public CircleEnemy( int x, int y, int r, LinkedList<Bullet> b, boolean l , int cX, Image[] i )
    {
        super( x, y, b, i );
        width = 70;
        height = 70;
        left = l;
        health = 75;
        speed = 2;
        radius = r;
        frequency = 200;
        duration = 80;
        scoreValue = 10;
        color = Color.magenta.darker().darker();
        moveCount = left? -45:45;
        startSpinning = false;
        center = new Point( cX, radius + y );
    }

    public void move()
    {
        if ( isParalyzed )
            paralyzeMove();
        else
        {
            animation = (animation + 1) % 80;
            shootCounter--;
            if ( shootCounter == 0 )
                shoot();

            if ( left? (x < center.getX()):(x > center.getX()) )
                x = x + (left? (speed * 2):(-speed * 2));
            else
            {
                startSpinning = true;
            }

            if ( startSpinning )
            {
                x = (int)( center.x + ( radius * Trig.cos( moveCount * (speed) ) ) );
                y = (int)( center.y + ( radius * (left? 1:-1) * Trig.sin( moveCount * (speed) ) ) );
                moveCount = (moveCount + 1) % 360;
            }
        }
    }

    public void draw( Graphics2D g )
    {
        if ( image != null )
        {
            g.drawImage( image[animation/16], x, y, null );
        }
        else
            super.draw( g );
    }
}
