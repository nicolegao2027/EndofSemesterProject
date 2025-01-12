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
            "In the fast-paced digital world today, typing skills are more important than ever. Whether in education, business, or personal life, the ability to communicate effectively through written text is crucial. For students, typing proficiency allows for quicker note-taking, more efficient completion of assignments, and effective communication with peers and teachers. As more educational institutions adopt digital platforms, the demand for proficient typists continues to grow. In the business realm, typing skills can significantly impact productivity. Employees who can type quickly and accurately can manage correspondence, create reports, and handle data entry tasks more efficiently. This proficiency can lead to better job performance, higher satisfaction rates, and increased opportunities for career advancement. In many cases, employers prioritize candidates who possess strong typing abilities, recognizing that these skills contribute to overall workplace efficiency. Moreover, the rise of remote work has further underscored the importance of typing skills. As teams collaborate online, effective written communication becomes vital. Misunderstandings can arise from poorly typed messages, making clarity and precision essential. Furthermore, with the increasing reliance on digital tools, being able to type proficiently can make adapting to new technologies smoother. In addition to practical applications, typing also fosters creativity. Writers, bloggers, and content creators rely on their typing skills to bring their ideas to life. The fluidity of typing enables a free flow of thoughts, allowing writers to concentrate on the content rather than the mechanics of writing. With the right typing techniques, individuals can unlock their full potential, channeling their creativity effectively and efficiently.",

            "Typing has evolved dramatically since its inception. Initially, the typewriter revolutionized written communication in the 19th century, allowing for faster and more legible text than handwriting. Early models were mechanical, requiring users to strike individual keys that would press typebars against an inked ribbon. This innovation was a game-changer for offices and businesses, transforming how documents were created and shared. With the advent of computers in the late 20th century, typing underwent another transformation. The introduction of the QWERTY keyboard layout became standard, and touch typing emerged as a crucial skill. Touch typing, where the typist uses all fingers without looking at the keys, significantly increases speed and efficiency. Typing courses became popular, teaching individuals not only the mechanics of typing but also the importance of posture and finger placement. Today, typing is an essential skill in the digital age. From emails to social media and coding, the ability to type swiftly and accurately is invaluable. Various software programs and online tools help individuals improve their typing skills, catering to different learning styles. Gamification has also entered the scene, with typing games making practice engaging and fun. As technology continues to advance, we see the emergence of voice recognition and predictive text features. While these innovations may reduce the amount of typing we do, they also highlight the importance of mastering this skill. Being proficient in typing not only enhances productivity but also opens up opportunities in various fields, from writing and editing to programming and data entry.",

            "Mastering typing techniques can greatly enhance both speed and accuracy, transforming the way we interact with technology. One of the most effective methods is touch typing, which involves using all ten fingers to type without looking at the keyboard. This technique not only boosts speed but also reduces the risk of repetitive strain injuries, making it a healthier choice for frequent typists. To begin mastering touch typing, it's essential to familiarize oneself with the home row keys: A, S, D, F, J, K, L, and semicolon. Placing the fingers correctly on these keys allows for easy access to all letters without unnecessary movement. Regular practice is key; online typing tutorials and exercises can help individuals build muscle memory and improve their overall typing skills. Another important aspect of typing is maintaining proper posture and ergonomics. Sitting up straight, keeping feet flat on the ground, and positioning the keyboard at elbow height can prevent discomfort and fatigue. Investing in an ergonomic keyboard and chair can further enhance the typing experience, allowing for longer periods of productive work without strain. Incorporating typing drills and games into practice sessions can also make learning enjoyable. Websites and applications offer a variety of engaging exercises that track progress, making it easy to see improvements over time. Setting personal goals, such as aiming for a specific words-per-minute rate, can provide motivation and a sense of accomplishment. As technology continues to advance, the demand for efficient typing skills remains strong. In a world where digital communication is ubiquitous, being a proficient typist is not just a useful skill; it’s essential for success in many fields. By committing to ongoing practice and attention to technique, individuals can elevate their typing abilities, paving the way for greater productivity and creativity in their personal and professional lives."
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
        frame.setSize(800, 560);
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
        passagePanel.setPreferredSize(new Dimension(800, 100));
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