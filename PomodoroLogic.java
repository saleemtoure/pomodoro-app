import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Scanner;

public class PomodoroLogic {

    public static void main(String[] args) {
        // MyCountDownTimer myCountDownTimer = new MyCountDownTimer();
        // JFrame frame = new JFrame();
        // frame.add(myCountDownTimer.getPanel());
        // frame.setPreferredSize(new Dimension(250, 250));
        // frame.setResizable(false);
        // try {
        // UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        // } catch (Exception e) {
        // System.exit(1);
        // }
        // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // frame.pack();
        // frame.setLocationRelativeTo(null);
        // frame.setVisible(true);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter total session time in minutes:");
        float totalMinutes = Float.parseFloat(scanner.nextLine());

        int breakLength = 5;
        int workSessionLength = 25;

        int numWorkSessions = (int) Math.floor(totalMinutes / (workSessionLength + breakLength));

        int totalWorkTime = numWorkSessions * workSessionLength;
        int totalBreakTime = numWorkSessions * breakLength;

        System.out.println("Number of work sessions: " + numWorkSessions);
        System.out.println("Total time spent on work: " + totalWorkTime + " minutes");
        System.out.println("Number of breaks: " + numWorkSessions);
        System.out.println("Total time spent on breaks: " + totalBreakTime + " minutes");

        scanner.close();
    }
}
