package FIRST_TASK;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class FileManager {

    private static File getFile() throws IOException {
        Scanner scanner = new Scanner(System.in);
        File file;

        System.out.println("Введите путь к файлу:");
        file = new File(scanner.nextLine());
        if (!file.exists()) {
            throw new FileNotFoundException("Такого файла не существует");
        }
        return file;
    }

    private static ArrayList<Person> readFile(File file) throws IOException, PersonException {
        ArrayList<Person> persons = new ArrayList<>();
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
                persons.add(processLine(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            bufferedReader.close();
        }
        return persons;
    }

    private static Person processLine(String line) {
        Person person = new Person();
        String[] lines = line.split(",");

        if (lines.length == 4) {
            person.setName(lines[0].trim());
            person.setSurname(lines[1].trim());
            person.setSex(lines[2].trim());
            person.setAge(Integer.parseInt(lines[3].trim()));
        }
        return person;
    }

    private static void workWithList(ArrayList<Person> persons) {
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

    private static void writeFile(File file, ArrayList<Person> persons) throws IOException {
        Writer writer = new FileWriter(file);

        try (writer) {
            for (Person person : persons) {
                writer.write(person.getName() + "," + person.getSurname() + "," + person.getSex() + "," + person.getAge() + "\n");
            }
        }
    }

    public static void letsGo() {
        try {
            File file = getFile();
            ArrayList<Person> persons = readFile(file);

            writeFile(file, persons);
            workWithList(persons);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
