package logic.services;

import java.io.*;

public final class PoliceService {
    private static RandomAccessFile file;
    private static final PoliceService ourInstance = new PoliceService();

    public static PoliceService getInstance() {
        return ourInstance;
    }

    private PoliceService() {

        try {
            String currentDirectory = new File(".").getCanonicalPath();
            file = new RandomAccessFile(currentDirectory + java.nio.file.FileSystems.getDefault().getSeparator() + "src" + java.nio.file.FileSystems.getDefault().getSeparator() +
                    "files" + java.nio.file.FileSystems.getDefault().getSeparator()+"RuleViolationRegistry.dat", "rw");
        } catch (IOException ex) {

        }
    }

    public static RandomAccessFile getRuleViolationFile() {
        return file;
    }
}
