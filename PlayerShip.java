import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.LinkedList;
import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import java.util.ArrayList;
import java.awt.Rectangle;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Image;


public class PlayerShip implements ActionListener, Health
{
    private int x, y, width, height, speed, animation, health, money, gun1, gun2;
    private int numNukes, shield;
    private Image[] images;
    private boolean left, right, up, down, isShooting, canShoot, rapid, doubleShot, firinLazer;
    private LinkedList<Bullet> bullets;
    private Timer shootTimer;
    private boolean[] guns;
    private ArrayList<Enemy> targets;
    private Random rand;
    private BufferedImage homingImage;

    public PlayerShip( int x, int y, int w, int h, LinkedList<Bullet> b, LinkedList<Enemy> e, ArrayList<Enemy> t )
    {
        this.x = x;
        this.y = y;
        width = w;
        height = h;
        speed = 4;
        animation = 0;
        health = 100;
        gun1 = 1;
        gun2 = -1;
        numNukes = 3;
        shield = 0;

        rand = new Random();

        guns = new boolean[9];
        guns[0] = true;
        for( int k = 1; k < guns.length; k++ )
            guns[k] = false;

        homingImage = new BufferedImage( 15, 30, BufferedImage.TYPE_INT_RGB );
        Graphics2D g = homingImage.createGraphics();
        images = new Image[2];
        try
        {
            images[0] = ImageIO.read( getClass().getResource( "images/rocket1.png" ) );
            images[1] = ImageIO.read( getClass().getResource( "images/rocket2.png" ) );
            g.drawImage( ImageIO.read( getClass().getResource( "images/homingmissile.png" ) ), 0, 0, null );
        }
        catch ( IOException ex )
        {
            ex.printStackTrace();
        }
        g.dispose();
        left = false;
        right = false;
        up = false;
        down = false;
        isShooting = false;
        canShoot = true;
        rapid = false;
        doubleShot = false;
        firinLazer = false;

        bullets = b;
        targets = t;

        shootTimer = new Timer( 500, this );
        shootTimer.setInitialDelay( 0 );
    }

    public boolean hasGun2()
    {
        return gun2 != -1;
    }

    public void unlockGun2()
    {
        gun2 = 0;
        doubleShot = true;
    }

    public void setTargets( ArrayList<Enemy> t )
    {
        targets = t;
    }

    public Rectangle getBounds()
    {
        return new Rectangle( x + 20, y, width - 40, height - 25 );
    }

    public int getNumNukes()
    {
        return numNukes;
    }

    public void addNukes( int k )
    {
        numNukes += k;
    }

    public void draw( Graphics2D g )
    {
        g.drawImage( getImage(), x+8, y, null );
        if ( shield > 0 )
        {
            g.setColor( Color.cyan.darker() );
            g.setStroke( new BasicStroke(5) );
            g.drawArc( x, y - 10, width, height/2, 25, 130 );
            g.setStroke( new BasicStroke() );
        }
    }

    public void enableRapidFire()
    {
        shootTimer.setDelay( 150 );
        rapid = true;
    }

    public int shield( int k )
    {
        shield -= k;
        if ( shield < 0 )
        {
            int j = Math.abs( shield );
            shield = 0;
            return j;
        }
        return 0;
    }

    public void addShield()
    {
        shield = Math.min( shield + 50, 100 );
    }

    public int getShield()
    {
        return shield;
    }

    public boolean rapidFire()
    {
        return rapid;
    }

    public boolean doubleShot()
    {
        return doubleShot;
    }

    public boolean isShooting()
    {
        return isShooting;
    }

    public void move()
    {
        animation = (animation + 1) % 50;

        x -= left? speed:0;
        x += right? speed:0;

        y -= up? speed:0;
        y += down? speed:0;

        if ( x < 0 )
            x = 0;
        if ( x + width > 800 )
            x = 800 - width;

        if ( y + height > 600 )
            y = 600 - height;
        if ( y < 400 )
            y = 400;

        if ( isShooting )
            shootGuns();
    }

    public void setShooting( boolean b )
    {
        isShooting = b;
        if ( isShooting )
        {
            shootTimer.start();
        }
        else
        {
            canShoot = true;
            firinLazer = false;
        }
    }

    public void shootGuns()
    {
        if ( canShoot )
        {
            canShoot = false;
            shoot( gun1 );
            if ( gun2 > 0 )
            {
                shoot( gun2 );
            }
            shootTimer.start();
        }
    }

