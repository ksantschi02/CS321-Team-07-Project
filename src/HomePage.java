
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
import java.util.ArrayList;


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

        //importing games

        Game cool = new Game(12, 5, 10, 10, 30, 7, 7.5, "Cool Game", "Action", "Is cool", "https://cf.geekdo-images.com/DCLgJlrvB-EqL6A3WgQLMQ__original/img/vGpYcxjDBCOVcI0BcWOevspTQMQ=/0x0/filters:format(jpeg)/pic5715770.jpg");
        Game cool1 = new Game(12, 5, 10, 10, 30, 7, 7.5, "Cool Game", "Action", "Is cool", "https://cf.geekdo-images.com/DCLgJlrvB-EqL6A3WgQLMQ__original/img/vGpYcxjDBCOVcI0BcWOevspTQMQ=/0x0/filters:format(jpeg)/pic5715770.jpg");
        Game cool2 = new Game(12, 5, 10, 10, 30, 7, 7.5, "Cool Game", "Action", "Is cool", "https://cf.geekdo-images.com/DCLgJlrvB-EqL6A3WgQLMQ__original/img/vGpYcxjDBCOVcI0BcWOevspTQMQ=/0x0/filters:format(jpeg)/pic5715770.jpg");
        Game[] coolGames = {cool, cool1, cool2};
        JScrollPane gameScroll = createGameScrollPane(coolGames);

        //setting up panel for each individual collection

        JPanel collectionPanel = createCollection();

        //setting up panel for each collection and scroll bar

        Box collectionBox = Box.createVerticalBox();
        collectionBox.add(collectionPanel);
        JScrollPane collectionScroll = new JScrollPane(collectionBox, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        collectionScroll.setPreferredSize(new Dimension(200, 200));

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
        JLabel genreLabel, playerCountLabel, nameLabel, imageLabel, playtimeLabel, ageLabel, avgRating;
        BufferedImage gameImage = null;

        JPanel gamePanel = new JPanel();
        gamePanel.setLayout(new GridBagLayout());

        Box infoBox = Box.createVerticalBox();

        nameLabel = new JLabel("Name: Creatures of the Deep");
        genreLabel = new JLabel("Genre: Action");
        playerCountLabel = new JLabel("PlayerCount: 5 - 10");
        playtimeLabel = new JLabel("Playtime: 10mins - 1hr");
        ageLabel = new JLabel("Age: 10 - 30+");
        avgRating = new JLabel("Avg Rating: 7.4/10");

        infoBox.add(nameLabel);
        infoBox.add(Box.createVerticalStrut(1));
        infoBox.add(genreLabel);
        infoBox.add(Box.createVerticalStrut(1));
        infoBox.add(playerCountLabel);
        infoBox.add(Box.createVerticalStrut(1));
        infoBox.add(playtimeLabel);
        infoBox.add(Box.createVerticalStrut(1));
        infoBox.add(ageLabel);
        infoBox.add(Box.createVerticalStrut(1));
        infoBox.add(avgRating);

        addComp(gamePanel, infoBox, 2, 0, 1, 1, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE);

        try{
            URL url = new URL("https://cf.geekdo-images.com/DCLgJlrvB-EqL6A3WgQLMQ__original/img/vGpYcxjDBCOVcI0BcWOevspTQMQ=/0x0/filters:format(jpeg)/pic5715770.jpg");
            gameImage = ImageIO.read(url);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        Image newGameImg = gameImage.getScaledInstance(140,140, Image.SCALE_SMOOTH);
        ImageIcon gameIcon = new ImageIcon(newGameImg);
        imageLabel = new JLabel(gameIcon);
        addComp(gamePanel, imageLabel, 0, 0, 2, 1, GridBagConstraints.WEST, GridBagConstraints.NONE);

        Border gameBorder = BorderFactory.createLineBorder(Color.black);
        gamePanel.setBorder(gameBorder);

        return gamePanel;
    }

    //constructor with parameters
    private JPanel createGame(String title, String imageUrl, int minPlayerCount, int maxPlayerCount, int minPlaytime, int maxPlaytime, int minAge, double avgRating, String genre, String description, ArrayList<Review> reviews)
    {
        JLabel genreLabel, playerCountLabel, nameLabel, imageLabel, playtimeLabel, ageLabel, ratingLabel;
        BufferedImage gameImage = null;

        JPanel gamePanel = new JPanel();
        gamePanel.setLayout(new GridBagLayout());

        Box infoBox = Box.createVerticalBox();

        nameLabel = new JLabel("Name: " + title);
        genreLabel = new JLabel("Genre: " + genre);
        playerCountLabel = new JLabel("PlayerCount: " + minPlayerCount + " - " + maxPlayerCount);
        playtimeLabel = new JLabel("Playtime: " + minPlaytime + "mins - " + maxPlaytime + "mins");
        ageLabel = new JLabel("Age: " + minAge + "+");
        ratingLabel = new JLabel("Avg Rating: " + avgRating + "/10");

        infoBox.add(nameLabel);
        infoBox.add(Box.createVerticalStrut(1));
        infoBox.add(genreLabel);
        infoBox.add(Box.createVerticalStrut(1));
        infoBox.add(playerCountLabel);
        infoBox.add(Box.createVerticalStrut(1));
        infoBox.add(playtimeLabel);
        infoBox.add(Box.createVerticalStrut(1));
        infoBox.add(ageLabel);
        infoBox.add(Box.createVerticalStrut(1));
        infoBox.add(ratingLabel);

        addComp(gamePanel, infoBox, 2, 0, 1, 1, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE);

        try{
            URL url = new URL(imageUrl);
            gameImage = ImageIO.read(url);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        Image newGameImg = gameImage.getScaledInstance(140,140, Image.SCALE_SMOOTH);
        ImageIcon gameIcon = new ImageIcon(newGameImg);
        imageLabel = new JLabel(gameIcon);
        addComp(gamePanel, imageLabel, 0, 0, 2, 1, GridBagConstraints.WEST, GridBagConstraints.NONE);

        Border gameBorder = BorderFactory.createLineBorder(Color.black);
        gamePanel.setBorder(gameBorder);

        return gamePanel;

    }

    //dynamically createsGameScrollPane from array
    private JScrollPane createGameScrollPane(Game[] games)
    {
        Box gameBox = Box.createVerticalBox();
        for(int i = 0; i < games.length; i++)
        {
            gameBox.add(createGame(games[i].getTitle(), games[i].getImage(), games[i].getMinPlayers(), games[i].getMaxPlayers(), games[i].getMinPlaytime(), games[i].getMaxPlaytime(), games[i].getMinAge(), games[i].getAvgRating(), games[i].getGenre(), games[i].getDescription(), games[i].getReviews()));
            gameBox.add(Box.createVerticalStrut(2));
        }

        JScrollPane gameScrollPane = new JScrollPane(gameBox, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        gameScrollPane.setPreferredSize(new Dimension(400, 220));

        return gameScrollPane;
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
