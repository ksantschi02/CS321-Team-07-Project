
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.nio.Buffer;

import javax.swing.border.*;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;



public class HomePage extends JFrame
{

    private static final int FRAME_WIDTH = 500;
    private static final int FRAME_HEIGHT = 300;

    JButton filterButton, saveButton, logoutButton, searchButton;

    JTextField searchTextField;

    JFrame homeFrame = new JFrame();

    User Jim = new User("CoolGuy47", "1234");

    Review rev1 = new Review(7.5,"Hey guys its me CoolGuy and I LOVE this board game man. It's crazy, it's like the coolest board ever made. They have some much killing which is probably the only reason why board games should be made.", Jim.getUser());
    Review rev2 = new Review(2,"This game kinda sucks now.", Jim.getUser());
    Review rev3 = new Review(7.5,"Hey guys its me CoolGuy and I LOVE this board game man. It's crazy, it's like the coolest board ever made. They have some much killing which is probably the only reason why board games should be made.", Jim.getUser());
    Review rev4 = new Review(2,"This game kinda sucks now.", Jim.getUser());
    ArrayList<Review> userReviews = new ArrayList<>(Arrays.asList(rev1, rev2, rev3, rev4));



    Game cool = new Game(12, 5, 10, 10, 30, 7, 7.5, "Cool Game", "Action", "Is cool", "https://cf.geekdo-images.com/DCLgJlrvB-EqL6A3WgQLMQ__original/img/vGpYcxjDBCOVcI0BcWOevspTQMQ=/0x0/filters:format(jpeg)/pic5715770.jpg", userReviews);
    Game cool1 = new Game(12, 5, 10, 10, 30, 7, 7.5, "Cool Game", "Action", "Is cool", "https://cf.geekdo-images.com/DCLgJlrvB-EqL6A3WgQLMQ__original/img/vGpYcxjDBCOVcI0BcWOevspTQMQ=/0x0/filters:format(jpeg)/pic5715770.jpg", userReviews);
    Game cool2 = new Game(12, 5, 10, 10, 30, 7, 7.5, "Cool Game", "Action", "Is cool", "https://cf.geekdo-images.com/DCLgJlrvB-EqL6A3WgQLMQ__original/img/vGpYcxjDBCOVcI0BcWOevspTQMQ=/0x0/filters:format(jpeg)/pic5715770.jpg", userReviews);
    ArrayList<Game> coolGames = new ArrayList<>(Arrays.asList(cool, cool1, cool2));

    Collection col1 = new Collection(1,"Rad", new ArrayList());
    Collection col2 = new Collection(2, "Bad", new ArrayList<>());
    Collection col3 = new Collection(2, "Mad", new ArrayList<>());
    Collection col4 = new Collection(2, "Lad", new ArrayList<>());
    Collection col5 = new Collection(2, "Iad", new ArrayList<>());
    Collection col6 = new Collection(1,"Pad", new ArrayList<>());
    Collection col7 = new Collection(2, "Sad", new ArrayList<>());
    ArrayList<Collection> collections = new ArrayList<>(Arrays.asList(col1, col2, col3, col4, col5, col6, col7));

