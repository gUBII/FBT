import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.imageio.*;
import java.nio.file.*;
import java.io.*;

class BabyFacePage extends JFrame {

    private String username;
    private JTextArea conversationArea;
    private JTextField inputField;

    public BabyFacePage(String username) {
        this.username = username;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLayout(new BorderLayout());

        try {
            BufferedImage img = ImageIO.read(new File("image.jpg"));
            setContentPane(new JLabel(new ImageIcon(img)));
            pack();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Conversation panel
        JPanel conversationPanel = new JPanel();
        conversationArea = new JTextArea(10, 40);
        conversationArea.setEditable(false);
        conversationPanel.add(conversationArea);
        add(conversationPanel, BorderLayout.NORTH);

        // User input panel
        JPanel inputPanel = new JPanel();
        inputField = new JTextField(40);
        JButton submitButton = new JButton("Submit");
        JButton logoutButton = new JButton("Logout");
        inputPanel.add(inputField);
        inputPanel.add(submitButton);
        inputPanel.add(logoutButton);
        add(inputPanel, BorderLayout.SOUTH);

        // Add action listeners to buttons
        submitButton.addActionListener(e -> submitQuestion());
        logoutButton.addActionListener(e -> logout());
    }

    private void submitQuestion() {
        String question = inputField.getText();
        conversationArea.append("User: " + question + "\n");

        // Save the question in the convo directory
        try {
            Path path = Paths.get("/convo/" + username + ".txt");
            Files.writeString(path, "User: " + question + "\n", StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Clear the input field
        inputField.setText("");
    }

    private void logout() {
        BabyFaceFrame loginPage = new BabyFaceFrame();
        loginPage.setVisible(true);
        dispose(); // Close the current page
    }
}
