import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GUI {

    MyCountDownTimer clock = new MyCountDownTimer();

    JFrame frame = new JFrame("Pomodoro");

    JPanel mainPanel = new JPanel(), upperPanel = new JPanel(), textPanel = new JPanel(), clockPanel;

    JButton colorSchemeButton = new JButton("\u263E");
    JTextField textField = new JTextField();

    JButton[] buttonList;
    Color primaryDark = new Color(30, 30, 30);
    Color secondaryDark = new Color(55, 55, 61);
    Color primaryLight = new Color(250, 250, 250);
    Color secondaryLight = new Color(204, 204, 204);

    Boolean isLight = false;

    GUI() {

        frame.setPreferredSize(new Dimension(250, 250));
        frame.setResizable(false);

        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        colorSchemeButton.setFont(new Font("DejaVu", Font.PLAIN, 15));
        colorSchemeButton.setFocusable(false);

        int buttonAmount = clock.allButtons.length;

        buttonList = new JButton[buttonAmount];

        int i = 0;
        for (JButton btn : clock.allButtons) {
            buttonList[i] = btn;
            i++;
            // Test
        }

    }

    public void drawGUI() {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            System.exit(1);
        }
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.getContentPane().add(mainPanel);
        lightMode();

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

        upperPanel.setOpaque(false);

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
        mainPanel.setBackground(primaryLight);

        for (JButton btn : buttonList) {
            btn.setBackground(secondaryLight);
            btn.setForeground(secondaryDark);
        }

        clock.clockPanel.setBackground(primaryLight);
        clock.timeLabel.setBackground(primaryLight);
        clock.timeLabel.setForeground(secondaryDark);
        clock.timeLabel.setBorder(BorderFactory.createLineBorder(primaryDark));

        colorSchemeButton.setText("\u263E");
        colorSchemeButton.setBackground(secondaryLight);
        colorSchemeButton.setForeground(secondaryDark);

        clock.sessionLabel.setForeground(secondaryDark);

        isLight = true;
    }

    void darkMode() {
        mainPanel.setBackground(primaryDark);
        for (JButton btn : buttonList) {
            btn.setBackground(secondaryDark);
            btn.setForeground(secondaryLight);
        }

        colorSchemeButton.setText("\u263C");
        colorSchemeButton.setBackground(secondaryDark);
        colorSchemeButton.setForeground(secondaryLight);

        clock.clockPanel.setBackground(primaryDark);
        clock.timeLabel.setBackground(primaryDark);
        clock.timeLabel.setForeground(secondaryLight);
        clock.timeLabel.setBorder(BorderFactory.createLineBorder(primaryLight));

        clock.sessionLabel.setForeground(secondaryLight);

        isLight = false;
    }

}
