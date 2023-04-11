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

public class ReviewPage extends JFrame
{

    JLabel reviewLabel;
    JTextArea descriptionArea;
    JFrame reviewFrame;
    JPanel reviewPanel;
    JButton backButton, setReviewButton;
    BufferedImage reviewImage = null;


    public ReviewPage(String image, String description, ArrayList<Review> reviews)
    {
        reviewFrame = new JFrame();
        reviewFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        reviewFrame.setLocationRelativeTo(null);
        reviewFrame.setTitle("ReviewPage");

        reviewPanel = new JPanel();
        reviewPanel.setLayout(new GridBagLayout());

        descriptionArea = new JTextArea(description);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setSize(180, 300);
        descriptionArea.setEditable(false);

        JScrollPane reviewScrollPane = createReviewScrollPane(reviews);

        backButton = new JButton("Back to Main Menu");
        ReviewPage.ListenForButton lForButton = new ReviewPage.ListenForButton();
        backButton.addActionListener(lForButton);

        try{
            URL url = new URL(image);
            reviewImage = ImageIO.read(url);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        Image newGameImg = reviewImage.getScaledInstance(200,180, Image.SCALE_SMOOTH);
        ImageIcon reviewIcon = new ImageIcon(newGameImg);
        reviewLabel = new JLabel(reviewIcon);


        reviewFrame.add(reviewPanel);

        addComp(reviewPanel, reviewLabel, 0, 0, 1, 2, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE);
        addComp(reviewPanel, descriptionArea, 1, 0, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE);
        addComp(reviewPanel, reviewScrollPane, 0, 2, 2, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE);
        addComp(reviewPanel, backButton, 0, 3, 1, 1, GridBagConstraints.SOUTHWEST, GridBagConstraints.NONE);



        reviewFrame.pack();
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
        Box reviewBox = Box.createVerticalBox();
        for(int i = 0; i < reviews.size(); i++)
        {
            reviewBox.add(createReview(reviews.get(i)));
            reviewBox.add(Box.createVerticalStrut(2));
        }

        JScrollPane reviewScrollPane = new JScrollPane(reviewBox, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        reviewScrollPane.setPreferredSize(new Dimension(400, 220));

        return reviewScrollPane;
    }

    public class ListenForButton implements ActionListener
    {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == backButton) {
                    reviewFrame.dispose();
            }

        }
    }
}
