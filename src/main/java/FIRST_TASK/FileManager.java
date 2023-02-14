package FIRST_TASK;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class FileManager {

    private static List<Person> persons;
    private static File file; //использвуется в двух методах

    private static void getFile() throws IOException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите путь к файлу:");
        file = new File(scanner.nextLine());
        if (!file.exists()) {
            throw new FileNotFoundException("Такого файла не существует");
        }
    }

    private static void readFile() throws IOException, PersonException {
        persons = new ArrayList<>();
        Reader reader = new FileReader(file);
        String line;
        BufferedReader bufferedReader = null;

        try (reader) {
            bufferedReader = new BufferedReader(reader);
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
        }finally {
            bufferedReader.close();
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
        int count = 0;
        int male = 0;
        int female = 0;
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
