package COLLECTIONS_TASK;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;

public class FileManager {

    private static Scanner scanner = new Scanner(System.in);
    private static File file;
    private static HashSet<Document> uniqueDocuments = new HashSet<>();
    private static HashMap<String, Document> documentHashMap = new HashMap<>();

    private static void getFile() throws IOException {

        System.out.println("Введите путь к файлу:");
        file = new File(scanner.nextLine());
        if (!file.exists()) {
            file.createNewFile();
        }
    }

    private static void readFile() throws IOException {

        Reader reader = new FileReader(file);
        String line;
        try (reader) {
            BufferedReader bufferedReader = new BufferedReader(reader);
            while ((line = bufferedReader.readLine()) != null) {
                processLine(line);
            }
        }
    }

    private static void processLine(String line) {

        Document document;
        String[] lines = line.split(",");

        if (lines[0].matches("((\\d{4}-)([A-Za-z]{3}-)){2}\\d[A_Za-z]\\d[A-Za-z]")) {
            document = new Document();
            document.setContractNumber(lines[0]);
            document.setInformation(lines[1]);
            document.setCreationDate(lines[2]);
            uniqueDocuments.add(document);
        }
    }

    public static void showMap() {

        try {
            getFile();
            readFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (Document document : uniqueDocuments) {
            documentHashMap.put(document.getContractNumber(), document);
        }
        for (Map.Entry<String, Document> map : documentHashMap.entrySet()) {
            System.out.println(map.getKey() + " -> " + map.getValue());
        }
    }
}
