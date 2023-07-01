import javax.swing.*;
import java.awt.*;
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

    private JTextField questionField;
    private JButton submitButton;

    public BabyFaceFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);
        setLayout(new BorderLayout());

        // Baby face panel
        JPanel facePanel = new BabyFacePanel();
        add(facePanel, BorderLayout.CENTER);

        // Question input panel
        JPanel questionPanel = new JPanel();
        JLabel label = new JLabel("Ask Ari:");
        questionField = new JTextField(20);
        submitButton = new JButton("Submit");
        questionPanel.add(label);
        questionPanel.add(questionField);
        questionPanel.add(submitButton);

        add(questionPanel, BorderLayout.SOUTH);

        // Add action listener to button
        submitButton.addActionListener(e -> {
            String question = questionField.getText();
            // Write the question to a file
            try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("Questions to Ari.txt", true)))) {
                out.println(question);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            questionField.setText("");
        });
    }
}

class BabyFacePanel extends JPanel {

    private Image image;

    public BabyFacePanel() {
        // Assume image.jpg is in the same directory
        ImageIcon ii = new ImageIcon("./image.jpg");
        image = ii.getImage();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw the image as the background
        g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
    }
}