    public HomePage()
    {

        //loginFrame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        homeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        homeFrame.setLocationRelativeTo(null);
        homeFrame.setTitle("HomePage");

        homeFrame.setSize(FRAME_WIDTH, FRAME_HEIGHT);





        //setting up the panel for the ENTIRE screen

        JPanel homePanel = new JPanel();

        homePanel.setLayout(new GridBagLayout());

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
        HomePage.ListenForButton lForButton = new HomePage.ListenForButton();
        logoutButton.addActionListener(lForButton);

        saveButton = new JButton("Save");
        searchPanel.add(saveButton);

        //importing both

        JScrollPane collectionScroll = createCollectionScrollPane(collections);
        JScrollPane gameScroll = createGameScrollPane(coolGames);


        //putting all of them together

        homeFrame.add(homePanel);

        addComp(homePanel, searchPanel, 0, 0, 8, 2, GridBagConstraints.NORTH, GridBagConstraints.NONE);

        addComp(homePanel, gameScroll, 0, 2, 4, 2, GridBagConstraints.SOUTHWEST, GridBagConstraints.NONE);

        addComp(homePanel, collectionScroll, 0, 2, 4, 2, GridBagConstraints.SOUTHEAST, GridBagConstraints.NONE);



        homeFrame.pack();

        homeFrame.setVisible(true);
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

    //base constructor (used to test things)
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
    public JPanel createGame(String title, String imageUrl, int minPlayerCount, int maxPlayerCount, int minPlaytime, int maxPlaytime, int minAge, double avgRating, String genre, String description, ArrayList<Review> reviews, Game game)
    {
        JLabel genreLabel, playerCountLabel, nameLabel, imageLabel, playtimeLabel, ageLabel, ratingLabel;
        JButton addButton, seeMoreButton;
        JComboBox collectionsComboBox;
        BufferedImage gameImage = null;

        JPanel gamePanel = new JPanel();
        gamePanel.setLayout(new GridBagLayout());

        Box infoBox = Box.createVerticalBox();
        Box buttonBox = Box.createHorizontalBox();

        nameLabel = new JLabel("Name: " + title);
        genreLabel = new JLabel("Genre: " + genre);
        playerCountLabel = new JLabel("PlayerCount: " + minPlayerCount + " - " + maxPlayerCount);
        playtimeLabel = new JLabel("Playtime: " + minPlaytime + " - " + maxPlaytime + " (mins)");
        ageLabel = new JLabel("Age: " + minAge + "+");
        ratingLabel = new JLabel("Avg Rating: " + avgRating + "/10");
        addButton = new JButton("Add To Collection");
        seeMoreButton = new JButton("Info/Rate");

        collectionsComboBox = new JComboBox();

        for(int i = 0; i < collections.size(); i++)
        {
            collectionsComboBox.addItem(collections.get(i).getTitle());
        }




        class ListenForGameButton implements ActionListener
        {
            public void actionPerformed(ActionEvent e)
            {
                if (e.getSource() == addButton)
                {
                    for(int i = 0; i < collections.size(); i++)
                    {
                        if(collections.get(i).getTitle() == collectionsComboBox.getSelectedItem())
                        {
                            collections.get(i).addGame(game);
                            JOptionPane.showMessageDialog(null, "Collection added to: " + collections.get(i).getTitle());
                        }
                    }
                }
                if (e.getSource() == collectionsComboBox)
                {
                    collectionsComboBox.removeAllItems();
                    for(int i = 0; i < collections.size(); i++)
                    {
                        collectionsComboBox.addItem(collections.get(i).getTitle());
                    }
                    gamePanel.revalidate();
                }
                if (e.getSource() == seeMoreButton)
                {
                    new ReviewPage(imageUrl, description, reviews);
                }
            }
        }

        ListenForGameButton lForButton = new ListenForGameButton();
        addButton.addActionListener(lForButton);
        seeMoreButton.addActionListener(lForButton);

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

        buttonBox.add(addButton);
        buttonBox.add(Box.createHorizontalStrut(5));
        buttonBox.add(collectionsComboBox);
        buttonBox.add(Box.createHorizontalStrut(5));
        buttonBox.add(seeMoreButton);

        addComp(gamePanel, infoBox, 1, 0, 1, 1, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE);
        addComp(gamePanel, buttonBox, 1, 1, 1, 1, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE);


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
            gameBox.add(createGame(games.get(i).getTitle(), games.get(i).getImage(), games.get(i).getMinPlayers(), games.get(i).getMaxPlayers(), games.get(i).getMinPlaytime(), games.get(i).getMaxPlaytime(), games.get(i).getMinAge(), games.get(i).getAvgRating(), games.get(i).getGenre(), games.get(i).getDescription(), games.get(i).getReviews(), games.get(i)));
            gameBox.add(Box.createVerticalStrut(2));
        }

        JScrollPane gameScrollPane = new JScrollPane(gameBox, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        gameScrollPane.setPreferredSize(new Dimension(460, 220));

        return gameScrollPane;
    }

    //constructor for collection
    private JPanel createCollection(String title, Collection collection)
    {

        JPanel collectionPanel = new JPanel();
        collectionPanel.setLayout(new GridBagLayout());
        JLabel collectionTitle;
        JButton collectionButton;

        collectionTitle = new JLabel(title);
        collectionTitle.setFont(new Font("Ariel", Font.BOLD, 20));

        collectionButton = new JButton("Go To Collection");

        class ListenForCollectionButton implements ActionListener
        {
            public void actionPerformed(ActionEvent e)
            {
                if (e.getSource() == collectionButton)
                {
                   for(int i = 0; i < collections.size(); i++)
                   {
                       if (collections.get(i).getTitle() == collection.getTitle())
                       {
                           new CollectionPage(collections.get(i), collections.get(i).getTitle());
                       }
                   }
                }
            }
        }

        ListenForCollectionButton lForButton = new ListenForCollectionButton();
        collectionButton.addActionListener(lForButton);

        class ListenForMouse extends MouseAdapter {

            @Override
            public void mouseClicked(MouseEvent e)
            {
                String collectionName = JOptionPane.showInputDialog("New Name Here", "new name");
                String previousCollectionName = collectionTitle.getText();
                System.out.println("Collection: " + collectionName);
                //DO NOT NAME AS " ", WILL BREAK PROGRAM
                if(collectionName != " " && collectionName != null)
                {
                    collectionTitle.setText(collectionName);
                }
                else
                {
                    collectionTitle.setText(previousCollectionName);
                }
            }
        }

        addComp(collectionPanel, collectionTitle, 1, 0, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.NONE);
        addComp(collectionPanel, collectionButton, 1, 1, 5, 4, GridBagConstraints.CENTER, GridBagConstraints.NONE);
        ListenForMouse lForMouse = new ListenForMouse();
        collectionTitle.addMouseListener(lForMouse);

        Border collectionBorder = BorderFactory.createLineBorder(Color.black);
        collectionPanel.setBorder(collectionBorder);




        return collectionPanel;
    }

    public JScrollPane createCollectionScrollPane(ArrayList<Collection> collections)
    {
        Box collectionBox = Box.createVerticalBox();
        
        for(int i = 0; i < collections.size(); i++)
        {
            collectionBox.add(createCollection(collections.get(i).getTitle(), collections.get(i)));
            collectionBox.add(Box.createVerticalStrut(2));
        }
        
        JScrollPane collectionScroll = new JScrollPane(collectionBox, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        collectionScroll.setPreferredSize(new Dimension(170, 220));
        
        return collectionScroll;
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
                   homeFrame.dispose();
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