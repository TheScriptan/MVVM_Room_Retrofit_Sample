package business.repository;

import android.os.AsyncTask;
import android.util.Log;

import java.util.List;
import java.util.concurrent.Executor;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import business.database.dao.UserDao;
import business.model.User;
import business.network.NetworkAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.AppExecutors;

public class UserRepository {

    private static UserRepository sInstance;
    private final UserDao userDao;
    private final NetworkAdapter networkAdapter;
    private final AppExecutors executors;

    private MutableLiveData<User> userMutableLiveData;

    public UserRepository(UserDao userDao, NetworkAdapter networkAdapter, AppExecutors executors){
        this.userDao = userDao;
        this.networkAdapter = networkAdapter;
        this.executors = executors;
        userMutableLiveData = new MutableLiveData<>();
    }

    public static UserRepository getInstance(UserDao userDao, NetworkAdapter networkAdapter, AppExecutors executors){
        if(sInstance == null){
            synchronized (UserRepository.class){
                if(sInstance == null){
                    sInstance = new UserRepository(userDao, networkAdapter, executors);
                }
            }
        }
        return sInstance;
    }

    public LiveData<User> getUserData(String username){
        refreshUser(username);
        executors.diskIO().execute(() -> {
            userMutableLiveData.postValue(userDao.getUserByName(username));
        });
        return userMutableLiveData;
    }

    public LiveData<List<User>> getUsers(){
        executors.diskIO().execute(() -> {

        });
        return userDao.getUsers();
    }

    public void refreshUser(String username){
        executors.diskIO().execute(() -> {
            boolean userExists = userDao.hasUser(username);
            Log.v("TEST", "User: " + userExists);
            if(!userExists){
                //networkAdapter.fetchUserData(username);
                //userDao.addUser(userNetworkData.getValue());
                networkAdapter.githubService.fetchUserData(username).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                            executors.diskIO().execute(() ->{
                                User user = response.body();
                                userDao.addUser(user);
                                Log.v("TEST", "DATA REFRESHED FROM NETWORK " + user.getBio());

                            });

                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {

                    }
                });
            }
        });
    }
}
