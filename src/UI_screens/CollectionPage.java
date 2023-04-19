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
    JButton filterButton, backButton, searchButton, removeGameButton, moveGameButton;
    JTextField searchTextField;
    JFrame collectionFrame;
    JScrollPane gameScroll;

    Box interactBox;
    Box gameBox = Box.createVerticalBox();
    JComboBox userRank = new JComboBox();
    Database collectionData;
    User currentUser;
    Collection allCollectionGames;

    public CollectionPage(Collection collection, String title, Database data, User user)
    {
        collectionFrame = new JFrame();

        collectionFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        collectionFrame.setTitle(title);

        collectionData = data;
        currentUser = user;

        JPanel collectionPanel = new JPanel();

        collectionPanel.setLayout(new GridBagLayout());

        //setting up panel for search bar and filter

        JPanel searchPanel = new JPanel();

        searchPanel.setLayout(new FlowLayout());

        allCollectionGames = new Collection(0, 0, null, currentUser.getCollection(title).getGames());

        for(int i = 0; i < collection.getSize(); i++)
        {
            userRank.addItem(i + 1);
        }

        CollectionPage.ListenForButton lForButton = new CollectionPage.ListenForButton();

        filterButton = new JButton("Filter");
        searchPanel.add(filterButton);

        searchTextField = new JTextField(30);
        searchPanel.add(searchTextField);

        searchButton = new JButton("Search");
        searchPanel.add(searchButton);
        searchButton.addActionListener(lForButton);

        backButton = new JButton("Back to Main Menu");
        searchPanel.add(backButton);
        backButton.addActionListener(lForButton);

        //importing games and collections

        gameScroll = createGameScrollPane(currentUser.getCollection(title).getGames());

        removeGameButton = new JButton("Remove a Game");
        moveGameButton = new JButton("Change Rank");

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
                        if((reGameInt > 0) && (reGameInt < collection.getSize()))
                        {
                            for(int i = 0; i < collection.getGames().size(); i++)
                            {
                                if(i == reGameInt - 1)
                                {
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
                        for(int i = 0; i < collection.getGames().size(); i++)
                        {
                            if(collection.getGames().get(i).getTitle().equals(moGame))
                            {
                                String moGame2 = JOptionPane.showInputDialog("What rank would you like to move it?", 1);
                                if (moGame2 != null)
                                {
                                    int moGameInt = 0;
                                    moGameInt = Integer.parseInt(moGame2);
                                    if((moGameInt > 0) && (moGameInt < collection.getGames().size()))
                                    {
                                        user.getCollection(title).moveGame(i, moGameInt);
                                        gameBox.removeAll();
                                        for(int j = 0; j < collection.getGames().size(); j++)
                                        {
                                            gameBox.add(createGame(user.getCollection(title).getGames().get(j).getTitle(), user.getCollection(title).getGames().get(j).getImage(), user.getCollection(title).getGames().get(j).getMinPlayers(), user.getCollection(title).getGames().get(j).getMaxPlayers(), user.getCollection(title).getGames().get(j).getMinPlaytime(), user.getCollection(title).getGames().get(j).getMaxPlaytime(), user.getCollection(title).getGames().get(j).getMinAge(), user.getCollection(title).getGames().get(j).getAvgRating(), user.getCollection(title).getGames().get(j).getGenre(), user.getCollection(title).getGames().get(j).getDescription(), user.getCollection(title).getGames().get(j).getReviews(), user.getCollection(title).getGames()));
                                            gameBox.add(Box.createVerticalStrut(2));
                                        }
                                        gameScroll.revalidate();
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

        ListenForCollectionButton lForColButton = new ListenForCollectionButton();

        removeGameButton.addActionListener(lForColButton);
        moveGameButton.addActionListener(lForColButton);

        //interactBox

        interactBox = Box.createHorizontalBox();
        interactBox.add(removeGameButton);
        interactBox.add(Box.createHorizontalStrut(10));
        interactBox.add(moveGameButton);

        //putting all of them together

        collectionFrame.add(collectionPanel);

        addComp(collectionPanel, searchPanel, 0, 0, 3, 1, GridBagConstraints.NORTH, GridBagConstraints.NONE);
        addComp(collectionPanel, gameScroll, 0, 1, 3, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE);
        addComp(collectionPanel, interactBox, 2, 3, 1, 1, GridBagConstraints.SOUTHEAST, GridBagConstraints.NONE);



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

    //dynamically createsGameScrollPane from array
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