    public void shoot( int gun )
    {
        if ( gun == 1 )
        {
            if ( doubleShot )
            {
                bullets.add( rapid? new RapidBullet( x, y ):new BasicBullet( x, y - 20 ) );
                bullets.add( rapid? new RapidBullet( x + width, y ):new BasicBullet( x + width, y - 20 ) );
            }
            else
                bullets.add( rapid? new RapidBullet( x + (width / 2) - 2, y ):new BasicBullet( x + (width / 2), y - 20 ) );
        }
        else if ( gun == 2 )
        {
            if ( doubleShot )
            {
                bullets.add( new Gun2Bullet( x - ((width-10)/2), y, -1 ) );
                bullets.add( new Gun2Bullet( x + ((width-10)/2), y, 1 ) );
                bullets.add( new Gun2Bullet( x + ((width-10)/2), y, -1 ) );
                bullets.add( new Gun2Bullet( x + (width-10) + ((width-10)/2), y, 1 ) );
            }
            else
            {
                bullets.add( new Gun2Bullet( x, y, -1 ) );
                bullets.add( new Gun2Bullet( x + width - 10, y, 1 ) );
            }
        }
        else if ( gun == 3 )
        {
            if ( doubleShot )
            {
                for ( int k = 0; k < 10; k++ )
                {
                    bullets.add( new BeeBullet(x, y ) );
                    bullets.add( new BeeBullet(x + width, y ) );
                }
            }
            else
                for ( int k = 0; k < 10; k++ )
                {
                    bullets.add( new BeeBullet( x + (width/2) - 5, y - 8) );
                }
        }
        else if ( gun == 4 )
        {
            if ( doubleShot )
            {
                bullets.add( new TripleGunBullet( x - 5, y, -1, 10 ) );
                bullets.add( new TripleGunBullet( x - 5, y, 1, 15 ) );
                bullets.add( new TripleGunBullet( x - 5, y, 0, 10 ) );
                bullets.add( new TripleGunBullet( x + width - 8, y, -1, 10 ) );
                bullets.add( new TripleGunBullet( x + width - 8, y, 1, 16 ) );
                bullets.add( new TripleGunBullet( x + width - 8, y, 0, 10 ) );
            }
            else
            {
                bullets.add( new TripleGunBullet( x + (width / 2) - 8, y, -1, 10 ) );
                bullets.add( new TripleGunBullet( x + (width / 2) - 8, y, 1, 16 ) );
                bullets.add( new TripleGunBullet( x + (width / 2) - 8, y, 0, 10 ) );
            }
        }
        else if ( gun == 5 )
        {
            if ( !firinLazer )
            {
                if ( doubleShot )
                {
                    bullets.add( new Laser( x, y, -(width/2) + 5, this ) );
                    bullets.add( new Laser( x, y, (width/2) + 5, this ) );
                }
                else
                    bullets.add( new Laser( x, y, 0, this) );
                firinLazer = true;
            }
        }
        else if ( gun == 6 )
        {
            if ( doubleShot )
            {
                for ( int k = -2; k < 3; k++ )
                {
                    bullets.add( new SprayBullet( x - 5, y, k ) );
                }
                for ( int k = -2; k < 3; k++ )
                {
                    bullets.add( new SprayBullet( x + width, y, k ) );
                }
            }
            else
                for ( int k = -2; k < 3; k++ )
                {
                    bullets.add( new SprayBullet( x + (width / 2 ), y, k ) );
                }
        }
        else if ( gun == 7 )
        {
            if ( doubleShot )
            {
                for ( int k = -2; k < 3; k++ )
                {
                    bullets.add( new CircleBullet( x - 5, y, k ) );
                }
                for ( int k = -2; k < 3; k++ )
                {
                    bullets.add( new CircleBullet( x + width, y, k ) );
                }
            }
            else
                for ( int k = -2; k < 3; k++ )
                {
                    bullets.add( new CircleBullet( x + (width / 2 )-5, y, k ) );
                }
        }
        else if ( gun == 8 )
        {
            if ( doubleShot )
            {
                bullets.add( new StunBullet( x, y ) );
                bullets.add( new StunBullet( x + width, y ) );
            }
            else
                bullets.add( new StunBullet( x + (width/2), y ) );
            shootTimer.start();
        }
        else if ( gun == 9 )
        {
            if ( doubleShot )
            {
                bullets.add( new HomingMissile( x, y, targets.size()==0? null:targets.get( rand.nextInt( targets.size() ) ), homingImage ) );
                bullets.add( new HomingMissile( x + width, y, targets.size()==0? null:targets.get( rand.nextInt( targets.size() ) ), homingImage ) );
            }
            else
                bullets.add( new HomingMissile( x + (width/2), y, targets.size()==0? null:targets.get( rand.nextInt( targets.size() ) ), homingImage ) );
        }
    }

    public void fireNuke()
    {
        if ( numNukes > 0 )
        {
            bullets.add( new Nuke( x + (width/2) - 5, y - 30 ) );
            numNukes--;
        }
    }

    public void unlockGun( int n )
    {
        guns[n - 1] = true;
        switchGun( n );
    }

    public void switchGun( int n )
    {
        if ( n == 5 )
            canShoot = true;
        if ( !guns[ n - 1 ] )
            return;
        else if ( gun2 == -1 )
        {
            gun1 = n;
        }
        else
        {
            if ( gun2 != 0 )
            {
                gun1 = gun2;
            }
            if ( n == gun2 )
            {
                gun2 = 0;
                doubleShot = true;

            }
            else
            {
                gun2 = n;
                doubleShot = false;
            }
        }

    }

    public boolean hasGun( int n )
    {
        return guns[n - 1];
    }

    public int getHealth()
    {
        return health;
    }

    public void increaseHealth( int k )
    {
        health = Math.min( health + k, 100 );
    }

    public void lowerHealth( int k )
    {
        health = Math.max( health - shield(k), 0 );
    }

    public Image getImage()
    {
        if ( animation < 25 )
            return images[0];
        else
        {
            return images[1];
        }

    }

    public void setLeft( boolean b )
    {
        left = b;
    }

    public void setRight( boolean b )
    {
        right = b;
    }

    public void setUp( boolean b )
    {
        up = b;
    }

    public void setDown( boolean b )
    {
        down = b;
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public int getMoney()
    {
        return money;
    }

    public int gun1()
    {
        return gun1;
    }

    public int gun2()
    {
        return gun2;
    }

    public void addMoney( int k )
    {
        money = Math.min( money + k, 999999 );
    }

    public void takeMoney( int k )
    {
        money -= k;
    }

    public void actionPerformed( ActionEvent e )
    {
        //if ( gun1 != 5 && gun2 != 5 )
        canShoot = true;
    }
}
