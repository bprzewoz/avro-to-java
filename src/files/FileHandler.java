package files;

import exceptions.InvalidFileException;

import java.io.*;
import java.nio.charset.Charset;

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
        this.inputFile = inputFile;
        this.outputFile = outputFile;
        try {
            openInputFile(inputFile);
            openOutputFile(outputFile);
        } catch (InvalidFileException invalidFileException) {
            System.out.println(invalidFileException.getMessage());
        }
    }

    private void openInputFile(String inputFile) throws InvalidFileException {
        try {
            inputStream = new FileInputStream(inputFile);
            bufferedInputStream = new BufferedInputStream(inputStream);
        } catch (IOException ioexception) {
            throw new InvalidFileException(100, inputFile);
        }
    }

    private void openOutputFile(String outputFile) throws InvalidFileException {
        try {
            File file = new File(outputFile);
            if (!file.exists()) {
                file.createNewFile();
            }
            outputStream = new FileOutputStream(file);
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

    public void write(String string) {
        try {
            outputStream.write(string.getBytes(Charset.forName("ASCII")));
        } catch (IOException ioexception) {
            try {
                throw new InvalidFileException(200, inputFile);
            } catch (InvalidFileException invalidFileException) {
                System.out.println(invalidFileException.getMessage());
            }
        }
    }

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
