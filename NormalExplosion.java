import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Image;
import java.awt.Graphics2D;


public class NormalExplosion extends Explosion
{

    public NormalExplosion( int x, int y, ExplosionImages exp )
    {
        this.x = x - 20;
        this.y = y - 20;
        animation = 0;
        length = 20;
        images = exp;
    }

    public void draw( Graphics2D g )
    {
        g.drawImage( images.get( (animation%length)/5 ), x, y, null );
    }
}
