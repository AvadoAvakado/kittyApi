package api.kittymodels;

import lombok.Getter;

import java.util.Map;
import java.util.function.Consumer;

@Getter
public class FavoriteInfo {
    private String id;
    private String user_id;
    private String image_id;
    private String sub_id;
    private String created_at;
    private Map<String, String> image;

    public void setImageId(String imageId) {
        image_id = imageId;
    }

    public void setSubId(String subId) {
        sub_id = subId;
    }

    public FavoriteInfo(Consumer<FavoriteInfo> consumer) {
        consumer.accept(this);
    }
}
