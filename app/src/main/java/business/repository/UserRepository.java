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
        return userMutableLiveData;
    }

    public LiveData<List<User>> getUsers(){
        return userDao.getUsers();
    }

    public void refreshUser(String username){
        executors.diskIO().execute(() -> {
            boolean userExists = userDao.hasUser(username);
            Log.v("TEST", "User: " + userExists);
            if(!userExists){
                networkAdapter.githubService.fetchUserData(username).enqueue(networkAdapter.fetchUserData(userDao, userMutableLiveData, username));
            } else {
                userMutableLiveData.postValue(userDao.getUserByName(username));
            }
        });
    }
}
