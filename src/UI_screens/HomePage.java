package UI_screens;

import data_retrieval.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import javax.imageio.ImageIO;
import java.io.IOException;
import javax.swing.border.*;
import java.util.ArrayList;
import game_info.*;
import program_users.*;

public class HomePage extends JFrame
{


    //filterPanel stuff
    JFrame filterFrame;
    JPanel filterPanel;
    JButton filterButton, saveButton, logoutButton, searchButton, createCollectionButton, deleteCollectionButton, refreshButton;
    JTextField searchTextField;
    JFrame homeFrame = new JFrame();
    JScrollPane gameScroll;

    Box collectionBox = Box.createVerticalBox();
    Box gameBox = Box.createVerticalBox();
    Database homepageData;
    ArrayList<Collection> collections;
    User currentUser;
    Collection allGames;
    boolean refresh = false;

    JPanel homePanel;

    public HomePage(ArrayList<Game> coolGames, User user, Database data)
    {

        //loginFrame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        homeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        homeFrame.setTitle("HomePage");


        collections = user.getCollections();

        currentUser = user;
        homepageData = data;
        //setting up the panel for the ENTIRE screen

        homePanel = new JPanel();

        homePanel.setLayout(new GridBagLayout());

        //setting up panel for search bar and filter

        JPanel searchPanel = new JPanel();

        searchPanel.setLayout(new FlowLayout());

        HomePage.ListenForButton lForButton = new HomePage.ListenForButton();

        filterButton = new JButton("Filter");
        searchPanel.add(filterButton);
        filterButton.addActionListener(lForButton);

        searchTextField = new JTextField(30);
        searchPanel.add(searchTextField);

        searchButton = new JButton("Search");
        searchPanel.add(searchButton);
        searchButton.addActionListener(lForButton);

        logoutButton = new JButton("Logout");
        searchPanel.add(logoutButton);
        logoutButton.addActionListener(lForButton);

        saveButton = new JButton("Save");
        searchPanel.add(saveButton);
        saveButton.addActionListener(lForButton);

        createCollectionButton = new JButton("Create Collection");
        deleteCollectionButton = new JButton("Delete Collection");

        //importing both

        JScrollPane collectionScroll = createCollectionScrollPane(collections);
        gameScroll = createGameScrollPane(coolGames);

        // need all the games in a global collection
        allGames = new Collection(0, 0, null, coolGames);
        class ListenForHomePageButton implements ActionListener
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if(e.getSource() == createCollectionButton)
                {
                    boolean sameCol = false;
                    String newCollection = JOptionPane.showInputDialog(null, "Please enter the name of the Collection");
                    if(newCollection != null)
                    {
                        for(int i = 0; i < currentUser.getCollections().size(); i++)
                        {
                            if(currentUser.getCollections().get(i).getTitle().equals(newCollection))
                            {
                                sameCol = true;
                            }
                        }
                        if(sameCol)
                        {
                            JOptionPane.showMessageDialog(null, "Name is already being used.");
                            sameCol = false;
                        }
                        else
                        {
                            currentUser.addCollection(new Collection(1, 1, newCollection, new ArrayList<Game>()));
                            collectionBox.removeAll();
                            for(int i = 0; i < collections.size(); i++)
                            {
                                collectionBox.add(createCollection(collections.get(i).getTitle(), collections.get(i)));
                                collectionBox.add(Box.createVerticalStrut(2));
                            }
                            collectionScroll.revalidate();
                        }
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "Invalid Input. Try Again.");
                    }


                }
                if(e.getSource() == deleteCollectionButton)
                {
                    String deleteCollection = JOptionPane.showInputDialog(null, "Please enter the name of the Collection you'd like deleted");
                    if(deleteCollection != null)
                    {
                        for(int i = 0; i < currentUser.getCollections().size(); i++)
                        {
                            if(currentUser.getCollections().get(i).getTitle().equals(deleteCollection))
                            {
                                collections.remove(currentUser.getCollections().get(i));
                                collectionBox.removeAll();
                                for(int j = 0; j < collections.size(); j++)
                                {
                                    collectionBox.add(createCollection(collections.get(j).getTitle(), collections.get(j)));
                                    collectionBox.add(Box.createVerticalStrut(2));
                                }
                                collectionScroll.revalidate();
                            }
                        }
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "Collection doesn't exist");
                    }

                }
            }
        }

        ListenForHomePageButton lforhomepage = new ListenForHomePageButton();


        //refresh button
        refreshButton = new JButton("Refresh Collection Names");
        refreshButton.addActionListener(lForButton);

        //create and delete collection buttons

        Box collectionButtonBox = Box.createHorizontalBox();

        createCollectionButton.addActionListener(lforhomepage);
        deleteCollectionButton.addActionListener(lforhomepage);

        collectionButtonBox.add(createCollectionButton);
        collectionButtonBox.add(Box.createHorizontalStrut(10));
        collectionButtonBox.add(deleteCollectionButton);

        //putting all of them together

        homeFrame.add(homePanel);

        addComp(homePanel, searchPanel, 0, 0, 8, 2, GridBagConstraints.NORTH, GridBagConstraints.NONE);

        addComp(homePanel, gameScroll, 0, 2, 4, 2, GridBagConstraints.SOUTHWEST, GridBagConstraints.NONE);

        addComp(homePanel, collectionScroll, 0, 2, 4, 2, GridBagConstraints.SOUTHEAST, GridBagConstraints.NONE);

        addComp(homePanel, collectionButtonBox, 3, 4, 1, 1, GridBagConstraints.SOUTHEAST, GridBagConstraints.NONE);


        if(refresh)
        {
            gameScroll.revalidate();
            refresh = false;
        }



        homeFrame.pack();
        homeFrame.setLocationRelativeTo(null);
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


    //constructor with parameters
    public JPanel createGame(String title, String imageUrl, int minPlayerCount, int maxPlayerCount, int minPlaytime, int maxPlaytime, int minAge, double avgRating, ArrayList<String> genre, String description, ArrayList<Review> reviews, Game game)
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
        genreLabel = new JLabel("Genre: " + genre.get(0));
        for(int i = 1; i < 2; i++)
        {
            if(i < genre.size())
            {
                genreLabel.setText(genreLabel.getText() + ", " + genre.get(i));
            }
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
        if(reviews.isEmpty())
        {
            ratingLabel = new JLabel("No Reviews Yet");
        }
        else
        {
            ratingLabel = new JLabel("Avg Rating: " + avgRating + "/10");
        }
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

                    boolean canAddGame = true;

                    for(int i = 0; i < collections.size(); i++)
                    {
                        if(collections.get(i).getTitle().equals(collectionsComboBox.getSelectedItem()))
                        {
                            for(int j = 0; j < collections.get(i).getSize(); j++)
                            {
                                if(collections.get(i).getGames().get(j).getTitle().equals(game.getTitle()))
                                {
                                    canAddGame = false;
                                }
                            }
                            if(canAddGame)
                            {
                                collections.get(i).addGame(game);
                                JOptionPane.showMessageDialog(null, "Game Added to Collection: " + collections.get(i).getTitle());
                            }
                            else
                            {
                                JOptionPane.showMessageDialog(null, "Game Is Already In Collection: " + collections.get(i).getTitle());
                            }
                        }
                    }
                }
                if (e.getSource() == seeMoreButton)
                {
                    new ReviewPage(imageUrl, description, reviews, currentUser, homepageData, avgRating);
                }
            }
        }

        class ListenForPopUp implements PopupMenuListener
        {
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                collectionsComboBox.removeAllItems();
                for(int i = 0; i < collections.size(); i++)
                {
                    collectionsComboBox.addItem(collections.get(i).getTitle());
                }
                refresh = true;
            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {

            }

            @Override
            public void popupMenuCanceled(PopupMenuEvent e) {

            }
        }


        ListenForGameButton lForButton = new ListenForGameButton();
        ListenForPopUp lForPopUp = new ListenForPopUp();
        addButton.addActionListener(lForButton);
        seeMoreButton.addActionListener(lForButton);
        collectionsComboBox.addPopupMenuListener(lForPopUp);

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


        addComp(gamePanel, infoBox, 1, 0, 1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE);
        addComp(gamePanel, buttonBox, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE);


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
        gamePanel.setName(title);

        return gamePanel;

    }

    //dynamically createsGameScrollPane from array
    public JScrollPane createGameScrollPane(ArrayList<Game> games)
    {
        for(int i = 0; i < games.size(); i++)
        {
            gameBox.add(createGame(games.get(i).getTitle(), games.get(i).getImage(), games.get(i).getMinPlayers(), games.get(i).getMaxPlayers(), games.get(i).getMinPlaytime(), games.get(i).getMaxPlaytime(), games.get(i).getMinAge(), games.get(i).getAvgRating(), games.get(i).getGenre(), games.get(i).getDescription(), games.get(i).getReviews(), games.get(i)));
            gameBox.add(Box.createVerticalStrut(2));
        }

        JScrollPane gameScrollPane = new JScrollPane(gameBox, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        gameScrollPane.setPreferredSize(new Dimension(460, 420));

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
                    int cool = 0;
                   for(int i = 0; i < collections.size(); i++)
                   {
                       if (collections.get(i).getTitle().equals(collection.getTitle()) && cool < 1)
                       {
                           new CollectionPage(collections.get(i), collections.get(i).getTitle(), homepageData, currentUser);
                           cool++;
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
                if((!collectionName.equals(" ")) && collectionName != null)
                {
                    collectionTitle.setText(collectionName);
                    for(int i = 0; i < collections.size(); i++)
                    {
                        if(collections.get(i).getTitle().equals(previousCollectionName))
                        {
                            collections.get(i).editTitle(collectionName);
                        }
                    }
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
        
        for(int i = 0; i < collections.size(); i++)
        {
            collectionBox.add(createCollection(collections.get(i).getTitle(), collections.get(i)));
            collectionBox.add(Box.createVerticalStrut(2));
        }
        
        JScrollPane collectionScroll = new JScrollPane(collectionBox, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        collectionScroll.setPreferredSize(new Dimension(170, 420));
        
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
                   homepageData.saveDatabase();
                   homeFrame.dispose();
               }
            }
            // Where to make Filter Frame
            if (e.getSource() == filterButton)
            {
                filterFrame = new JFrame();
                filterFrame.setTitle("FilterPage");

                filterPanel = new JPanel();
                filterPanel.setLayout(new GridBagLayout());

                JLabel minPlayerFilter, maxPlayerFilter, avgRatingFilter, playtimeFilter, ageFilter, genreFilter, titleFilter;
                JComboBox minPlayerComboBox, maxPlayerComboBox, avgRatingComboBox, playtimeComboBox, ageComboBox, genreComboBox, titleComboBox;
                JButton backFilterButton, applyFilterButton;

                minPlayerFilter = new JLabel("Min Players: ");
                maxPlayerFilter = new JLabel("Max Players: ");
                ageFilter = new JLabel("Age: ");
                genreFilter = new JLabel("Genre: ");
                avgRatingFilter = new JLabel("Avg Rating: ");
                playtimeFilter = new JLabel("Playtime: ");
                titleFilter = new JLabel("Title: ");

                backFilterButton = new JButton("Back");
                applyFilterButton = new JButton("Apply Filter");

                String[] minPlay = {" ", "1", "2", "3", "4"};
                minPlayerComboBox = new JComboBox<>(minPlay);
                String[] maxPlay = {" ", "1", "2", "3", "4", "5", "6", "7", "8"};
                maxPlayerComboBox = new JComboBox<>(maxPlay);
                String[] age = {" ", "3", "7", "12", "16"};
                ageComboBox = new JComboBox<>(age);
                String[] genre = {" ", "family", "dexterity", "party", "abstract", "thematic", "euro", "war"};
                genreComboBox = new JComboBox<>(genre);
                String[] avgRat = {" ", "asc", "desc"};
                avgRatingComboBox = new JComboBox<>(avgRat);
                String[] playTime = {" ", "asc", "desc"};
                playtimeComboBox = new JComboBox<>(playTime);
                String[] title = {" ", "asc", "desc"};
                titleComboBox = new JComboBox<>(title);

                class ListenForFilterButton implements ActionListener
                {
                    @Override
                    public void actionPerformed(ActionEvent e)
                    {

                        if(e.getSource() == backFilterButton)
                        {
                            filterFrame.dispose();
                        }
                        if(e.getSource() == applyFilterButton)
                        {

                        }

                    }
                }

                ListenForFilterButton lForFilterButton = new ListenForFilterButton();

                applyFilterButton.addActionListener(lForFilterButton);
                backFilterButton.addActionListener(lForFilterButton);




                addComp(filterPanel, minPlayerFilter, 0, 0, 1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE);
                addComp(filterPanel, minPlayerComboBox, 1, 0, 1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE);

                addComp(filterPanel, maxPlayerFilter, 0, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE);
                addComp(filterPanel, maxPlayerComboBox, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE);

                addComp(filterPanel, ageFilter, 0, 2, 1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE);
                addComp(filterPanel, ageComboBox, 1, 2, 1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE);

                addComp(filterPanel, genreFilter, 0, 3, 1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE);
                addComp(filterPanel, genreComboBox, 1, 3, 1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE);

                addComp(filterPanel, avgRatingFilter, 2, 0, 1, 1, GridBagConstraints.EAST, GridBagConstraints.NONE);
                addComp(filterPanel, avgRatingComboBox, 3, 0, 1, 1, GridBagConstraints.EAST, GridBagConstraints.NONE);

                addComp(filterPanel, playtimeFilter, 2, 1, 1, 1, GridBagConstraints.EAST, GridBagConstraints.NONE);
                addComp(filterPanel, playtimeComboBox, 3, 1, 1, 1, GridBagConstraints.EAST, GridBagConstraints.NONE);

                addComp(filterPanel, titleFilter, 2, 2, 1, 1, GridBagConstraints.EAST, GridBagConstraints.NONE);
                addComp(filterPanel, titleComboBox, 3, 2, 1, 1, GridBagConstraints.EAST, GridBagConstraints.NONE);

                addComp(filterPanel, backFilterButton, 0, 4, 1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE);
                addComp(filterPanel, applyFilterButton, 3, 4, 1, 1, GridBagConstraints.EAST, GridBagConstraints.NONE);

                filterFrame.add(filterPanel);
                filterFrame.pack();
                filterFrame.setLocationRelativeTo(null);
                filterFrame.setVisible(true);
            }
            // Where to implement Search Feature
            if (e.getSource() == searchButton)
            {
                gameBox.removeAll();

                ArrayList<Game> results = allGames.search(searchTextField.getText());
                for (Game g : results) {
                    gameBox.add(createGame(g.getTitle(), g.getImage(), g.getMinPlayers(), g.getMaxPlayers(), g.getMinPlaytime(), g.getMaxPlaytime(), g.getMinAge(), g.getAvgRating(), g.getGenre(), g.getDescription(), g.getReviews(), g));
                    gameBox.add(Box.createVerticalStrut(2));
                }

                gameScroll.revalidate();
            }
            if (e.getSource() == saveButton)
            {
                homepageData.saveDatabase();
            }
        }
    }
}
