import java.util.LinkedList;
import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Color;
import java.util.ArrayList;


public class Enemy2 extends Enemy
{

    public Enemy2( int x, int y, LinkedList<Bullet> b, Image[] i )
    {
        super( x, y, b, i );
        health = 40;
        color = Color.blue.darker();
        frequency = 200;
        duration = 100;
        scoreValue = 8;
        shootCounter = rand.nextInt( frequency );
    }
}
