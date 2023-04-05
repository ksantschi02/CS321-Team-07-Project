
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.nio.Buffer;
import java.util.Collection;
import javax.swing.border.*;


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

        //setting up the panel for the ENTIRE screen

        JPanel homePanel = new JPanel();

        homePanel.setLayout(new GridBagLayout());

        //setting up panel for search bar and filter

        JButton filterButton, saveButton, logoutButton, searchButton;

        JTextField searchTextField;

        JPanel searchPanel = new JPanel();

        searchPanel.setLayout(new FlowLayout());

        filterButton = new JButton("Filter");
        searchPanel.add(filterButton);

        searchTextField = new JTextField(30);
        searchPanel.add(searchTextField);

        searchButton = new JButton("Search");
        searchPanel.add(searchButton);

        logoutButton = new JButton("Logout");
        searchPanel.add(logoutButton);

        saveButton = new JButton("Save");
        searchPanel.add(saveButton);

        //setting up panel for each individual game

        JPanel gamePanel = createGame();
        JPanel gamePanel2 = createGame();

        //setting up panel for all the games with a scroll bar

        Box gameBox = Box.createVerticalBox();
        gameBox.add(gamePanel);
        gameBox.add(Box.createVerticalStrut(2));
        gameBox.add(gamePanel2);
        JScrollPane gameScroll = new JScrollPane(gameBox, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        gameScroll.setPreferredSize(new Dimension(300, 200));

        //setting up panel for each individual collection

        JPanel collectionPanel = createCollection();

        //setting up panel for each collection and scroll bar

        Box collectionBox = Box.createVerticalBox();
        collectionBox.add(collectionPanel);
        JScrollPane collectionScroll = new JScrollPane(collectionBox, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        collectionScroll.setPreferredSize(new Dimension(250, 200));

        //putting all of them together

        homeFrame.add(homePanel);

        addComp(homePanel, searchPanel, 0, 0, 8, 2, GridBagConstraints.NORTH, GridBagConstraints.NONE);

        addComp(homePanel, gameScroll, 0, 2, 4, 2, GridBagConstraints.SOUTHWEST, GridBagConstraints.NONE);

        addComp(homePanel, collectionScroll, 0, 2, 4, 2, GridBagConstraints.SOUTHEAST, GridBagConstraints.NONE);



        homeFrame.pack();

        homeFrame.setVisible(true);
    }

    private void addComp(JPanel thePanel, JComponent comp, int xPos, int yPos, int compWidth, int compHeight, int place, int stretch)
    {
        GridBagConstraints gridConstraints = new GridBagConstraints();

        gridConstraints.gridx = xPos;
        gridConstraints.gridy = yPos;
        gridConstraints.gridwidth = compWidth;
        gridConstraints.gridheight = compHeight;
        gridConstraints.weightx = 100;
        gridConstraints.weighty = 100;
        gridConstraints.insets = new Insets(5,5,5,5);
        gridConstraints.anchor = place;
        gridConstraints.fill = stretch;

        thePanel.add(comp, gridConstraints);

    }

    //base constructor
    private JPanel createGame()
    {
        JLabel genreLabel, playerCountLabel, nameLabel, imageLabel;
        BufferedImage gameImage = null;

        JPanel gamePanel = new JPanel();
        gamePanel.setLayout(new GridBagLayout());

        genreLabel = new JLabel("Genre:");
        addComp(gamePanel, genreLabel, 3, 0, 1, 1, GridBagConstraints.SOUTHWEST, GridBagConstraints.NONE);

        playerCountLabel = new JLabel("Count:");
        addComp(gamePanel, playerCountLabel, 2, 0, 1, 1, GridBagConstraints.SOUTHWEST, GridBagConstraints.NONE);

        nameLabel = new JLabel("Name:");
        addComp(gamePanel, nameLabel, 2, 0, 1, 1, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE);

        try{
            URL url = new URL("https://cf.geekdo-images.com/DCLgJlrvB-EqL6A3WgQLMQ__original/img/vGpYcxjDBCOVcI0BcWOevspTQMQ=/0x0/filters:format(jpeg)/pic5715770.jpg");
            gameImage = ImageIO.read(url);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        Image newGameImg = gameImage.getScaledInstance(120,120, Image.SCALE_SMOOTH);
        ImageIcon gameIcon = new ImageIcon(newGameImg);
        imageLabel = new JLabel(gameIcon);
        addComp(gamePanel, imageLabel, 0, 0, 2, 1, GridBagConstraints.WEST, GridBagConstraints.NONE);

        Border gameBorder = BorderFactory.createLineBorder(Color.black);
        gamePanel.setBorder(gameBorder);

        return gamePanel;
    }

    //constructor with parameters
    private JPanel createGame(String Name, String imageUrl, Integer minPlayerCount, Integer minAge, Double avgRating, String genre, String description)
    {
        JLabel genreLabel, playerCountLabel, nameLabel, imageLabel;
        BufferedImage gameImage = null;

        JPanel gamePanel = new JPanel();
        gamePanel.setLayout(new GridBagLayout());

        genreLabel = new JLabel("Genre: " + genre);
        addComp(gamePanel, genreLabel, 3, 0, 1, 1, GridBagConstraints.SOUTHWEST, GridBagConstraints.NONE);

        playerCountLabel = new JLabel("Count:");
        addComp(gamePanel, playerCountLabel, 2, 0, 1, 1, GridBagConstraints.SOUTHWEST, GridBagConstraints.NONE);

        nameLabel = new JLabel("Name: " + Name);
        addComp(gamePanel, nameLabel, 2, 0, 1, 1, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE);

        try{
            URL url = new URL(imageUrl);
            gameImage = ImageIO.read(url);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        Image newGameImg = gameImage.getScaledInstance(120,120, Image.SCALE_SMOOTH);
        ImageIcon gameIcon = new ImageIcon(newGameImg);
        imageLabel = new JLabel(gameIcon);
        addComp(gamePanel, imageLabel, 0, 0, 2, 1, GridBagConstraints.WEST, GridBagConstraints.NONE);

        return gamePanel;

    }

    //constructor for collection
    private JPanel createCollection()
    {

        JPanel collectionPanel = new JPanel();
        collectionPanel.setLayout(new GridBagLayout());
        JLabel collectionTitle;

        collectionTitle = new JLabel("Collection");
        class ListenForMouse extends MouseAdapter {

            @Override
            public void mouseClicked(MouseEvent e) {
                String collectionName = JOptionPane.showInputDialog("New Name Here");
                collectionTitle.setText(collectionName);

            }
        }

        addComp(collectionPanel, collectionTitle, 1, 0, 1, 1, GridBagConstraints.EAST, GridBagConstraints.NONE);
        ListenForMouse lForMouse = new ListenForMouse();
        collectionTitle.addMouseListener(lForMouse);




        return collectionPanel;
    }






}
