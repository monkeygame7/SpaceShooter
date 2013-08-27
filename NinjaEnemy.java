import java.awt.Rectangle;
import java.util.LinkedList;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;


public class NinjaEnemy extends Enemy
{
    private boolean appearing, disappearing;
    private int appearCounter, moveDelay;
    private PlayerShip player;

    public NinjaEnemy( int x, int y, LinkedList<Bullet> b, Image[] i, PlayerShip p )
    {
        super( x, y, b, i );
        appearing = true;
        disappearing = false;
        appearCounter = 50;
        width = 50;
        height = 75;
        scoreValue = 30;
        player = p;
        moveDelay = rand.nextInt( 100 ) + 10;
        health = 150;
    }

    public void move()
    {
        if ( isParalyzed )
            paralyzeMove();
        else
        {
            if ( disappearing )
            {
                appearCounter++;
                if ( appearCounter == 10 )
                {
                    x = rand.nextInt( 800 - width );
                    y = rand.nextInt( 400 - height );
                    disappearing = false;
                    appearing = true;
                }
            }
            else if ( appearing )
            {
                appearCounter--;
                if ( appearCounter == 0 )
                {
                    appearing = false;
                }
            }
            else
            {
                moveDelay--;
                if ( moveDelay == 0 )
                {
                    disappearing = true;
                    if ( rand.nextInt( 10 ) < 8 )
                    {
                        int direction = (int) Math.toDegrees( Math.atan2( (player.getX() - x) + (width/2),(y  - player.getY())  + (height/2) ) );
                        bullets.add( new NinjaStar( x + (width / 2), y + height, direction ) );
                    }
                    moveDelay = rand.nextInt( 100 ) + 10;
                }
            }
        }
    }

    public void draw( Graphics2D g )
    {
        if ( appearCounter == 0 )
            g.drawImage( image[0], x, y, null );
        else if ( appearCounter < 5 )
            g.drawImage( image[1], x, y, null );
        else if ( appearCounter < 8)
            g.drawImage( image[2], x, y, null );
    }

    public Rectangle getBounds()
    {
        return new Rectangle( x, y, appearCounter>0? 0:width, appearCounter>0? 0:height );
    }
}
