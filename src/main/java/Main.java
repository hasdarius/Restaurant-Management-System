import Controller.Controller;

public class Main {
    public static void main(String[] args) {

        if (args.length == 1) {
            Controller controller = new Controller(args[0]);
        } else {
            System.out.println("Please introduce the file from which to deserialize as argument!");
        }
    }
}
