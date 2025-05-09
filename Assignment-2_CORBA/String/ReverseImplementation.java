import ReverseModule.ReversePOA;

class ReverseImplementation extends ReversePOA {
    ReverseImplementation() {
        super();
        System.out.println("Reverse Object Created");
    }

    public String reverse_string(String str) {
        StringBuffer sb = new StringBuffer(str);
        return "Server Send : " + sb.reverse().toString();
    }
}