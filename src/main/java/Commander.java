import java.io.*;
import java.util.*;

public class Commander {
    private final ArrayList<String> execute_Files = new ArrayList<>();
    private final CollectionManager manager;
    private String userCommand;

    private String[] finalUserCommand;
    private final String[] history = new String[6];

    {
        userCommand = "";
    }

    public Commander(CollectionManager manager) {
        this.manager = manager;
    }

    /**
     * Хрень которая лишила меня сна 19.05.2021-20.05.2021
     * а в дальнейшем мб и вуза =/
     */

    private void execute_script(String path) {

        if (execute_Files.contains(path)) {
            System.out.println("Looping " + "\n" +
                    "Going to intensive mod ");
            try {
                execute_Files.clear();
                interactiveMod();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        File file = new File(path);
        try (Scanner exScanner = new Scanner(file)) {
            execute_Files.add(path);
            while (!userCommand.equals("exit")) {
                try {
                    userCommand = exScanner.nextLine();

                } catch (NoSuchElementException e) {
                    try {
                        execute_Files.clear();
                        interactiveMod();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
                finalUserCommand = userCommand.trim().split(" ", 2);
                start(exScanner);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
    }

    /**
     * Interactive mod
     */
    public void interactiveMod() throws IOException {
        try (Scanner commandReader = new Scanner(System.in)) {
            while (!userCommand.equals("exit")) {
                try {
                    userCommand = commandReader.nextLine();
                    finalUserCommand = userCommand.trim().split(" ", 2);
                    start(commandReader);
                } catch (NoSuchElementException e) {
                    System.exit(0);
                }


            }
        }
    }

    /**
     * Gets command and use them
     */
    public void start(Scanner scanner) {

        try {
            history[5] = this.finalUserCommand[0];
            for (int i = 0; i < 5; i++) {
                history[i] = history[i + 1];
            }
            switch (this.finalUserCommand[0]) {

                case "":
                    break;
                case "help":
                    manager.help();
                    break;
                case "info":
                    manager.info();
                    break;
                case "add":
                    manager.add(scanner);
                    break;
                case "show":
                    manager.show();
                    break;
                case "update":
                    manager.update(this.finalUserCommand[1], scanner);
                    break;
                case "clear":
                    manager.clear();
                    break;
                case "save":
                    manager.save();
                    break;
                case "remove_by_id":
                    manager.remove_by_id(this.finalUserCommand[1]);
                    break;

                case "history":
                    System.out.println("Last 5 commands: \n");
                    for (int i = 0; i < 5; i++) {
                        if (history[i] != null) {
                            System.out.println(history[i]);
                        }
                    }
                    break;
                case "remove_all_by_form_of_education":
                    manager.remove_all_by_form_of_education(this.finalUserCommand[1]);
                    break;
                case "count_by_semester_enum":
                    manager.count_by_semester_enum(this.finalUserCommand[1]);
                    break;
                case "add_if_min":
                    manager.add_if_min(scanner);
                    break;
                case "remove_head":
                    manager.remove_head();
                    break;
                case "execute_script":
                    try {
                        execute_script(this.finalUserCommand[1]);
                    } catch (Exception e) {
                        System.out.println("File error");
                        try {
                            interactiveMod();
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    }
                    break;
                case "print_unique_semester_enum":
                    manager.print_unique_semester_enum();
                    break;
                default:
                    if (finalUserCommand[0].equals("exit")) System.exit(0);
                    System.out.println("Unknown command. Use 'help' for help.");
                    for (int i = 5; i > 0; i--) {
                        history[i] = history[i - 1];
                    }
            }
        } catch (ArrayIndexOutOfBoundsException ex) {
            System.out.println("No argument ex");
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Commander)) return false;
        Commander commander = (Commander) o;
        return Objects.equals(manager, commander.manager);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(manager, userCommand);
        result = 31 * result + Arrays.hashCode(finalUserCommand);
        return result;
    }
}