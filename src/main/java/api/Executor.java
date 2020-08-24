package api;

import api.Enums.PropertyFiles;
import api.exceptions.InvalidUserIdentifierException;
import api.exceptions.NotSpecifiedUserIdentifierException;
import api.kittymodels.FavoriteInfo;
import api.kittymodels.Kitty;
import api.kittymodels.VoteInfo;
import api.requests.managers.KittyRequests;
import api.utils.PropertiesUtil;
import api.utils.UserPropertiesUtil;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.sun.scenario.effect.GaussianShadow;
import lombok.extern.log4j.Log4j;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Log4j
public class Executor {
    /*private static final String authenticationKey = "872b5e2a-9ed4-48ae-a6e5-a308812e119d";
    final static Gson gson = new Gson();
    final static String kittyUrl = "https://api.thecatapi.com/v1/images/search";
    private final static String allBreedsUrl = "https://api.thecatapi.com/v1/breeds";
    private static final OkHttpClient client = new OkHttpClient();
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public static JsonElement getRandomKittyNew() throws IOException {
        Request request = new Request.Builder().url(kittyUrl)
                .addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)")
                .addHeader("'x-api-key", authenticationKey)
                .get().build();
        Response response = client.newCall(request).execute();
        return gson.fromJson(response.body().string(), JsonElement.class);
    }

    public static boolean isJsonArray(final String jsonString) {
        return (jsonString.startsWith("[") && jsonString.endsWith("]"));
    }

    public String getKittyImageUrl(final JsonArray kittyJsonObject) {
        return kittyJsonObject.get(0)
                .getAsJsonObject()
                .get("url")
                .getAsString();
    }

    public String getKittyImageId(final JsonArray kittyJsonObject) {
        return kittyJsonObject.get(0)
                .getAsJsonObject()
                .get("id")
                .getAsString();
    }

    public JsonElement getRandomKitty(){
        BufferedReader rd = null;
        final StringBuilder responseString = new StringBuilder();
        JsonElement responseJsonObject = null;
        try {
            URL url = new URL(kittyUrl);
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            rd = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                responseString.append(line);
            }
            requireNonArray(responseString.toString());
            responseJsonObject = gson.fromJson(responseString.toString(), JsonElement.class);
        } catch (IOException e) {
            log.warn(String.format("Exception while getting random kitty!\n%s", Arrays.toString(e.getStackTrace())));
        } finally {
            if (rd != null) {
                try {
                    rd.close();
                } catch (IOException e) {
                    log.warn(String.format("Buffered reader is not closed!\n%s", Arrays.toString(e.getStackTrace())));
                }

            }
        }
       return responseJsonObject;
    }

    public static void requireNonArray(final String responseString) {
        if (isJsonArray(responseString)) {
            log.info(String.format("Be aware, next json object is array:\n%s", responseString));
        }
    }

    private static void requireNonNull(final Object object, final String message) {
        if (object == null) {
            log.warn("Element can't be null");
            throw new NullPointerException(String.format("Element can't be null\n%s", message == null ? "" : message));
        }
    }

    private static void requireNonNull(final Object object) {
        requireNonNull(object, null);
    }

    public static void saveImage(String imageUrl, String destinationFile) throws IOException {
        URL url = new URL(imageUrl);
        URLConnection uc = url.openConnection();
        uc.addRequestProperty("User-Agent",
                "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
        InputStream is = uc.getInputStream();//rl.openStream();
        OutputStream os = new FileOutputStream(destinationFile);
        byte[] b = new byte[2048];
        int length;
        while ((length = is.read(b)) != -1) {
            os.write(b, 0, length);
        }
        is.close();
        os.close();
    }

    public static void main(String[] args) throws IOException {
        JsonElement breedInfo = getBreedInfo("mcoo");
        String a1 = "aa";
    }

    private static HttpUrl buildUrl(final String url) throws IOException {
        final HttpUrl.Builder searchUrlWithParams = Objects.requireNonNull(HttpUrl.parse(url)).newBuilder();

    }

    public static JsonElement getBreedInfo(final String breedId) throws IOException {
        HttpUrl.Builder searchUrlWithParams = Objects.requireNonNull(HttpUrl.parse(kittyUrl)).newBuilder();
        searchUrlWithParams.addQueryParameter("breed_ids", breedId);
        searchUrlWithParams.addQueryParameter("limit", "3");
        searchUrlWithParams.addQueryParameter("page", "20");
        searchUrlWithParams.addQueryParameter("order", "DESC");
        Request request = new Request.Builder().url(searchUrlWithParams.build()/*kittyUrl + String.format("?breed_ids=%s", breedId))*/
                /*.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)")
                .addHeader("'x-api-key", authenticationKey)
                //.addHeader("breed_ids", breedId)
                .get().build();
        String string = request.toString();
        //Response response = client.newCall(request).execute();
        Response response = client.newCall(request).execute();
        return gson.fromJson(Objects.requireNonNull(response.body()).string(), JsonElement.class);
    }

    public static JsonElement getAllBreeds() throws IOException {
        Request request = new Request.Builder().url(allBreedsUrl)
                .addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)")
                .addHeader("'x-api-key", authenticationKey)
                .get().build();
        Response response = client.newCall(request).execute();
        return gson.fromJson(Objects.requireNonNull(response.body()).string(), JsonElement.class);
    }

    public static void main1(String[] args) throws IOException{
        api.Executor instance = new api.Executor();
        Consumer<Supplier<JsonArray>> pattern = (supplier) -> {
            BasicConfigurator.configure();
            //DELETE
            //JsonElement kek = getRandomKitty("asd");
            //DELETE
            //final JsonArray randomKittyObject = (JsonArray) instance.getRandomKitty();
            final JsonArray randomKittyObject = supplier.get();
            final String randomKittyImageUrl = instance.getKittyImageUrl(randomKittyObject);
            Date date = new Date(System.currentTimeMillis());
            DateFormat formatter = new SimpleDateFormat("HH-mm-ss-SSS");
            formatter.setTimeZone(TimeZone.getTimeZone("UTC+02:00"));
            String dateFormatted = formatter.format(date);
            try {
                saveImage(randomKittyImageUrl, String.format("%s%sKittyNum%s.jpg", "kittyPictures",
                        File.separator, instance.getKittyImageId(randomKittyObject)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        };

        BiFunction<Long, Long, Long> getDurationMs = (timeBefore, timeAfter) -> timeAfter - timeBefore;

        Function<Long, String> getDurationFormatted = (timeMs) -> {
            return String.format("Seconds: %s, Milliseconds: %s", timeMs/1_000, (timeMs - timeMs/1_000*1_000));
        };

        Runnable oldMethod = () -> pattern.accept(() -> (JsonArray) instance.getRandomKitty());
        Runnable newMethod = () -> pattern.accept(() -> {
            JsonArray result = null;
            try {
               result = (JsonArray) getRandomKittyNew();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        });
        long[] timeOldSum = {0};
        long[] timeNewSum = {0};
        IntStream.range(0, 20).forEach(i -> {
            long timeBeforeOldMethod = System.currentTimeMillis();
            oldMethod.run();
            long timeAfterOldMethod = System.currentTimeMillis();
            timeOldSum[0] += timeAfterOldMethod - timeBeforeOldMethod;
        });

        IntStream.range(0, 20).forEach(i -> {
            long timeBeforeNewMethod = System.currentTimeMillis();
            newMethod.run();
            long timeAfterNewMethod = System.currentTimeMillis();
            timeNewSum[0] += timeAfterNewMethod - timeBeforeNewMethod;
        });

        System.out.println(String.format("OLD: %s", getDurationFormatted.apply(timeOldSum[0])));
        System.out.println(String.format("NEW: %s", getDurationFormatted.apply(timeNewSum[0])));


        /*long timeBeforeNewMethod = System.currentTimeMillis();
        newMethod.run();
        long timeAfterNewMethod = System.currentTimeMillis();*/







