import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

class BullyAlgorithm {
    private List<Integer> processes;
    private Integer leader;

    public BullyAlgorithm(List<Integer> processes) {
        this.processes = new ArrayList<>(processes);
        Collections.sort(this.processes); // Sort to ensure highest ID wins
        this.leader = null;
    }

    public void startElection(int initiator) {
        System.out.println("Process " + initiator + " starts an election...");
        List<Integer> higherProcesses = new ArrayList<>();

        for (int process : processes) {
            if (process > initiator) {
                higherProcesses.add(process);
            }
        }

        if (higherProcesses.isEmpty()) {
            leader = initiator;
            System.out.println("Process " + initiator + " is the new leader!");
        } else {
            boolean responseReceived = false;
            for (int process : higherProcesses) {
                System.out.println("Process " + initiator + " sends election message to " + process);
                
                // Simulating response with user input instead of random
                Scanner scanner = new Scanner(System.in);
                System.out.print("Should process " + process + " respond? (yes/no): ");
                String response = scanner.next().toLowerCase();

                if (response.equals("yes")) { 
                    responseReceived = true;
                    System.out.println("Process " + process + " responds and starts its own election...");
                    startElection(process);
                    return;
                }
            }

            if (!responseReceived) {
                leader = initiator;
                System.out.println("No response from higher processes.");
                System.out.println("Process " + initiator + " is the new leader!");
            }
        }
    }

    public void detectCrash(Integer crashedLeader) {
        if (crashedLeader == null) {
            System.out.println("No leader has been elected yet. Election needs to be initiated.");
            return;
        }
        if (leader != null && leader.equals(crashedLeader)) {
            System.out.println("Leader " + crashedLeader + " has crashed! Starting new election...");
            startElection(processes.get(0)); // Restart election from the smallest ID process
        }
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

        BullyAlgorithm bully = new BullyAlgorithm(processes);
        bully.startElection(initiator);

        // Step 3: Simulate leader failure
        System.out.print("Do you want to simulate leader failure? (yes/no): ");
        String crashInput = scanner.next().toLowerCase();

        if (crashInput.equals("yes")) {
            System.out.print("Enter the crashed leader ID: ");
            int crashedLeader = scanner.nextInt();
            bully.detectCrash(crashedLeader);
        }

        scanner.close();
    }
}