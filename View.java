import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class View {

    JFrame vindu;
    JPanel hovedPanel, toppPanel, tekstPanel, tittelPanel, oversiktPanel, knappPanel, mediaPanel;
    JLabel oversiktMaal, oversiktFullfoert;
    JButton modus, tid, pauseTid, avspilling, pauseAvspilling;
    JButton[] knappeListe;
    Color mainDark, buttonDark, mainLight, buttonLight;

    int fullfortMin, maalMin, fullfortOekt, maalOekt;
    Boolean isLight;

    View() {
        vindu = new JFrame("Pomodoro");
        hovedPanel = new JPanel();
        toppPanel = new JPanel();
        tekstPanel = new JPanel();
        tittelPanel = new JPanel();
        oversiktPanel = new JPanel();
        knappPanel = new JPanel();
        mediaPanel = new JPanel();

        modus = new JButton("Bytt til lys modus");
        tid = new JButton("Her skal tiden staa");
        pauseTid = new JButton("Pauseknapp");
        avspilling = new JButton("Pause avspilling");
        pauseAvspilling = new JButton("Fortsett avspilling");

        mainDark = new Color(30, 30, 30);
        buttonDark = new Color(55, 55, 61);
        mainLight = new Color(250, 250, 250);
        buttonLight = new Color(204, 204, 204);

        knappeListe = new JButton[] { modus, tid, pauseTid, avspilling, pauseAvspilling };

        oversiktMaal = new JLabel("Fullfoert: " + fullfortMin + " av " + maalMin + " minutter");
        oversiktFullfoert = new JLabel("Naa paa oekt " + fullfortOekt + " av " + maalOekt);
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
        vindu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        hovedPanel.setLayout(new BorderLayout());
        vindu.getContentPane().add(hovedPanel);
        lysModus();

        // * Dark and Lightmode */

        toppPanel.add(modus);

        class ByttModus implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent e) {
                byttModus();
            }

        }
        modus.addActionListener(new ByttModus());

        // * */

        tekstPanel.setLayout(new BorderLayout());
        tekstPanel.add(toppPanel, BorderLayout.NORTH);
        tekstPanel.add(tittelPanel, BorderLayout.CENTER);
        tekstPanel.add(oversiktPanel, BorderLayout.SOUTH);

        toppPanel.setOpaque(false);
        tekstPanel.setOpaque(false);
        tittelPanel.setOpaque(false);
        oversiktPanel.setOpaque(false);
        knappPanel.setOpaque(false);
        mediaPanel.setOpaque(false);

        tittelPanel.add(oversiktMaal);

        oversiktPanel.add(oversiktFullfoert);

        knappPanel.add(tid);
        knappPanel.add(pauseTid);

        mediaPanel.add(avspilling);
        mediaPanel.add(pauseAvspilling);

        hovedPanel.add(tekstPanel, BorderLayout.NORTH);
        hovedPanel.add(knappPanel, BorderLayout.CENTER);
        hovedPanel.add(mediaPanel, BorderLayout.SOUTH);

        vindu.pack();
        vindu.setLocationRelativeTo(null);
        vindu.setVisible(true);
    }

    void byttModus() {
        if (isLight) { // skal bli moerk
            moerkModus();
        } else { // skal bli lys
            lysModus();
        }
    }

    void lysModus() {
        hovedPanel.setBackground(mainLight);
        for (JButton knapp : knappeListe) {
            knapp.setBackground(buttonLight);
            knapp.setForeground(buttonDark);
        }

        oversiktFullfoert.setForeground(buttonDark);
        oversiktMaal.setForeground(buttonDark);

        modus.setText("Bytt til moerk modus");

        isLight = true;
    }

    void moerkModus() {
        hovedPanel.setBackground(mainDark);
        for (JButton knapp : knappeListe) {
            knapp.setBackground(buttonDark);
            knapp.setForeground(buttonLight);
        }

        modus.setText("Bytt til lys modus");

        oversiktFullfoert.setForeground(buttonLight);
        oversiktMaal.setForeground(buttonLight);

        isLight = false;
    }

}
