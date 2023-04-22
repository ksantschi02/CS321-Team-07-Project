package UI_screens;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import game_info.*;
import program_users.*;
import data_retrieval.*;

public class LoginPage extends JFrame{

    // Login GUI variables
    JLabel usernameLabel, passwordLabel, newAccountLabel, newAccountClickLabel;
    JPasswordField passwordTextField;
    JTextField usernameTextField;
    JButton loginButton, newAccountButton, backButton;
    JFrame newAccountFrame;
    JFrame loginFrame = new JFrame();

    // New Account Creation GUI variables
    JLabel newUsernameLabel, newPasswordLabel;
    JTextField newUsernameTextField, newPasswordTextField;

    // XML data variables
    ArrayList<Game> loginGames;
    ArrayList<User> loginUsers;
    User CurrentUser;
    Database loginData;

    // functionality variables
    boolean isUser = false;

    /**
     * creates a LoginPage GUI that will allow the User to create an account or log in to the program
     * @param games arraylist of games from the database
     * @param users arraylist of users from the database
     * @param data the database itself to save to
     */
    public LoginPage(ArrayList<Game> games, ArrayList<User> users, Database data) {

        // Setting the basics of the frame Pt. 1
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setTitle("LoginPage");

        // Initializes database data so it is global in the file
        loginGames = games;
        loginUsers = users;
        loginData = data;

        // Action Listener for Login GUI buttons
        class ListenForButton implements ActionListener
        {
            public void actionPerformed(ActionEvent e)
            {
                if (e.getSource() == loginButton)
                {
                    // get user input from textfields
                    String Username = usernameTextField.getText();
                    String Password = passwordTextField.getText();

                    // checks the user input with the userlist
                    for(int i = 0; i < loginUsers.size(); i++)
                    {
                        if(Username.equals(loginUsers.get(i).getUser()) && Password.equals(loginUsers.get(i).getPassword()))
                        {
                            isUser = true; // see if account exists
                            CurrentUser = loginUsers.get(i);
                        }
                    }
                    if(isUser) // if account does exist
                    {
                        JOptionPane.showMessageDialog(null, "Login Successful");
                        new HomePage(loginGames, CurrentUser, loginData); // loads next screen with XML data and the Current User
                        loginFrame.dispose(); // removes this screen since its purpose is complete
                        isUser = false;
                    }
                    else // account doesn't exist or if it is typed in wrong
                    {
                        JOptionPane.showMessageDialog(null, "Username or Password Wrong");
                    }
                }
                if(e.getSource() == newAccountButton)
                {
                    if((!(newUsernameTextField.getText().isEmpty())) && (!((newPasswordTextField.getText().isEmpty())))) // make sure of no null input
                    {
                        data.addUserScratch(newUsernameTextField.getText(), newPasswordTextField.getText()); // creates a new user from textfields
                        JOptionPane.showMessageDialog(null, "User successfully created");
                        data.saveDatabase(); // saves the new user into the userlist
                        newAccountFrame.dispose();
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "Need to Enter into Both Fields");
                    }
                }
                if(e.getSource() == backButton)
                {
                    newAccountFrame.dispose(); // basically "return back to menu"
                }
            }

        }

        // Mouse Listener for "Click Here!" text
        class ListenForMouse extends MouseAdapter {

