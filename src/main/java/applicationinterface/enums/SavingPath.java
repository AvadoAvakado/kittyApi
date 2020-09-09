package applicationinterface.enums;

import java.io.File;

public enum SavingPath {
    RANDOM_KITTY(String.format("%s%skittyPictures%srandomkitty%s",
            System.getProperty("user.dir"), File.separator, File.separator, File.separator));

    private final String path;

    public String getPath() {
        return path;
    }

    SavingPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return getPath();
    }
}
