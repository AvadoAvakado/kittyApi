package api.requests.managers;

import api.Enums.PropertyFiles;
import api.requests.interfaces.IKitty;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import api.kittymodels.BreedInfo;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import api.utils.PropertiesUtil;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class KittyRequests implements IKitty {
    private static final Logger logger = LogManager.getLogger(KittyRequests.class.getName());
    private final static Gson gson = new Gson();
    private final PropertiesUtil propertiesUtil = new PropertiesUtil(PropertyFiles.API_PROPERTIES);
    private final OkHttpClient client = new OkHttpClient();

    private final String userAgent = propertiesUtil.getValueByKey("userAgentMozilla");
    private final String key = propertiesUtil.getValueByKey("kittyAuthenticationKey");

    private Request.Builder getBuilderWithDefaultHeaders(final String url) {
        return addDefaultHeaders(new Request.Builder().url(url));
    }

    private Request.Builder getBuilderWithDefaultHeaders(final HttpUrl httpUrl) {
        return addDefaultHeaders(new Request.Builder().url(httpUrl));
    }

    private Request.Builder addDefaultHeaders(Request.Builder builder) {
        return builder.addHeader("User-Agent", userAgent)
                .addHeader("'x-api-key", key);
    }

    /**
     * Method executes request from parameter and returns it's response
     * @param request
     * @return
     */
    private Response executeRequest(final Request request) {
        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            logger.warn(String.format("There is an error in executing request\n%s",
                    Arrays.toString(e.getStackTrace())));
        }
        return response;
    }

    /**
     * Method returns response's body
     * @param response
     * @return
     */
    private JsonElement getResponseBody(Response response) {
        JsonElement body = null;
        try {
            body = gson.fromJson(Objects.requireNonNull(response.body()).string(), JsonElement.class);
        } catch (IOException e) {
            logger.warn(String.format("There is an error in getting response's body\n%s",
                    Arrays.toString(e.getStackTrace())));
        }
        return body;
    }

    private JsonArray requireJsonArray(final JsonElement jsonElement) {
        if (jsonElement instanceof JsonArray) {
            return jsonElement.getAsJsonArray();
        } else {
            throw new IllegalStateException(String.format("%s should be JsonArray", jsonElement.toString()));
        }
    }

    /**
     * Method calls @getListOfRandomKitties(int) with "1" as parameter
     * @return First element from result of @getListOfRandomKitties(int)
     */
    @Override
    public JsonElement getRandomKitty() {
        return requireJsonArray(getListOfRandomKitties(1)).get(0);
    }

    /**
     * Method executes request to get JsonArray filled with random kitties
     * @param numberOfKitties number of desired random kitties
     * @return
     */
    public JsonArray getListOfRandomKitties(int numberOfKitties) {
        HttpUrl urlWithQueryParameter = Objects.requireNonNull(HttpUrl.parse(propertiesUtil.getValueByKey("kittySearchUrl")))
                .newBuilder().addQueryParameter("limit", String.valueOf(numberOfKitties)).build();
        Request request = getBuilderWithDefaultHeaders(urlWithQueryParameter).build();
        return requireJsonArray(getResponseBody(executeRequest(request)));
    }

    /**
     * Method finds kitty image url in @kittyJsonOElement and returns it
     * @param kittyJsonElement
     * @return
     */
    @Override
    public String getKittyImageUrl(JsonElement kittyJsonElement) {
        return kittyJsonElement.getAsJsonObject()
                .get("url")
                .getAsString();
    }

    /**
     * Method finds kitty image id in @kittyJsonObject and returns it
     * @param kittyJsonElement
     * @return
     */
    @Override
    public String getKittyImageId(JsonElement kittyJsonElement) {
        return kittyJsonElement.getAsJsonObject()
                .get("id")
                .getAsString();
    }

    /**
     *
     * @return
     */
    @Override
    public List<BreedInfo> getAllBreeds() {
        Request request = getBuilderWithDefaultHeaders(propertiesUtil.getValueByKey("kittyBreedsListUrl"))
                .get().build();
        Response response = executeRequest(request);
        return StreamSupport.stream(getResponseBody(response).getAsJsonArray().spliterator(), true)
                .map(breed -> gson.fromJson(breed, BreedInfo.class)).collect(Collectors.toList());
    }
}
