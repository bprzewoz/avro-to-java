import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by splbap on 2017-10-13.
 */
public class FileHandler {

    public static void main(String[] args) {
        FileHandler fileHandler = new FileHandler("inputFile.txt", "outputFile.txt");
        int c;
        while ((c = fileHandler.nextChar()) != -1) {
            System.out.println((char) c);
        }
        fileHandler.closeFiles();
    }

    private FileReader inputStream = null;
    private FileWriter outputStream = null;

    public FileHandler(String inputFile, String outputFile) {
        openFiles(inputFile, outputFile);
    }

    private void openFiles(String inputFile, String outputFile) {
        openInputFile(inputFile);
        openOutputFile(outputFile);
    }

    private void openInputFile(String inputFile) {
        try {
            inputStream = new FileReader(inputFile);
        } catch (IOException ioe) {
            System.out.println("Blad otwarcia pliku wejsciowego.");
        }
    }

    private void openOutputFile(String outputFile) {
        try {
            outputStream = new FileWriter(outputFile);
        } catch (IOException ioe) {
            System.out.println("Blad otwarcia pliku wyjsciowego.");
        }
    }

    public int nextChar() {
        int ascii = -1;
        try {
            ascii = inputStream.read();
        } catch (IOException ioe) {
            System.out.println("Blad odczytu pliku wejsciowego.");
        }
        return ascii;
    }

    public void closeFiles() {
        closeInputFile();
        closeOutputFile();
    }

    private void closeInputFile() {
        try {
            if (inputStream != null) {
                inputStream.close();
            }
        } catch (IOException ioe) {
            System.out.println("Blad zamkniecia pliku wejsciowego.");
        }
    }

    private void closeOutputFile() {
        try {
            if (outputStream != null) {
                outputStream.close();
            }
        } catch (IOException ioe) {
            System.out.println("Blad zamkniecia pliku wyjsciowego.");
        }
    }
}
