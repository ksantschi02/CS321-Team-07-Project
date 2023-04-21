package UI_screens;

import data_retrieval.Database;
import game_info.Collection;
import game_info.Game;
import program_users.User;

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
import game_info.*;

public class CollectionPage extends JPanel
{
    // filter frame and collection frame variables
    JButton filterButton, backButton, searchButton, removeGameButton, moveGameButton;
    JPanel filterPanel;
    JTextField searchTextField;
    JFrame collectionFrame, filterFrame;
    JScrollPane gameScroll;
    Box interactBox;
    Box gameBox = Box.createVerticalBox();
    JComboBox userRank = new JComboBox();

    // variables to make parameters global
    User currentUser;
    Collection allCollectionGames;

    /**
     * creates a CollectionPage where the user can rank (move) games, remove games, and search and filter them in their collections
     * @param collection the collection being used or edited
     * @param title the title of the collection
     * @param user the current user in the program
     */
    public CollectionPage(Collection collection, String title, User user)
    {
        // collection frame settings
        collectionFrame = new JFrame();
        collectionFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        collectionFrame.setTitle(title);

        // setting global variable
        currentUser = user;

        // setting layout
        JPanel collectionPanel = new JPanel();
        collectionPanel.setLayout(new GridBagLayout());

        // setting up panel for search bar and filter
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout());
        allCollectionGames = new Collection(0, 0, null, currentUser.getCollection(title).getGames());

        // initializing combobox
        for(int i = 0; i < collection.getSize(); i++)
        {
            userRank.addItem(i + 1);
        }

        // initializing and adding action listeners
        CollectionPage.ListenForButton lForButton = new CollectionPage.ListenForButton();

        filterButton = new JButton("Filter");
        searchPanel.add(filterButton);
        filterButton.addActionListener(lForButton);

        searchTextField = new JTextField(30);
        searchPanel.add(searchTextField);

        searchButton = new JButton("Search");
        searchPanel.add(searchButton);
        searchButton.addActionListener(lForButton);

        backButton = new JButton("Back to Main Menu");
        searchPanel.add(backButton);
        backButton.addActionListener(lForButton);

        removeGameButton = new JButton("Remove a Game");
        moveGameButton = new JButton("Change Rank");

        //importing games
        gameScroll = createGameScrollPane(currentUser.getCollection(title).getGames());

