
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.util.ArrayList;

public class LoginPage extends JFrame{

    JLabel usernameLabel, passwordLabel, newAccountLabel, newAccountClickLabel;
    JPasswordField passwordTextField;
    JTextField usernameTextField;
    JButton loginButton;
    JFrame loginFrame = new JFrame();

    int buttonClicked;

    JTextArea textArea1;

    private static final int FRAME_WIDTH = 500;
    private static final int FRAME_HEIGHT = 300;

    ArrayList<Game> loginGames;
    ArrayList<User> loginUsers;

    boolean isUser = false;

    public LoginPage(ArrayList<Game> games, ArrayList<User> users) {

        //loginFrame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setLocationRelativeTo(null);
        loginFrame.setTitle("LoginPage");

        loginGames = games;
        loginUsers = users;

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

    private class ListenForButton implements ActionListener
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
                    }
                }
                if(isUser == true)
                {
                    JOptionPane.showMessageDialog(null, "Login Successful");
                    new HomePage(loginGames, loginUsers);
                    loginFrame.dispose();
                    isUser = false;
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Username or Password Wrong");
                }

            }
        }

    }

    private class ListenForMouse extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent e) {
            JLabel newUsernameLabel, newPasswordLabel;
            JTextField newUsernameTextField, newPasswordTextField;

            JFrame newAccountFrame = new JFrame();


            newAccountFrame.setLocationRelativeTo(null);
            newAccountFrame.setTitle("NewAccountPage");

            JPanel newAccountPanel = new JPanel();
            newAccountPanel.setLayout(new GridBagLayout());

            newUsernameLabel = new JLabel("Username:");
            addComp(newAccountPanel, newUsernameLabel, 0, 0, 1,1,GridBagConstraints.WEST,GridBagConstraints.NONE);
            newUsernameTextField = new JTextField(30);
            addComp(newAccountPanel, newUsernameTextField, 1, 0, 2,1,GridBagConstraints.EAST,GridBagConstraints.NONE);

            newPasswordLabel =  new JLabel("Password:");
            addComp(newAccountPanel, newPasswordLabel, 0, 1, 1,1,GridBagConstraints.WEST,GridBagConstraints.NONE);
            newPasswordTextField = new JTextField(30);
            addComp(newAccountPanel, newPasswordTextField, 1, 1, 1,1,GridBagConstraints.EAST,GridBagConstraints.NONE);


            newAccountFrame.add(newAccountPanel);
            newAccountFrame.pack();
            newAccountFrame.setVisible(true);


        }

    }

}
