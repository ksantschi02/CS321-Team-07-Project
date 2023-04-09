import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class CollectionPage extends JPanel
{


    JButton filterButton, saveButton, logoutButton, searchButton;

    JTextField searchTextField;
    JFrame collectionFrame;
    public CollectionPage(Game[] games)
    {
        collectionFrame = new JFrame();

        collectionFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        collectionFrame.setLocationRelativeTo(null);
        collectionFrame.setTitle("CollectionPage");

        JPanel collectionPanel = new JPanel();

        collectionPanel.setLayout(new GridBagLayout());

        //setting up panel for search bar and filter

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
        CollectionPage.ListenForButton lForButton = new CollectionPage.ListenForButton();
        logoutButton.addActionListener(lForButton);

        saveButton = new JButton("Save");
        searchPanel.add(saveButton);

        //importing games and collections


        JScrollPane gameScroll = createGameScrollPane(games);


        //putting all of them together

        collectionFrame.add(collectionPanel);

        addComp(collectionPanel, searchPanel, 0, 0, 8, 2, GridBagConstraints.NORTH, GridBagConstraints.NONE);

        addComp(collectionPanel, gameScroll, 0, 2, 4, 2, GridBagConstraints.SOUTHWEST, GridBagConstraints.NONE);



        collectionFrame.pack();

        collectionFrame.setVisible(true);
    }

    public void addComp(JPanel thePanel, JComponent comp, int xPos, int yPos, int compWidth, int compHeight, int place, int stretch)
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


    //constructor with parameters
    public JPanel createGame(String title, String imageUrl, int minPlayerCount, int maxPlayerCount, int minPlaytime, int maxPlaytime, int minAge, double avgRating, String genre, String description, ArrayList<Review> reviews)
    {
        JLabel genreLabel, playerCountLabel, nameLabel, imageLabel, playtimeLabel, ageLabel, ratingLabel;
        JButton addButton;
        BufferedImage gameImage = null;

        JPanel gamePanel = new JPanel();
        gamePanel.setLayout(new GridBagLayout());

        Box infoBox = Box.createVerticalBox();

        nameLabel = new JLabel("Name: " + title);
        genreLabel = new JLabel("Genre: " + genre);
        playerCountLabel = new JLabel("PlayerCount: " + minPlayerCount + " - " + maxPlayerCount);
        playtimeLabel = new JLabel("Playtime: " + minPlaytime + " - " + maxPlaytime + " (mins)");
        ageLabel = new JLabel("Age: " + minAge + "+");
        ratingLabel = new JLabel("Avg Rating: " + avgRating + "/10");
        addButton = new JButton("Add to Collection");



        class ListenForGameButton implements ActionListener
        {
            public void actionPerformed(ActionEvent e)
            {
                if (e.getSource() == addButton)
                {

                }
            }
        }

        ListenForGameButton lForButton = new ListenForGameButton();
        addButton.addActionListener(lForButton);

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


        addComp(gamePanel, infoBox, 1, 0, 1, 1, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE);
        addComp(gamePanel, addButton, 1, 1, 1, 1, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE);

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
        addComp(gamePanel, imageLabel, 0, 0, 1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE);

        Border gameBorder = BorderFactory.createLineBorder(Color.black);
        gamePanel.setBorder(gameBorder);

        return gamePanel;

    }

    //dynamically createsGameScrollPane from array
    public JScrollPane createGameScrollPane(Game[] games)
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

    public class ListenForButton implements ActionListener
    {

        public void actionPerformed(ActionEvent e)
        {
            if (e.getSource() == logoutButton)
            {
                int answer = JOptionPane.showConfirmDialog(null, "Are you sure?");
                if(answer == JOptionPane.YES_OPTION)
                {
                    collectionFrame.dispose();
                }
            }
            if (e.getSource() == filterButton)
            {

            }
            if (e.getSource() == searchButton)
            {

            }
            if (e.getSource() == saveButton)
            {

            }
        }

    }


}
