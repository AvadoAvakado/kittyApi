package api.enums;

import java.io.File;

public enum PropertyFiles {
    API_PROPERTIES("api.properties"),
    USER_PROPERTIES("user.properties");

    String path;

    PropertyFiles(String path) {
        String mainResources = String.format("%s%ssrc%smain%sresources%s", System.getProperty("user.dir"),
                File.separatorChar, File.separatorChar, File.separatorChar, File.separatorChar);
        this.path = mainResources + File.separatorChar + path;
    }

    public String getPath() {
        return path;
    }
}
