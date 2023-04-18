package UI_screens;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import game_info.*;
import program_users.*;
import data_retrieval.*;


public class LoginPage extends JFrame{

    JLabel usernameLabel, passwordLabel, newAccountLabel, newAccountClickLabel;
    JPasswordField passwordTextField;
    JTextField usernameTextField;
    JButton loginButton, newAccountButton, backButton;
    JFrame newAccountFrame;
    JFrame loginFrame = new JFrame();

    //for account creation
    JLabel newUsernameLabel, newPasswordLabel;
    JTextField newUsernameTextField, newPasswordTextField;

    private static final int FRAME_WIDTH = 500;
    private static final int FRAME_HEIGHT = 300;

    ArrayList<Game> loginGames;
    ArrayList<User> loginUsers;
    User CurrentUser;
    Database loginData;

    boolean isUser = false;


    public LoginPage(ArrayList<Game> games, ArrayList<User> users, Database data) {

        //loginFrame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setTitle("LoginPage");

        loginGames = games;
        loginUsers = users;
        loginData = data;


        //actionlistener class
        class ListenForButton implements ActionListener
        {

            public void actionPerformed(ActionEvent e)
            {
                if (e.getSource() == loginButton)
                {
                    String Username = usernameTextField.getText();
                    String Password = passwordTextField.getText();

                    for(int i = 0; i < loginUsers.size(); i++)
                    {
                        if(Username.equals(loginUsers.get(i).getUser()) && Password.equals(loginUsers.get(i).getPassword()))
                        {
                            isUser = true;
                            CurrentUser = loginUsers.get(i);
                        }
                    }
                    if(isUser)
                    {
                        JOptionPane.showMessageDialog(null, "Login Successful");
                        new HomePage(loginGames, CurrentUser, loginData);
                        loginFrame.dispose();
                        isUser = false;
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "Username or Password Wrong");
                    }
                }
                if(e.getSource() == newAccountButton)
                {
                    if((!(newUsernameTextField.getText().isEmpty())) && (!((newPasswordTextField.getText().isEmpty()))))
                    {
                        data.addUserScratch(newUsernameTextField.getText(), newPasswordTextField.getText());
                        JOptionPane.showMessageDialog(null, "User successfully created");
                        data.saveDatabase();
                        newAccountFrame.dispose();
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "Need to Enter into Both Fields");
                    }
                }
                if(e.getSource() == backButton)
                {
                    newAccountFrame.dispose();
                }
            }

        }


        //listen for mouse class

        class ListenForMouse extends MouseAdapter {

            @Override
            public void mouseClicked(MouseEvent e) {

                newAccountFrame = new JFrame();

                newAccountFrame.setTitle("NewAccountPage");

                JPanel newAccountPanel = new JPanel();
                newAccountPanel.setLayout(new GridBagLayout());

                ListenForButton lForButton = new ListenForButton();

                newAccountButton = new JButton("Create Account");
                newAccountButton.addActionListener(lForButton);
                backButton = new JButton("Back");
                backButton.addActionListener(lForButton);


                newUsernameLabel = new JLabel("Username:");
                addComp(newAccountPanel, newUsernameLabel, 0, 0, 1,1,GridBagConstraints.WEST,GridBagConstraints.NONE);
                newUsernameTextField = new JTextField(30);
                addComp(newAccountPanel, newUsernameTextField, 1, 0, 2,1,GridBagConstraints.EAST,GridBagConstraints.NONE);

                newPasswordLabel =  new JLabel("Password:");
                addComp(newAccountPanel, newPasswordLabel, 0, 1, 1,1,GridBagConstraints.WEST,GridBagConstraints.NONE);
                newPasswordTextField = new JTextField(30);
                addComp(newAccountPanel, newPasswordTextField, 1, 1, 1,1,GridBagConstraints.EAST,GridBagConstraints.NONE);

                addComp(newAccountPanel, newAccountButton, 1, 2, 1,1,GridBagConstraints.SOUTHEAST,GridBagConstraints.NONE);
                addComp(newAccountPanel, backButton, 0, 2, 1,1,GridBagConstraints.SOUTHWEST,GridBagConstraints.NONE);


                newAccountFrame.add(newAccountPanel);
                newAccountFrame.pack();

                newAccountFrame.setLocationRelativeTo(null);
                newAccountFrame.setVisible(true);


            }

        }

        // create panel and login text

        JPanel thePanel = new JPanel();

        thePanel.setLayout(new GridBagLayout());

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

        loginFrame.add(thePanel);

        loginFrame.pack();

        loginFrame.setLocationRelativeTo(null);

        loginFrame.setVisible(true);

    }

    private void addComp(JPanel thePanel, JComponent comp, int xPos, int yPos, int compWidth, int compHeight, int place, int stretch)
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




}
