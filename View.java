import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class View {

    JFrame frame;
    JPanel mainPanel, upperPanel, textPanel, titlePanel, overviewPanel, mediaPanel;
    JLabel overviewGoal, overviewCompleted;
    JButton mode, startButton, resetButton, playMediaButton, pauseMediaButton;
    JButton[] buttonList;
    Color mainDark, buttonDark, mainLight, buttonLight;
    MyClock clock;

    int fullfortMin, maalMin, fullfortOekt, maalOekt;
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

        mode = new JButton("Bytt til lys mode");
        startButton = clock.startButton;
        resetButton = clock.resetButton;
        playMediaButton = new JButton("Pause playMediaButton");
        pauseMediaButton = new JButton("Fortsett playMediaButton");

        mainDark = new Color(30, 30, 30);
        buttonDark = new Color(55, 55, 61);
        mainLight = new Color(250, 250, 250);
        buttonLight = new Color(204, 204, 204);

        buttonList = new JButton[] { mode, startButton, resetButton, playMediaButton, pauseMediaButton };

        overviewGoal = new JLabel("Fullfoert: " + fullfortMin + " av " + maalMin + " minutter");
        overviewCompleted = new JLabel("Naa paa oekt " + fullfortOekt + " av " + maalOekt);
        fullfortOekt = 0;
        maalOekt = 0;

        fullfortMin = 0;
        maalMin = 0;

        isLight = false;
    }

    public static void main(String[] args) {
        View view = new View();

        view.tegnGUI();
    }

    public void tegnGUI() {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            System.exit(1);
        }
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainPanel.setLayout(new BorderLayout());
        frame.getContentPane().add(mainPanel);
        lysModus();

        // * Dark and Lightmode */

        upperPanel.add(mode);

        class ByttModus implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent e) {
                byttModus();
            }

        }
        mode.addActionListener(new ByttModus());

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

        mediaPanel.add(playMediaButton);
        mediaPanel.add(pauseMediaButton);

        mainPanel.add(textPanel, BorderLayout.NORTH);
        mainPanel.add(clock.getPanel(), BorderLayout.CENTER);
        mainPanel.add(mediaPanel, BorderLayout.SOUTH);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    void byttModus() {
        if (isLight) { // skal bli moerk
            moerkModus();
        } else { // skal bli lys
            lysModus();
        }
    }

    void lysModus() {
        mainPanel.setBackground(mainLight);
        for (JButton knapp : buttonList) {
            knapp.setBackground(buttonLight);
            knapp.setForeground(buttonDark);
        }

        overviewCompleted.setForeground(buttonDark);
        overviewGoal.setForeground(buttonDark);

        mode.setText("Bytt til moerk mode");

        isLight = true;
    }

    void moerkModus() {
        mainPanel.setBackground(mainDark);
        for (JButton knapp : buttonList) {
            knapp.setBackground(buttonDark);
            knapp.setForeground(buttonLight);
        }

        mode.setText("Bytt til lys mode");

        overviewCompleted.setForeground(buttonLight);
        overviewGoal.setForeground(buttonLight);

        isLight = false;
    }

}
