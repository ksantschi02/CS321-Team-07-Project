package UI_screens;

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


    boolean addAvailable = true;
    User currentUser;
    Box reviewBox = Box.createVerticalBox();


    public ReviewPage(String image, String description, ArrayList<Review> reviews, User user)
    {

        JFrame reviewFrame;
        reviewFrame = new JFrame();
        reviewFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        reviewFrame.setTitle("ReviewPage");

        JLabel reviewLabel;
        JTextArea descriptionArea;
        JPanel bigReviewPanel;
        JButton backButton, addReviewButton, editButton;
        BufferedImage reviewImage = null;

        currentUser = user;

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

        //for addReviewPanel
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

        JScrollPane reviewScrollPane = createReviewScrollPane(reviews);

        //listener class
        class ListenForButton implements ActionListener
        {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == backButton)
                {
                    reviewFrame.dispose();
                }
                if (e.getSource() == addReviewButton)
                {
                    if(addAvailable == true)
                    {
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

                        addComp(addReviewPanel, infoBox, 0, 0, 1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE);
                        addComp(addReviewPanel, contentArea, 0, 1, 3, 1, GridBagConstraints.WEST, GridBagConstraints.NONE);
                        addComp(addReviewPanel, deleteButton, 2, 2, 1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE);
                        addComp(addReviewPanel, finishChangesButton, 1, 2, 1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE);

                        Border reviewBorder = BorderFactory.createLineBorder(Color.black);
                        addReviewPanel.setBorder(reviewBorder);
                        reviewBox.add(addReviewPanel);
                        reviewScrollPane.revalidate();
                        addAvailable = false;
                    }
                }
                if(e.getSource() == finishChangesButton)
                {
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
                        if((selection - 1) <= reviews.size())
                        {
                            Object[] options = {"Change Rating", "Change Review", "Delete Rating"};
                            int x = JOptionPane.showOptionDialog(null,"What would you like to edit?", "Edit Review", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, options, null);
                            if(x == 0)
                            {
                                String avoidRating = JOptionPane.showInputDialog(null, "Enter a new rating (1-10)");
                                if(avoidRating != null)
                                {
                                    double editRating = Double.parseDouble(avoidRating);
                                    if((editRating >= 1) && (editRating <= 10) && (reviews.get(selection - 1).getAuthor() == currentUser.getUser()) )
                                    {
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
                                        JOptionPane.showMessageDialog(null, "Invalid operation. Please try again.");
                                    }
                                }
                            }
                            else if(x == 1)
                            {
                                String editReview = JOptionPane.showInputDialog(null, "Enter a new review", "new review");
                                if(editReview != null)
                                {
                                    if((!(editReview.isEmpty())) && (reviews.get(selection - 1).getAuthor() == currentUser.getUser()))
                                    {
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
                            else if(x == 2)
                            {
                                if(reviews.get(selection - 1).getAuthor() == currentUser.getUser())
                                {
                                    reviewBox.remove(selection - 1);
                                    reviews.remove(selection - 1);
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


        ListenForButton lForButton = new ListenForButton();
        backButton.addActionListener(lForButton);
        addReviewButton.addActionListener(lForButton);
        editButton.addActionListener(lForButton);

        finishChangesButton.addActionListener(lForButton);
        deleteButton.addActionListener(lForButton);

        try{
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

        addComp(bigReviewPanel, reviewLabel, 0, 0, 1, 1, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE);
        addComp(bigReviewPanel, descriptionArea, 1, 0, 1, 1, GridBagConstraints.NORTHEAST, GridBagConstraints.NONE);
        addComp(bigReviewPanel, reviewScrollPane, 0, 1, 2, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE);
        addComp(bigReviewPanel, backButton, 0, 2, 1, 1, GridBagConstraints.SOUTHWEST, GridBagConstraints.NONE);
        addComp(bigReviewPanel, editButton, 1, 2, 1, 1, GridBagConstraints.SOUTHEAST, GridBagConstraints.NONE);
        addComp(bigReviewPanel, addReviewButton, 2, 2, 1, 1, GridBagConstraints.SOUTHEAST, GridBagConstraints.NONE);



        reviewFrame.pack();
        reviewFrame.setLocationRelativeTo(null);
        reviewFrame.setVisible(true);
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

    public JPanel createReview(Review review)
    {
        JLabel authorLabel, ratingLabel;
        JTextArea contentArea;
        JPanel reviewPanel = new JPanel();
        reviewPanel.setLayout(new GridBagLayout());
        Box infoBox = Box.createHorizontalBox();

        authorLabel = new JLabel("Name: " + review.getAuthor());
        ratingLabel = new JLabel("Rating: " + review.getRating() + "/10");
        contentArea = new JTextArea(review.getContent());
        contentArea.setLineWrap(true);
        contentArea.setSize(340, 300);
        contentArea.setWrapStyleWord(true);
        contentArea.setEditable(false);

        infoBox.add(authorLabel);
        infoBox.add(Box.createHorizontalStrut(20));
        infoBox.add(ratingLabel);

        addComp(reviewPanel, infoBox, 0, 0, 1, 1, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE);
        addComp(reviewPanel, contentArea, 0, 1, 3, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE);

        Border reviewBorder = BorderFactory.createLineBorder(Color.black);
        reviewPanel.setBorder(reviewBorder);

        return reviewPanel;
    }

    //dynamically createsReviewScrollPane from array
    public JScrollPane createReviewScrollPane(ArrayList<Review> reviews)
    {
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
