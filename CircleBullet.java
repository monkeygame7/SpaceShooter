import java.awt.Color;


public class CircleBullet extends SprayBullet
{
    private int distance;
    private boolean goUp;

    public CircleBullet( int x, int y, int d )
    {
        super( x, y, d );
        c = Color.pink;
        width = 10;
        height = 10;
        speed = 9;
        damage = 8;
        radius = Math.abs( direction )==2? 100:15;
        if ( direction ==0 )
            radius = 60;
        center.setLocation( x, y - radius );
        distance = (int)( Math.random() * 360 ) + 90;
        movement = (90)/speed;
        goUp = false;
    }

    public void move()
    {
        movement += (direction<0? -1:1);

        if ( movement > ((distance+360)/speed) || movement < ((distance-360)/speed) )
            goUp = true;
        if ( goUp )
            y -= speed*2;
        else
        {
            x = (int)( center.x + 1.5*( radius * Trig.cos( movement * (speed) ) ) );
            y = (int)( center.y + 0.5*( radius * Trig.sin( movement * (speed) ) ) );
        }
    }
}
