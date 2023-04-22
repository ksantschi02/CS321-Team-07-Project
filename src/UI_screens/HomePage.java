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
    // Filter Page variables
    JFrame filterFrame;
    JPanel filterPanel;
    JButton filterButton, saveButton, logoutButton, searchButton, createCollectionButton, deleteCollectionButton;
    JTextField searchTextField;
    JFrame homeFrame = new JFrame();
    JScrollPane gameScroll;

    // global box variables to hold games and collections
    Box collectionBox = Box.createVerticalBox();
    Box gameBox = Box.createVerticalBox();

    // XML data variables
    Database homepageData;
    ArrayList<Collection> collections;
    User currentUser;

    // other necessary global variables
    Collection allGames;
    boolean refresh = false;
    JPanel homePanel;
    boolean minPlay_ = false, maxPlay_ = false, age_ = false, genre_= false,
            avgRat_ = false, playtime_ = false, title_ = false;

    /**
     * Creates HomePage Screen and GUI based on inputted parameters, allows the User to make/delete colletions,
     * look at reviews, add games to collections, search, filter, change collection names, log out, etc.
     * @param coolGames Takes an arraylist of games from which to dynamically fill up the page
     * @param user Should contain the current user of the program (from the LoginPage)
     * @param data The database to which the program will pull from and save to
     */
    public HomePage(ArrayList<Game> coolGames, User user, Database data)
    {
        // constraints for frame
        homeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        homeFrame.setTitle("HomePage");

        // make XML and current user global
        collections = user.getCollections();
        currentUser = user;
        homepageData = data;

        // setting up layout
        homePanel = new JPanel();
        homePanel.setLayout(new GridBagLayout());

        // setting up panel for search bar and filter GUI
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

        // create buttons for removing and adding collections
        createCollectionButton = new JButton("Create Collection");
        deleteCollectionButton = new JButton("Delete Collection");

        // importing collections and list of games
        JScrollPane collectionScroll = createCollectionScrollPane(collections);
        gameScroll = createGameScrollPane(coolGames);

        // need all the games in a global collection
        allGames = new Collection(0, 0, "Home Page Games", coolGames);
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

        // Action Listeners for create and delete buttons
        ListenForHomePageButton lforhomepage = new ListenForHomePageButton();
        createCollectionButton.addActionListener(lforhomepage);
        deleteCollectionButton.addActionListener(lforhomepage);

        // GUI box for create and delete buttons
        Box collectionButtonBox = Box.createHorizontalBox();
        collectionButtonBox.add(createCollectionButton);
        collectionButtonBox.add(Box.createHorizontalStrut(10));
        collectionButtonBox.add(deleteCollectionButton);

        // global refresh if anything changes the list of games
        if(refresh)
        {
            gameScroll.revalidate();
            refresh = false;
        }

        // putting everything together
        addComp(homePanel, searchPanel, 0, 0, 8, 2, GridBagConstraints.NORTH, GridBagConstraints.NONE);
        addComp(homePanel, gameScroll, 0, 2, 4, 2, GridBagConstraints.SOUTHWEST, GridBagConstraints.NONE);
        addComp(homePanel, collectionScroll, 0, 2, 4, 2, GridBagConstraints.SOUTHEAST, GridBagConstraints.NONE);
        addComp(homePanel, collectionButtonBox, 3, 4, 1, 1, GridBagConstraints.SOUTHEAST, GridBagConstraints.NONE);

        // homeFrame constraints
        homeFrame.add(homePanel);
        homeFrame.pack();
        homeFrame.setLocationRelativeTo(null);
        homeFrame.setVisible(true);
    }

    /**
     * Function that adds components to the GridBagLayout in a streamlined manner
     * @param thePanel the Panel that is going to be added too
     * @param comp what is going to be added to the panel
     * @param xPos where the component is going to go in the layout along the x-axis
     * @param yPos where the component is going to go in the layout along the y-axis
     * @param compWidth how much space the component will take up in the x-axis in comparison to the other components
     * @param compHeight how much space the component will take up in the y-axis in comparison to the other components
     * @param place the gridbagconstraint that this component will align to if no other components are blocking it
     * @param stretch whether the component will stretch to fill empty space or not
     */
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

    /**
     * creates a game panel from the provided parameters, so it can be added to a ScrollPane
     * @param title the game title
     * @param imageUrl the image url
     * @param minPlayerCount the minimum player count
     * @param maxPlayerCount the maximum player count
     * @param minPlaytime the minimum playtime for the game
     * @param maxPlaytime the maximum playtime for the game
     * @param minAge the minimum age to play the game
     * @param avgRating the average rating of all reviews for the game
     * @param genre string of genre tags for a game
     * @param description the description (if available) for the game
     * @param reviews an arraylist of all the reviews for an individual game
     * @param game the game object that all this information is being pulled from
     * @return returns a JPanel with all the data incorporated in it
     */
    public JPanel createGame(String title, String imageUrl, int minPlayerCount, int maxPlayerCount, int minPlaytime, int maxPlaytime, int minAge, double avgRating, ArrayList<String> genre, String description, ArrayList<Review> reviews, Game game)
    {
        // variables
        JLabel genreLabel, playerCountLabel, nameLabel, imageLabel, playtimeLabel, ageLabel, ratingLabel;
        JButton addButton, seeMoreButton;
        JComboBox collectionsComboBox;
        BufferedImage gameImage = null;

        // set up panel
        JPanel gamePanel = new JPanel();
        gamePanel.setLayout(new GridBagLayout());

        // boxes to make panel look nice
        Box infoBox = Box.createVerticalBox();
        Box buttonBox = Box.createHorizontalBox();

        // initializing a game onto the panel
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

        // combobox to choose what collection to add to
        collectionsComboBox = new JComboBox();

        for(int i = 0; i < collections.size(); i++)
        {
            collectionsComboBox.addItem(collections.get(i).getTitle());
        }

        // Action Listener for buttons on each individual game panel
        class ListenForGameButton implements ActionListener
        {
            public void actionPerformed(ActionEvent e)
            {
                if (e.getSource() == addButton)
                {

                    boolean canAddGame = true;
                    // loop that adds a game to a collection
                    for(int i = 0; i < collections.size(); i++)
                    {
                        // picks collection from the combobox choice
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
                    // creates a ReviewPage based on the game selected
                    new ReviewPage(imageUrl, description, reviews, currentUser);
                }
            }
        }

        // PopUp Listener that allows the combobox to refresh with new names when it is clicked
        class ListenForPopUp implements PopupMenuListener
        {
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                // refresh the combobox
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

        // add all action listeners to buttons
        ListenForGameButton lForButton = new ListenForGameButton();
        ListenForPopUp lForPopUp = new ListenForPopUp();
        addButton.addActionListener(lForButton);
        seeMoreButton.addActionListener(lForButton);
        collectionsComboBox.addPopupMenuListener(lForPopUp);

        // add all the game info together
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

        // add all the buttons together
        buttonBox.add(addButton);
        buttonBox.add(Box.createHorizontalStrut(5));
        buttonBox.add(collectionsComboBox);
        buttonBox.add(Box.createHorizontalStrut(5));
        buttonBox.add(seeMoreButton);

        // add components to the panel
        addComp(gamePanel, infoBox, 1, 0, 1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE);
        addComp(gamePanel, buttonBox, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE);

        // code to import the url and turn it into an image
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

        // add image to the panel
        addComp(gamePanel, imageLabel, 0, 0, 1, 2, GridBagConstraints.WEST, GridBagConstraints.NONE);

        // add a border to each individual panel
        Border gameBorder = BorderFactory.createLineBorder(Color.black);
        gamePanel.setBorder(gameBorder);

        return gamePanel;
    }

    /**
     * Creates the scrollable panel that will hold all the games in an imported Game arraylist
     * also calls creategame for all games in the arraylist
     * @param games the arraylist of games being imported
     * @return the JScrollPane
     */
    public JScrollPane createGameScrollPane(ArrayList<Game> games)
    {
        // create a JScrollPane with all the games and their UI implemented
        for(int i = 0; i < games.size(); i++)
        {
            gameBox.add(createGame(games.get(i).getTitle(), games.get(i).getImage(), games.get(i).getMinPlayers(), games.get(i).getMaxPlayers(), games.get(i).getMinPlaytime(), games.get(i).getMaxPlaytime(), games.get(i).getMinAge(), games.get(i).getAvgRating(), games.get(i).getGenre(), games.get(i).getDescription(), games.get(i).getReviews(), games.get(i)));
            gameBox.add(Box.createVerticalStrut(2));
        }
        JScrollPane gameScrollPane = new JScrollPane(gameBox, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        gameScrollPane.setPreferredSize(new Dimension(460, 420));

        return gameScrollPane;
    }

    /**
     * creates a collection panel from the parameters provided
     * @param title collection title
     * @param collection collection of games being put in the panel
     * @return the collection panel that will go in the JScrollPane for collections
     */
    private JPanel createCollection(String title, Collection collection)
    {
        // variables
        JPanel collectionPanel = new JPanel();
        collectionPanel.setLayout(new GridBagLayout());
        JLabel collectionTitle;
        JButton collectionButton;

        // initialize some things
        collectionTitle = new JLabel(title);
        collectionTitle.setFont(new Font("Ariel", Font.BOLD, 20));
        collectionButton = new JButton("Go To Collection");

        // action listener for go to collection button
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
                           // creates a collection page based on current collection
                           new CollectionPage(collections.get(i), collections.get(i).getTitle(), currentUser);
                           cool++;
                       }
                   }
                }
            }
        }

        // add action listener
        ListenForCollectionButton lForButton = new ListenForCollectionButton();
        collectionButton.addActionListener(lForButton);

        // mouse listener for changing the name of a collection
        class ListenForMouse extends MouseAdapter {

            @Override
            public void mouseClicked(MouseEvent e)
            {
                String collectionName = JOptionPane.showInputDialog("New Name Here", "new name");
                String previousCollectionName = collectionTitle.getText();
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

        // add everything to a panel
        addComp(collectionPanel, collectionTitle, 1, 0, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.NONE);
        addComp(collectionPanel, collectionButton, 1, 1, 5, 4, GridBagConstraints.CENTER, GridBagConstraints.NONE);

        // add mouse listener
        ListenForMouse lForMouse = new ListenForMouse();
        collectionTitle.addMouseListener(lForMouse);

        // give panel border
        Border collectionBorder = BorderFactory.createLineBorder(Color.black);
        collectionPanel.setBorder(collectionBorder);

        return collectionPanel;
    }

    // dynamically creates a CollectionScrollPane from an arraylist
    /**
     * creates a JScrollPane containing a bunch of Collection Panels
     * implements the createCollection function
     * @param collections the arraylist of collections from a user that will be added to the pane
     * @return the finished JScrollPane to be used in GUI
     */
    public JScrollPane createCollectionScrollPane(ArrayList<Collection> collections)
    {
        // creates the JScrollPane
        for(int i = 0; i < collections.size(); i++)
        {
            collectionBox.add(createCollection(collections.get(i).getTitle(), collections.get(i)));
            collectionBox.add(Box.createVerticalStrut(2));
        }
        JScrollPane collectionScroll = new JScrollPane(collectionBox, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        collectionScroll.setPreferredSize(new Dimension(170, 420));
        
        return collectionScroll;
    }

    // ActionListener for HomePanel GUI buttons
    public class ListenForButton implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            if (e.getSource() == logoutButton)
            {
               int answer = JOptionPane.showConfirmDialog(null, "Are you sure?");
               if(answer == JOptionPane.YES_OPTION)
               {
                   // saves current changes to reviews, collections, etc.
                   homepageData.saveDatabase();
                   homeFrame.dispose();
               }
            }
            if (e.getSource() == filterButton)
            {
                // frame settings
                filterFrame = new JFrame();
                filterFrame.setTitle("FilterPage");
                filterPanel = new JPanel();
                filterPanel.setLayout(new GridBagLayout());

                // variables
                JLabel minPlayerFilter, maxPlayerFilter, avgRatingFilter, playtimeFilter, ageFilter, genreFilter, titleFilter;
                JComboBox minPlayerComboBox, maxPlayerComboBox, avgRatingComboBox, playtimeComboBox, ageComboBox, genreComboBox, titleComboBox;
                JButton backFilterButton, applyFilterButton;

                // initializing all labels, comboboxes, etc.
                minPlayerFilter = new JLabel("Min Players: ");
                maxPlayerFilter = new JLabel("Max Players: ");
                ageFilter = new JLabel("Age: ");
                genreFilter = new JLabel("Genre: ");
                avgRatingFilter = new JLabel("Avg Rating: ");
                playtimeFilter = new JLabel("Playtime: ");
                titleFilter = new JLabel("Title: ");

                backFilterButton = new JButton("Back");
                applyFilterButton = new JButton("Apply Filter");

                String[] minPlay = {" ", "<=1", "<=2", "<=3", "<=4"};
                minPlayerComboBox = new JComboBox<>(minPlay);
                String[] maxPlay = {" ", ">=1", ">=2", ">=3", ">=4", ">=5", ">=6", ">=7", ">=8"};
                maxPlayerComboBox = new JComboBox<>(maxPlay);
                String[] age = {" ", "<=3", "<=7", "<=12", "<=16"};
                ageComboBox = new JComboBox<>(age);
                String[] genre = {" ", "Card Game", "Science Fiction", "Economic", "Adventure", "Educational", "Deduction", "Exploration", "Animals", "Movies / TV / Radio Theme", "Fantasy", "Fighting", "Civilization", "Puzzle"};
                genreComboBox = new JComboBox<>(genre);
                String[] avgRat = {" ", "asc", "desc"};
                avgRatingComboBox = new JComboBox<>(avgRat);
                String[] playtime = {" ", "asc", "desc"};
                playtimeComboBox = new JComboBox<>(playtime);
                String[] title = {" ", "asc", "desc"};
                titleComboBox = new JComboBox<>(title);

                // set selected index equal to current filter applied
                if (minPlay_)
                {
                    minPlayerComboBox.setSelectedIndex(allGames.getFilter().getFilterType());
                } else if (maxPlay_)
                {
                    maxPlayerComboBox.setSelectedIndex(allGames.getFilter().getFilterType() - 4);
                } else if (age_)
                {
                    ageComboBox.setSelectedIndex(allGames.getFilter().getFilterType() - 12);
                } else if (genre_)
                {
                    genreComboBox.setSelectedIndex(allGames.getFilter().getFilterType() - 16);
                }

                // set selected index equal to current sort applied
                if (avgRat_)
                {
                    avgRatingComboBox.setSelectedIndex(allGames.getFilter().getSortType());
                } else if (playtime_)
                {
                    playtimeComboBox.setSelectedIndex(allGames.getFilter().getSortType() - 4);
                } else if (title_)
                {
                    titleComboBox.setSelectedIndex(allGames.getFilter().getSortType() - 2);
                }

                // action listener for back and set filter buttons on panel
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
                            // check which combo boxes are changed
                            minPlay_ = false; maxPlay_ = false; age_ = false; genre_= false;
                            avgRat_ = false; playtime_ = false; title_ = false;
                            if (minPlayerComboBox.getSelectedIndex() > 0) {minPlay_ = true;}
                            if (maxPlayerComboBox.getSelectedIndex() > 0) {maxPlay_ = true;}
                            if (ageComboBox.getSelectedIndex() > 0) {age_ = true;}
                            if (genreComboBox.getSelectedIndex() > 0) {genre_ = true;}
                            if (avgRatingComboBox.getSelectedIndex() > 0) {avgRat_ = true;}
                            if (playtimeComboBox.getSelectedIndex() > 0) {playtime_ = true;}
                            if (titleComboBox.getSelectedIndex() > 0) {title_ = true;}

                            // if more than one filter or sort is checked
                            if (((minPlay_ ? 1:0) + (maxPlay_ ? 1:0) + (age_ ? 1:0) + (genre_ ? 1:0)) > 1 ||
                                ((avgRat_ ? 1:0) + (playtime_ ? 1:0) + (title_ ? 1:0)) > 1)
                            {
                                // do nothing
                            } else
                            {
                                // edit the filter type according to comboboxes
                                if (minPlay_)
                                {
                                    allGames.editFilterType(minPlayerComboBox.getSelectedIndex());
                                } else if (maxPlay_)
                                {
                                    allGames.editFilterType(maxPlayerComboBox.getSelectedIndex() + 4);
                                } else if (age_)
                                {
                                    allGames.editFilterType(ageComboBox.getSelectedIndex() + 12);
                                } else if (genre_)
                                {
                                    allGames.editFilterType(genreComboBox.getSelectedIndex() + 16);
                                } else
                                {
                                    allGames.editFilterType(0);
                                }

                                // edit the sort type according to comboboxes
                                if (avgRat_)
                                {
                                    allGames.editSortType(avgRatingComboBox.getSelectedIndex());
                                } else if (playtime_)
                                {
                                    allGames.editSortType(playtimeComboBox.getSelectedIndex() + 4);
                                } else if (title_)
                                {
                                    allGames.editSortType(titleComboBox.getSelectedIndex() + 2);
                                } else
                                {
                                    allGames.editSortType(0);
                                }

                                // update ui
                                gameBox.removeAll();

                                ArrayList<Game> results = allGames.search(searchTextField.getText());

                                for (Game g : results) {
                                    gameBox.add(createGame(g.getTitle(), g.getImage(), g.getMinPlayers(), g.getMaxPlayers(), g.getMinPlaytime(), g.getMaxPlaytime(), g.getMinAge(), g.getAvgRating(), g.getGenre(), g.getDescription(), g.getReviews(), g));
                                    gameBox.add(Box.createVerticalStrut(2));
                                }

                                gameScroll.revalidate();

                                filterFrame.dispose();
                            }
                        }
                    }
                }

                // add the action listeners to the buttons
                ListenForFilterButton lForFilterButton = new ListenForFilterButton();
                applyFilterButton.addActionListener(lForFilterButton);
                backFilterButton.addActionListener(lForFilterButton);



                // add all the GUI to the panel
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

                // filter frame constraints
                filterFrame.add(filterPanel);
                filterFrame.pack();
                filterFrame.setLocationRelativeTo(null);
                filterFrame.setVisible(true);
            }
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
