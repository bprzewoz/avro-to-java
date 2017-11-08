package files;

import exceptions.InvalidFileException;
import lexing.Lexer;
import lexing.Token;
import lexing.TokenType;

import java.io.*;

/**
 * Created by splbap on 2017-10-13.
 */
public class FileHandler {

    private String inputFile = null;
    private String outputFile = null;
    private InputStream inputStream = null;
    private OutputStream outputStream = null;
    private BufferedInputStream bufferedInputStream = null;

    public FileHandler(String inputFile) {
        try {
            openInputFile(inputFile);
        } catch (InvalidFileException invalidFileException) {
            System.out.println(invalidFileException.getMessage());
        }
    }

    public FileHandler(String inputFile, String outputFile) {
        try {
            openInputFile(inputFile);
            openOutputFile(outputFile);
        } catch (InvalidFileException invalidFileException) {
            System.out.println(invalidFileException.getMessage());
        }
    }

    private void openInputFile(String inputFile) throws InvalidFileException {
        try {
            this.inputFile = inputFile;
            inputStream = new FileInputStream(inputFile);
            bufferedInputStream = new BufferedInputStream(inputStream);
        } catch (IOException ioexception) {
            throw new InvalidFileException(100, inputFile);
        }
    }

    private void openOutputFile(String outputFile) throws InvalidFileException {
        try {
            this.outputFile = outputFile;
            outputStream = new FileOutputStream(outputFile);
        } catch (IOException ioexception) {
            throw new InvalidFileException(200, outputFile);
        }
    }

    public int nextChar() {
        int ascii = -1;
        try {
            ascii = bufferedInputStream.read();
        } catch (IOException ioexception) {
            try {
                throw new InvalidFileException(200, inputFile);
            } catch (InvalidFileException invalidFileException) {
                System.out.println(invalidFileException.getMessage());
            }
        }
        return ascii;
    }

//    public void mark() {
//        bufferedInputStream.mark(10);
//    }
//
//    public void reset() {
//        try {
//            bufferedInputStream.reset();
//        } catch (IOException ioe) {
//            System.out.println("Blad resetowania znacznika pliku wejsciowego.");
//        }
//    }

    public void closeFiles() {
        try {
            closeInputFile();
            closeOutputFile();
        } catch (InvalidFileException invalidFileException) {
            System.out.println(invalidFileException.getMessage());
        }
    }

    private void closeInputFile() throws InvalidFileException {
        try {
            if (bufferedInputStream != null) {
                bufferedInputStream.close();
            }
        } catch (IOException ioexception) {
            throw new InvalidFileException(300, inputFile);
        }
    }

    private void closeOutputFile() throws InvalidFileException {
        try {
            if (outputStream != null) {
                outputStream.close();
            }
        } catch (IOException ioexception) {
            throw new InvalidFileException(300, outputFile);
        }
    }
}
