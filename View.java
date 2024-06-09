import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class View {

    JFrame vindu;
    JPanel hovedPanel, tekstPanel, tittelPanel, oversiktPanel, knappPanel, mediaPanel;

    View() {
        vindu = new JFrame("Pomodoro");
        hovedPanel = new JPanel();
        tekstPanel = new JPanel();
        tittelPanel = new JPanel();
        oversiktPanel = new JPanel();
        knappPanel = new JPanel();
        mediaPanel = new JPanel();
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
        vindu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // !TODO : FIKS FARGER
        // Add darkmode & Lightmode
        hovedPanel.setLayout(new BorderLayout());
        hovedPanel.setBackground(swing.BLACK);
        hovedPanel.setOpaque(true);

        tekstPanel.setLayout(new BorderLayout());
        tekstPanel.add(tittelPanel, BorderLayout.NORTH);
        tekstPanel.add(oversiktPanel, BorderLayout.SOUTH);

        int fullfortMin = 0;
        int maalMin = 0;
        tittelPanel.add(
                new JLabel("Fullfoert: " + fullfortMin + " av "
                        + maalMin + " minutter"));

        int fullfortOekt = 0;
        int maalOekt = 0;
        oversiktPanel.add(new JLabel("Naa paa oekt " + fullfortOekt + " av " + maalOekt));

        knappPanel.add(new JButton("Her skal tiden staa"));
        knappPanel.add(new JButton("Pauseknapp"));

        mediaPanel.add(new JButton("Pause avspilling"));
        mediaPanel.add(new JButton("Fortsett avspilling"));

        hovedPanel.add(tekstPanel, BorderLayout.NORTH);
        hovedPanel.add(knappPanel, BorderLayout.CENTER);
        hovedPanel.add(mediaPanel, BorderLayout.SOUTH);

        vindu.add(hovedPanel);

        vindu.setSize(400, 400);
        vindu.pack();
        vindu.setLocationRelativeTo(null);
        vindu.setVisible(true);
    }

}
