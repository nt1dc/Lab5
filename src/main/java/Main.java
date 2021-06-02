import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        File helpFile = new File("src/main/resources/help.txt");
        Scanner scanner =null;
        try {
            scanner = new Scanner(helpFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while (scanner.hasNextLine()) {
            System.out.println(scanner.nextLine());
        }
        if (args.length > 0) {
            Commander commander = new Commander(new CollectionManager(args[0]));
            commander.interactiveMod();
        } else {
            System.out.println("File path should be passed to program by using: command line argument. \n\n");
        System.exit(0);
        }
    }
}
