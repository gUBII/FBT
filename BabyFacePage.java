import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class BabyFacePage extends JFrame {

    private JTextField questionField;
    private JButton askButton;

    public BabyFacePage() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);
        setLayout(new BorderLayout());

        // Baby face panel
        JPanel facePanel = new JPanel(); // Replace with your BabyFacePanel if you have one
        add(facePanel, BorderLayout.CENTER);

        // Question input panel
        JPanel questionPanel = new JPanel(new FlowLayout());
        JLabel label = new JLabel("Ask Ari:");
        questionField = new JTextField(20);
        askButton = new JButton("Ask");
        questionPanel.add(label);
        questionPanel.add(questionField);
        questionPanel.add(askButton);
        add(questionPanel, BorderLayout.SOUTH);

        // Add action listener to button
        askButton.addActionListener(e -> {
            String question = questionField.getText();
            // Perform some action with the question (e.g., display an answer)
            System.out.println("You asked: " + question);
            questionField.setText(""); // Clear the field after asking
        });
    }
}
