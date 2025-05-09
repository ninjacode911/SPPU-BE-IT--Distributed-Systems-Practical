import java.rmi.Naming;
import java.util.Scanner;

public class Client implements Runnable {
    private final int a, b;
    private final String operation;

    public Client(int a, int b, String operation) {
        this.a = a;
        this.b = b;
        this.operation = operation;
    }

    public void run() {
        try {
            long threadId = Thread.currentThread().getId();
            System.out.println("\nThread ID: " + threadId + " performing: " + operation);

            String url = "rmi://localhost/Server";
            ServerInterface serverInterface = (ServerInterface) Naming.lookup(url);

            if (operation.equals("add")) {
                System.out.println("Addition: " + serverInterface.add(a, b));
            } else if (operation.equals("sub")) {
                System.out.println("Subtraction: " + serverInterface.sub(a, b));
            } else if (operation.equals("mul")) {
                System.out.println("Multiplication: " + serverInterface.mul(a, b));
            } else if (operation.equals("div")) {
                System.out.println("Division: " + serverInterface.div(a, b));
            } else {
                System.out.println("Invalid operation");
            }
        } catch (Exception e) {
            System.out.println("Client Error: " + e);
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] operations = {"add", "sub", "mul", "div"};

        try {
            System.out.print("Enter Num 1 >> ");
            int a = sc.nextInt();

            System.out.print("Enter Num 2 >> ");
            int b = sc.nextInt();

            for (String op : operations) {
                Thread clientThread = new Thread(new Client(a, b, op));
                clientThread.start();
                clientThread.join();  // Optional: sequential execution
            }
        } catch (Exception e) {
            System.out.println("Client Error: " + e);
        } finally {
            sc.close();
        }
    }
}