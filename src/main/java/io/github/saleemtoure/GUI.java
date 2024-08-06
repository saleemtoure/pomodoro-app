package io.github.saleemtoure;

import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import java.util.ArrayList;

import javax.swing.*;

import com.formdev.flatlaf.*;

public class GUI implements ActionListener {

    static MyCountDownTimer clock = new MyCountDownTimer();

    JFrame frame = new JFrame("Pomodoro App");

    JPanel mainPanel = new JPanel(), upperPanel = new JPanel(), textPanel = new JPanel();
    // JPanel mainPanel = new JPanel(), upperPanel = new JPanel(), textPanel = new
    // JPanel(), clockPanel;

    JButton colorSchemeButton = new JButton("\u263E");
    JTextField textField = new JTextField();
    Boolean isLight = true;

    Font defaultFontLarge = new Font("DejaVu", Font.PLAIN, 35);
    static Font defaultFontMedium = new Font("DejaVu", Font.PLAIN, 15);
    Font defaultFontSmall = new Font("DejaVu", Font.PLAIN, 10);

    static ArrayList<JLabel> sessionsPanelLabelList = new ArrayList<>();
    ArrayList<Session> sessions;
    JPanel clockPanel = new JPanel();
    JPanel sessionsPanel = new JPanel();
    static JButton continueButton = new JButton();
    static JLabel timeLabel = new JLabel();
    JLabel spinnerText = new JLabel();
    SpinnerModel model = new SpinnerNumberModel(60, 60, null, 5);
    JSpinner spinner = new JSpinner();
    JButton spinnerButton = new JButton();

    GUI() {

        frame.setPreferredSize(new Dimension(250, 250));
        frame.setResizable(false);

        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        colorSchemeButton.setFont(new Font("DejaVu", Font.PLAIN, 15));
        colorSchemeButton.setFocusable(false);

        continueButton = new JButton("Start");
        spinner = new JSpinner(model);
        spinnerText = new JLabel("min");
        spinnerButton = new JButton("Start");

        spinnerText.setFont(defaultFontMedium);
        spinner.setFont(defaultFontMedium);
        spinnerButton.setFont(defaultFontMedium);
        spinnerButton.addActionListener(this);

    }

    public void drawGUI() {

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.getContentPane().add(mainPanel);

        // * Dark and Lightmode */

        class switchColorScheme implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent e) {
                switchColorScheme();
            }

        }
        colorSchemeButton.addActionListener(new switchColorScheme());

        // * *//

        upperPanel.add(colorSchemeButton);
        mainPanel.add(upperPanel);
        clockPanel.add(spinner);
        clockPanel.add(spinnerText);
        clockPanel.add(spinnerButton);
        mainPanel.add(clockPanel);

        lightMode();

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
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

        if (isLight) {
            lightMode();
        } else {
            darkMode();
        }

    }

    void switchColorScheme() {
        if (isLight) { // skal bli moerk
            darkMode();
        } else { // skal bli lys
            lightMode();
        }
    }

    void lightMode() {
        isLight = true;
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
            colorSchemeButton.setText("\u263E");
            SwingUtilities.updateComponentTreeUI(frame);
            frame.pack();
        } catch (Exception e) {
            System.err.println("Failed to initialize LaF");
            System.exit(1);
        }
    }

    void darkMode() {
        isLight = false;
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
            colorSchemeButton.setText("\u263C");
            SwingUtilities.updateComponentTreeUI(frame);
            frame.pack();
        } catch (Exception e) {
            System.err.println("Failed to initialize LaF");
            System.exit(1);
        }

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
            clockPanel.remove(spinnerButton);
            clockPanel.remove(spinnerText);
            clockPanel.remove(spinner);
            clockPanel.revalidate();
            clockPanel.repaint();
            implementPomodoroGUI(value);
            addClock(value);
        }
        if (e.getSource() == continueButton) {
            if (!clock.started) {
                clock.started = true;
                continueButton.setText("Pause");
                clock.startTimer();

            } else {
                clock.started = false;
                continueButton.setText("Fortsett");
                clock.stop();
            }
        }

    }

    void implementPomodoroGUI(int value) {
        clock.implementPomodoro(value);
        // sessions = PomodoroLogic.calculateSessions(value);
        sessions = clock.sessions;
        if (!sessions.isEmpty()) {
            updateLabel();
        }

        for (Session s : sessions) {
            sessionsPanelLabelList.add(new JLabel(s.getSessionIcon()));
        }

        for (JLabel label : sessionsPanelLabelList) {
            sessionsPanel.add(label);
            label.setFont(defaultFontMedium);
        }
    }

    static void updateLabel() {

        int minutesRemaining = (clock.remainingTime / 60000) % 60;
        int secondsRemaining = (clock.remainingTime / 1000) % 60;

        timeLabel.setText(String.format("%02d:%02d", minutesRemaining, secondsRemaining));

    }
}
