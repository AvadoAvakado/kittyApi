package api.requests.interfaces;

import api.enums.Categories;
import api.exceptions.InvalidUserIdentifierException;
import api.exceptions.NotSpecifiedUserIdentifierException;
import api.kittymodels.FavoriteInfo;
import api.kittymodels.Kitty;
import api.kittymodels.VoteInfo;
import api.kittymodels.BreedInfo;

import java.util.List;

public interface IKitty {

    Kitty getRandomKitty();

    List<Kitty> getListOfRandomKitties (int numberOfKitties);

    List<Kitty> getKittiesByCategory(int numberOfKitties, Categories category);

    List<BreedInfo> getAllBreeds();

    BreedInfo getBreedInfo(String breedId);

    List<VoteInfo> getVotesList();

    List<VoteInfo> getVotesList(String subId);

    void deleteVote(int voteId);

    void upVote(String imageId) throws InvalidUserIdentifierException, NotSpecifiedUserIdentifierException;

    void downVote(String imageId) throws InvalidUserIdentifierException, NotSpecifiedUserIdentifierException;

    void addFavourite(String imageId);

    List<FavoriteInfo> getFavourites();

    FavoriteInfo getFavourite(String favouriteId);

    void deleteFavourite(String favouriteId);
}
