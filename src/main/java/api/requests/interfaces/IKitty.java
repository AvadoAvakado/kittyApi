package api.requests.interfaces;

import com.google.gson.JsonElement;
import api.kittymodels.BreedInfo;

import java.io.IOException;
import java.util.List;

public interface IKitty {

    JsonElement getRandomKitty() throws IOException;

    List<BreedInfo> getAllBreeds();
}
