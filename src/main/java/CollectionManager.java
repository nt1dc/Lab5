import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.*;
import java.util.Date;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

public class CollectionManager {

    private Queue<StudyGroup> groups;
    Boolean correctly = false;
    private final Date initDate;
    String savePath;

    public CollectionManager(String collectionPath) throws IOException {
        if (collectionPath == null)
            throw new FileNotFoundException("File path should be passed to program by using: command line argument");
        try {
            savePath = collectionPath;
            load(collectionPath);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Something gone wrong with file");
            System.exit(0);
        }


        this.initDate = new Date();


    }

    /**
     * Loading collection from file
     *
     * @param collectionPath
     * @throws IOException
     */
    public void load(String collectionPath) throws IOException {
        groups = new PriorityQueue<StudyGroup>();
        File file = new File(collectionPath);
        ObjectMapper xmlMapper = new XmlMapper();
        String xml = inputStreamToString(new FileInputStream(file));
        groups = xmlMapper.readValue(xml, new TypeReference<PriorityQueue<StudyGroup>>() {
        });
        if (!groups.isEmpty()) {
            System.out.println("Collection created");
        } else System.out.println("Empty collection");
    }

    /**
     * HelpMethod for  load
     * BufferReading
     *
     * @param is
     * @return
     * @throws IOException
     */
    private String inputStreamToString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        String line = new String();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        while (true) {
            try {
                if ((line = br.readLine()) == null) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            sb.append(line);
        }
        try {
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * Show Commands
     * Read help-file
     */
    public void help() {
        File helpFile = new File("help.txt");
        Scanner scanner = null;
        try {
            scanner = new Scanner(helpFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while (scanner.hasNextLine()) {
            System.out.println(scanner.nextLine());
        }
    }

    /**
     * Info about collection
     */
    public void info() {
        try {


            System.out.println("" +
                    "Collection Type: " + groups.getClass().getSimpleName() +
                    "\nInitialization date : " + initDate +
                    "\nCollection size : " + groups.size());
        } catch (NullPointerException e) {
            System.out.println("Empty collection");
        }
    }

    /**
     * add element to collection
     *
     * @param scanner
     */
    public void add(Scanner scanner) {
        GroupBuilder newGroup = new GroupBuilder(scanner);
        newGroup.setFields();
        groups.add(newGroup.studyGropCreator());
    }

    /**
     * Show collection elements
     */
    public void show() {
        try {


            if (groups.size() != 0)
                groups.forEach(group -> System.out.println(group.toString() + "\n"));
            else System.out.println("Коллекция пуста.");
        } catch (NullPointerException e) {
            System.out.println("Пустая коллекция");
        }
    }

    /**
     * Update element of collection by ID
     *
     * @param id
     * @param scanner
     */
    public void update(String id, Scanner scanner) {
        try {
            Long thisId = Long.parseLong(id);
            if (groups.size() != 0) {
                for (StudyGroup group : groups) {
                    if (group.getId().equals(thisId)) {
                        group.updateName(scanner);
                        group.updateStudentsCount(scanner);
                        group.getCoordinates().updateX(scanner);
                        group.getCoordinates().updateY(scanner);
                        group.updateStudentsCount(scanner);
                        group.updateFormOfEducationByScan(scanner);
                        group.updateSemesterEnum(scanner);
                        group.getGroupAdmin().updateName(scanner);
                        group.getGroupAdmin().updateBirthday(scanner);
                        group.getGroupAdmin().updateHeight(scanner);
                        group.getGroupAdmin().updateWeight(scanner);
                        group.getGroupAdmin().updatePassportID(scanner);
                        break;
                    }
                }
            } else System.out.println("Empty collection");
        } catch (Exception e) {
            System.out.println("Incorrect ID");
        }
        System.out.println("Element successfully updated");
    }

    /**
     * Clear Collection
     */
    public void clear() {
        try {
            System.out.println("Clearing...");
            if (!groups.isEmpty()) {
                groups.clear();
                System.out.println("Successfully cleared");

            } else System.out.println("Impossible to clear empty collection");
        } catch (Exception e) {
            System.out.println("ya ne ebu ch sluchilos");
        }
    }

    /**
     * Сохранение коллекции в файл
     */
    public void save() {
        XmlMapper xmlMapper = new XmlMapper();
        File file = new File(savePath);
        System.out.println("Saving....");

        try {
            xmlMapper.writeValue(file, groups);

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Successfully saved");
    }

    /**
     * Delete element of collection by id
     *
     * @param id
     */
    public void remove_by_id(String id) {
        correctly = false;
        if (groups.isEmpty()) System.out.println("Empty collection");
        else
            try {
                Long removeID = Long.parseLong(id);

                for (StudyGroup group : groups) {
                    if (group.getId().equals(removeID)) {
                        groups.remove(group);
                        correctly = true;
                        break;
                    }
                }
                if (correctly) {
                    System.out.println("Group successfully deleted");


                } else {
                    System.out.println("Group with "+removeID+" hasn't found");
                }
            } catch (Exception e) {
                System.out.println("Incorrect ID");
            }
        correctly = false;
    }

    /**
     * Delete element of collection by FormOfEducation
     *
     * @param form
     */
    public void remove_all_by_form_of_education(String form) {
        correctly = false;
        try {
            for (StudyGroup group : groups) {
                if (group.getFormOfEducation().equals(FormOfEducation.valueOf(form))) {
                    groups.remove(group);
                    System.out.println("Group " + group.getName() + "  was deleted");
                    correctly = true;
                }
            }
            if (correctly) {
                System.out.println("Groups with  " + form + " was deleted");
            } else System.out.println("No group with this for of education");
        } catch (Exception e) {
            System.out.println("Incorrect form of education");
        }
    }

    /**
     * Shows count of groups with entered semester
     *
     * @param semeter
     */
    public void count_by_semester_enum(String semeter) {
        if (semeter.equals("THIRD") || semeter.equals("FIFTH") || semeter.equals("SIXTH")) {

            try {
                int counter = 0;

                for (StudyGroup group : groups) {
                    if (group.getSemesterEnum().equals(Semester.valueOf(semeter))) {
                        counter++;
                    }
                }
                System.out.println("Founded " + counter + " groups");
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else System.out.println("Entered incorrect semester");

    }

    /**
     * Add new element if, it's count of students less than minimum
     *
     * @param scanner
     */
    public void add_if_min(Scanner scanner) {
        try {
            StudyGroup newStudyGroup = new GroupBuilder(scanner).studyGropCreator();
            if (newStudyGroup.getStudentsCount() < groups.poll().getStudentsCount()) {
                groups.add(newStudyGroup);
                System.out.println("Group successfully added");
            } else {
                System.out.println("Founded less count of students");
            }
        } catch (Exception e) {
            System.out.println("GOD BLESS");
        }
    }

    /**
     * Shows first element add delete it
     */
    public void remove_head() {
        if (groups.isEmpty()){
            System.out.println("Collection is empty");

        }else {
            System.out.println(groups.poll());
        }
    }

    /**
     * Show unique semesterEnum
     */
    public void print_unique_semester_enum() {
        int[] hasEnum = {0, 0, 0};
        boolean hasUnique = false;
        if (!groups.isEmpty()) {
            System.out.println("Searching unique  semesterEnum....");
            for (StudyGroup group : groups) {
                if (group.getSemesterEnum().equals(Semester.THIRD)) {
                    hasEnum[0] = hasEnum[0] + 1;
                }
                if (group.getSemesterEnum().equals(Semester.FIFTH)) {
                    hasEnum[1] = hasEnum[0] + 1;
                }
                if (group.getSemesterEnum().equals(Semester.SIXTH)) {
                    hasEnum[2] = hasEnum[0] + 1;
                }

            }
            if (hasEnum[0] == 1) {
                System.out.println("THIRD");
                hasUnique = true;
            }
            if (hasEnum[1] == 1) {
                System.out.println("FIFTH");
                hasUnique = true;
            }
            if (hasEnum[2] == 1) {
                System.out.println("SIXTH");
                hasUnique = true;
            }
            if (!hasUnique) System.out.println("Unique semesterEnum wasn't founded");
        } else System.out.println(("Empty collection" +
                ""));
    }
}


