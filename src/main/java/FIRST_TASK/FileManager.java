package FIRST_TASK;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class FileManager {

    private static Scanner scanner;
    private static ArrayList<Person> persons;
    private static File file;
    private static int count;
    private static int male;
    private static int female;


    private static void getFile() throws IOException {

        scanner = new Scanner(System.in);

        System.out.println("Введите путь к файлу:");
        file = new File(scanner.nextLine());
        if (!file.exists()) {
            throw new FileNotFoundException("Такого файла не существует");
        }
    }

    private static void readFile() throws FileNotFoundException, PersonException {

        persons = new ArrayList<>();
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
        String[] lines = line.split(",");

        if (lines.length == 4) {
            person.setName(lines[0].trim());
            person.setSurname(lines[1].trim());
            person.setSex(lines[2].trim());
            person.setAge(Integer.parseInt(lines[3].trim()));
        }
        persons.add(person);
    }

    private static void workWithList() {

        count = 0;
        male = 0;
        female = 0;
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
            workWithList();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
