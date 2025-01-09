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
            "Typing is a skill that every individual should try to master. In the modern world, where computers and mobile devices dominate our lives, the ability to type quickly and accurately is invaluable. Imagine being able to complete your work faster, communicate more effectively with others, or even just enjoy the satisfaction of typing up an idea without struggling to find the right keys. Typing is not merely about speed; it is also about precision. While it may seem daunting to learn to type efficiently, consistent practice can make a significant difference. A good typist knows the placement of every key by heart. This muscle memory allows them to type without looking at the keyboard, keeping their focus on the screen and the task at hand. Typing tests are a great way to track progress and challenge yourself. They help identify areas where improvement is needed, whether it's speed or accuracy. By regularly taking typing tests, you not only refine your skills but also build confidence. Many people start by typing slower than they would like, but with persistence, their typing speed improves dramatically over time. Typing also has cognitive benefits. It trains your brain to process information faster and improves motor coordination. Think of typing as a workout for your mind and fingers. It sharpens your focus and enhances multitasking abilities. One of the best ways to improve your typing is to practice with different types of texts. Typing long passages, especially those with varied vocabulary and punctuation, helps you adapt to different writing styles. Over time, you will notice that even complex sentences become easier to type. Typing is a skill that will serve you well throughout your life, whether you're writing emails, working on reports, or chatting with friends online. So take the time to practice, challenge yourself, and enjoy the process of becoming a better typist.",

            "The art of typing has evolved significantly over the years. From typewriters to modern keyboards, the tools we use to type have changed, but the basic principles remain the same. Typing is not just a mechanical skill; it is an essential form of communication. Every email, article, essay, and message you write involves typing. The faster and more accurately you type, the more efficient you become at expressing your ideas. For professionals in any field, typing is a skill that can save hours of work. For students, it is a tool that enhances learning by enabling them to take notes and complete assignments efficiently. Typing is also a fun activity that can improve your vocabulary and language skills. When you type different texts, you come across new words, phrases, and sentence structures. This exposure helps you become a more effective communicator. Additionally, typing regularly improves your hand-eye coordination. Your brain learns to send instructions to your fingers more quickly, and your fingers learn to respond without hesitation. This coordination is not only useful for typing but also for other activities that require fine motor skills. The best way to improve your typing speed is to practice consistently. Start with simple exercises, such as typing out common phrases or practicing with typing games. As you become more comfortable, challenge yourself by typing longer passages. Pay attention to your posture and hand placement. Sit up straight and keep your wrists relaxed. These small adjustments can make a big difference in your typing performance. Remember, typing is a skill that improves gradually. Celebrate small achievements along the way, such as typing faster than you did last week or making fewer mistakes. Over time, you will become a confident and efficient typist.",

            "Technology has transformed the way we work, communicate, and learn, and typing has become an essential skill for navigating this digital world. From writing emails to coding software, typing is a fundamental part of our daily lives. As the world becomes increasingly reliant on technology, the ability to type efficiently has become more important than ever. Whether you're drafting a document, chatting with colleagues, or creating content, the speed and accuracy of your typing can impact your productivity and the quality of your work. Developing strong typing skills requires both practice and patience. Start with the basics by learning the proper hand placement on the keyboard. Focus on accuracy before speed, as precision is the foundation of effective typing. Once you have mastered the basics, gradually increase your speed by challenging yourself with timed typing exercises. Typing tests and games can make practice more engaging and fun. They not only measure your progress but also motivate you to improve. Regular practice helps build muscle memory, allowing you to type without consciously thinking about key placement. This muscle memory is a critical aspect of typing fluency, enabling you to focus on your thoughts rather than the mechanics of typing. Typing is not only a valuable professional skill but also a life skill that enhances your ability to communicate and process information. It trains your brain to think quickly and organize your thoughts clearly. Whether you're a student, a professional, or simply someone who enjoys writing, typing efficiently can save you time and effort. As technology continues to evolve, typing will remain a cornerstone of digital literacy. Embrace the opportunity to improve your typing skills and unlock new possibilities for productivity and creativity. Remember, the journey to becoming a proficient typist is a rewarding one."
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