        // action listener for removing and moving game buttons
        class ListenForCollectionButton implements ActionListener
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == removeGameButton)
                {
                    String reGame = JOptionPane.showInputDialog("Which game would you like to remove? (Use Rank #)", 1);
                    if(reGame != null)
                    {
                        Integer reGameInt;
                        reGameInt = Integer.parseInt(reGame);
                        if((reGameInt > 0) && (reGameInt <= collection.getSize()))
                        {
                            for(int i = 0; i < collection.getGames().size(); i++)
                            {
                                if(i == reGameInt - 1)
                                {
                                    // removes game and refreshes pane
                                    user.getCollection(title).getGames().remove(i);
                                    gameBox.removeAll();
                                    for(int j = 0; j < collection.getGames().size(); j++)
                                    {
                                        gameBox.add(createGame(user.getCollection(title).getGames().get(j).getTitle(), user.getCollection(title).getGames().get(j).getImage(), user.getCollection(title).getGames().get(j).getMinPlayers(), user.getCollection(title).getGames().get(j).getMaxPlayers(), user.getCollection(title).getGames().get(j).getMinPlaytime(), user.getCollection(title).getGames().get(j).getMaxPlaytime(), user.getCollection(title).getGames().get(j).getMinAge(), user.getCollection(title).getGames().get(j).getAvgRating(), user.getCollection(title).getGames().get(j).getGenre(), user.getCollection(title).getGames().get(j).getDescription(), user.getCollection(title).getGames().get(j).getReviews(), user.getCollection(title).getGames()));
                                        gameBox.add(Box.createVerticalStrut(2));
                                    }
                                    gameScroll.revalidate();
                                }
                            }
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(null,"There are no games there.");
                        }
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null,"Invalid Operation.");
                    }

                }
                if(e.getSource() == moveGameButton)
                {
                    String moGame = JOptionPane.showInputDialog("Which game would you like to move? (Use Name)", "Game Name");
                    if( moGame != null)
                    {
                        // this variable keeps it so the dialog box can't happen twice
                        int cool = 0;
                        for(int i = 0; i < collection.getGames().size(); i++)
                        {
                            if(collection.getGames().get(i).getTitle().equals(moGame) && cool == 0)
                            {
                                String moGame2 = JOptionPane.showInputDialog("What rank would you like to move it?", 1);
                                cool = 1;
                                if (moGame2 != null)
                                {
                                    int moGameInt = 0;
                                    moGameInt = Integer.parseInt(moGame2);
                                    if((moGameInt > 0) && (moGameInt <= collection.getGames().size()))
                                    {
                                        // moves game and refreshes page
                                        user.getCollection(title).moveGame(i, moGameInt - 1);
                                        gameBox.removeAll();
                                        for(int j = 0; j < collection.getGames().size(); j++)
                                        {
                                            gameBox.add(createGame(user.getCollection(title).getGames().get(j).getTitle(), user.getCollection(title).getGames().get(j).getImage(), user.getCollection(title).getGames().get(j).getMinPlayers(), user.getCollection(title).getGames().get(j).getMaxPlayers(), user.getCollection(title).getGames().get(j).getMinPlaytime(), user.getCollection(title).getGames().get(j).getMaxPlaytime(), user.getCollection(title).getGames().get(j).getMinAge(), user.getCollection(title).getGames().get(j).getAvgRating(), user.getCollection(title).getGames().get(j).getGenre(), user.getCollection(title).getGames().get(j).getDescription(), user.getCollection(title).getGames().get(j).getReviews(), user.getCollection(title).getGames()));
                                            gameBox.add(Box.createVerticalStrut(2));
                                        }
                                        gameScroll.revalidate();
                                    }
                                    else
                                    {
                                        JOptionPane.showMessageDialog(null, "Game cannot be moved there.");
                                    }
                                }
                                else
                                {
                                    JOptionPane.showMessageDialog(null, "Invalid Input. Try Again.");
                                }
                            }
                        }
                    }
                }
            }
        }

        // add action listeners to buttons
        ListenForCollectionButton lForColButton = new ListenForCollectionButton();
        removeGameButton.addActionListener(lForColButton);
        moveGameButton.addActionListener(lForColButton);

        // interactBox initializing
        interactBox = Box.createHorizontalBox();
        interactBox.add(removeGameButton);
        interactBox.add(Box.createHorizontalStrut(10));
        interactBox.add(moveGameButton);

        // putting all of them together
        collectionFrame.add(collectionPanel);
        addComp(collectionPanel, searchPanel, 0, 0, 3, 1, GridBagConstraints.NORTH, GridBagConstraints.NONE);
        addComp(collectionPanel, gameScroll, 0, 1, 3, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE);
        addComp(collectionPanel, interactBox, 2, 3, 1, 1, GridBagConstraints.SOUTHEAST, GridBagConstraints.NONE);

        // frame constraints
        collectionFrame.pack();
        collectionFrame.setLocationRelativeTo(null);
        collectionFrame.setVisible(true);
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
     * @return returns a JPanel with all the data incorporated in it
     */
    public JPanel createGame(String title, String imageUrl, int minPlayerCount, int maxPlayerCount, int minPlaytime, int maxPlaytime, int minAge, double avgRating, ArrayList<String> genre, String description, ArrayList<Review> reviews, ArrayList<Game> games)
    {
        JLabel genreLabel, playerCountLabel, nameLabel, imageLabel, playtimeLabel, ageLabel, ratingLabel, rankLabel;
        JButton moveRank;
        BufferedImage gameImage = null;

        JPanel gamePanel = new JPanel();
        gamePanel.setLayout(new GridBagLayout());

        Box infoBox = Box.createVerticalBox();
        Box bigBox = Box.createHorizontalBox();

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

        rankLabel = new JLabel("Rank: ");

        for(int i = 0; i < games.size(); i++)
        {
            if(games.get(i).getTitle().equals(title))
            {
                rankLabel.setText("Rank: " + (i + 1));
            }
        }

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
        infoBox.add(Box.createVerticalStrut(20));
        infoBox.add(rankLabel);




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

        bigBox.add(imageLabel);
        bigBox.add(Box.createHorizontalStrut(20));
        bigBox.add(infoBox);

        addComp(gamePanel, bigBox, 0, 0, 1, 2, GridBagConstraints.WEST, GridBagConstraints.NONE);

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

        for(int i = 0; i < games.size(); i++)
        {
            gameBox.add(createGame(games.get(i).getTitle(), games.get(i).getImage(), games.get(i).getMinPlayers(), games.get(i).getMaxPlayers(), games.get(i).getMinPlaytime(), games.get(i).getMaxPlaytime(), games.get(i).getMinAge(), games.get(i).getAvgRating(), games.get(i).getGenre(), games.get(i).getDescription(), games.get(i).getReviews(), games));
            gameBox.add(Box.createVerticalStrut(2));
        }

        JScrollPane gameScrollPane = new JScrollPane(gameBox, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        gameScrollPane.setPreferredSize(new Dimension(550, 520));


        return gameScrollPane;
    }

    // action listener that resembles the one for the search panel on the HomePage
    public class ListenForButton implements ActionListener
    {
        // same code for creating filterpanel etc. as the HomePage
        public void actionPerformed(ActionEvent e)
        {
            if (e.getSource() == backButton)
            {
                collectionFrame.dispose();
            }
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
                            boolean minPlay_ = false, maxPlay_ = false, age_ = false, genre_= false,
                                    avgRat_ = false, playtime_ = false, title_ = false;
                            if (minPlayerComboBox.getSelectedIndex() > 0) {minPlay_ = true;}
                            if (maxPlayerComboBox.getSelectedIndex() > 0) {maxPlay_ = true;}
                            if (ageComboBox.getSelectedIndex() > 0) {age_ = true;}
                            if (genreComboBox.getSelectedIndex() > 0) {genre_ = true;}
                            if (avgRatingComboBox.getSelectedIndex() > 0) {avgRat_ = true;}
                            if (playtimeComboBox.getSelectedIndex() > 0) {playtime_ = true;}
                            if (titleComboBox.getSelectedIndex() > 0) {title_ = true;}

                            if (((minPlay_ ? 1:0) + (maxPlay_ ? 1:0) + (age_ ? 1:0) + (genre_ ? 1:0)) > 1 ||
                                    ((avgRat_ ? 1:0) + (playtime_ ? 1:0) + (title_ ? 1:0)) > 1)
                            {
                                // do nothing
                            } else
                            {
                                if (minPlay_)
                                {
                                    allCollectionGames.editFilterType(minPlayerComboBox.getSelectedIndex());
                                } else if (maxPlay_)
                                {
                                    allCollectionGames.editFilterType(maxPlayerComboBox.getSelectedIndex() + 4);
                                } else if (age_)
                                {
                                    allCollectionGames.editFilterType(ageComboBox.getSelectedIndex() + 12);
                                } else if (genre_)
                                {
                                    allCollectionGames.editFilterType(genreComboBox.getSelectedIndex() + 16);
                                } else
                                {
                                    allCollectionGames.editFilterType(0);
                                }

                                if (avgRat_)
                                {
                                    allCollectionGames.editSortType(avgRatingComboBox.getSelectedIndex());
                                } else if (playtime_)
                                {
                                    allCollectionGames.editSortType(playtimeComboBox.getSelectedIndex() + 4);
                                } else if (title_)
                                {
                                    allCollectionGames.editSortType(titleComboBox.getSelectedIndex() + 2);
                                } else
                                {
                                    allCollectionGames.editSortType(0);
                                }

                                gameBox.removeAll();

                                ArrayList<Game> results = allCollectionGames.search(searchTextField.getText());

                                for (Game g : results) {
                                    gameBox.add(createGame(g.getTitle(), g.getImage(), g.getMinPlayers(), g.getMaxPlayers(), g.getMinPlaytime(), g.getMaxPlaytime(), g.getMinAge(), g.getAvgRating(), g.getGenre(), g.getDescription(), g.getReviews(), results));
                                    gameBox.add(Box.createVerticalStrut(2));
                                }

                                gameScroll.revalidate();

                                filterFrame.dispose();
                            }
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
            if (e.getSource() == searchButton)
            {
                gameBox.removeAll();

                ArrayList<Game> results = allCollectionGames.search(searchTextField.getText());

                for (Game g : results) {
                    gameBox.add(createGame(g.getTitle(), g.getImage(), g.getMinPlayers(), g.getMaxPlayers(), g.getMinPlaytime(), g.getMaxPlaytime(), g.getMinAge(), g.getAvgRating(), g.getGenre(), g.getDescription(), g.getReviews(), results));
                    gameBox.add(Box.createVerticalStrut(2));
                }

                gameScroll.revalidate();
            }
        }
    }
}
