package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class Photo implements Serializable {

    private transient FileInputStream photoStream;
    private String photoPath;
    public static final String DEFAULT_PHOTO_PATH = "C:\\Users\\PWIN\\Desktop\\Projekat\\GarageApplication\\com.artse.vehicles\\src\\util\\Photo.java";
    public static final Photo DEFAULT_PHOTO = new Photo();

    public FileInputStream getPhotoStream() {
        return photoStream;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public Photo() {
        this(DEFAULT_PHOTO_PATH);
    }

    public Photo( String path ) {
        photoPath = Files.exists(Paths.get(path)) && path.endsWith(".png") ? path : DEFAULT_PHOTO_PATH;

        try {
            photoStream = new FileInputStream(photoPath);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public Photo( Path path ) {

        this(path.toString());
    }
}


