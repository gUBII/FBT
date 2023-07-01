import javax.swing.*;
import java.awt.*;
import java.nio.file.*;
import java.security.*;
import java.math.*;
import java.io.*;

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
        String hashedPassword = hashPassword(password);

        try {
            Path path = Paths.get("/usr/" + username + ".txt");
            Files.write(path, hashedPassword.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        usernameField.setText("");
        passwordField.setText("");
    }

    private void login() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String hashedPassword = hashPassword(password);

        try {
            Path path = Paths.get("/usr/" + username + ".txt");
            String storedHashedPassword = new String(Files.readAllBytes(path));

            if (hashedPassword.equals(storedHashedPassword)) {
                // Successful login
                BabyFacePage babyFacePage = new BabyFacePage(username);
                babyFacePage.setVisible(true);
                dispose(); // Close the login frame
            } else {
                // Incorrect password
                JOptionPane.showMessageDialog(null, "Incorrect password. Please try again.");
                passwordField.setText("");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] messageDigest = md.digest(password.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
