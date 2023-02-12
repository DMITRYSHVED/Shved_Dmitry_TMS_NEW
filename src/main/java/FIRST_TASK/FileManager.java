package FIRST_TASK;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class FileManager {

    private static Scanner scanner = new Scanner(System.in);
    private static ArrayList<Person> persons = new ArrayList<>();
    private static File file;
    private static int count = 0;
    private static int male = 0;
    private static int female = 0;


    private static void getFile() throws IOException {

        System.out.println("Введите путь к файлу:");
        file = new File(scanner.nextLine());
        if (!file.exists()) {
            file.createNewFile();
        }
    }

    private static void readFile() throws FileNotFoundException, PersonException {

        Reader reader = new FileReader(file);
        String line;
        try (reader) {
            BufferedReader bufferedReader = new BufferedReader(reader);
            while ((line = bufferedReader.readLine()) != null) {
                if (line.isEmpty()) {
                    continue;
                }
                if (!line.matches("([A-Za-z\s]+,){3}\\d+")) {
                    throw new PersonException("INVALID PERSON INFORMATION FORM");
                }
                processLine(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void processLine(String line) {

        Person person = new Person();
        String[] lines;
        lines = line.split(",");
        if (lines.length == 4) {
            person.setName(lines[0].trim());
            person.setSurname(lines[1].trim());
            person.setSex(lines[2].trim());
            person.setAge(Integer.parseInt(lines[3].trim()));
        }
        persons.add(person);
    }

    private static void workWithList() {

        Comparator<Person> comparator = Comparator.comparing(obj -> obj.getName());
        comparator.thenComparing(obj -> obj.getSurname());
        persons.sort(comparator);
        for (Person person : persons) {
            if (person.getAge() > 30) {
                count++;
            }
            if (person.getSex().equalsIgnoreCase("male")) {
                male++;
            } else if (person.getSex().equalsIgnoreCase("female")) {
                female++;
            }
        }
        System.out.println("Count: " + count + "\nMale: " + male + "\nFemale: " + female);
    }

    private static void writeFile() throws IOException {

        Writer writer = new FileWriter(file);
        try (writer) {
            for (Person person : persons) {
                writer.write(person.getName() + "," + person.getSurname() + "," + person.getSex() + "," + person.getAge() + "\n");
            }
        }
    }

    public static void letsGo() {

        try {
            getFile();
            readFile();
            writeFile();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        workWithList();
    }
}
