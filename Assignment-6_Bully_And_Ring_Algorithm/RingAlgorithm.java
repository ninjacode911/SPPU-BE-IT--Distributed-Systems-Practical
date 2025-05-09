import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

class RingAlgorithm {
    private List<Integer> processes;
    private Integer leader;

    public RingAlgorithm(List<Integer> processes) {
        this.processes = new ArrayList<>(processes);
        Collections.sort(this.processes);
        this.leader = null;
    }

    public void startElection(int initiator) {
        System.out.println("Process " + initiator + " starts an election.");
        List<Integer> electionRing = new ArrayList<>();
        int index = processes.indexOf(initiator);

        if (index == -1) { // Handle invalid initiator
            System.out.println("Error: Initiator process " + initiator + " not found.");
            return;
        }

        for (int i = 0; i < processes.size(); i++) {
            int process = processes.get((index + i) % processes.size());
            electionRing.add(process);
            System.out.println("Process " + process + " passes the election message.");
        }

        leader = Collections.max(electionRing);
        System.out.println("Process " + leader + " is elected as the leader.");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Step 1: Get process IDs from the user
        System.out.print("Enter the number of processes: ");
        int n = scanner.nextInt();

        List<Integer> processes = new ArrayList<>();
        System.out.println("Enter process IDs: ");
        for (int i = 0; i < n; i++) {
            processes.add(scanner.nextInt());
        }

        // Step 2: Select initiator 
        System.out.print("Enter the initiator process ID: ");
        int initiator = scanner.nextInt();

        RingAlgorithm ring = new RingAlgorithm(processes);
        ring.startElection(initiator);

        scanner.close();
    }
}