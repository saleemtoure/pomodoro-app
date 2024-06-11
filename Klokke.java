import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class Klokke implements ActionListener {

    JFrame frame = new JFrame();
    JPanel klokkePanel = new JPanel();
    JButton startButton = new JButton("Start");
    JButton resetButton = new JButton("Reset");
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

    Klokke() {

        timeLabel.setText(hoursString + ":" + minutesString + ":" + secondsString);
        timeLabel.setBounds(100, 100, 200, 100);
        timeLabel.setFont(new Font("Verdana", Font.PLAIN, 35));
        timeLabel.setBorder(BorderFactory.createBevelBorder(1));
        timeLabel.setOpaque(true);
        timeLabel.setHorizontalAlignment(JTextField.CENTER);

        startButton.setBounds(100, 200, 100, 50);
        startButton.setFont(new Font("Verdana", Font.PLAIN, 20));
        startButton.setFocusable(false);
        startButton.addActionListener(this);

        resetButton.setBounds(200, 200, 100, 50);
        resetButton.setFont(new Font("Verdana", Font.PLAIN, 20));
        resetButton.setFocusable(false);
        resetButton.addActionListener(this);

        klokkePanel.setLayout(null);
        klokkePanel.setPreferredSize(new Dimension(420, 420));
        klokkePanel.add(timeLabel);
        klokkePanel.add(startButton);
        klokkePanel.add(resetButton);
    }

    JPanel getPanel() {
        return klokkePanel;
    }

    void start() {
        timer.start();
    }

    void stop() {
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
        if (e.getSource() == startButton) {
            if (!started) {
                started = true;
                startButton.setText("Stop");
                start();
            } else {
                started = false;
                startButton.setText("Start");
                stop();
            }
        }
        if (e.getSource() == resetButton) {
            started = false;
            startButton.setText("Start");
            reset();
        }
    }
}