package business.network;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import business.database.AppDatabase;
import business.database.dao.RepoDao;
import business.database.dao.UserDao;
import business.model.Repo;
import business.model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.AppExecutors;

public class NetworkAdapter {

    private static NetworkAdapter sInstance;
    public static GithubService githubService;
    private static AppExecutors executors;
    private MutableLiveData<User> userData;
    private Call<User> userCall;

    public NetworkAdapter(AppExecutors appExecutors) {
        githubService = githubService.retrofit.create(GithubService.class);
        userData = new MutableLiveData<>();
        executors = appExecutors;
    }

    public static NetworkAdapter getInstance(AppExecutors appExecutors) {
        if (sInstance == null) {
            synchronized (NetworkAdapter.class) {
                if (sInstance == null) {
                    sInstance = new NetworkAdapter(appExecutors);
                }
            }
        }
        return sInstance;
    }

    public Callback<User> fetchUserData(UserDao userDao, MutableLiveData<User> userMutableLiveData) {
        Callback<User> fetchUser = new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                executors.networkIO().execute(() -> {
                    if (response.isSuccessful()) {
                        User user = response.body();
                        userDao.addUser(user);
                        userMutableLiveData.postValue(user);
                    }
                });
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.v("TEST", "FAILED TO RETRIEVE API USER");
            }
        };
        return fetchUser;
    }

    public Callback<Repo> fetchRepoData(RepoDao repoDao, MutableLiveData<Repo> repoMutableLiveData){
        Callback<Repo> fetchRepo = new Callback<Repo>() {
            @Override
            public void onResponse(Call<Repo> call, Response<Repo> response) {
                executors.networkIO().execute(() -> {
                    if(response.isSuccessful()){
                        Repo repo = response.body();
                        repoDao.insertRepo(repo);
                        repoMutableLiveData.postValue(repo);
                    }
                });
            }

            @Override
            public void onFailure(Call<Repo> call, Throwable t) {
                Log.v("TEST", "FAILED TO RETRIEVE API USER");
            }
        };
        return fetchRepo;
    }
}