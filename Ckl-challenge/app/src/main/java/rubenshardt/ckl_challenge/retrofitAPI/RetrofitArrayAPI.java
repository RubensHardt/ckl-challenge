package rubenshardt.ckl_challenge.retrofitAPI;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;

/**
 * Created by rubenshardtjunior on 11/29/16.
 */

public interface RetrofitArrayAPI {

    //Retrofit get annotation with our URL and the method that will return the articles list.
    @GET("challenge")
    Call<List<APIArticle>> getArticleDetails();

}