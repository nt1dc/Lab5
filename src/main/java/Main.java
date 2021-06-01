import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        if (args.length > 0) {
            Commander commander = new Commander(new CollectionManager(args[0]));
            commander.interactiveMod();
        } else {
            System.out.println("File path should be passed to program by using: command line argument. \n\n");
        System.exit(0);
        }
    }
}
