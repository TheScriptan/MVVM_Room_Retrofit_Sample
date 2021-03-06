package business.network;

import business.model.Repo;
import business.model.User;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GithubService  {

    @GET("users/{username}")
    Call<User> fetchUserData(
            @Path("username") String username);

    @GET("repos/{owner}/{repo}")
    Call<Repo> fetchRepoData(
            @Path("owner") String username,
            @Path("repo") String repo
    );

    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.github.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
