import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.util.LinkedList;
import java.util.Iterator;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;
import java.awt.Font;
import java.awt.image.RescaleOp;
import java.util.ArrayList;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public class Board extends JPanel implements KeyListener, MouseListener
{
    private BufferedImage image;
    private PlayerShip player;
    private LinkedList<Bullet> bullets;
    private boolean startScreen, paused, showStore, gameOver, betweenWaves,
    wasShooting, difficultySelect;
    private int zoom, continues, moneyValue, difficulty;
    private double score;
    private LinkedList<Enemy> enemies;
    private LinkedList<Money> money;
    private LinkedList<HealthPack> healthPacks;
    private LinkedList<Explosion> explosions;
    private Random rand;
    private Wave wave;
    private Star[] stars;
    private ArrayList<Enemy> targetEnemies;
    private ExplosionImages expImages;
    private RescaleOp darkenOp;
    private BufferedImageOp blurOp;

    public static final Color BGCOLOR = new Color( 15, 16, 28 );

    public Board()
    {
        this.setIgnoreRepaint( true );
        this.setSize( 800, 600 );
        this.setFocusable( true );
        this.addKeyListener( this );
        this.addMouseListener( this );
        difficulty = 1;
    }

    public void initialize( boolean showStart )
    {
        float[] blurKernel = { 1 / 9f, 1 / 9f, 1 / 9f, 1 / 9f, 1 / 9f, 1 / 9f, 1 / 9f, 1 / 9f, 1 / 9f };
        blurOp = new ConvolveOp(new Kernel(3, 3, blurKernel));
        float scaleFactor = .8f;
        darkenOp = new RescaleOp(scaleFactor, 0, null);

        bullets = new LinkedList<Bullet>();
        image = new BufferedImage( 800, 600, BufferedImage.TYPE_INT_RGB );
        enemies = new LinkedList<Enemy>();
        targetEnemies = new ArrayList<Enemy>();
        player = new PlayerShip( 400, 400, 85, 100, bullets, enemies, targetEnemies );
        money = new LinkedList<Money>();
        healthPacks = new LinkedList<HealthPack>();
        targetEnemies = new ArrayList<Enemy>();
        explosions = new LinkedList<Explosion>();
        expImages = new ExplosionImages();
        rand = new Random();
        startScreen = showStart;
        paused = false;
        showStore = true;
        gameOver = false;
        betweenWaves = false;
        wasShooting = false;
        difficultySelect = false;
        zoom = 0;
        score = 0;
        continues = 0;
        setDifficulty( difficulty );
        wave = new Wave( bullets, player, enemies );
        stars = new Star[100];
        for ( int k = 0; k < stars.length; k++ )
        {
            stars[k] = new Star( rand.nextInt( 800 ), rand.nextInt(600), (k%2) + 1, rand );
        }
        //player.unlockGun2();
        //player.unlockGun( 5 );
        //player.unlockGun( 9 );
        //player.enableRapidFire();
        //player.enableDoubleShot();
        player.addMoney( 999999 );
        //player.lowerHealth( 40 );
    }

    public void setDifficulty( int d )
    {
        difficulty = d;
        if ( difficulty == 1 )
            moneyValue = 150;
        else if ( difficulty == 2 )
            moneyValue = 100;
        else if ( difficulty == 3 )
            moneyValue = 50;
        else if ( difficulty == 4 )
            moneyValue = 25;
    }

    public void update()
    {
        if ( continues < (int)score / 5000 )
        {
            continues++;
        }
        if ( paused || showStore || gameOver || startScreen )
            return;
        else if ( player.getHealth() == 0 )
        {
            gameOver = true;
        }
        else if ( enemies.size()  == 0 )
        {
            betweenWaves = true;
            zoom = 0;

            player.setShooting( false );

            wave.nextWave();
            if ( enemies.size() == 0 )
            {
                showStore = true;
                bullets.clear();
            }
        }
        else
        {
            player.move();
            Iterator<Enemy> enemyItr;

            Iterator<Bullet> itr = bullets.iterator();

            while ( itr.hasNext() )
            {
                Bullet temp = itr.next();
                temp.move();
                if ( betweenWaves )
                    temp.move();
                if ( !betweenWaves )
                {
                    if ( temp.enemy() )
                    {
                        if ( temp.getBounds().intersects( player.getBounds() ) )
                        {
                            temp.explode( explosions, expImages );
                            temp.collideWith( player );
                            score -= (100/difficulty);
                        }
                    }
                    else
                    {
                        enemyItr = enemies.iterator();
                        while ( enemyItr.hasNext() )
                        {
                            Enemy tempE = enemyItr.next();
                            if ( tempE.canTarget() )
                                if ( temp.getBounds().intersects( tempE.getBounds() ) )
                                {
                                    temp.explode( explosions, expImages );
                                    temp.collideWith( tempE );
                                    score += temp.getValue();
                                }
                        }
                    }
                }
            }

            Iterator<Money> moneyItr = money.iterator();

            while ( moneyItr.hasNext() )
            {
                Money temp = moneyItr.next();
                if ( temp.getY() > 600 )
                {
                    moneyItr.remove();
                }
                else if ( temp.getBounds().intersects( player.getBounds() ) )
                {
                    player.addMoney( temp.getValue() );
                    moneyItr.remove();
                    score += 10;
                }
                else
                    temp.move();
            }

            Iterator<HealthPack> healthItr = healthPacks.iterator();

            while ( healthItr.hasNext() )
            {
                HealthPack temp = healthItr.next();
                if ( temp.getY() > 600 )
                {
                    healthItr.remove();
                }
                else if ( temp.getBounds().intersects( player.getBounds() ) )
                {
                    player.increaseHealth( temp.getValue() );
                    healthItr.remove();
                    score += 5;
                }
                else
                    temp.move();
            }
            if ( !betweenWaves )
            {
                enemyItr = enemies.iterator();
                targetEnemies.clear();
                while ( enemyItr.hasNext() )
                {
                    Enemy temp = enemyItr.next();
                    if ( temp.isDead() )
                    {
                        if ( rand.nextInt( 5 - (difficulty/2) ) == 1 )
                            money.add( new Money( temp.getX(), temp.getY(), 40, 75, moneyValue ) );
                        else if ( rand.nextInt( 7 - difficulty ) == 1 )
                            healthPacks.add( new HealthPack( temp.getX(), temp.getY(), 20, 20, 5 ) );
                        explosions.add( new NormalExplosion( temp.getX() + (temp.getWidth()/2), temp.getY() + (temp.getHeight()/2), expImages ) );
                        score += temp.getValue() * difficulty;
                        enemyItr.remove();
                    }
                    else
                    {
                        temp.move();
                        targetEnemies.add( temp );
                    }
                }
                player.setTargets( targetEnemies );
            }

            Iterator<Explosion> explItr = explosions.iterator();
            while ( explItr.hasNext() )
            {
                Explosion temp = explItr.next();
                temp.step();
                if ( temp.isDone() )
                {
                    explItr.remove();
                }
            }
        }
        score = Math.max( 0, score );
    }

    public void drawStartScreen()
    {
        Graphics2D g = image.createGraphics();
        g.setColor( BGCOLOR );
        g.fillRect( 0, 0, 800, 600 );

        drawStars( g );

        if ( difficultySelect )
        {
            int mX = 0;
            int mY = 0;
            g.setColor( Color.red );
            g.setFont( new Font( "Comic Sans MS", Font.BOLD, 50 ) );
            g.drawString( "Select Difficulty", 210, 150 );
            g.setColor( Color.white );
            g.setFont( new Font( "Comic Sans MS", Font.PLAIN, 35 ) );
            if ( getMousePosition() != null )
            {
                mX = (int)getMousePosition().getX();
                mY = (int)getMousePosition().getY();
            }
            g.setColor( (mX>350 && mX<475 && mY<250 && mY > 220)? Color.darkGray:Color.white );
            g.drawString( "Easy", 375, 250 );
            g.setColor( (mX>350 && mX<475 && mY<300 && mY > 270)? Color.darkGray:Color.white );
            g.drawString( "Medium", 350, 300 );
            g.setColor( (mX>350 && mX<475 && mY<350 && mY > 320)? Color.darkGray:Color.white );
            g.drawString( "Hard", 370, 350 );
            g.setColor( (mX>350 && mX<475 && mY<400 && mY > 370)? Color.darkGray:Color.white );
            g.drawString( "Insane", 360, 400 );
        }
        else
        {
            g.setColor( Color.red );
            g.setFont( new Font( "Chiller", Font.BOLD, 100 ) );
            g.drawString( "Space Shooter", 170, 150 );
            g.setFont( new Font( "Gigi", Font.PLAIN, 20) );
            g.setColor( Color.white );
            g.drawString( "Created by Shayan Motevalli", 300, 190 );
            g.setFont( new Font( "Comic Sans MS", Font.PLAIN, 35 ) );
            g.drawString( "Click Anywhere to Start", 220, 500 );
        }

        g.dispose();
    }

    public void drawBuffer()
    {
        Graphics2D g = image.createGraphics();
        g.setColor( BGCOLOR );
        g.fillRect( 0, 0, 800, 600 );

        drawStars( g );

        player.draw( g );

        if ( !betweenWaves )
        {
            Iterator<Enemy> enemyItr = enemies.iterator();
            while ( enemyItr.hasNext() )
            {
                enemyItr.next().draw( g );
            }
        }

        drawBullets( g );

        Iterator<Explosion> explItr = explosions.iterator();
        while ( explItr.hasNext() )
        {
            explItr.next().draw( g );
        }

        g.setColor( Color.yellow );
        g.setFont( new Font( "Comic Sans MS", Font.PLAIN, 72) );

        Iterator<Money> moneyItr = money.iterator();
        while ( moneyItr.hasNext() )
        {
            Money m = moneyItr.next();
            g.drawString( "$", m.getX(), m.getY() + m.getHeight() - 15 );
        }

        Iterator<HealthPack> healthItr = healthPacks.iterator();
        while ( healthItr.hasNext() )
        {
            HealthPack h = healthItr.next();
            g.setColor( Color.white );
            g.fill( h.getBounds() );
            g.setColor( Color.red );
            g.fillRect( h.getX() + (h.getWidth() / 2) - 2, h.getY(), 5, h.getHeight() );
            g.fillRect( h.getX(), h.getY() + (h.getHeight() / 2) - 1, h.getWidth(), 5 );
            g.setColor( Color.black );
            g.draw( h.getBounds() );
        }
        drawHealth( g );

        g.setColor( Color.white );
        g.setFont( new Font( "Comic Sans MS", Font.PLAIN, 20 ) );
        g.drawString( ""+(int)score, 10, 25 );
        g.drawString( "Level " + wave.getLevel(), 725, 25 );
        g.drawString( continues + " continues", 10, 595 );
        g.drawString( "Nukes: " + player.getNumNukes(), 680, 595 );
        g.setFont( new Font( "Comic Sans MS", Font.PLAIN, 16 ) );
        g.drawString( "$" + player.getMoney(), 720, 40 );

        if ( paused )
        {
            image = blurOp.filter(image, null);
            image = darkenOp.filter(image, null);
            g.dispose();
            g = image.createGraphics();
            g.setColor(Color.white);
            g.setFont( new Font( "Comic Sans MS", Font.BOLD, 72 ) );
            g.drawString("PAUSED", 250, 300);
        }
        g.dispose();
    }

    public void drawStore()
    {
        Graphics2D g = image.createGraphics();
        int leftAlign = 9;
        int midAlign = 420;
        g.setColor( Color.black );
        g.fillRect( 0, 0, 800, 600 );
        g.setColor( Color.lightGray );
        g.fillRect( 411, 75, 5, 520 );
        g.setFont( new Font( "Arial", Font.BOLD, 50 ) );
        g.drawString( "Store", 350, 40 );
        g.setFont( new Font( "Arial", Font.BOLD, 30 ) );
        g.drawString( "$" + player.getMoney(), 10, 30 );
        g.drawString( "Back", 700, 30 );
        g.setFont( new Font( "Arial", Font.BOLD, 36 ) );
        g.setColor( player.getHealth()>=100? Color.darkGray:Color.white );
        g.drawString( "Health  -  $100", leftAlign, 100 );
        g.setColor( player.getShield()>=100? Color.darkGray:Color.white );
        g.drawString( "Shield  -  $400", midAlign, 100 );
        g.setColor( player.hasGun( 2 )? Color.darkGray:Color.white );
        g.drawString( "Double Shot  -  $300", leftAlign, 200 );
        g.setColor( player.rapidFire()? Color.darkGray:Color.white );
        g.drawString( "Rapid Fire  -  $450", midAlign, 200 );
        g.setColor( player.hasGun( 3 )? Color.darkGray:Color.white );
        g.drawString( "Bee-Bee Gun  -  $1000", leftAlign, 300 );
        g.setColor( player.hasGun( 4 )? Color.darkGray:Color.white );
        g.drawString( "Tri-Shot  -  $1450", midAlign, 300 );
        g.setColor( player.hasGun( 8 )? Color.darkGray:Color.white );
        g.drawString( "Stun Gun  -  $1600", leftAlign, 350 );
        g.setColor( player.hasGun( 5 )? Color.darkGray:Color.white );
        g.drawString( "BLARGH!  -  $1800", leftAlign, 400 );
        g.setColor( player.hasGun( 6 )? Color.darkGray:Color.white );
        g.drawString( "Hanuk-Gun  -  $2000", midAlign, 400 );
        g.setColor( player.hasGun( 7 )? Color.darkGray:Color.white );
        g.drawString( "Vortex  -  $2100", midAlign, 500 );
        g.setColor( player.hasGun2()? Color.darkGray:Color.white );
        g.drawString( "Dual Wield  -  $4000", leftAlign, 500 );
        g.setColor( player.hasGun( 9 )? Color.darkGray:Color.white );
        g.drawString( "Homing  -  $3000", midAlign, 545 );
        g.setColor( Color.white );
        g.drawString( "Nuke x1  -  $350", leftAlign, 590 );
        g.drawString( "Nuke x3  -  $800", midAlign, 590 );
        drawHealth( g );
        g.dispose();
    }

    public void drawGameOver()
    {
        Graphics2D g = image.createGraphics();
        int mX = 0;
        int mY = 0;
        if ( getMousePosition() != null )
        {
            mX = (int)getMousePosition().getX();
            mY = (int)getMousePosition().getY();
        }
        g.setColor( Color.black );
        g.fillRect( 0, 0, 800, 600 );
        g.setFont( new Font( "Bradley Hand ITC", Font.BOLD, 72) );
        g.setColor( Color.red.darker().darker() );
        g.drawString( "GAME OVER", 180, 125 );
        g.setColor( Color.white );
        g.setFont( new Font( "Bradley Hand ITC", Font.BOLD, 60) );
        g.drawString( "Play Again?", 225, 275 );
        g.setFont( new Font( "Bradley Hand ITC", Font.BOLD, 45) );
        g.setColor( (mX>300 && mX<370 && mY<360 && mY > 320)? Color.darkGray:Color.white );
        g.drawString( "Yes", 300, 350 );
        g.setColor( Color.white );
        g.drawString( "/", 375, 350 );
        g.setColor( (mX>395 && mX<450 && mY<360 && mY > 320)? Color.darkGray:Color.white );
        g.drawString( "No", 395, 350 );
        g.setColor( Color.white );
        g.drawString( continues + " Continues", 10, 595 );
        g.dispose();
    }

    public void drawBullets( Graphics2D g )
    {
        Iterator<Bullet> itr = bullets.iterator();
        while ( itr.hasNext() )
        {
            Bullet b = itr.next();
            if ( b.getY() + b.getHeight() < 0 || b.getY() > 600 || b.getX() > 800
                || b.getX() + b.getWidth() < 0 )
            {
                itr.remove();
            }
            else
            {
                b.draw( g );
            }
        }
    }

    public void drawStars( Graphics2D g )
    {
        if ( zoom > 50 )
        {
            betweenWaves = false;
            player.setShooting( wasShooting );
        }
        else if ( !paused )
            zoom++;
        for ( int k = 0; k < stars.length; k++ )
        {
            Star s = stars[k];
            if ( !paused )
                s.move( betweenWaves );
            s.draw( g, betweenWaves, paused );
        }
    }

    public void drawHealth( Graphics2D g )
    {
        g.setColor( Color.red );
        g.fillRect( 0, 0, 5, (100 - player.getHealth()) * 6 );
        g.setColor( Color.green );
        g.fillRect( 0, (100 - player.getHealth()) * 6, 5, player.getHealth() * 6 );
        g.setColor( Color.cyan );
        g.fillRect( 5, (100 - player.getShield()) * 6, 5, player.getShield() * 6 );
    }

    public void drawScreen()
    {
        Graphics2D g = (Graphics2D)this.getGraphics();
        g.drawImage( image, null, 0, 0 );
        g.dispose();
    }

    public void startGame()
    {
        initialize( true );

        while ( true )
        {
            try
            {
                update();
                if ( startScreen )
                    drawStartScreen();
                else if ( gameOver )
                    drawGameOver();
                else if ( showStore )
                    drawStore();
                else
                    drawBuffer();
                drawScreen();
                Thread.sleep( (11-difficulty) );
            }
            catch ( Exception e )
            {
                e.printStackTrace();
            }
        }
    }

    public void keyPressed( KeyEvent e )
    {
        if ( gameOver || showStore || startScreen )
            return;
        int key = e.getKeyCode();

        if ( key == KeyEvent.VK_K )
        {
            difficulty = ( (difficulty + 2) % 4 ) + 1;
            setDifficulty( difficulty );
        }

        if ( key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A )
            player.setLeft( true );
        if ( key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D )
            player.setRight( true );
        if ( key == KeyEvent.VK_UP || key == KeyEvent.VK_W )
            player.setUp( true );
        if ( key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S )
            player.setDown( true );

        if ( key == KeyEvent.VK_SPACE )
        {
            if ( !betweenWaves )
                player.setShooting( true );
            wasShooting = true;
        }

        if ( key == KeyEvent.VK_SHIFT && !betweenWaves && !paused )
            player.fireNuke();

        if ( key == KeyEvent.VK_ENTER )
            paused = !paused;

        if ( key == KeyEvent.VK_1 )
            player.switchGun( 1 );
        if ( key == KeyEvent.VK_2 )
            player.switchGun( 2 );
        if ( key == KeyEvent.VK_3 )
            player.switchGun( 3 );
        if ( key == KeyEvent.VK_4 )
            player.switchGun( 4 );
        if ( key == KeyEvent.VK_5 )
            player.switchGun( 5 );
        if ( key == KeyEvent.VK_6 )
            player.switchGun( 6 );
        if ( key == KeyEvent.VK_7 )
            player.switchGun( 7 );
        if ( key == KeyEvent.VK_8 )
            player.switchGun( 8 );
        if ( key == KeyEvent.VK_9 )
            player.switchGun( 9 );
    }

    public void keyReleased( KeyEvent e )
    {
        int key = e.getKeyCode();

        if ( key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A )
            player.setLeft( false );
        if ( key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D )
            player.setRight( false );
        if ( key == KeyEvent.VK_UP || key == KeyEvent.VK_W )
            player.setUp( false );
        if ( key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S )
            player.setDown( false );

        if ( key == KeyEvent.VK_SPACE )
        {
            player.setShooting( false );
            wasShooting = false;
        }
    }

    public void keyTyped( KeyEvent e ){}

    public void mouseClicked( MouseEvent e )
    {
        if ( !gameOver && !showStore && !startScreen )
            return;
        if ( startScreen )
        {
            if ( difficultySelect )
            {
                if ( e.getX() > 350 && e.getX() < 475 && e.getY() > 220 && e.getY() < 400 )
                {
                    if ( e.getY() < 250 )
                    {
                        setDifficulty( 1 );
                    }
                    else if ( e.getY() < 300 )
                    {
                        setDifficulty( 2 );
                    }
                    else if ( e.getY() < 350 )
                    {
                        setDifficulty( 3 );
                    }
                    else
                    {
                        setDifficulty( 4 );
                    }
                    startScreen = false;
                    difficultySelect = false;
                    betweenWaves = true;
                }
            }
            else
            {
                difficultySelect = true;
            }
        }
        else if ( gameOver )
        {
            if ((e.getX()>300 && e.getX()<370 && e.getY()<360 && e.getY() > 320))
            {
                if ( continues > 0 )
                {
                    continues--;
                    gameOver = false;
                    player.increaseHealth( 100 );
                    score -= 5500;
                    bullets.clear();
                    showStore = true;
                    wave.restartLevel();
                }
                else
                {
                    initialize( false );
                }
            }
            else if ( e.getX()>395 && e.getX()<450 && e.getY()<360 && e.getY() > 320 )
                initialize( true );
        }
        else if ( showStore )
        {
            if ( e.getX() > 700 && e.getX() < 800 && e.getY() < 30 )
                showStore = false;
            else if ( e.getX() > 420 ) //right side
            {
                if ( e.getY() > 70 && e.getY() < 100 && e.getX() < 660 )
                {
                    if ( player.getMoney() >= 400 && player.getShield() < 100 )
                    {
                        player.addShield();
                        player.takeMoney( 400 );
                    }
                }
                else if ( e.getY() > 170 && e.getY() < 200 && e.getX() < 730 )
                {
                    if ( player.getMoney() >= 450 && !player.rapidFire() )
                    {
                        player.enableRapidFire();
                        player.takeMoney( 450 );
                    }
                }
                else if ( e.getY() > 270 && e.getY() < 300 && e.getX() < 710 )
                {
                    if ( player.getMoney() >= 1450 && !player.hasGun( 4 ) )
                    {
                        player.unlockGun( 4 );
                        player.takeMoney( 1450 );
                    }
                }
                else if ( e.getY() > 370 && e.getY() < 400 && e.getX() < 765 )
                {
                    if ( player.getMoney() >= 2000 && !player.hasGun( 6 ) )
                    {
                        player.unlockGun( 6 );
                        player.takeMoney( 2000 );
                    }
                }
                else if ( e.getY() > 470 && e.getY() < 500 && e.getX() < 685 )
                {
                    if ( player.getMoney() >= 2100 && !player.hasGun( 7 ) )
                    {
                        player.unlockGun( 7 );
                        player.takeMoney( 2100 );
                    }
                }
                else if ( e.getY() > 515 && e.getY() < 545 && e.getX() < 705 )
                {
                    if ( player.getMoney() >= 3000 && !player.hasGun( 9 ) )
                    {
                        player.unlockGun( 9 );
                        player.takeMoney( 3000 );
                    }
                }
                else if ( e.getY() > 560 && e.getY() < 590 && e.getX() < 685 )
                {
                    if ( player.getMoney() >= 800 )
                    {
                        player.addNukes( 3 );
                        player.takeMoney( 800 );
                    }
                }
            }
            else if ( e.getX() > 10 )
            {
                if ( e.getY() > 70 && e.getY() < 100 && e.getX() < 255 )
                {
                    if ( player.getMoney() >= 100 && player.getHealth() < 100 )
                    {
                        player.increaseHealth( 10 );
                        player.takeMoney( 100 );
                    }
                }
                else if ( e.getY() > 170 && e.getY() < 200 && e.getX() < 350 )
                {
                    if ( player.getMoney() >= 300 && !player.hasGun( 2 ) )
                    {
                        player.unlockGun( 2 );
                        player.takeMoney( 300 );
                    }
                }
                else if ( e.getY() > 270 && e.getY() < 300 && e.getX() < 385 )
                {
                    if ( player.getMoney() >= 1000 && !player.hasGun( 3 ) )
                    {
                        player.unlockGun( 3 );
                        player.takeMoney( 1000 );
                    }
                }
                else if ( e.getY() > 320 && e.getY() < 350 && e.getX() < 320 )
                {
                    if ( player.getMoney() >= 1600 && !player.hasGun( 8 ) )
                    {
                        player.unlockGun( 8 );
                        player.takeMoney( 1600 );
                    }
                }
                else if ( e.getY() > 370 && e.getY() < 400 && e.getX() < 325 )
                {
                    if ( player.getMoney() >= 1800 && !player.hasGun( 5 ) )
                    {
                        player.unlockGun( 5 );
                        player.takeMoney( 1800 );
                    }
                }
                else if ( e.getY() > 470 && e.getY() < 500 && e.getX() < 340 )
                {
                    if ( player.getMoney() >= 4000 && !player.doubleShot() )
                    {
                        player.unlockGun2();
                        player.takeMoney( 4000 );
                    }
                }
                else if ( e.getY() > 560 && e.getY() < 590 && e.getX() < 275 )
                {
                    if ( player.getMoney() >= 350 )
                    {
                        player.addNukes( 1 );
                        player.takeMoney( 350 );
                    }
                }
            }
        }
    }

    public void mouseEntered( MouseEvent arg0 ){}
    public void mouseExited( MouseEvent arg0 ){}
    public void mousePressed( MouseEvent arg0 ){}
    public void mouseReleased( MouseEvent arg0 ){}
}
