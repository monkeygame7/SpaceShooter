import javax.swing.JPanel;
import javax.swing.JFrame;

public class SpaceShooter
{
    private Board board;

    public SpaceShooter()
    {
        JFrame frame = new JFrame( "Space Shooter" );
        frame.setSize( 800, 628 );
        board = new Board();
        frame.getContentPane().add( board );
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.setResizable( false );
        frame.setVisible( true );
    }

    public static void main( String[] args )
    {
        new SpaceShooter().start();
    }

    public void start()
    {
        board.startGame();
    }
}
