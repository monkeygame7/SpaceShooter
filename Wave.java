import java.util.LinkedList;
import java.util.ArrayList;


public class Wave
{
    private int wave, level;
    private LinkedList<Bullet> bullets;
    private PlayerShip player;
    private LinkedList<Enemy> enemies;
    private EnemyImages images;

    public Wave( LinkedList<Bullet> b, PlayerShip p, LinkedList<Enemy> e )
    {
        bullets = b;
        player = p;
        wave = 0;
        level = 9;
        enemies = e;
        images = new EnemyImages();
    }

    public void restartLevel()
    {
        wave = 0;
        enemies.clear();
    }

    public int getLevel()
    {
        return level;
    }

    public void nextWave()
    {
        enemies.clear();
        if ( level == 1 )
            level1();
        else if ( level == 2 )
            level2();
        else if ( level == 3 )
            level3();
        else if ( level == 4 )
            level4();
        else if ( level == 5 )
            level5();
        else if ( level == 6 )
            level6();
        else if ( level == 7 )
            level7();
        else if ( level == 8 )
            level8();
        else if ( level == 9 )
            level9();
        else if ( level == 10 )
            level10();
        else
            level = 1;
    }

    public void level1()
    {
        wave++;
        if ( wave < 3 )
        {
            for ( int k = 0; k < 4; k++ )
            {
                for ( int j = 0; j < 3; j++ )
                {
                    enemies.add( new Enemy( 50 + ( 200 * k ) + ( j == 1? 15:0), 10 + ( 100 * j ), bullets, images.get( EnemyImages.ENEMY1 ) ) );
                }
            }
        }
        else if ( wave == 3 )
        {
            enemies.add( new BossEnemy( 10, 10, 100, bullets, player, images.get( EnemyImages.BOSS ) ) );
            for ( int k = 0; k < 4; k++ )
            {
                for ( int j = 1; j < 3; j++ )
                {
                    enemies.add( new Enemy( 50 + ( 200 * k ) + ( j== 2? 15:0 ), 10 + ( 100 * j ), bullets, images.get( EnemyImages.ENEMY1 ) ) );
                }
            }
        }
        else
        {
            level++;
            wave = 0;
        }
    }

    public void level2()
    {
        wave++;
        if ( wave < 3 )
        {
            for ( int k = 0; k < 4; k++ )
            {
                for ( int j = 0; j < 3; j++ )
                {
                    enemies.add( j == 0? new Enemy2( 50 + ( 200 * k ) + ( j == 0? 15:0), 10 + ( 100 * j ), bullets, images.get( EnemyImages.ENEMY2 ) ):
                        new Enemy( 50 + ( 200 * k ) + ( j == 1? 15:0), 10 + ( 100 * j ), bullets, images.get( EnemyImages.ENEMY1 ) ));
                }
            }
        }
        else if ( wave == 3 )
        {
            enemies.add( new BossEnemy( 10, 10, 150, bullets, player, images.get( EnemyImages.BOSS ) ) );
            for ( int k = 0; k < 4; k++ )
            {
                for ( int j = 1; j < 3; j++ )
                {
                    enemies.add( j == 1? new Enemy2( 50 + ( 200 * k ) + ( j== 2? 15:0 ), 10 + ( 100 * j ), bullets, images.get( EnemyImages.ENEMY2 ) ):
                        new Enemy( 50 + ( 200 * k ) + ( j== 2? 15:0 ), 10 + ( 100 * j ), bullets, images.get( EnemyImages.ENEMY1 ) ) );
                }
            }
        }
        else
        {
            level++;
            wave = 0;
        }
    }

    public void level3()
    {
        wave++;
        if ( wave < 3 )
        {
            for ( int k = 0; k < 4; k++ )
            {
                for ( int j = 0; j < 3; j++ )
                {
                    enemies.add( new Enemy2( 50 + ( 200 * k ), 10 + ( 100 * j ), bullets, images.get( EnemyImages.ENEMY2 ) ) );
                }
            }
        }
        else if ( wave == 3 )
        {
            for ( int k = 0; k < 5; k++ )
            {
                enemies.add( new ZigZagEnemy( 150 * k, 25 * k, bullets, player, images.get( EnemyImages.ZIGZAG ) ) );
            }
        }
        else if ( wave == 4 )
        {
            enemies.add( new BossEnemy( 10, 10, 200, bullets, player, images.get( EnemyImages.BOSS ) ) );
            for ( int k = 0; k < 4; k++ )
            {
                for ( int j = 1; j < 3; j++ )
                {
                    enemies.add( new Enemy2( 50 + ( 200 * k ) + ( j== 2? 15:0 ), 10 + ( 100 * j ), bullets, images.get( EnemyImages.ENEMY2 ) ) );
                }
            }
        }
        else
        {
            level++;
            wave = 0;
        }
    }

