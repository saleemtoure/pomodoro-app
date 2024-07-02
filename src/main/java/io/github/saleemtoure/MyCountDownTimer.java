package io.github.saleemtoure;

import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import java.util.ArrayList;
import javax.swing.*;

import com.formdev.flatlaf.FlatLightLaf;

class MyCountDownTimer implements ActionListener {

    JPanel clockPanel = new JPanel();
    JPanel sessionsPanel = new JPanel();
    JButton continueButton = new JButton();
    JLabel timeLabel = new JLabel();
    JLabel spinnerText = new JLabel();
    SpinnerModel model = new SpinnerNumberModel(60, 60, null, 5);
    JSpinner spinner = new JSpinner();
    JButton spinnerButton = new JButton();

    ArrayList<JLabel> sessionsPanelLabelList = new ArrayList<>();
    ArrayList<Session> sessions;
    Font defaultFontLarge = new Font("DejaVu", Font.PLAIN, 35);
    Font defaultFontMedium = new Font("DejaVu", Font.PLAIN, 15);
    Font defaultFontSmall = new Font("DejaVu", Font.PLAIN, 10);

    int currentSessionIndex;
    int remainingTime;
    int startTime;
    Timer timer;
    boolean started = false;

    MyCountDownTimer() {

        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception e) {
            System.err.println("Failed to initialize LaF");
            System.exit(1);
        }

        continueButton = new JButton("Start");
        spinner = new JSpinner(model);
        spinnerText = new JLabel("min");
        spinnerButton = new JButton("Start");

        clockPanel.add(spinner);
        clockPanel.add(spinnerText);
        spinnerText.setFont(defaultFontMedium);
        spinner.setFont(defaultFontMedium);
        spinnerButton.setFont(defaultFontMedium);
        clockPanel.add(spinnerButton);
        spinnerButton.addActionListener(this);

    }

    void startTimer() {

        timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (remainingTime > 0) {
                    // remainingTime -= 1000;
                    remainingTime -= 240000; // For testing
                    updateLabel();
                } else {
                    timer.stop();
                    nextSession();
                }
            }
        });
        timer.start();
    }

    void updateLabel() {

        int minutesRemaining = (remainingTime / 60000) % 60;
        int secondsRemaining = (remainingTime / 1000) % 60;

        timeLabel.setText(String.format("%02d:%02d", minutesRemaining, secondsRemaining));

    }

    JPanel getPanel() {
        return clockPanel;
    }

    void stop() {
        timer.stop();
    }

    void addClock(int value) {

        int tL_LeftTopX = 20;
        int tl_width = 200;
        int tl_height = 50;
        timeLabel.setBounds(tL_LeftTopX, 0, tl_width, tl_height);
        timeLabel.setFont(defaultFontLarge);
        timeLabel.setHorizontalAlignment(JTextField.CENTER);

        continueButton.setBounds((tL_LeftTopX + (tl_width / 4)), tl_height + 15, 100, 30);
        continueButton.setFont(defaultFontMedium);
        continueButton.setFocusable(false);
        continueButton.addActionListener(this);

        sessionsPanel.setBounds(tL_LeftTopX, 100, tl_width, tl_height);
        sessionsPanel.setFont(defaultFontSmall);

        clockPanel.setLayout(null);
        clockPanel.setPreferredSize(new Dimension(300, 150));
        clockPanel.add(sessionsPanel);
        clockPanel.add(timeLabel);
        clockPanel.add(continueButton);

    }

    void implementPomodoro(int value) {
        sessions = PomodoroLogic.calculateSessions(value);
        for (Session s : sessions) {
            sessionsPanelLabelList.add(new JLabel(s.getSessionIcon()));
        }

        for (JLabel label : sessionsPanelLabelList) {
            sessionsPanel.add(label);
            label.setFont(defaultFontMedium);
        }

        currentSessionIndex = 0;

        if (!sessions.isEmpty()) {
            Session session = sessions.get(currentSessionIndex);
            if (session instanceof Work) {
                remainingTime = ((Work) session).sessionLength * 60 * 1000;
            } else if (session instanceof Break) {
                remainingTime = ((Break) session).sessionLength * 60 * 1000;
            }
            updateLabel();
        }
    }

    void nextSession() {
        sessions.get(currentSessionIndex).setComplete(true);
        if (sessions.get(currentSessionIndex).completed()) {
            sessionsPanelLabelList.get(currentSessionIndex).setForeground(Color.ORANGE);
        }
        currentSessionIndex++;
        if (currentSessionIndex < sessions.size()) {
            Session session = sessions.get(currentSessionIndex);
            if (session instanceof Work) {
                remainingTime = ((Work) session).sessionLength * 60 * 1000;
            } else if (session instanceof Break) {
                remainingTime = ((Break) session).sessionLength * 60 * 1000;
            }
            updateLabel();
            startTimer();
            beeps(1);
        } else {
            timeLabel.setText("All Sessions Complete!");
            timeLabel.setFont(defaultFontMedium);
            continueButton.setText("DONE!");
            continueButton.setEnabled(false);
            beeps(3);
        }
    }

    void beeps(int numOfBeeps) {
        // For å unngå at hovedtråden blir blokkert og ser fryst ut når lydene spilles
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < numOfBeeps; i++) {
                    Toolkit.getDefaultToolkit().beep();
                    try {
                        Thread.sleep(1300);
                    } catch (InterruptedException e) {
                        System.out.print(e);
                    }
                }
            }
        }).start();
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
            implementPomodoro(value);
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