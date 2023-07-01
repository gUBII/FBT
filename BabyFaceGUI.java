import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class BabyFaceGUI {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new BabyFaceFrame();
            frame.setVisible(true);
        });
    }
}

class BabyFaceFrame extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton registerButton;
    private JButton loginButton;

    public BabyFaceFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLayout(new BorderLayout());

        // Register panel
        JPanel registerPanel = new JPanel(new GridLayout(3, 2));
        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");
        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        registerButton = new JButton("Register");
        registerPanel.add(usernameLabel);
        registerPanel.add(usernameField);
        registerPanel.add(passwordLabel);
        registerPanel.add(passwordField);
        registerPanel.add(registerButton);
        add(registerPanel, BorderLayout.NORTH);

        // Login panel
        JPanel loginPanel = new JPanel(new FlowLayout());
        JLabel loginLabel = new JLabel("Login with existing account:");
        loginButton = new JButton("Login");
        loginPanel.add(loginLabel);
        loginPanel.add(loginButton);
        add(loginPanel, BorderLayout.CENTER);

        // Add action listeners to buttons
        registerButton.addActionListener(e -> register());
        loginButton.addActionListener(e -> login());
    }

    private void register() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        // Create a text file with the username and password
        try (PrintWriter out = new PrintWriter(username + ".txt")) {
            out.println(username);
            out.println(password);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        JOptionPane.showMessageDialog(this, "Registration successful!");

        // Clear fields after registration
        usernameField.setText("");
        passwordField.setText("");
    }

    private void login() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        // Check if the username and password match the saved record
        try (BufferedReader br = new BufferedReader(new FileReader(username + ".txt"))) {
            String storedUsername = br.readLine();
            String storedPassword = br.readLine();

            if (storedUsername.equals(username) && storedPassword.equals(password)) {
                JOptionPane.showMessageDialog(this, "Login successful!");
                BabyFacePage babyFacePage = new BabyFacePage();
                babyFacePage.setVisible(true);
                // Perform additional actions after successful login
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password!");
            }
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(this, "Account does not exist!");
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        // Clear fields after login attempt
        usernameField.setText("");
        passwordField.setText("");
    }
}
