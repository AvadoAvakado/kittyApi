package api.requests.managers;

import api.enums.PropertyFiles;
import api.utils.PropertiesUtil;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import okhttp3.*;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Function;

public abstract class RequestManager {
    protected final Logger logger;
    protected final Gson gson;
    protected final PropertiesUtil propertiesUtil;
    protected final OkHttpClient client;

    protected final MediaType JSON;
    protected final String userAgent;

    protected <T extends RequestManager> RequestManager(Class<T> extender) {
        BasicConfigurator.configure();
        logger = LogManager.getLogger(extender);
        gson = new Gson();
        client = new OkHttpClient();
        JSON = MediaType.parse("application/json; charset=utf-8");
        propertiesUtil = new PropertiesUtil(PropertyFiles.API_PROPERTIES);
        userAgent = propertiesUtil.getValueByKey("userAgentMozilla");
    }

    protected Request.Builder getBuilderWithDefaultHeaders(final String url) {
        return addDefaultHeaders(new Request.Builder().url(url));
    }

    protected Request.Builder getBuilderWithDefaultHeaders(final HttpUrl httpUrl) {
        return addDefaultHeaders(new Request.Builder().url(httpUrl));
    }

    protected abstract Request.Builder addDefaultHeaders(Request.Builder builder);

    /**
     * Method executes request from parameter and returns it's response
     * ATTENTION: after this method executed, returned Response should be
     * closed by executing close() method OR passed as parameter to getResponseBody() method
     * (because there is string() call method in it and this method perform closing)
     * @param request
     * @return
     */
    protected Response executeRequest(final Request request) {
        Function<Response, String> getBodyHandlingException = (response) -> {
            String bodyAsString = null;
            try {
                bodyAsString = Objects.requireNonNull(response.body(), "Response body is null").string();
            } catch (IOException e) {
                logger.warn(String.format("Error in getting response body as string\n%s", Arrays.toString(e.getStackTrace())));
            }
            return bodyAsString;
        };

        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            logger.warn(String.format("There is an error in executing request\n%s",
                    Arrays.toString(e.getStackTrace())));
        }
        Objects.requireNonNull(response, "Response is null");
        if (!String.valueOf(response.code()).startsWith("20")) {
            throw new IllegalStateException(String.format("Response status code: %s\nMessage: %s", response.code(), getBodyHandlingException.apply(response)));
        }
        return response;
    }

    /**
     * Method returns response's body
     * @param response
     * @return
     */
    protected JsonElement getResponseBody(Response response) {
        JsonElement body = null;
        try {
            body = gson.fromJson(Objects.requireNonNull(response.body()).string(), JsonElement.class);
        } catch (IOException e) {
            logger.warn(String.format("There is an error in getting response's body\n%s",
                    Arrays.toString(e.getStackTrace())));
        }
        return body;
    }

    protected static JsonArray requireJsonArray(final JsonElement jsonElement) {
        if (jsonElement instanceof JsonArray) {
            return jsonElement.getAsJsonArray();
        } else {
            throw new IllegalStateException(String.format("%s should be JsonArray", jsonElement.toString()));
        }
    }
}
