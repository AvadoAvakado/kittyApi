package api.kittymodels;

import lombok.Getter;
import lombok.Setter;

import java.util.function.Consumer;

@Getter
public class VoteInfo {
    private int id;
    private String image_id;
    private String sub_id;
    private String created_at;
    //available values are 1 and 0 only
    private int value;
    private String country_code;

    public void setImageId(final String imageId) {
        image_id = imageId;
    }

    public void setValue(final int value) {
        this.value = value;
    }

    public void setSubId(String subId) {
        sub_id = subId;
    }

    public VoteInfo(Consumer<VoteInfo> consumer) {
        consumer.accept(this);
    }
}
