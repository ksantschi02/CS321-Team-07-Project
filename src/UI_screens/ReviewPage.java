package UI_screens;

import data_retrieval.Database;
import game_info.Review;

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
import program_users.*;

public class ReviewPage extends JFrame
{

    // global variables
    boolean addAvailable = true;
    User currentUser;
    Box reviewBox = Box.createVerticalBox();
    int reviewCounter = 0;

    /**
     * creates a GUI page for reviews that allows a User to create, edit, or delete their own reviews
     * also contains the description of a game if applicable
     * @param image the url for the image of the game with the reviews
     * @param description the description of a game if there is one
     * @param reviews the arraylist of reviews for the specific game chosen
     * @param user the current user
     */
    public ReviewPage(String image, String description, ArrayList<Review> reviews, User user)
    {
        // setting for the frame
        JFrame reviewFrame;
        reviewFrame = new JFrame();
        reviewFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        reviewFrame.setTitle("ReviewPage");

        // variables for the panel
        JLabel reviewLabel;
        JTextArea descriptionArea;
        JPanel bigReviewPanel;
        JButton backButton, addReviewButton, editButton;
        BufferedImage reviewImage = null;

        // making parameters global
        currentUser = user;

        // initializing panel and variables
        bigReviewPanel = new JPanel();
        bigReviewPanel.setLayout(new GridBagLayout());

        descriptionArea = new JTextArea(description);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setSize(650, 350);
        descriptionArea.setEditable(false);

        backButton = new JButton("Back to Main Menu");
        addReviewButton = new JButton("Add Review");
        editButton = new JButton("Edit Review");

        // variables for the panel that appears when adding a review
        JButton finishChangesButton, deleteButton;
        finishChangesButton = new JButton("Finish Changes");
        deleteButton = new JButton("Delete Review");
        String[] ratings = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        JComboBox ratingComboBox;
        ratingComboBox = new JComboBox(ratings);
        JLabel ratingLabel;
        ratingLabel = new JLabel("Rating: ");
        JTextArea contentArea;
        contentArea = new JTextArea();

        // calls the createReviewScrollPane function and creates the main GUI
        JScrollPane reviewScrollPane = createReviewScrollPane(reviews);

        // Action Listener for buttons on addReviewPanel and ReviewPanel
        class ListenForButton implements ActionListener
        {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == backButton)
                {
                    reviewFrame.dispose();
                }
                if (e.getSource() == addReviewButton)
                {
                    if(addAvailable)
                    {
                        // initialize and set up addReviewPanel
                        ratingComboBox.setVisible(true);
                        finishChangesButton.setVisible(true);
                        deleteButton.setVisible(true);
                        JLabel authorLabel, interiorReviewLabel;
                        JPanel addReviewPanel = new JPanel();
                        addReviewPanel.setLayout(new GridBagLayout());
                        Box infoBox = Box.createHorizontalBox();
                        authorLabel = new JLabel("Name: " + currentUser.getUser());
                        interiorReviewLabel = new JLabel("Review: ");
                        contentArea.setLineWrap(true);
                        contentArea.setSize(250, 100);
                        contentArea.setWrapStyleWord(true);
                        contentArea.setEditable(true);

                        infoBox.add(authorLabel);
                        infoBox.add(Box.createHorizontalStrut(20));
                        infoBox.add(interiorReviewLabel);
                        infoBox.add(Box.createHorizontalStrut(5));
                        infoBox.add(ratingComboBox);

                        // add components to addReviewPanel
                        addComp(addReviewPanel, infoBox, 0, 0, 1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE);
                        addComp(addReviewPanel, contentArea, 0, 1, 3, 1, GridBagConstraints.WEST, GridBagConstraints.NONE);
                        addComp(addReviewPanel, deleteButton, 2, 2, 1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE);
                        addComp(addReviewPanel, finishChangesButton, 1, 2, 1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE);

                        // put border on and refresh the page
                        Border reviewBorder = BorderFactory.createLineBorder(Color.black);
                        addReviewPanel.setBorder(reviewBorder);
                        reviewBox.add(addReviewPanel);
                        reviewScrollPane.revalidate();
                        addAvailable = false;
                    }
                }
                if(e.getSource() == finishChangesButton)
                {
                    // get rid of addReviewPanel and add a new ReviewPanel to the ScrollPane
                    addAvailable = true;
                    Review newReview = new Review(Double.parseDouble(ratingComboBox.getSelectedItem().toString()), contentArea.getText(), currentUser.getUser());
                    reviews.add(newReview);
                    reviewBox.remove(reviewBox.getComponentCount() - 1); //remove addReviewPanel
                    reviewBox.add(createReview(newReview)); //add new one
                    ratingComboBox.setVisible(false);
                    finishChangesButton.setVisible(false);
                    deleteButton.setVisible(false);
                    contentArea.setText("");
                    reviewScrollPane.revalidate();
                }
                if(e.getSource() == deleteButton)
                {
                    // deletes and updates the pane
                    addAvailable = true;
                    reviewBox.remove(reviewBox.getComponentCount() - 1); //remove the review being edited
                    reviewScrollPane.revalidate();
                }
                if(e.getSource() == editButton)
                {
                    addAvailable = false;
                    String avoid = JOptionPane.showInputDialog(null, "Please type the number for the Review");
                    if(avoid != null)
                    {
                        int selection = Integer.parseInt(avoid);
                        if((selection > 0) && ((selection - 1) < reviews.size()))
                        {
                            // big JOptionPane decisions with each correlating to different user choice
                            Object[] options = {"Change Rating", "Change Review", "Delete Review"};
                            int x = JOptionPane.showOptionDialog(null,"What would you like to edit?", "Edit Review", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, options, null);
                            // change rating
                            if(x == 0)
                            {
                                String avoidRating = JOptionPane.showInputDialog(null, "Enter a new rating (1-10)");
                                if(avoidRating != null)
                                {
                                    double editRating = Double.parseDouble(avoidRating);
                                    if((editRating >= 1) && (editRating <= 10) && (reviews.get(selection - 1).getAuthor().equals(currentUser.getUser())))
                                    {
                                        // sets rating and updates pane
                                        reviewCounter = 1;
                                        reviews.get(selection - 1).editRating(editRating);
                                        reviewBox.removeAll();
                                        for(int i = 0; i < reviews.size(); i++)
                                        {
                                            reviewBox.add(createReview(reviews.get(i)));
                                            reviewBox.add(Box.createVerticalStrut(2));
                                        }
                                        reviewScrollPane.revalidate();
                                    }
                                    else
                                    {
                                        System.out.println(reviews.get(selection - 1).getAuthor());
                                        System.out.print(currentUser.getUser());
                                        JOptionPane.showMessageDialog(null, "Invalid operation. Please try again.");
                                    }
                                }
                            }
                            // change review
                            else if(x == 1)
                            {
                                String editReview = JOptionPane.showInputDialog(null, "Enter a new review", "new review");
                                if(editReview != null)
                                {
                                    if((!(editReview.isEmpty())) && (reviews.get(selection - 1).getAuthor().equals(currentUser.getUser())))
                                    {
                                        // changes the review and updates the panel
                                        reviewCounter = 1;
                                        reviews.get(selection - 1).editContent(editReview);
                                        reviewBox.removeAll();
                                        for(int i = 0; i < reviews.size(); i++)
                                        {
                                            reviewBox.add(createReview(reviews.get(i)));
                                            reviewBox.add(Box.createVerticalStrut(2));
                                        }
                                        reviewScrollPane.revalidate();
                                    }
                                    else
                                    {
                                        JOptionPane.showMessageDialog(null, "Invalid operation. Please try again.");
                                    }
                                }
                            }
                            // delete review
                            else if(x == 2)
                            {
                                if(reviews.get(selection - 1).getAuthor().equals(currentUser.getUser()))
                                {
                                    // removes a review and updates the panel
                                    reviewCounter = 1;
                                    reviewBox.remove(selection - 1);
                                    reviews.remove(selection - 1);
                                    reviewBox.removeAll();
                                    for(int i = 0; i < reviews.size(); i++)
                                    {
                                        reviewBox.add(createReview(reviews.get(i)));
                                        reviewBox.add(Box.createVerticalStrut(2));
                                    }
                                    reviewScrollPane.revalidate();
                                }
                                else
                                {
                                    JOptionPane.showMessageDialog(null, "You cannot delete other users' reviews!");
                                }
                            }
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(null, "There are currently no reviews at that position.");
                        }
                    }

                    addAvailable = true;
                }
            }
        }

        // add action listeners to buttons
        ListenForButton lForButton = new ListenForButton();
        backButton.addActionListener(lForButton);
        addReviewButton.addActionListener(lForButton);
        editButton.addActionListener(lForButton);

        finishChangesButton.addActionListener(lForButton);
        deleteButton.addActionListener(lForButton);

        // code for adding image
        try
        {
            URL url = new URL(image);
            reviewImage = ImageIO.read(url);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        Image newGameImg = reviewImage.getScaledInstance(descriptionArea.getWidth() - 250, descriptionArea.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon reviewIcon = new ImageIcon(newGameImg);
        reviewLabel = new JLabel(reviewIcon);


        reviewFrame.add(bigReviewPanel);

        // add everything to the bigReviewPanel and finish the GUI
        addComp(bigReviewPanel, reviewLabel, 0, 0, 1, 1, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE);
        addComp(bigReviewPanel, descriptionArea, 1, 0, 1, 1, GridBagConstraints.NORTHEAST, GridBagConstraints.NONE);
        addComp(bigReviewPanel, reviewScrollPane, 0, 1, 2, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE);
        addComp(bigReviewPanel, backButton, 0, 2, 1, 1, GridBagConstraints.SOUTHWEST, GridBagConstraints.NONE);
        addComp(bigReviewPanel, editButton, 1, 2, 1, 1, GridBagConstraints.SOUTHEAST, GridBagConstraints.NONE);
        addComp(bigReviewPanel, addReviewButton, 2, 2, 1, 1, GridBagConstraints.SOUTHEAST, GridBagConstraints.NONE);

        // reviewFrame constraints
        reviewFrame.pack();
        reviewFrame.setLocationRelativeTo(null);
        reviewFrame.setVisible(true);
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
     * creates a reviewPanel to put in the JScrollPane
     * implements all the variables in a review into GUI
     * @param review the review being put in the panel
     * @return a completed reviewPanel
     */
    public JPanel createReview(Review review)
    {
        // variables
        JLabel authorLabel, ratingLabel, reviewNumberLabel;
        JTextArea contentArea;
        JPanel reviewPanel = new JPanel();
        reviewPanel.setLayout(new GridBagLayout());
        Box infoBox = Box.createHorizontalBox();

        // initialize variables
        authorLabel = new JLabel("Name: " + review.getAuthor());
        ratingLabel = new JLabel("Rating: " + review.getRating() + "/10");
        reviewNumberLabel = new JLabel("Review #: ");
        reviewNumberLabel.setText("Review #" + reviewCounter);
        reviewCounter++;

        contentArea = new JTextArea(review.getContent());
        contentArea.setLineWrap(true);
        contentArea.setSize(340, 300);
        contentArea.setWrapStyleWord(true);
        contentArea.setEditable(false);

        // initialize box with labels
        infoBox.add(reviewNumberLabel);
        infoBox.add(Box.createHorizontalStrut(10));
        infoBox.add(authorLabel);
        infoBox.add(Box.createHorizontalStrut(20));
        infoBox.add(ratingLabel);

        // add everything to panel
        addComp(reviewPanel, infoBox, 0, 0, 1, 1, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE);
        addComp(reviewPanel, contentArea, 0, 1, 3, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE);

        // add border
        Border reviewBorder = BorderFactory.createLineBorder(Color.black);
        reviewPanel.setBorder(reviewBorder);

        return reviewPanel;
    }

    /**
     * creates a scroll pane of a bunch of review panels
     * implements the createReview function
     * @param reviews arraylist of reviews for a single game
     * @return the finished JScrollPane that will be added the main GUI
     */
    public JScrollPane createReviewScrollPane(ArrayList<Review> reviews)
    {
        // reviewCounter keeps of review numbers and resets like this to 1, so it can update properly
        reviewCounter = 1;

        // add to box and scroll pane
        for(int i = 0; i < reviews.size(); i++)
        {
            reviewBox.add(createReview(reviews.get(i)));
            reviewBox.add(Box.createVerticalStrut(2));
        }
        JScrollPane reviewScrollPane = new JScrollPane(reviewBox, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        reviewScrollPane.setPreferredSize(new Dimension(500, 400));

        return reviewScrollPane;
    }
}
