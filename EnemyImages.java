import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Image;


public class EnemyImages
{
    private Image[] enemy1, enemy2, enemy3, basicHoming, boss, circleEnemy,
    homingCircle, superBoss, zigZag;
    private Image[] ninjaEnemy;

    public final static int ENEMY1 = 1;
    public final static int ENEMY2 = 2;
    public final static int ENEMY3 = 3;
    public final static int BASICHOMING = 4;
    public final static int BOSS = 5;
    public final static int CIRCLE = 6;
    public final static int HOMINGCIRCLE = 7;
    public final static int SUPERBOSS = 8;
    public final static int ZIGZAG = 9;
    public final static int NINJA = 10;

    public EnemyImages()
    {
        enemy1 = new Image[2];
        enemy2 = new Image[2];
        boss = new Image[2];
        circleEnemy = new Image[5];
        zigZag = new Image[4];
        superBoss = new Image[2];
        ninjaEnemy = new Image[3];
        try
        {
            enemy1[0] = ImageIO.read( getClass().getResource( "images/basicenemy.png" ) );
            enemy1[1] = ImageIO.read( getClass().getResource( "images/basicenemy2.png" ) );

            enemy2[0] = ImageIO.read( getClass().getResource( "images/enemy2_1.png" ) );
            enemy2[1] = ImageIO.read( getClass().getResource( "images/enemy2_2.png" ) );

            boss[0] = ImageIO.read( getClass().getResource( "images/boss1.png" ) );
            boss[1] = ImageIO.read( getClass().getResource( "images/boss2.png" ) );

            superBoss[0] = ImageIO.read( getClass().getResource( "images/superboss1.png" ) );
            superBoss[1] = ImageIO.read( getClass().getResource( "images/superboss2.png" ) );

            for ( int j = 0; j < circleEnemy.length; j++ )
            {
                circleEnemy[j] = ImageIO.read( getClass().getResource( "images/circleenemy" + (j+1) + ".png" ) );
            }

            for ( int j = 0; j < zigZag.length; j++ )
            {
                zigZag[j] = ImageIO.read( getClass().getResource( "images/zigzagenemy" + (j+1) + ".png" ) );
            }
            ninjaEnemy[0] = ImageIO.read( getClass().getResource( "images/ninjaenemy.png" ) );
            ninjaEnemy[1] = ImageIO.read( getClass().getResource( "images/ninjaenemydisappearing1.png" ) );
            ninjaEnemy[2] = ImageIO.read( getClass().getResource( "images/ninjaenemydisappearing2.png" ) );
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }
    }

    public Image[] get( int k )
    {
        if ( k == 1 )
            return enemy1;
        if ( k == 2 )
            return enemy2;
        if ( k == 3 )
            return enemy3;
        if ( k == 4 )
            return basicHoming;
        if ( k == 5 )
            return  boss;
        if ( k == 6 )
            return circleEnemy;
        if ( k == 7 )
            return homingCircle;
        if ( k == 8 )
            return superBoss;
        if ( k == 9 )
            return zigZag;
        if ( k == 10 )
            return ninjaEnemy;
        else
            return null;
    }
}