            @Override
            public void mouseClicked(MouseEvent e) {

                // basics of New Account Page
                newAccountFrame = new JFrame();
                newAccountFrame.setTitle("NewAccountPage");
                JPanel newAccountPanel = new JPanel();
                newAccountPanel.setLayout(new GridBagLayout());

                // create buttons and attach listeners
                ListenForButton lForButton = new ListenForButton();
                newAccountButton = new JButton("Create Account");
                newAccountButton.addActionListener(lForButton);
                backButton = new JButton("Back");
                backButton.addActionListener(lForButton);

                // create and attach a space for Username input
                newUsernameLabel = new JLabel("Username:");
                addComp(newAccountPanel, newUsernameLabel, 0, 0, 1,1,GridBagConstraints.WEST,GridBagConstraints.NONE);
                newUsernameTextField = new JTextField(30);
                addComp(newAccountPanel, newUsernameTextField, 1, 0, 2,1,GridBagConstraints.EAST,GridBagConstraints.NONE);

                // create and attach a space for Password input
                newPasswordLabel =  new JLabel("Password:");
                addComp(newAccountPanel, newPasswordLabel, 0, 1, 1,1,GridBagConstraints.WEST,GridBagConstraints.NONE);
                newPasswordTextField = new JTextField(30);
                addComp(newAccountPanel, newPasswordTextField, 1, 1, 1,1,GridBagConstraints.EAST,GridBagConstraints.NONE);

                // add buttons to New Account Page GUI
                addComp(newAccountPanel, newAccountButton, 1, 2, 1,1,GridBagConstraints.SOUTHEAST,GridBagConstraints.NONE);
                addComp(newAccountPanel, backButton, 0, 2, 1,1,GridBagConstraints.SOUTHWEST,GridBagConstraints.NONE);

                // constraints of the frame
                newAccountFrame.add(newAccountPanel);
                newAccountFrame.pack();
                newAccountFrame.setLocationRelativeTo(null);
                newAccountFrame.setVisible(true);
            }

        }

        // create panel and set layout
        JPanel thePanel = new JPanel();
        thePanel.setLayout(new GridBagLayout());

        // initialize all GUI elements and add them to the panel
        usernameLabel = new JLabel("Username:");
        addComp(thePanel, usernameLabel, 0, 0, 1,1,GridBagConstraints.WEST,GridBagConstraints.NONE);

        usernameTextField = new JTextField(30);
        addComp(thePanel, usernameTextField, 1, 0, 2,1,GridBagConstraints.EAST,GridBagConstraints.NONE);

        passwordLabel =  new JLabel("Password:");
        addComp(thePanel, passwordLabel, 0, 1, 1,1,GridBagConstraints.WEST,GridBagConstraints.NONE);

        passwordTextField = new JPasswordField(30);
        addComp(thePanel, passwordTextField, 1, 1, 2,1,GridBagConstraints.EAST,GridBagConstraints.NONE);

        loginButton = new JButton("Login");
        ListenForButton lForButton = new ListenForButton();
        loginButton.addActionListener(lForButton);
        addComp(thePanel, loginButton, 0, 2, 2, 1, GridBagConstraints.PAGE_END, GridBagConstraints.NONE);

        newAccountLabel = new JLabel("*new account?");
        addComp(thePanel, newAccountLabel, 0, 3, 1, 1, GridBagConstraints.EAST, GridBagConstraints.NONE);

        newAccountClickLabel = new JLabel("CLICK HERE");
        addComp(thePanel, newAccountClickLabel, 1, 3, 1, 1, GridBagConstraints.EAST, GridBagConstraints.NONE);
        ListenForMouse lForMouse = new ListenForMouse();
        newAccountClickLabel.addMouseListener(lForMouse);

        // constraints of the frame
        loginFrame.add(thePanel);
        loginFrame.pack(); // fixes spacing of items
        loginFrame.setLocationRelativeTo(null); // spawns in the middle of the screen
        loginFrame.setVisible(true);
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
    private void addComp(JPanel thePanel, JComponent comp, int xPos, int yPos, int compWidth, int compHeight, int place, int stretch)
    {
        GridBagConstraints gridConstraints = new GridBagConstraints();

        // sets constraints for an item
        gridConstraints.gridx = xPos;
        gridConstraints.gridy = yPos;
        gridConstraints.gridwidth = compWidth;
        gridConstraints.gridheight = compHeight;
        gridConstraints.weightx = 100;
        gridConstraints.weighty = 100;
        gridConstraints.insets = new Insets(5,5,5,5);
        gridConstraints.anchor = place;
        gridConstraints.fill = stretch;

        // adds item to panel requested
        thePanel.add(comp, gridConstraints);
    }




}
