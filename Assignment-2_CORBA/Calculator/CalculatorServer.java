import CalculatorApp.*;
import org.omg.CORBA.*;
import org.omg.PortableServer.*;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

class CalculatorImpl extends CalculatorPOA {
    private ORB orb;

    public void setORB(ORB orb_val) {
        orb = orb_val;
    }

    public float add(float a, float b) {
        return a + b;
    }

    public float subtract(float a, float b) {
        return a - b;
    }

    public float multiply(float a, float b) {
        return a * b;
    }

    public float divide(float a, float b) {
        if (b == 0) {
            throw new RuntimeException("Division by zero!");
        }
        return a / b;
    }
}

public class CalculatorServer {
    public static void main(String[] args) {
        try {
            // Initialize the ORB
            ORB orb = ORB.init(args, null);

            // Get reference to root POA & activate manager
            POA rootPoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            rootPoa.the_POAManager().activate();

            // Create and register servant
            CalculatorImpl calculatorImpl = new CalculatorImpl();
            calculatorImpl.setORB(orb);
            org.omg.CORBA.Object ref = rootPoa.servant_to_reference(calculatorImpl);
            Calculator href = CalculatorHelper.narrow(ref);

            // Bind object in Naming Service
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

            String name = "Calculator";
            NameComponent[] path = ncRef.to_name(name);
            ncRef.rebind(path, href);

            System.out.println("CalculatorServer ready and waiting ...");

            // Wait for requests
            orb.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}