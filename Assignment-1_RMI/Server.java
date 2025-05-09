import java.rmi.Naming;

public class Server {
    public static void main(String[] args) {
        try {
            ServerImplementation obj = new ServerImplementation();
            Naming.rebind("rmi://localhost/Server", obj);
            System.out.println("Server Started");
        } catch (Exception e) {
            System.out.println("Exception Occurred at Server! " + e.getMessage());
        }
    }
}