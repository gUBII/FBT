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
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        // Scaled image
        ImageIcon imageIcon = new ImageIcon("image.jpg"); 
        Image image = imageIcon.getImage();
        Image scaledImage = image.getScaledInstance(600, 400, java.awt.Image.SCALE_SMOOTH); 
        setContentPane(new JLabel(new ImageIcon(scaledImage)));

        // Conversation panel
        JPanel conversationPanel = new JPanel();
        conversationArea = new JTextArea(10, 40);
        conversationArea.setEditable(false);
        conversationPanel.add(new JScrollPane(conversationArea));
        add(conversationPanel);

        // User input panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        JLabel askAriLabel = new JLabel("Ask Ari:");
        inputField = new JTextField(40);
        JPanel buttonPanel = new JPanel();
        JButton submitButton = new JButton("Submit");
        JButton logoutButton = new JButton("Logout");
        buttonPanel.add(submitButton);
        buttonPanel.add(logoutButton);
        inputPanel.add(askAriLabel);
        inputPanel.add(inputField);
        inputPanel.add(buttonPanel);
        add(inputPanel);

        // Add action listeners to buttons
        submitButton.addActionListener(e -> submitQuestion());
        logoutButton.addActionListener(e -> logout());

        pack();
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
