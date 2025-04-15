import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class PomodoroTimer extends JFrame {
    private static final int WORK_TIME = 25 * 60;
    private static final int BREAK_TIME = 5 * 60;

    private int timeLeft;
    private Timer timer;
    private boolean isWorking = true;

    private JLabel timeLabel;
    private JLabel countLabel;
    private JButton startButton;
    private JButton stopButton;
    private JButton resetButton;

    private int pomodoroCount = 0;

    public PomodoroTimer() {
        // ウィンドウ設定
        setTitle("🍅 ポモドーロ・タイマー");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // 中央に表示
        getContentPane().setBackground(new Color(255, 248, 220)); // ベージュ背景
        setLayout(new BorderLayout());

        // 時間表示
        timeLabel = new JLabel("25:00", SwingConstants.CENTER);
        timeLabel.setFont(new Font("SansSerif", Font.BOLD, 60));
        timeLabel.setForeground(new Color(194, 221, 255)); // 青
        add(timeLabel, BorderLayout.NORTH);

        // ポモドーロ回数表示
        countLabel = new JLabel("ポモドーロ回数: 0", SwingConstants.CENTER);
        countLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));
        countLabel.setForeground(new Color(90, 50, 30));
        add(countLabel, BorderLayout.CENTER);

        // ボタンパネル
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(194, 221, 255));
        buttonPanel.setLayout(new FlowLayout());

        startButton = new JButton("▶ 開始");
        stopButton = new JButton("■ 停止");
        resetButton = new JButton("↺ リセット");
        
        
        
        // ボタンデザイン
        JButton[] buttons = {startButton, stopButton, resetButton};
        for (JButton btn : buttons) {
            btn.setFont(new Font("SansSerif", Font.PLAIN, 16));
            btn.setBackground(new Color(194, 221, 255));
            btn.setFocusPainted(false);
            btn.setBorder(BorderFactory.createLineBorder(new Color(178, 34, 34), 2));
        }

        // ボタン追加
        buttonPanel.add(startButton);
        buttonPanel.add(stopButton);
        buttonPanel.add(resetButton);
        add(buttonPanel, BorderLayout.SOUTH);

        timeLeft = WORK_TIME;

        // タイマー処理
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeLeft--;
                updateTimeDisplay();

                if (timeLeft == 0) {
                    if (isWorking) {
                        pomodoroCount++;
                        countLabel.setText("ポモドーロ回数: " + pomodoroCount);
                        timeLeft = BREAK_TIME;
                        isWorking = false;
                    } else {
                        timeLeft = WORK_TIME;
                        isWorking = true;
                    }
                }
            }
        });

        // ボタンの動き
        startButton.addActionListener(e -> timer.start());
        stopButton.addActionListener(e -> timer.stop());
        resetButton.addActionListener(e -> {
            timer.stop();
            timeLeft = WORK_TIME;
            isWorking = true;
            pomodoroCount = 0;
            updateTimeDisplay();
            countLabel.setText("ポモドーロ回数: 0");
        });
    }

    private void updateTimeDisplay() {
        int minutes = timeLeft / 60;
        int seconds = timeLeft % 60;
        timeLabel.setText(String.format("%02d:%02d", minutes, seconds));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PomodoroTimer timer = new PomodoroTimer();
            timer.setVisible(true);
        });
    }
}