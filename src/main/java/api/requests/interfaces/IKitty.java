package api.requests.interfaces;

import com.google.gson.JsonElement;
import api.kittymodels.BreedInfo;

import java.io.IOException;
import java.util.List;

public interface IKitty {

    JsonElement getRandomKitty() throws IOException;

    String getKittyImageUrl(JsonElement kittyJsonObject);

    String getKittyImageId(JsonElement kittyJsonObject);

    List<BreedInfo> getAllBreeds();
}
