import java.io.*;

/**
 * Created by splbap on 2017-10-13.
 */
public class FileHandler {

    public static void main(String[] args) {
        FileHandler fileHandler = new FileHandler("inputFile.txt", "outputFile.txt");
        Lexer lexer = new Lexer(fileHandler);
        Token token;
        while ((token = lexer.nextToken()).getTokenType() != TokenType.EOF) {
            System.out.println(token.getTokenValue());
        }
        fileHandler.closeFiles();
    }

    private InputStream inputStream = null;
    private OutputStream outputStream = null;
    private BufferedInputStream bufferedInputStream = null;

    public FileHandler(String inputFile, String outputFile) {
        openFiles(inputFile, outputFile);
    }

    private void openFiles(String inputFile, String outputFile) {
        openInputFile(inputFile);
        openOutputFile(outputFile);
    }

    private void openInputFile(String inputFile) {
        try {
            inputStream = new FileInputStream(inputFile);
            bufferedInputStream = new BufferedInputStream(inputStream);
        } catch (IOException ioe) {
            System.out.println("Blad otwarcia pliku wejsciowego.");
        }
    }

    private void openOutputFile(String outputFile) {
        try {
            outputStream = new FileOutputStream(outputFile);
        } catch (IOException ioe) {
            System.out.println("Blad otwarcia pliku wyjsciowego.");
        }
    }

    public int nextChar() {
        int ascii = -1;
        try {
            ascii = bufferedInputStream.read();
        } catch (IOException ioe) {
            System.out.println("Blad odczytu pliku wejsciowego.");
        }
        return ascii;
    }

    public void mark() {
        bufferedInputStream.mark(10);
    }

    public void reset() {
        try {
            bufferedInputStream.reset();
        } catch (IOException ioe) {
            System.out.println("Blad resetowania znacznika pliku wejsciowego.");
        }
    }

    public void closeFiles() {
        closeInputFile();
        closeOutputFile();
    }

    private void closeInputFile() {
        try {
            if (bufferedInputStream != null) {
                bufferedInputStream.close();
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
