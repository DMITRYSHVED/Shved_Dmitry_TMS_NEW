package COLLECTIONS_TASK;

import java.io.*;
import java.util.*;

public class FileManager {

    private static Set<Document> uniqueDocuments;

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

    private static void readFile() throws IOException {
        uniqueDocuments = new HashSet<>();
        Reader reader = new FileReader(getFile());
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
        HashMap<String, Document> documentHashMap = new HashMap<>();

        try {
            readFile();
            for (Document document : uniqueDocuments) {
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
