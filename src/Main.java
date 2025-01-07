import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Timer;
import java.util.TimerTask;

public class Main {

    private JFrame frame;
    private JLabel passageLabel;
    private JTextField inputField;
    private JLabel timerLabel, resultLabel, bestResultLabel, liveStatsLabel;
    private JButton start30Button, start60Button, start180Button, restartButton;

    private String[] passages = {
            "The quick brown fox jumps over the lazy dog. This is a simple sentence that contains all the letters of the English alphabet. Typing this sentence can help you warm up your fingers and improve your typing skills.",
            "Typing is an essential skill in the modern world. The more you practice, the better and faster you will become. Take some time every day to improve your typing speed and accuracy.",
            "Practice makes perfect. Typing regularly will help you build muscle memory and increase your confidence. Don't rush, but focus on typing each word correctly and watch your progress over time."
    };

    private String selectedPassage = "";
    private int timerDuration = 0;
    private Timer timer;
    private int timeRemaining;
    private int correctChars = 0;
    private int bestWpm30 = 0, bestWpm60 = 0, bestWpm180 = 0;

    public Main() {
        setupUI();
    }

    private void setupUI() {
        frame = new JFrame("Typing Speed Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 400);
        frame.setLayout(new BorderLayout());

        // Top Panel for Timer and Buttons
        JPanel topPanel = new JPanel();
        timerLabel = new JLabel("Select a duration to start");
        timerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        timerLabel.setForeground(Color.BLACK);
        topPanel.add(timerLabel);

        JPanel buttonPanel = new JPanel();
        start30Button = new JButton("30 Seconds");
        start60Button = new JButton("1 Minute");
        start180Button = new JButton("3 Minutes");

        buttonPanel.add(start30Button);
        buttonPanel.add(start60Button);
        buttonPanel.add(start180Button);
        topPanel.add(buttonPanel);

        frame.add(topPanel, BorderLayout.NORTH);

        // Center Panel for Passage and Input Area
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());

        passageLabel = new JLabel("", SwingConstants.LEFT);
        passageLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        passageLabel.setVerticalAlignment(SwingConstants.TOP);
        passageLabel.setHorizontalAlignment(SwingConstants.LEFT);
        passageLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Wrap the passageLabel in a fixed-size panel
        JPanel passagePanel = new JPanel(new BorderLayout());
        passagePanel.setPreferredSize(new Dimension(800, 100)); // Fixed height to avoid scrolling
        passagePanel.add(passageLabel, BorderLayout.CENTER);

        inputField = new JTextField();
        inputField.setFont(new Font("Arial", Font.PLAIN, 16));
        inputField.setEnabled(false);

        centerPanel.add(passagePanel, BorderLayout.CENTER);
        centerPanel.add(inputField, BorderLayout.SOUTH);

        frame.add(centerPanel, BorderLayout.CENTER);

        // Bottom Panel for Results
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(3, 1));

