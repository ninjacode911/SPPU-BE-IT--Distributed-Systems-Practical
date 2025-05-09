import java.util.*;

public class TokenRing {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Getting the number of nodes in the ring
        System.out.println("Enter the number of nodes you want in the ring : ");
        int n = sc.nextInt();

        // Display the ring formation
        System.out.println("Ring formed is as below : ");
        for (int i = 0; i < n; i++) {
            System.out.print(i + " ");
        }
        System.out.println("\n0 (loop back to Node 0)");
        System.out.println("Node : Initially Token is at Node 0");

        int choice = 0;
        int token = 0; // Initially token is at node 0
        int sender, receiver;

        do {
            // Getting sender, receiver, and data validation of sender
            while (true) {
                System.out.println("\nEnter Sender: ");
                sender = sc.nextInt();
                if (sender < 0 || sender >= n) {
                    System.out.println("\nEnter a valid sender between 0 and " + (n-1));
                } else {
                    break;
                }
            }

            // Validation of receiver
            while (true) {
                System.out.println("Enter Receiver: ");
                receiver = sc.nextInt();
                if (receiver < 0 || receiver >= n) {
                    System.out.println("\nEnter a valid receiver between 0 and " + (n-1));
                } else {
                    break;
                }
            }

            System.out.println("\nEnter Data to send: ");
            int data = sc.nextInt();

            // Token passing
            System.out.println("\nSender is " + sender + " and Sending Data is " + data);

            // Token Passing: Move token from current position to sender
            System.out.println("\nToken Passing: [Previous Node -> Next Node]");
            if (token != sender) {
                for (int i = token; i != sender; i = (i + 1) % n) {
                    System.out.print(i + " -> ");
                }
            }
            System.out.println(sender);
            System.out.println("Note: Current Token is at Node " + sender);

            // Data Passing: Move data from sender to receiver
            System.out.println("\nData Passing: [Previous Node -> Next Node]");
            for (int i = sender; i != receiver; i = (i + 1) % n) {
                System.out.print(i + " -> ");
            }
            System.out.println(receiver);
            System.out.println("\nReceiver: " + receiver + ", Received the data: " + data);
            
            // Token now moves to the sender
            token = sender;

            // Asking user if they want to send data again
            System.out.print("\nDo you want to send data again? If yes Enter 1, If no Enter 0: ");
            choice = sc.nextInt();

        } while (choice == 1);

        sc.close();
    }
}