package api.Enums;

public enum Categories {
    BOXES(5),
    CLOTHES(15),
    HATS(1),
    SINKS(14),
    SPACE(2),
    SUNGLASSES(4),
    TIES(7);

    private int id;

    Categories(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
