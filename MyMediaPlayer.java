import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class MyMediaPlayer implements ActionListener {

    JFrame frame = new JFrame();
    JPanel mediaPanel = new JPanel();
    // String startIcon = "\u25B6";
    // String pauseIcon = "\u23F8";
    // String resetIcon = "\21BA";
    String startIcon = "Start";
    String pauseIcon = "Pause";
    String resetIcon = "Restart";

    JButton playMediaButton = new JButton(startIcon);
    JButton resetMediaButton = new JButton(resetIcon);
    JButton[] allButtons = new JButton[] { playMediaButton, resetMediaButton };

    JLabel timeLabel = new JLabel();
    int elapsedTime = 0;
    int secondsPassed = 0;
    int minutesPassed = 0;
    int hoursPassed = 0;
    boolean started = false;

    String secondsString = String.format("%02d", secondsPassed);
    String minutesString = String.format("%02d", minutesPassed);
    String hoursString = String.format("%02d", hoursPassed);

    Timer timer = new Timer(1000, new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            elapsedTime += 1000;
            hoursPassed = (elapsedTime / 3600000);
            minutesPassed = (elapsedTime / 60000) % 60;
            secondsPassed = (elapsedTime / 1000) % 60;

            secondsString = String.format("%02d", secondsPassed);
            minutesString = String.format("%02d", minutesPassed);
            hoursString = String.format("%02d", hoursPassed);

            timeLabel.setText(hoursString + ":" + minutesString + ":" + secondsString);
        }
    });

    MyMediaPlayer() {

        timeLabel.setText(hoursString + ":" + minutesString + ":" + secondsString);
        timeLabel.setBounds(50, 0, 200, 50);
        timeLabel.setFont(new Font("Verdana", Font.PLAIN, 35));
        timeLabel.setBorder(BorderFactory.createBevelBorder(1));
        timeLabel.setOpaque(true);
        timeLabel.setHorizontalAlignment(JTextField.CENTER);

        playMediaButton.setBounds(50, 50, 100, 30);
        playMediaButton.setFont(new Font("Verdana", Font.PLAIN, 15));
        playMediaButton.setFocusable(false);
        playMediaButton.addActionListener(this);

        resetMediaButton.setBounds(150, 50, 100, 30);
        resetMediaButton.setFont(new Font("Verdana", Font.PLAIN, 15));
        resetMediaButton.setFocusable(false);
        resetMediaButton.addActionListener(this);

        mediaPanel.setLayout(null);
        mediaPanel.setPreferredSize(new Dimension(300, 80));
        mediaPanel.add(timeLabel);
        mediaPanel.add(playMediaButton);
        mediaPanel.add(resetMediaButton);
    }

    JPanel getPanel() {
        return mediaPanel;
    }

    void start() {
        timer.start();
    }

    void pause() {
        timer.stop();
    }

    void reset() {
        timer.stop();
        elapsedTime = 0;
        secondsPassed = minutesPassed = hoursPassed = 0;
        secondsString = String.format("%02d", secondsPassed);
        minutesString = String.format("%02d", minutesPassed);
        hoursString = String.format("%02d", hoursPassed);
        timeLabel.setText(hoursString + ":" + minutesString + ":" + secondsString);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == playMediaButton) {
            if (!started) {
                started = true;
                playMediaButton.setText(pauseIcon);
                start();
            } else {
                started = false;
                playMediaButton.setText(startIcon);
                pause();
            }
        }
        if (e.getSource() == resetMediaButton) {
            started = false;
            playMediaButton.setText(startIcon);
            reset();
        }
    }
}