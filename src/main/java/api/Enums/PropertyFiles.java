package api.Enums;

public enum PropertyFiles {
    API_PROPERTIES("api.properties");

    String path;

    PropertyFiles(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
