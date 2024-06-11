import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class View {
    // !NESTE STEG BLIR Å SKILLE UT LOGIKK TIL MVC OG RYDDE OPP I LINJER OG
    // INITALIZATIONS
    // ! FIX LAYOUT ISSUES MED NAA SPILLES DELEN, EVT GJØR MEDIASPILLEREN TIL SIN
    // (IDE: I PAUSER SÅ ÅPNES EN RNADOM VIDEP)
    // EGEN KLASSE

    JFrame frame;
    JPanel mainPanel, upperPanel, textPanel, titlePanel, overviewPanel, mediaPanel;
    JLabel overviewGoal, overviewCompleted, nowPlaying;
    JButton mode, startButton, resetButton, playMediaButton, pauseMediaButton;
    JButton[] buttonList;
    Color primaryDark, secondaryDark, primaryLight, secondaryLight;
    MyClock clock;

    int completedMinutes, goalMinutes, completedSessions, goalSession;
    Boolean isLight;

    View() {
        frame = new JFrame("Pomodoro");
        mainPanel = new JPanel();
        upperPanel = new JPanel();
        textPanel = new JPanel();
        titlePanel = new JPanel();
        overviewPanel = new JPanel();
        mediaPanel = new JPanel();

        clock = new MyClock();

        mode = new JButton();
        mode.setFocusable(false);
        startButton = clock.startButton;
        resetButton = clock.resetButton;
        playMediaButton = new JButton("\u23F5");
        playMediaButton.setFocusable(false);
        pauseMediaButton = new JButton("\u23F8");
        pauseMediaButton.setFocusable(false);

        primaryDark = new Color(30, 30, 30);
        secondaryDark = new Color(55, 55, 61);
        primaryLight = new Color(250, 250, 250);
        secondaryLight = new Color(204, 204, 204);

        buttonList = new JButton[] { mode, startButton, resetButton, playMediaButton, pauseMediaButton };

        overviewGoal = new JLabel("Fullfoert: " + completedMinutes + " av " + goalMinutes + " minutter");
        overviewCompleted = new JLabel("Naa paa oekt " + completedSessions + " av " + goalSession);
        completedSessions = 0;
        goalSession = 0;

        completedMinutes = 0;
        goalMinutes = 0;

        isLight = false;

        nowPlaying = new JLabel("Naa spilles: ");
    }

    public static void main(String[] args) {
        View view = new View();

        view.drawGUI();
    }

    public void drawGUI() {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            System.exit(1);
        }
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainPanel.setLayout(new BorderLayout());
        frame.getContentPane().add(mainPanel);
        lightMode();

        // * Dark and Lightmode */

        upperPanel.add(mode);

        class switchColorScheme implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent e) {
                switchColorScheme();
            }

        }
        mode.addActionListener(new switchColorScheme());

        // * */

        textPanel.setLayout(new BorderLayout());
        textPanel.add(upperPanel, BorderLayout.NORTH);
        textPanel.add(titlePanel, BorderLayout.CENTER);
        textPanel.add(overviewPanel, BorderLayout.SOUTH);

        upperPanel.setOpaque(false);
        textPanel.setOpaque(false);
        titlePanel.setOpaque(false);
        overviewPanel.setOpaque(false);
        mediaPanel.setOpaque(false);

        titlePanel.add(overviewGoal);

        overviewPanel.add(overviewCompleted);

        mediaPanel.add(nowPlaying);
        mediaPanel.add(playMediaButton);
        mediaPanel.add(pauseMediaButton);

        mainPanel.add(textPanel, BorderLayout.NORTH);
        mainPanel.add(clock.getPanel(), BorderLayout.CENTER);
        mainPanel.add(mediaPanel, BorderLayout.SOUTH);

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
        for (JButton knapp : buttonList) {
            knapp.setBackground(secondaryLight);
            knapp.setForeground(secondaryDark);
        }

        clock.clockPanel.setBackground(primaryLight);
        clock.timeLabel.setBackground(primaryLight);
        clock.timeLabel.setForeground(secondaryDark);

        overviewCompleted.setForeground(secondaryDark);
        overviewGoal.setForeground(secondaryDark);
        nowPlaying.setForeground(primaryDark);

        mode.setText("\u263E");

        isLight = true;
    }

    void darkMode() {
        mainPanel.setBackground(primaryDark);
        for (JButton knapp : buttonList) {
            knapp.setBackground(secondaryDark);
            knapp.setForeground(secondaryLight);
        }

        mode.setText("\u263C");

        clock.clockPanel.setBackground(primaryDark);
        clock.timeLabel.setBackground(primaryDark);
        clock.timeLabel.setForeground(secondaryLight);

        overviewCompleted.setForeground(secondaryLight);
        overviewGoal.setForeground(secondaryLight);
        nowPlaying.setForeground(primaryLight);

        isLight = false;
    }

}
