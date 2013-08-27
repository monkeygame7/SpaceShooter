import java.awt.Image;
import java.util.LinkedList;


public class UltraBoss extends SuperBossEnemy
{
    private LinkedList<Enemy> enemies;
    private int waveCount;
    private boolean shielding;

    public UltraBoss( int x, int y, LinkedList<Bullet> b, PlayerShip p, Image[] i, LinkedList<Enemy> e )
    {
        super( x, y, 1500, b, p, i );
        enemies = e;
    }

    public void paralyze()
    {
        isParalyzed = true;
        paralyzeCounter = 5;
    }
}
