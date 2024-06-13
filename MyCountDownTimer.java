import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;

import javax.swing.*;

class MyCountDownTimer implements ActionListener {

    JFrame frame = new JFrame();
    JPanel clockPanel = new JPanel();
    JButton continueButton = new JButton("Start");
    JLabel timeLabel = new JLabel();
    JLabel spinnerText = new JLabel("min");
    JButton[] allButtons = new JButton[] { continueButton };
    SpinnerModel model = new SpinnerNumberModel(20, 20, null, 5);
    JSpinner spinner = new JSpinner(model);
    JButton spinnerButton = new JButton("Start");

    int remainingTime;
    int startTime;
    Timer timer;
    boolean started = false;

    void startTimer() {
        timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (remainingTime > 0) {
                    remainingTime -= 1000;
                    updateLabel();
                } else {
                    timer.stop();
                    timeLabel.setText("Denne oekten er ferdig!");
                    Toolkit.getDefaultToolkit().beep();
                }
            }
        });
        timer.start();
    }

    void updateLabel() {
        int minutesPassed = (remainingTime / 60000) % 60;
        int secondsPassed = (remainingTime / 1000) % 60;

        if (remainingTime < 60000) { // If less than a minute remains
            timeLabel.setText(String.format("%02d:%02d", 0, secondsPassed));
        } else {
            timeLabel.setText(String.format("%02d:%02d", minutesPassed, secondsPassed));
        }
    }

    MyCountDownTimer() {

        clockPanel.add(spinner);
        clockPanel.add(spinnerText);
        spinner.setFont(new Font("DejaVu", Font.PLAIN, 15));
        spinner.setMaximumSize(new Dimension(50, 50));
        clockPanel.add(spinnerButton);
        spinnerButton.addActionListener(this);

    }

    JPanel getPanel() {
        return clockPanel;
    }

    void stop() {
        timer.stop();
    }

    void addClock(int value) {

        int tL_LeftTopX = 25; // Main window width = 250. jeg vil ha (250/2)-tl_width/2 //!NOE ER FEIL HER
        int tl_width = 200;
        int tl_height = 50;
        remainingTime = value * 60 * 1000;
        updateLabel();
        timeLabel.setText(String.valueOf(value));
        timeLabel.setBounds(tL_LeftTopX, 0, tl_width, tl_height);
        timeLabel.setFont(new Font("DejaVu", Font.PLAIN, 35));
        timeLabel.setBorder(BorderFactory.createBevelBorder(1));
        timeLabel.setOpaque(true);
        timeLabel.setHorizontalAlignment(JTextField.CENTER);

        continueButton.setBounds((tL_LeftTopX + (tl_width / 4)), tl_height, 100, 30);
        continueButton.setFont(new Font("DejaVu", Font.PLAIN, 15));
        continueButton.setFocusable(false);
        continueButton.addActionListener(this);

        clockPanel.setLayout(null);
        clockPanel.setPreferredSize(new Dimension(300, 80));
        clockPanel.add(timeLabel);
        clockPanel.add(continueButton);

    }

    void done() {
        timer.stop();
        updateLabel();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == spinnerButton) {
            try {
                spinner.commitEdit();
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
            int value = (Integer) spinner.getValue();
            startTime = value;
            clockPanel.remove(spinnerButton);
            clockPanel.remove(spinnerText);
            clockPanel.remove(spinner);
            clockPanel.revalidate();
            clockPanel.repaint();
            addClock(value);
        }
        if (e.getSource() == continueButton) {
            if (!started) {
                started = true;
                continueButton.setText("Pause");
                startTimer();

            } else {
                started = false;
                continueButton.setText("Fortsett");
                stop();
            }
        }

    }
}