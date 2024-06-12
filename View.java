import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class View {
    // !NESTE STEG BLIR Å SKILLE UT LOGIKK TIL MVC OG RYDDE OPP I LINJER OG
    // INITALIZATIONS

    // !STEG BLIR Å GJØRE STOPPEKLOKKE TIL COUNTDOWN OG GJØR DET MULIG Å SKRIVE INN
    // TID DIREKTE I KLOKKA
    // ! FIX LAYOUT ISSUES MED NAA SPILLES DELEN, EVT GJØR MEDIASPILLEREN TIL SIN
    // (IDE: I PAUSER SÅ ÅPNES EN RNADOM VIDEP)
    // EGEN KLASSE
    MyClock clock = new MyClock();

    JFrame frame = new JFrame("Pomodoro");

    JPanel mainPanel = new JPanel(), upperPanel = new JPanel(), textPanel = new JPanel(), titlePanel = new JPanel(), overviewPanel = new JPanel(),
            spinnerPanel = new JPanel();

    JLabel overviewCompleted;
    JButton colorSchemeButton = new JButton("\u263E");
    JTextField textField = new JTextField();
    SpinnerModel model = new SpinnerNumberModel(20, 20, null, 5);
    JSpinner spinner = new JSpinner(model);

    JButton[] buttonList;
    Color primaryDark = new Color(30, 30, 30);
    Color secondaryDark = new Color(55, 55, 61);
    Color primaryLight = new Color(250, 250, 250);
    Color secondaryLight = new Color(204, 204, 204);

    int completedMinutes = 0, goalMinutes = 0, completedSessions = 0, goalSession = 0;
    Boolean isLight = false;

    View() {

        frame.setPreferredSize(new Dimension(250, 250));
        frame.setResizable(false);

        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        colorSchemeButton.setFont(new Font("DejaVu", Font.PLAIN, 15));
        colorSchemeButton.setFocusable(false);

        int buttonAmount = clock.allButtons.length;

        buttonList = new JButton[buttonAmount];

        spinner.setFont(new Font("DejaVu", Font.PLAIN, 15));
        spinner.setMaximumSize(new Dimension(50, 50));

        // 1. Get the editor component of your spinner:

        int i = 0;
        for (JButton btn : clock.allButtons) {
            buttonList[i] = btn;
            i++;
        }

        overviewCompleted = new JLabel("Naa paa oekt " + completedSessions + " av " + goalSession);
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

        textPanel.setLayout(new BorderLayout());
        textPanel.add(upperPanel, BorderLayout.NORTH);
        textPanel.add(titlePanel, BorderLayout.CENTER);
        textPanel.add(overviewPanel, BorderLayout.SOUTH);

        upperPanel.setOpaque(false);
        textPanel.setOpaque(false);
        titlePanel.setOpaque(false);
        overviewPanel.setOpaque(false);

        overviewPanel.add(overviewCompleted);

        JLabel spinnerText = new JLabel("Skriv inn antall minutter");
        spinnerPanel.add(spinner);
        spinnerPanel.add(spinnerText);

        JPanel clockPanel = clock.getPanel();

        mainPanel.add(textPanel);
        mainPanel.add(spinnerPanel);
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

        overviewCompleted.setForeground(secondaryDark);

        colorSchemeButton.setText("\u263E");
        colorSchemeButton.setBackground(secondaryLight);
        colorSchemeButton.setForeground(secondaryDark);

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

        overviewCompleted.setForeground(secondaryLight);

        isLight = false;
    }

}
