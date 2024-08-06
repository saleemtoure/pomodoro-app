package io.github.saleemtoure;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

class MyCountDownTimer {

    ArrayList<Session> sessions;
    int currentSessionIndex;
    int remainingTime;
    Timer timer;
    boolean started = false;

    MyCountDownTimer() {

    }

    void startTimer() {

        timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (remainingTime > 0) {
                    remainingTime -= 1000;
                    // remainingTime -= 240000; // For testing
                    GUI.updateLabel();
                } else {
                    timer.stop();
                    nextSession();
                }
            }
        });
        timer.start();
    }

    void stop() {
        timer.stop();
    }

    void implementPomodoro(int value) {
        sessions = PomodoroLogic.calculateSessions(value);
        currentSessionIndex = 0;

        if (!sessions.isEmpty()) {
            Session session = sessions.get(currentSessionIndex);
            if (session instanceof Work) {
                remainingTime = ((Work) session).sessionLength * 60 * 1000;
            } else if (session instanceof Break) {
                remainingTime = ((Break) session).sessionLength * 60 * 1000;
            }
        }
    }

    void nextSession() {
        sessions.get(currentSessionIndex).setComplete();
        if (sessions.get(currentSessionIndex).completed()) {
            GUI.sessionsPanelLabelList.get(currentSessionIndex).setForeground(Color.ORANGE);
        }
        currentSessionIndex++;
        if (currentSessionIndex < sessions.size()) {
            Session session = sessions.get(currentSessionIndex);
            if (session instanceof Work) {
                remainingTime = ((Work) session).sessionLength * 60 * 1000;
            } else if (session instanceof Break) {
                remainingTime = ((Break) session).sessionLength * 60 * 1000;
            }
            GUI.updateLabel();
            startTimer();
            beeps(1);
        } else {
            GUI.timeLabel.setText("All Sessions Complete!");
            GUI.timeLabel.setFont(GUI.defaultFontMedium);
            GUI.continueButton.setText("DONE!");
            GUI.continueButton.setEnabled(false);
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

    // @Override
    // public void actionPerformed(ActionEvent e) {
    // if (e.getSource() == spinnerButton) {
    // try {
    // spinner.commitEdit();
    // } catch (ParseException e1) {
    // e1.printStackTrace();
    // }
    // int value = (Integer) spinner.getValue();
    // clockPanel.remove(spinnerButton);
    // clockPanel.remove(spinnerText);
    // clockPanel.remove(spinner);
    // clockPanel.revalidate();
    // clockPanel.repaint();
    // implementPomodoro(value);
    // addClock(value);
    // }
    // if (e.getSource() == continueButton) {
    // if (!started) {
    // started = true;
    // continueButton.setText("Pause");
    // startTimer();

    // } else {
    // started = false;
    // continueButton.setText("Fortsett");
    // stop();
    // }
    // }

    // }
}
