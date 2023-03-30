
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
public class HomePage extends JFrame
{

    private static final int FRAME_WIDTH = 500;
    private static final int FRAME_HEIGHT = 300;


    public HomePage()
    {
        JFrame homeFrame = new JFrame();
        //loginFrame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        homeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        homeFrame.setLocationRelativeTo(null);
        homeFrame.setTitle("HomePage");

        homeFrame.setSize(FRAME_WIDTH, FRAME_HEIGHT);

        JPanel thePanel = new JPanel();

        homeFrame.add(thePanel);

        homeFrame.setVisible(true);
    }

}
