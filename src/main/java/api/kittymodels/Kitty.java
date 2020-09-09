package api.kittymodels;

import lombok.Getter;

import java.util.List;

/**
 * Describes the response body of kitty search request result
 */
@Getter
public class Kitty {
    private List<Categories> breeds;
    private String id;
    private String url;
    private String width;
    private String height;

    @Getter
    public class Categories {
        private String id;
        private String name;
    }
}