    public void level4()
    {
        wave++;
        if ( wave == 1 )
        {
            for ( int k = 0; k < 4; k++ )
            {
                for ( int j = 0; j < 3; j++ )
                {
                    enemies.add( new Enemy2( 50 + ( 200 * k ), 10 + ( 100 * j ), bullets, images.get( EnemyImages.ENEMY2 ) ) );
                }
            }
        }
        else if ( wave == 2 )
        {
            for ( int k = 0; k < 10; k++ )
            {
                enemies.add( new CircleEnemy( -72 * k, 10, 150, bullets, true, images.get( EnemyImages.CIRCLE ) ) );
                enemies.add( new CircleEnemy( (int)(72 * (11.2 + k)), 10, 150, bullets, false, images.get( EnemyImages.CIRCLE ) ) );
            }
        }
        else if ( wave == 3 )
        {
            for ( int k = 0; k < 10; k++ )
            {
                enemies.add( new ZigZagEnemy( (70 * k) + 5, (int)(Math.random() * 350), bullets, player, images.get( EnemyImages.ZIGZAG ) ) );
            }
        }
        else if ( wave == 4 )
        {
            enemies.add( new SuperBossEnemy( 300, 10, 500, bullets, player, images.get( EnemyImages.SUPERBOSS ) ) );
            for ( int k = 0; k < 6; k++ )
            {
                enemies.add( new CircleEnemy( -120 * k, 120, 100, bullets, true, images.get( EnemyImages.CIRCLE ) ) );
            }
            for ( int k = 3; k < 6; k++ )
            {
                for ( int j = 1; j < 3; j++ )
                {
                    enemies.add( new Enemy2( (130 * k), (120 * j), bullets, images.get( EnemyImages.ENEMY2 ) ) );
                }
            }
        }
        else
        {
            level++;
            wave = 0;
        }
    }

    public void level5()
    {
        wave++;
        if ( wave == 1 )
        {
            for ( int k = 0; k < 8; k++ )
            {
                enemies.add( new CircleEnemy( -90 * k, 10, 100, bullets, true, images.get( EnemyImages.CIRCLE ) ) );
                enemies.add( new CircleEnemy( 800 + (90 * k), 10, 100, bullets, false, 650, images.get( EnemyImages.CIRCLE ) ) );
            }
            for ( int k = 0; k < 5; k++ )
            {
                enemies.add( new CircleEnemy( -144 * k, 70, 100, bullets, true, 310, images.get( EnemyImages.CIRCLE ) ) );
                enemies.add( new CircleEnemy( 800 + (144 * k), 70, 100, bullets, false, 500, images.get( EnemyImages.CIRCLE ) ) );
            }
        }
        else if ( wave == 2 )
        {
            for ( int k = 0; k < 10; k++ )
            {
                enemies.add( new CircleEnemy( -72 * k, 10, 150, bullets, true, images.get( EnemyImages.CIRCLE ) ) );
                enemies.add( new ZigZagEnemy( (70 * k) + 5, (int)(Math.random() * 350), bullets, player, images.get( EnemyImages.ZIGZAG ) ) );
            }
        }
        else if ( wave == 3 )
        {
            for ( int k = 0; k < 10; k++ )
            {
                enemies.add( new CircleEnemy( -72 * k, 10, 150, bullets, true, images.get( EnemyImages.CIRCLE ) ) );
                if ( k < 7 )
                    enemies.add( new ZigZagEnemy( (70 * k) + 5, (int)(Math.random() * 350), bullets, player, images.get( EnemyImages.ZIGZAG ) ) );
                enemies.add( new CircleEnemy( (int)(72 * (11.2 + k)), 10, 150, bullets, false, images.get( EnemyImages.CIRCLE ) ) );
            }
        }
        else if ( wave == 4 )
        {
            enemies.add( new SuperBossEnemy(10, 10, 600, bullets, player, images.get( EnemyImages.SUPERBOSS )) );
            for ( int k = 0; k < 7; k++ )
            {
                enemies.add( new ZigZagEnemy( (100 * k) + 5, (int)(Math.random() * 250) + 100, 100, bullets, player, images.get( EnemyImages.ZIGZAG ) ) );
            }
        }
        else
        {
            level++;
            wave = 0;
        }
    }

