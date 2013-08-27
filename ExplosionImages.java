import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Image;


public class ExplosionImages
{
    private Image[] images;

    public ExplosionImages()
    {
        images = new Image[4];
        for ( int k = 0; k < 4; k++ )
        {
            try
            {
                images[k] = ImageIO.read( getClass().getResource( "images/explosion" + (k+1) + ".png" ) );
            }
            catch ( IOException e )
            {
                e.printStackTrace();
            }
        }
    }

    public Image get( int k )
    {
        return images[k];
    }
}
