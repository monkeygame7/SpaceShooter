import java.awt.Image;
import java.awt.Graphics2D;

public abstract class Explosion
{
    protected int x, y, animation, length;
    protected ExplosionImages images;

    public void step()
    {
        animation++;
    }

    public abstract void draw( Graphics2D g );

    public boolean isDone()
    {
        return animation >= length;
    }
}