        resultLabel = new JLabel("");
        resultLabel.setFont(new Font("Arial", Font.BOLD, 16));
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);

        bestResultLabel = new JLabel("");
        bestResultLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        bestResultLabel.setHorizontalAlignment(SwingConstants.CENTER);

        liveStatsLabel = new JLabel("Accuracy: 0% | WPM: 0");
        liveStatsLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        liveStatsLabel.setHorizontalAlignment(SwingConstants.CENTER);

        restartButton = new JButton("Restart");
        restartButton.setVisible(false);

        bottomPanel.add(liveStatsLabel);
        bottomPanel.add(resultLabel);
        bottomPanel.add(bestResultLabel);
        bottomPanel.add(restartButton);

        frame.add(bottomPanel, BorderLayout.SOUTH);

        // Add Listeners
        start30Button.addActionListener(e -> startTest(30));
        start60Button.addActionListener(e -> startTest(60));
        start180Button.addActionListener(e -> startTest(180));
        inputField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                checkTyping();
            }
        });
        restartButton.addActionListener(e -> resetTest());

        frame.setVisible(true);
    }

    private void startTest(int duration) {
        timerDuration = duration;
        timeRemaining = duration;

        selectedPassage = passages[(int) (Math.random() * passages.length)];
        updatePassageDisplay();

        inputField.setText("");
        inputField.setEnabled(true);
        inputField.requestFocus();

        resultLabel.setText("");
        bestResultLabel.setText("");
        liveStatsLabel.setText("Accuracy: 0% | WPM: 0");
        restartButton.setVisible(false);

        timerLabel.setText("Time Remaining: " + timeRemaining + "s");
        timerLabel.setForeground(Color.BLACK);

        if (timer != null) {
            timer.cancel();
        }

        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(() -> {
                    if (timeRemaining > 0) {
                        timeRemaining--;
                        timerLabel.setText("Time Remaining: " + timeRemaining + "s");
                        if (timeRemaining <= 10) {
                            timerLabel.setForeground(Color.RED);
                        }
                    } else {
                        timerLabel.setText("Time Remaining: 0s");
                        endTest();
                        timer.cancel();
                    }
                });
            }
        }, 0, 1000);
    }

    private void updatePassageDisplay() {
        StringBuilder highlightedText = new StringBuilder("<html>");
        for (int i = 0; i < selectedPassage.length(); i++) {
            highlightedText.append("<font color='gray'>").append(selectedPassage.charAt(i)).append("</font>");
        }
        highlightedText.append("</html>");
        passageLabel.setText(highlightedText.toString());
    }

    private void checkTyping() {
        String typedText = inputField.getText();
        StringBuilder highlightedText = new StringBuilder("<html>");

        correctChars = 0; // Reset correct characters before recalculating
        int totalTypedChars = typedText.length();

        for (int i = 0; i < selectedPassage.length(); i++) {
            if (i < typedText.length()) {
                if (typedText.charAt(i) == selectedPassage.charAt(i)) {
                    highlightedText.append("<font color='green'>").append(selectedPassage.charAt(i)).append("</font>");
                    correctChars++; // Count correct characters
                } else {
                    highlightedText.append("<font color='red'>").append(selectedPassage.charAt(i)).append("</font>");
                }
            } else {
                highlightedText.append("<font color='gray'>").append(selectedPassage.charAt(i)).append("</font>");
            }
        }

        highlightedText.append("</html>");
        passageLabel.setText(highlightedText.toString());

        // Calculate real-time WPM and accuracy
        int wpm = (totalTypedChars / 5) * (60 / timerDuration); // Words per minute
        int accuracy = (totalTypedChars > 0) ? (correctChars * 100) / totalTypedChars : 0; // Accuracy %

        liveStatsLabel.setText("Accuracy: " + accuracy + "% | WPM: " + wpm);
    }

    private void endTest() {
        inputField.setEnabled(false);

        String typedText = inputField.getText();
        int totalTypedChars = typedText.length();
        int wpm = (totalTypedChars / 5) * (60 / timerDuration);
        int accuracy = (totalTypedChars > 0) ? (correctChars * 100) / totalTypedChars : 0;

        resultLabel.setText("Final WPM: " + wpm + ", Final Accuracy: " + accuracy + "%");

        if (timerDuration == 30 && wpm > bestWpm30) {
            bestWpm30 = wpm;
        } else if (timerDuration == 60 && wpm > bestWpm60) {
            bestWpm60 = wpm;
        } else if (timerDuration == 180 && wpm > bestWpm180) {
            bestWpm180 = wpm;
        }

        bestResultLabel.setText("Best WPM for " + timerDuration + " seconds: " +
                (timerDuration == 30 ? bestWpm30 : timerDuration == 60 ? bestWpm60 : bestWpm180));

        restartButton.setVisible(true);
    }

    private void resetTest() {
        timerLabel.setText("Select a duration to start");
        passageLabel.setText("");
        inputField.setText("");
        inputField.setEnabled(false);
        resultLabel.setText("");
        bestResultLabel.setText("");
        liveStatsLabel.setText("Accuracy: 0% | WPM: 0");
        restartButton.setVisible(false);

        if (timer != null) {
            timer.cancel();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }
}