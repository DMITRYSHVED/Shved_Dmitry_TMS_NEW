package COLLECTIONS_TASK;

import FIRST_TASK.Person;

import java.io.*;
import java.util.*;

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

    private static Set<Document> readFile(File file) throws IOException {
        Set<Document> uniqueDocuments = new HashSet<>();
        Reader reader = new FileReader(file);
        String line;
        BufferedReader bufferedReader = null;

        try (reader) {
            bufferedReader = new BufferedReader(reader);
            while ((line = bufferedReader.readLine()) != null) {
                uniqueDocuments.add(processLine(line));
            }
        }finally {
            bufferedReader.close();
        }
        return uniqueDocuments;
    }

    private static Document processLine(String line) {
        Document document = new Document();
        String[] lines = line.split(",");

        if (lines[0].matches("((\\d{4}-)([A-Za-z]{3}-)){2}\\d[A_Za-z]\\d[A-Za-z]")) {
            document.setContractNumber(lines[0]);
            document.setInformation(lines[1]);
            document.setCreationDate(lines[2]);
        }
        return document;
    }

    public static void showMap() {
        HashMap<String, Document> documentHashMap = new HashMap<>();

        try {
            for (Document document : readFile(getFile())) {
                documentHashMap.put(document.getContractNumber(), document);
            }
            for (Map.Entry<String, Document> map : documentHashMap.entrySet()) {
                System.out.println(map.getKey() + " -> " + map.getValue());
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