    public void level6()
    {
        wave++;
        if ( wave == 1 )
        {
            for ( int k = 0; k < 13; k++ )
            {
                for ( int j = 0; j < 4; j++ )
                {
                    enemies.add( new Enemy2( (k * 56) + 10, 10 + (j*75), bullets, images.get( EnemyImages.ENEMY2 ) ) );
                }
            }
        }
        else if ( wave == 2 )
        {
            for ( int k = 0; k < 6; k++ )
            {
                for ( int j = 0; j < 4; j++ )
                {
                    enemies.add( new SidewaysEnemy( (k%2==(j%2==0? 0:1)? true:false), (k * 145) + 10, 10 + (j*80), bullets, null ) );
                }
            }
        }
        else if ( wave == 3 )
        {
            enemies.add( new SuperBossEnemy(10, 10, 600, bullets, player, images.get( EnemyImages.SUPERBOSS ) ) );
            for ( int k = 0; k < 10; k++ )
            {
                enemies.add( new SidewaysEnemy( k%2==0? true:false, 72 * k + 10, 300, bullets, null ) );
            }
            for ( int k = 0; k < 4; k++ )
            {
                enemies.add( new CircleEnemy( (-180*k), 105, 100, bullets, true, 125, images.get( EnemyImages.CIRCLE ) ) );
                enemies.add( new CircleEnemy( (-180*k), 105, 100, bullets, true, 375, images.get( EnemyImages.CIRCLE ) ) );
                enemies.add( new CircleEnemy( (180*k) + 800, 105, 100, bullets, false, 625, images.get( EnemyImages.CIRCLE ) ) );
            }
        }
        else
        {
            level++;
            wave = 0;
        }
    }

    public void level7()
    {
        wave++;
        if ( wave == 1 )
        {
            for ( int k = 0; k < 7; k++ )
            {
                for ( int j = 0; j < 4; j++ )
                {
                    enemies.add( new Enemy3( 10 + (k*110), 10 + (j*80), bullets, images.get( EnemyImages.ENEMY3 ) ) );
                }
            }
            for ( int k = 0; k < 3; k++ )
            {
                enemies.add( new SidewaysEnemy( k==1? false:true, 10 + (700/(k+1)), 10 + (120*k), bullets, null ) );
            }
        }
        else if ( wave == 2 )
        {
            for ( int k = 0; k < 8; k++ )
            {
                enemies.add( new SidewaysEnemy( k%2==0? true:false, (k * 90) + 10, 10, bullets, null ) );
            }
            for ( int k = 0; k < 16; k++)
            {
                enemies.add( new CircleEnemy( -90 * k, 75, 125, bullets, true, k<8? 400:150, images.get( EnemyImages.CIRCLE ) ) );
                if ( k < 8 )
                    enemies.add( new CircleEnemy( 800 + (90*k), 75, 125, bullets, false, 650, images.get( EnemyImages.CIRCLE ) ) );
            }
        }
        else if ( wave == 3 )
        {
            enemies.add( new SuperBossEnemy( 10, 10, 600, bullets, player, images.get( EnemyImages.SUPERBOSS ) ) );
            enemies.add( new BossEnemy( 350, 150, 600, bullets, player, images.get( EnemyImages.BOSS ) ) );
            for ( int k = 0; k < 10; k++ )
            {
                enemies.add( new SidewaysEnemy( k%2==0? true:false, 72 * k + 10, 275, bullets, null ) );
            }
        }
        else
        {
            level++;
            wave = 0;
        }
    }

