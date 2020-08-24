package api.requests.managers;

import api.Enums.Categories;
import api.exceptions.InvalidUserIdentifierException;
import api.exceptions.NotSpecifiedUserIdentifierException;
import api.kittymodels.FavoriteInfo;
import api.kittymodels.Kitty;
import api.kittymodels.VoteInfo;
import api.requests.interfaces.IKitty;
import api.utils.UserPropertiesUtil;
import api.kittymodels.BreedInfo;
import okhttp3.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class KittyRequests extends RequestManager implements IKitty {
    private final UserPropertiesUtil userPropertiesUtil = new UserPropertiesUtil();
    private final String key = propertiesUtil.getValueByKey("kittyAuthenticationKey");

    public KittyRequests() {
        super(KittyRequests.class);
    }

    @Override
    public Request.Builder addDefaultHeaders(Request.Builder builder) {
        return builder.addHeader("x-api-key", key)
                .addHeader("User-Agent", userAgent);
    }

    /**
     * Method calls @getListOfRandomKitties(int) with "1" as parameter
     * @return First element from result of @getListOfRandomKitties(int)
     */
    @Override
    public Kitty getRandomKitty() {
        return getListOfRandomKitties(1).get(0);
    }

    /**
     * Method executes request to get JsonArray filled with random kitties
     * @param numberOfKitties number of desired random kitties
     * @return
     */
    @Override
    public List<Kitty> getListOfRandomKitties (int numberOfKitties) {
        HttpUrl urlWithQueryParameter = Objects.requireNonNull(HttpUrl.parse(propertiesUtil.getValueByKey("kittySearchUrl")))
                .newBuilder().addQueryParameter("limit", String.valueOf(numberOfKitties)).build();
        Request request = getBuilderWithDefaultHeaders(urlWithQueryParameter).build();
        return StreamSupport.stream(requireJsonArray(getResponseBody(executeRequest(request))).spliterator(), true)
                .map(kitty -> gson.fromJson(kitty, Kitty.class)).collect(Collectors.toList());
    }

    /**
     *
     * @param numberOfKitties
     * @param category
     * @return List of kitties, length of @numberOfKitties and each kitty correspond to @category
     */
    @Override
    public List<Kitty> getKittiesByCategory(int numberOfKitties, Categories category) {
        HttpUrl urlWithQueryParameter = Objects.requireNonNull(HttpUrl.parse(propertiesUtil.getValueByKey("kittySearchUrl")))
                .newBuilder().addQueryParameter("limit", String.valueOf(numberOfKitties))
                .addQueryParameter("category_ids", String.valueOf(category.getId()))
                .build();
        Request request = getBuilderWithDefaultHeaders(urlWithQueryParameter).build();
        return StreamSupport.stream(requireJsonArray(getResponseBody(executeRequest(request))).spliterator(), true)
                .map(kitty -> gson.fromJson(kitty, Kitty.class)).collect(Collectors.toList());
    }

    @Override
    public List<BreedInfo> getAllBreeds() {
        Request request = getBuilderWithDefaultHeaders(propertiesUtil.getValueByKey("kittyBreedsListUrl"))
                .get().build();
        Response response = executeRequest(request);
        return StreamSupport.stream(getResponseBody(response).getAsJsonArray().spliterator(), true)
                .map(breed -> gson.fromJson(breed, BreedInfo.class)).collect(Collectors.toList());
    }

    @Override
    public BreedInfo getBreedInfo(String breedId) {
        HttpUrl urlWithQueryParameter = Objects.requireNonNull(HttpUrl.parse(propertiesUtil.getValueByKey("kittySearchUrl")))
                .newBuilder().addQueryParameter("breed_id", breedId)
                .build();
        Request request = getBuilderWithDefaultHeaders(urlWithQueryParameter).build();
        return gson.fromJson(requireJsonArray(getResponseBody(executeRequest(request))).get(0), BreedInfo.class);
    }

    @Override
    public List<VoteInfo> getVotesList() {
        HttpUrl urlVotesList = Objects.requireNonNull(HttpUrl.parse(propertiesUtil.getValueByKey("kittyVotesUrl")));
        Request request = getBuilderWithDefaultHeaders(urlVotesList).build();
        return StreamSupport.stream(requireJsonArray(getResponseBody(executeRequest(request))).spliterator(), true)
                .map(vote -> gson.fromJson(vote, VoteInfo.class)).collect(Collectors.toList());
    }

    @Override
    public List<VoteInfo> getVotesList(String subId) {
        HttpUrl urlVotesList = Objects.requireNonNull(HttpUrl.parse(propertiesUtil.getValueByKey("kittyVotesUrl")))
                .newBuilder().addQueryParameter("sub_id", subId).build();
        Request request = getBuilderWithDefaultHeaders(urlVotesList).build();
        return StreamSupport.stream(requireJsonArray(getResponseBody(executeRequest(request))).spliterator(), true)
                .map(vote -> gson.fromJson(vote, VoteInfo.class)).collect(Collectors.toList());
    }

    /**
     * WARNING - DOES NOT WORK. response status 200, message "SUCCESSFUL" but vote not deletes
     * Votes deleting only in case sub_id was specified at creation of vote
     * @param voteId
     */
    @Override
    public void deleteVote(int voteId) {
        HttpUrl voteUrl = Objects.requireNonNull(HttpUrl.parse(String.format("%s/%s",
                propertiesUtil.getValueByKey("kittyVotesUrl"), voteId)));
        Request request = getBuilderWithDefaultHeaders(voteUrl).delete().build();
        executeRequest(request);
    }

    @Override
    public void upVote(String imageId) throws InvalidUserIdentifierException, NotSpecifiedUserIdentifierException {
        makeVote(imageId, 1, userPropertiesUtil.getSubId());
    }

    @Override
    public void downVote(String imageId) throws InvalidUserIdentifierException, NotSpecifiedUserIdentifierException {
        makeVote(imageId, 0, userPropertiesUtil.getSubId());
    }

    /**
     *
     * @param imageId
     * @param voteValue vote value should be 1 or 0
     */
    private void makeVote(String imageId, int voteValue, String subId) throws InvalidUserIdentifierException {
        if (voteValue < 0 || voteValue > 1) {
            logger.warn(String.format("Unaccepted value. Expecting 1 or 2, but current is: %s", voteValue));
        } else if (subId == null || subId.isEmpty()) {
            throw new InvalidUserIdentifierException();
        }
        VoteInfo vote = new VoteInfo(voteInfo -> {
            voteInfo.setImageId(imageId);
            voteInfo.setValue(voteValue);
            voteInfo.setSubId(subId);
        });
        HttpUrl urlVotesList = Objects.requireNonNull(HttpUrl.parse(propertiesUtil.getValueByKey("kittyVotesUrl")));
        RequestBody requestBody = RequestBody.create(JSON, gson.toJson(vote));
        Request request = getBuilderWithDefaultHeaders(urlVotesList).post(requestBody).build();
        executeRequest(request);
    }

    @Override
    public void addFavourite(String imageId) {
        HttpUrl urlFavourites = Objects.requireNonNull(HttpUrl.parse(propertiesUtil.getValueByKey("kittyFavouritesUrl")));
        String body = gson.toJson(new FavoriteInfo(favoriteInfo -> favoriteInfo.setImageId(imageId)));
        RequestBody requestBody = RequestBody.create(JSON, body);
        Request request = getBuilderWithDefaultHeaders(urlFavourites).post(requestBody).build();
        executeRequest(request);
    }

    @Override
    public List<FavoriteInfo> getFavourites() {
        HttpUrl urlFavouritesList = Objects.requireNonNull(HttpUrl.parse(propertiesUtil.getValueByKey("kittyFavouritesUrl")));
        Request request = getBuilderWithDefaultHeaders(urlFavouritesList).get().build();
        return StreamSupport.stream(requireJsonArray(getResponseBody(executeRequest(request))).spliterator(), true)
                .map(favoriteItem -> gson.fromJson(favoriteItem, FavoriteInfo.class)).collect(Collectors.toList());
    }

    @Override
    public FavoriteInfo getFavourite(String favouriteId) {
        HttpUrl urlFavourite = Objects.requireNonNull(HttpUrl.parse(String.format("%s/%s",
                propertiesUtil.getValueByKey("kittyFavouritesUrl"), favouriteId)));
        Request request = getBuilderWithDefaultHeaders(urlFavourite).get().build();
        return gson.fromJson(getResponseBody(executeRequest(request)), FavoriteInfo.class);
    }

    @Override
    public void deleteFavourite(String favouriteId) {
        HttpUrl urlFavourite = Objects.requireNonNull(HttpUrl.parse(String.format("%s/%s",
                propertiesUtil.getValueByKey("kittyFavouritesUrl"), favouriteId)));
        Request request = getBuilderWithDefaultHeaders(urlFavourite).delete().build();
        executeRequest(request);
    }
}
