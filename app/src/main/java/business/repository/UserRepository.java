package business.repository;

import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import business.database.AppDatabase;
import business.database.dao.RepoDao;
import business.database.dao.UserDao;
import business.model.Repo;
import business.model.User;
import business.network.NetworkAdapter;
import utils.AppExecutors;

public class UserRepository {

    private static UserRepository sInstance;
    private final UserDao userDao;
    private final RepoDao repoDao;
    private final NetworkAdapter networkAdapter;
    private final AppExecutors executors;

    private MutableLiveData<User> userMutableLiveData;
    private MutableLiveData<Repo> repoMutableLiveData;

    public UserRepository(AppDatabase db, NetworkAdapter networkAdapter, AppExecutors executors){
        this.userDao = db.userDao();
        this.repoDao = db.repoDao();
        this.networkAdapter = networkAdapter;
        this.executors = executors;

        userMutableLiveData = new MutableLiveData<>();
        repoMutableLiveData = new MutableLiveData<>();
    }

    public static UserRepository getInstance(AppDatabase db, NetworkAdapter networkAdapter, AppExecutors executors){
        if(sInstance == null){
            synchronized (UserRepository.class){
                if(sInstance == null){
                    sInstance = new UserRepository(db, networkAdapter, executors);
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

    private void refreshUser(String username){
        //Reduces additional get CALL. I know my coding sucks xd
        if(username.equals(""))

            return;
        executors.diskIO().execute(() -> {
            boolean userExists = userDao.hasUser(username);
            Log.v("TEST", "User: " + userExists);
            if(!userExists){
                networkAdapter.githubService.fetchUserData(username)
                        .enqueue(networkAdapter.fetchUserData(userDao, userMutableLiveData));
            } else {
                userMutableLiveData.postValue(userDao.getUserByName(username));
            }
        });
    }

    public LiveData<Repo> getRepoData(String username, String repoName){
        refreshRepo(username, repoName);
        return repoMutableLiveData;
    }

    public LiveData<List<Repo>> getRepos(){
        return repoDao.getRepos();
    }

    private void refreshRepo(String user, String repoName){
        executors.diskIO().execute(() -> {
            boolean repoExists = repoDao.hasRepo(repoName);
            if(!repoExists){
                networkAdapter.githubService.fetchRepoData(user, repoName)
                        .enqueue(networkAdapter.fetchRepoData(repoDao, repoMutableLiveData));
            } else {
                repoMutableLiveData.postValue(repoDao.getRepoByName(repoName));
            }
        });
    }
}