    public void level8()
    {
        wave++;
        if ( wave == 1 )
        {
            for ( int k = 0; k < 6; k++ )
            {
                enemies.add( new SidewaysEnemy( k%2==0? true:false, 10 + (k * 120), 10, bullets, null ) );
                enemies.add( new HomingCircleEnemy( k * -120, 90, 140, bullets, player, true, 400, images.get( EnemyImages.HOMINGCIRCLE ) ) );
            }
            for ( int k = 0; k < 10; k++ )
            {
                enemies.add( new ZigZagEnemy( k * 60, (int)(Math.random() * 200) + 100, 80, bullets, player, images.get( EnemyImages.ZIGZAG ) ) );
            }
        }
        else if ( wave == 2 )
        {
            for ( int k = 0; k < 14; k++ )
            {
                enemies.add( new HomingCircleEnemy( -105 * k, 75, 125, bullets, player, true, k<7? 400:150, images.get( EnemyImages.HOMINGCIRCLE ) ) );
                if ( k < 7 )
                    enemies.add( new HomingCircleEnemy( 800 + (105*k), 75, 125, bullets, player, false, 650, images.get( EnemyImages.HOMINGCIRCLE ) ) );
            }
        }
        else if ( wave == 3 )
        {
            enemies.add( new SuperBossEnemy( 10, 10, 500, bullets, player, images.get( EnemyImages.SUPERBOSS ) ) );
            enemies.add( new SuperBossEnemy( 500, 10, 500, bullets, player, images.get( EnemyImages.SUPERBOSS ) ) );
            enemies.add( new BossEnemy( 10, 200, 400, bullets, player, images.get( EnemyImages.BOSS ) ) );
            enemies.add( new BossEnemy( 550, 200, 400, bullets, player, images.get( EnemyImages.BOSS ) ) );
        }
        else
        {
            level++;
            wave = 0;
        }
    }

    public void level9()
    {
        wave++;
        if ( wave == 1 )
        {
            for ( int k = 0; k < 4; k++ )
            {
                for ( int j = 0; j < 6; j++ )
                {
                    enemies.add( new BasicHomingEnemy( (j%2==0)? true:false, (j * 140) + 15, (k * 80) + 10, bullets, player, images.get( EnemyImages.BASICHOMING ) ) );
                }
            }
        }
        else if ( wave == 2 )
        {
            for ( int k = 0; k < 5; k++ )
            {
                for ( int j = 0; j < 3; j++ )
                {
                    enemies.add( new NinjaEnemy( 160 * k + 15, 130 * j + 5, bullets, images.get( EnemyImages.NINJA ), player ) );
                }
            }
        }
        else if ( wave == 3 )
        {
            enemies.add( new SuperBossEnemy( 10, 10, 750, bullets, player, images.get( EnemyImages.SUPERBOSS ) ) );
            for ( int k = 0; k < 4; k++ )
            {
                for ( int j = 0; j < 3; j++ )
                {
                    if ( k % 2 == 1 )
                        enemies.add( new BasicHomingEnemy( (j%2==0)? true:false, (j * 280) + 15, (k * 80) + 90, bullets, player, images.get( EnemyImages.BASICHOMING ) ) );
                    else
                        enemies.add( new SidewaysEnemy( (j%2==0)? true:false, (j * 280) + 15, (k * 80) + 90, bullets, null ) );
                }
            }
        }
        else
        {
            level++;
            wave = 0;
        }
    }

    public void level10()
    {
        wave++;
        if ( wave == 1 )
        {
            for ( int k = 0; k < 5; k++ )
            {
                for ( int j = 0; j < 4; j++ )
                {
                    enemies.add( new NinjaEnemy( 160 * k + 15, 80 * j + 5, bullets, images.get( EnemyImages.NINJA ), player ) );
                }
            }
        }
        else if ( wave == 2 )
        {
            for ( int k = 0; k < 5; k++ )
            {
                for ( int j = 0; j < 3; j++ )
                {
                    boolean b = (k + j) % 2 == 1;
                    if ( b )
                    {
                        enemies.add( new SidewaysEnemy( b, 10 + (156*k), 10 + (100*j), bullets, null) );
                    }
                    else
                    {
                        enemies.add( new BasicHomingEnemy( b, 10 + (156*k), 10 + (100*j), bullets, player, null) );
                    }
                }
            }
        }
        else if ( wave == 3 )
        {
            for ( int k = 0; k < 4; k++ )
            {
                for ( int j = 0; j < 4; j++ )
                enemies.add( new NinjaEnemy( 230 * k + 10, 80 * j + 1, bullets, images.get( EnemyImages.NINJA ), player ) );
            }
            enemies.add( new SuperBossEnemy(10, 10, 750, bullets, player, images.get( EnemyImages.SUPERBOSS ) ) );
        }
        else
        {
            level++;
            wave = 0;
        }
    }
}
