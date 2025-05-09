import ReverseModule.*;
import org.omg.CosNaming.*;
import org.omg.CORBA.*;
import java.io.*;

public class ReverseClient {
    public static void main(String[] args) {
        try {
            ORB orb = ORB.init(args, null);
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

            Reverse reverseImpl = ReverseHelper.narrow(ncRef.resolve_str("Reverse"));

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Enter a string: ");
            String str = br.readLine();

            String result = reverseImpl.reverse_string(str);
            System.out.println(result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}