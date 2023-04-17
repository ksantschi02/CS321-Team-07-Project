import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;

public class CollectionPage extends JPanel
{



    JButton filterButton, saveButton, backButton, searchButton;
    JTextField searchTextField;
    JFrame collectionFrame;

    User Jim = new User("CoolGuy47", "1234");

    JComboBox userRank = new JComboBox();

    public CollectionPage(Collection collection, String title)
    {
        collectionFrame = new JFrame();

        collectionFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        collectionFrame.setTitle(title);

        JPanel collectionPanel = new JPanel();

        collectionPanel.setLayout(new GridBagLayout());

        //setting up panel for search bar and filter

        JPanel searchPanel = new JPanel();

        searchPanel.setLayout(new FlowLayout());

        for(int i = 0; i < collection.getSize(); i++)
        {
            userRank.addItem(i + 1);
        }

        filterButton = new JButton("Filter");
        searchPanel.add(filterButton);

        searchTextField = new JTextField(30);
        searchPanel.add(searchTextField);

        searchButton = new JButton("Search");
        searchPanel.add(searchButton);

        backButton = new JButton("Back to Main Menu");
        searchPanel.add(backButton);
        CollectionPage.ListenForButton lForButton = new CollectionPage.ListenForButton();
        backButton.addActionListener(lForButton);

        saveButton = new JButton("Save");
        searchPanel.add(saveButton);

        //importing games and collections

        Jim.addCollection(collection);

        JScrollPane gameScroll = createGameScrollPane(Jim.getCollection(title).getGames());




        //putting all of them together

        collectionFrame.add(collectionPanel);

        addComp(collectionPanel, searchPanel, 0, 0, 3, 1, GridBagConstraints.NORTH, GridBagConstraints.NONE);
        addComp(collectionPanel, gameScroll, 0, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE);



        collectionFrame.pack();
        collectionFrame.setLocationRelativeTo(null);
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
    public JPanel createGame(String title, String imageUrl, int minPlayerCount, int maxPlayerCount, int minPlaytime, int maxPlaytime, int minAge, double avgRating, ArrayList<String> genre, String description, ArrayList<Review> reviews)
    {
        JLabel genreLabel, playerCountLabel, nameLabel, imageLabel, playtimeLabel, ageLabel, ratingLabel;
        JButton addButton;
        BufferedImage gameImage = null;

        JPanel gamePanel = new JPanel();
        gamePanel.setLayout(new GridBagLayout());

        Box infoBox = Box.createVerticalBox();

        nameLabel = new JLabel("Name: " + title);
        genreLabel = new JLabel("Genre: " + genre.get(0));
        for(int i = 1; i < genre.size(); i++)
        {
            genreLabel.setText(genreLabel.getText() + ", " + genre.get(i));
        }
        if(minPlayerCount != maxPlayerCount)
        {
            playerCountLabel = new JLabel("Player Count: " + minPlayerCount + " - " + maxPlayerCount);
        }
        else
        {
            playerCountLabel = new JLabel("Player Count: " + maxPlayerCount);
        }
        if(minPlaytime != maxPlaytime)
        {
            playtimeLabel = new JLabel("Playtime: " + minPlaytime + " - " + maxPlaytime + " (mins)");
        }
        else
        {
            playtimeLabel = new JLabel("Playtime: " + maxPlaytime + " (mins)");
        }
        ageLabel = new JLabel("Age: " + minAge + "+");
        ratingLabel = new JLabel("Avg Rating: " + avgRating + "/10");

        JComboBox userIndRank = new JComboBox<>();

        for(int i = 0; i < userRank.getItemCount(); i++)
        {
            userIndRank.addItem(userRank.getItemAt(i));
        }

        class ListenForGameButton implements ActionListener
        {
            public void actionPerformed(ActionEvent e)
            {

            }
        }

        ListenForGameButton lForButton = new ListenForGameButton();


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
        addComp(gamePanel, userIndRank, 1, 1, 1,1, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE);


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
        addComp(gamePanel, imageLabel, 0, 0, 1, 2, GridBagConstraints.WEST, GridBagConstraints.NONE);

        Border gameBorder = BorderFactory.createLineBorder(Color.black);
        gamePanel.setBorder(gameBorder);

        return gamePanel;

    }

    //dynamically createsGameScrollPane from array
    public JScrollPane createGameScrollPane(ArrayList<Game> games)
    {
        Box gameBox = Box.createVerticalBox();
        for(int i = 0; i < games.size(); i++)
        {
            gameBox.add(createGame(games.get(i).getTitle(), games.get(i).getImage(), games.get(i).getMinPlayers(), games.get(i).getMaxPlayers(), games.get(i).getMinPlaytime(), games.get(i).getMaxPlaytime(), games.get(i).getMinAge(), games.get(i).getAvgRating(), games.get(i).getGenre(), games.get(i).getDescription(), games.get(i).getReviews()));
            gameBox.add(Box.createVerticalStrut(2));
        }

        JScrollPane gameScrollPane = new JScrollPane(gameBox, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        gameScrollPane.setPreferredSize(new Dimension(400, 420));

        return gameScrollPane;
    }

    public class ListenForButton implements ActionListener
    {

        public void actionPerformed(ActionEvent e)
        {
            if (e.getSource() == backButton)
            {

                collectionFrame.dispose();

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
