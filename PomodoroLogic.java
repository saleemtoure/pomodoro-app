import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PomodoroLogic {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter total session time in minutes:");
        float totalMinutes = Float.parseFloat(scanner.nextLine());

        int breakLength = 5;
        int workSessionLength = 25; // Fixed work session length

        ArrayList<Object> sessionSequence = new ArrayList<>();
        ArrayList<Integer> sessionLengths = new ArrayList<>();

        // Calculate number of full work sessions and breaks
        int numWorkSessions = (int) Math.floor(totalMinutes / (workSessionLength + breakLength));
        int numBreaks = numWorkSessions; // Initially set to the number of work sessions

        // Ensure the last session is a work session by reducing the number of breaks
        if (numWorkSessions > 1) {
            numBreaks--;
        }

        // Calculate total time for full work sessions and breaks
        int usedMinutes = numWorkSessions * workSessionLength + numBreaks * breakLength;
        int leftoverMinutes = (int) totalMinutes - usedMinutes;

        // Distribute leftover minutes to work sessions
        int additionalMinutesPerSession = leftoverMinutes / numWorkSessions;
        int remainingMinutes = leftoverMinutes % numWorkSessions;

        for (int i = 0; i < numWorkSessions; i++) {
            int sessionTime = workSessionLength + additionalMinutesPerSession;
            if (remainingMinutes > 0) {
                sessionTime++;
                remainingMinutes--;
            }
            sessionLengths.add(sessionTime);
        }

        // Build session sequence
        for (int i = 0; i < numWorkSessions; i++) {
            sessionSequence.add((Object) new Work());
            if (i < numBreaks) {
                sessionSequence.add((Object) new Break());
            }
        }

        // Output results
        int totalWorkTime = sessionLengths.stream().mapToInt(Integer::intValue).sum();
        int totalBreakTime = numBreaks * breakLength;

        System.out.println("Number of work sessions: " + numWorkSessions);
        System.out.println("Total time spent on work: " + totalWorkTime + " minutes");
        System.out.println("Number of breaks: " + numBreaks);
        System.out.println("Total time spent on breaks: " + totalBreakTime + " minutes");

        // Output session sequence
        System.out.println("\nSession sequence:");
        System.out.println(sessionSequence);

        // Output session lengths
        System.out.println("\nSession lengths:");
        System.out.println(sessionLengths);

        scanner.close();
    }
}
