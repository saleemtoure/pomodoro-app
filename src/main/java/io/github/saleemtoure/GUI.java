package io.github.saleemtoure;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import com.formdev.flatlaf.*;

public class GUI {

    MyCountDownTimer clock = new MyCountDownTimer();

    JFrame frame = new JFrame("Pomodoro App");

    JPanel mainPanel = new JPanel(), upperPanel = new JPanel(), textPanel = new JPanel(), clockPanel;

    JButton colorSchemeButton = new JButton("\u263E");
    JTextField textField = new JTextField();
    Boolean isLight = true;

    GUI() {

        frame.setPreferredSize(new Dimension(250, 250));
        frame.setResizable(false);

        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        colorSchemeButton.setFont(new Font("DejaVu", Font.PLAIN, 15));
        colorSchemeButton.setFocusable(false);
    }

    public void drawGUI() {

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.getContentPane().add(mainPanel);

        // * Dark and Lightmode */

        upperPanel.add(colorSchemeButton);

        class switchColorScheme implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent e) {
                switchColorScheme();
            }

        }
        colorSchemeButton.addActionListener(new switchColorScheme());

        // * */

        clockPanel = clock.getPanel();

        mainPanel.add(upperPanel);
        mainPanel.add(clockPanel);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    void switchColorScheme() {
        if (isLight) { // skal bli moerk
            darkMode();
        } else { // skal bli lys
            lightMode();
        }
    }

    void lightMode() {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
            colorSchemeButton.setText("\u263E");
            isLight = true;
            SwingUtilities.updateComponentTreeUI(frame);
            frame.pack();
        } catch (Exception e) {
            System.err.println("Failed to initialize LaF");
            System.exit(1);
        }
    }

    void darkMode() {
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
            SwingUtilities.updateComponentTreeUI(frame);
            colorSchemeButton.setText("\u263C");
            isLight = false;
            frame.pack();
        } catch (Exception e) {
            System.err.println("Failed to initialize LaF");
            System.exit(1);
        }

    }

}
