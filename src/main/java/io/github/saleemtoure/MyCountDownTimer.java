package io.github.saleemtoure;
import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import java.util.ArrayList;
import javax.swing.*;

class MyCountDownTimer implements ActionListener {

    JPanel clockPanel = new JPanel();
    JButton continueButton = new JButton("Start");
    JLabel timeLabel = new JLabel();
    JButton[] allButtons = new JButton[] { continueButton };
    JLabel spinnerText = new JLabel("min");
    SpinnerModel model = new SpinnerNumberModel(60, 60, null, 5);
    JSpinner spinner = new JSpinner(model);
    JButton spinnerButton = new JButton("Start");
    ArrayList<Session> sessions;

    int currentSessionIndex;
    int remainingTime;
    int startTime;
    Timer timer;
    boolean started = false;

    JLabel sessionLabel = new JLabel();

    void startTimer() {

        timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (remainingTime > 0) {
                    // remainingTime -= 1000;
                    remainingTime -= 240000;
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

        // TODO stygt
        sessionLabel.setText(
                "<html> <br> Oekt " + (currentSessionIndex + 1) + " av " + sessions.size() + "<br> ("
                        + sessions.get(currentSessionIndex).getSessionLength()
                        + " minutter) </html>");

    }

    MyCountDownTimer() {

        clockPanel.add(spinner);
        clockPanel.add(spinnerText);
        spinner.setFont(new Font("DejaVu", Font.PLAIN, 15));
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

        int tL_LeftTopX = 20;
        int tl_width = 200;
        int tl_height = 50;
        timeLabel.setBounds(tL_LeftTopX, 0, tl_width, tl_height);
        timeLabel.setFont(new Font("DejaVu", Font.PLAIN, 35));
        timeLabel.setOpaque(true);
        timeLabel.setHorizontalAlignment(JTextField.CENTER);

        continueButton.setBounds((tL_LeftTopX + (tl_width / 4)), tl_height + 15, 100, 30);
        continueButton.setFont(new Font("DejaVu", Font.PLAIN, 15));
        continueButton.setFocusable(false);
        continueButton.addActionListener(this);

        sessionLabel.setBounds(tL_LeftTopX, 80, tl_width, tl_height);
        sessionLabel.setFont(new Font("DejaVu", Font.PLAIN, 10));
        sessionLabel.setOpaque(false);
        sessionLabel.setHorizontalAlignment(JTextField.CENTER);

        clockPanel.setLayout(null);
        clockPanel.setPreferredSize(new Dimension(300, 80));
        clockPanel.add(sessionLabel);
        clockPanel.add(timeLabel);
        clockPanel.add(continueButton);

    }

    void implementPomodoro(int value) {
        sessions = PomodoroLogic.calculateSessions(value);

        // TODO Legg til en ikon oversikt av økter istedet for tekst
        // String s = "";
        // for (Session session : sessions) {
        // if (session instanceof Work) {
        // if (s == "") {
        // s += " ";
        // }
        // s += " " + ((Work) session).getSessionLength();
        // } else {
        // s += " " + ((Break) session).getSessionLength();
        // }
        // }
        // sessionLabel.setText(s);
        // sessionLabel.setForeground(new Color(124, 180, 248));

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
            timeLabel.setFont(new Font("DejaVu", Font.PLAIN, 15));
            sessionLabel.setText(
                            
                    "<html><br> Fullfoert " + currentSessionIndex + " av " + sessions.size() + " oekter <br>" + " Arbeidet totalt "
                            + PomodoroLogic.totalWorkTime + " minutter</html>");
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