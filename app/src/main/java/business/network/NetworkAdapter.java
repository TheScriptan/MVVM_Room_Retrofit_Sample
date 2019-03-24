package business.network;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import business.model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NetworkAdapter {

    private static NetworkAdapter sInstance;
    private static GithubService githubService;
    private MutableLiveData<User> userData;
    private Call<User> userCall;

    public NetworkAdapter(){
        githubService = githubService.retrofit.create(GithubService.class);
        userData = new MutableLiveData<>();
    }

    public static NetworkAdapter getInstance(){
        if(sInstance == null){
            synchronized (NetworkAdapter.class){
                if(sInstance == null){
                    sInstance = new NetworkAdapter();
                }
            }
        }
        return sInstance;
    }

    public LiveData<User> getUserData(){
        return userData;
    }

    public LiveData<User> fetchUserData(String username){
        boolean status;
        userCall = githubService.fetchUserData(username);
        userCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                userData.postValue(response.body());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("ERROR", "Failed to GET user from API");
            }
        });
        return userData;
    }
}