        /*BasicConfigurator.configure();
        api.Executor instance = new api.Executor();
        //DELETE
        JsonElement kek = getRandomKitty("asd");
        //DELETE
        final JsonArray randomKittyObject = (JsonArray) instance.getRandomKitty();
        //final JsonArray randomKittyObject = (JsonArray) getRandomKitty("asd");;
        final String randomKittyImageUrl = instance.getKittyImageUrl(randomKittyObject);
        Date date = new Date(System.currentTimeMillis());
        DateFormat formatter = new SimpleDateFormat("HH-mm-ss-SSS");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC+02:00"));
        String dateFormatted = formatter.format(date);
        saveImage(randomKittyImageUrl, String.format("KittyNum%s.jpg", instance.getKittyImageId(randomKittyObject)));*/
    //}

    public static void main(String[] args) throws NotSpecifiedUserIdentifierException, IOException, InvalidUserIdentifierException {
        KittyRequests kittyRequests = new KittyRequests();
       //FileUtils.saveFileFromUrl(new Gson().fromJson(kittyRequests.getRandomKitty(), Kitty.class).getUrl(), "aaa.jpg");
        //List<BreedInfo> breedInfos = kittyRequests.getAllBreeds();
       // JsonArray randomKitties = kittyRequests.getListOfRandomKitties(2);
        //JsonElement rk = kittyRequests.getRandomKitty();
        //kittyRequests.upVote("ckb");
        //new UserPropertiesUtil().setSubId("Hello _ dudes");
        //String aa = new UserPropertiesUtil().getSubId();
        List<VoteInfo> voteInfos = kittyRequests.getVotesList();
        //kittyRequests.upVote(kittyRequests.getRandomKitty().getUrl());
        //DELETE
        voteInfos.forEach(voteInfo -> {
            String aaa = voteInfo.getSub_id();
            boolean a2 = voteInfo.getSub_id() != null;
        });
        //DELETE

        List<VoteInfo> filtered = voteInfos.stream().filter(voteInfo -> voteInfo.getSub_id() != null).collect(Collectors.toList());
        kittyRequests.deleteVote(filtered.get(0).getId());
        filtered = voteInfos.stream().filter(voteInfo -> voteInfo.getSub_id() != null).collect(Collectors.toList());

        kittyRequests.deleteVote(voteInfos.get(0).getId());
        voteInfos = kittyRequests.getVotesList();
        VoteInfo vvv = voteInfos.stream().filter(voteInfo -> voteInfo.getImage_id().equals("asf2")).findFirst().get();
        kittyRequests.deleteVote(vvv.getId());
        voteInfos = kittyRequests.getVotesList();
        //JsonArray check = kittyRequests.getFavorites();


        List<VoteInfo> kittiesWithHats = kittyRequests.getVotesList();
        VoteInfo statusTwo = kittiesWithHats.stream().filter(voteInfo -> voteInfo.getImage_id().equals("ckb")).findFirst().get();
        kittyRequests.deleteVote(statusTwo.getId());
        List<VoteInfo> afterDeleting = kittyRequests.getVotesList();
        //JsonElement list = kittyRequests.getKittiesByCategory(2, Categories.HATS, Categories.SUNGLASSES);
        String just = "aa";

        //List<String> kittiesLinks = StreamSupport.stream(randomKitties.spliterator(), true)
        //        .map(kittyRequests::getKittyImageUrl).collect(Collectors.toList());
        //kittiesLinks.forEach((randomKitty) -> {
        //    System.out.println("KEKEK = " + randomKitty);
        //});
       // JsonElement firstBreedPureJson = breeds.get(0);
        //BreedInfoModel firsBreedFormatted = new Gson().fromJson(firstBreedPureJson, BreedInfoModel.class);
    }
}
