import java.util.LinkedList;
import java.awt.Image;
import java.awt.Color;
import java.util.ArrayList;


public class Enemy3 extends Enemy
{

    public Enemy3( int x, int y, LinkedList<Bullet> b, Image[] i )
    {
        super( x, y, b, i );
        health = 70;
        width = 40;
        height = 40;
        scoreValue = 15;
        color = Color.blue.darker();
        frequency = 100;
        shootCounter = rand.nextInt( frequency );
    }
}
