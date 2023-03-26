import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;

public class LoginPage extends JFrame{

    JLabel usernameLabel, passwordLabel;
    JTextField usernameTextField, passwordTextField;
    JButton loginButton;

    int buttonClicked;

    JTextArea textArea1;

    private static final int FRAME_WIDTH = 500;
    private static final int FRAME_HEIGHT = 300;

    public LoginPage() {
        JFrame loginFrame = new JFrame();
        //loginFrame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setLocationRelativeTo(null);
        loginFrame.setTitle("LoginPage");

        // create panel and login text

        JPanel thePanel = new JPanel();

        thePanel.setLayout(new GridBagLayout());

        usernameLabel = new JLabel("Username:");

        addComp(thePanel, usernameLabel, 0, 0, 1,1,GridBagConstraints.WEST,GridBagConstraints.NONE);

        usernameTextField = new JTextField(30);

        addComp(thePanel, usernameTextField, 1, 0, 2,1,GridBagConstraints.EAST,GridBagConstraints.NONE);

        passwordLabel =  new JLabel("Password:");

        addComp(thePanel, passwordLabel, 0, 1, 1,1,GridBagConstraints.WEST,GridBagConstraints.NONE);

        passwordTextField = new JTextField(30);

        addComp(thePanel, passwordTextField, 1, 1, 2,1,GridBagConstraints.EAST,GridBagConstraints.NONE);

        loginButton = new JButton("Login");

        ListenForButton lForButton = new ListenForButton();

        loginButton.addActionListener(lForButton);

        addComp(thePanel, loginButton, 0, 2, 2, 1, GridBagConstraints.PAGE_END, GridBagConstraints.NONE);

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
                if(Username.equals("Michael") && Password.equals("123"))
                {
                    JOptionPane.showMessageDialog(null, "Login Successful");
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Username or Password Wrong");
                }

            }
        }

    }

}
